package pl.wsb.java.flightapp.logic;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;
import pl.wsb.java.flightapp.FlightConfigurationProperties;
import pl.wsb.java.flightapp.model.*;
import pl.wsb.java.flightapp.model.projection.GroupReadModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Test
    @DisplayName("should throw IllegalStateException when configured to allow just 1 group and the other undone group exists")
    void createGroup_noMultipleGroupsConfing_And_undoneGroupExists_throwsIllegalStateException() {
        //given
        FlightGroupRepository mockGroupRepository = groupRepositoryReturning(true);
        //and
        var mockAirtpotrField = mock(FlightConfigurationProperties.AirportField.class);
        when(mockAirtpotrField.isAllowMultipleFlights()).thenReturn(false);
        //and
        var mockConfig = mock(FlightConfigurationProperties.class);
        when(mockConfig.getAirportField()).thenReturn(mockAirtpotrField);
        //system under test
        var toTest = new UserService(null, mockGroupRepository, mockConfig);
        //when
        var exception = catchThrowable(()->toTest.createGroup(LocalDateTime.now(),0));
        // then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("one undone group");

    }

    @Test
    @DisplayName("should throw IllegalArgumentException when configuration ok and no users for a given id")
    void createGroup_configurationOk_And_noProjects_throwsIllegalArgumentException() {
        //given
        var mockRepository = mock(UserRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //and
        FlightGroupRepository mockGroupRepository = groupRepositoryReturning(false);
        //and
        FlightConfigurationProperties mockConfig = configurationReturning(true);
        //system under test
        var toTest = new UserService(mockRepository, null, mockConfig);
        //when
        var exception = catchThrowable(()->toTest.createGroup(LocalDateTime.now(),0));
        // then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");

    }


    @Test
    @DisplayName("should throw IllegalArgumentException when configured to allow just 1 group and no groups and no users for a given id")
    void createGroup_noMultipleGroupsConfing_And_noUndoneGroupExists_throwsIllegalArgumentException() {
        //given
        var mockRepository = mock(UserRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //and
        FlightGroupRepository mockGroupRepository = groupRepositoryReturning(false);
        //and
        FlightConfigurationProperties mockConfig = configurationReturning(true);
        //system under test
        var toTest = new UserService(mockRepository, mockGroupRepository, mockConfig);
        //when
        var exception = catchThrowable(()->toTest.createGroup(LocalDateTime.now(),0));
        // then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");
    }

//    @Test
//    @DisplayName("should create new group from user")
//    void createGroup_configurationOk_existingUser_createsAndSaveGroup() {
//        //given
//        var today = LocalDate.now().atStartOfDay();
//        //and
//        var user = userWith("user", "root",Set.of("1","0"));
//        var mockRepository = mock(UserRepository.class);
//        when(mockRepository.findById(anyInt()))
//                .thenReturn(Optional.of(user));
//        //and
//        InMemoryGroupRepository inMemoryGroupRepo = inMemoryGroupRepository();
//        int countBeforeCall = inMemoryGroupRepo.count();
//        //and
//        FlightConfigurationProperties mockConfig = configurationReturning(true);
//        //system under test
//        var toTest = new UserService(mockRepository, inMemoryGroupRepo, mockConfig);
//        //when
//        GroupReadModel result = toTest.createGroup(today, 1);
//        //then
//       assertThat(result).hasFieldOrPropertyWithValue("login", "user");
//       assertThat(result).hasFieldOrPropertyWithValue("pas", "root");
//       assertThat(result.getFlights()).allMatch(details ->details.getDescription().equals("1"));
//       assertThat(result.getFlights()).allMatch(details ->details.getDescription().equals("0"));
//       assertThat(countBeforeCall+1).isEqualTo(inMemoryGroupRepo.count());
//    }
//    private User userWith(String login, String pas, Set<String> names){
//        Set<UserDetails> detail = names.stream()
//                .map(name -> {
//                    var details = mock(UserDetails.class);
//                    when(details.getFirstName()).thenReturn("1");
//                    when(details.getLastName()).thenReturn("0");
//                    return details;
//                }).collect(Collectors.toSet());
//        var result = mock(User.class);
//        when(result.getLogin()).thenReturn(login);
//        when(result.getPas()).thenReturn(pas);
//        return result;
//    }

    private FlightGroupRepository groupRepositoryReturning(final boolean result) {
        var mockGroupRepository = mock(FlightGroupRepository.class);
        when(mockGroupRepository.existsByDoneIsFalseAndUser_Id(anyInt())).thenReturn(result);
        return mockGroupRepository;
    }
    private FlightConfigurationProperties configurationReturning(final boolean result) {
        var mockAirtpotrField = mock(FlightConfigurationProperties.AirportField.class);
        when(mockAirtpotrField.isAllowMultipleFlights()).thenReturn(result);
        var mockConfig = mock(FlightConfigurationProperties.class);
        when(mockConfig.getAirportField()).thenReturn(mockAirtpotrField);
        return mockConfig;
    }
    private InMemoryGroupRepository inMemoryGroupRepository(){
                return new InMemoryGroupRepository();}

    private static class InMemoryGroupRepository implements FlightGroupRepository{
            private int index = 0;
            private Map<Integer, FlightGroup> map = new HashMap<>();

            public int count() {
                return map.values().size();
            }


            @Override
            public List<FlightGroup> findAll() {
                return new ArrayList<>(map.values());
            }

            @Override
            public Optional<FlightGroup> findById(Integer id) {
                return Optional.ofNullable(map.get(id));
            }

            @Override
            public FlightGroup save(FlightGroup entity) {
                if (entity.getId() == 0) {
                    try {
                        var field = FlightGroup.class.getDeclaredField("id");
                        field.setAccessible(true);
                        field.set(entity, ++index);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
                map.put(entity.getId(), entity);
                return entity;
            }

            @Override
            public boolean existsByDoneIsFalseAndUser_Id(Integer userId) {
                return map.values().stream()
                        .filter(group -> !group.isDone())
                        .anyMatch(group -> group.getUser() !=null && group.getUser().getId() == userId);
            }
        };

}


