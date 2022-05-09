package pl.wsb.java.flightapp.logic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.wsb.java.flightapp.model.FlightGroup;
import pl.wsb.java.flightapp.model.FlightGroupRepository;
import pl.wsb.java.flightapp.model.FlightRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FlightGroupServiceTest {
    @Test
    @DisplayName("should throw when undone flights")
    void toggleGroup_undoneFlights_throwsIllegalStateException() {
        //given
        FlightRepository mockFlightRepository = flightRepositoryReturning(true);
        //system under test
        var toTest = new FlightGroupService(null, mockFlightRepository);
        //when
        var exception = catchThrowable(() -> toTest.toggleGroup(1));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("undone flights");
    }


    @Test
    @DisplayName("should throw when no group")
    void toggleGroup_wrongId_throwsIllegalArgumentException() {
        //given
        FlightRepository mockFlightRepository = flightRepositoryReturning(false);
        //and
        var mockRepository= mock(FlightGroupRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //system under test
        var toTest = new FlightGroupService(mockRepository, mockFlightRepository);
        //when
        var exception = catchThrowable(() -> toTest.toggleGroup(1));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Flight group not found");

}    @Test
    @DisplayName("should toggle group")
    void toggleGroup_worksAsExpected() {
        //given
        FlightRepository mockFlightRepository = flightRepositoryReturning(false);
        //and
        var group = new FlightGroup();
        var beforeToggle = group.isDone();
        var mockRepository= mock(FlightGroupRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.of(group));
        //system under test
        var toTest = new FlightGroupService(mockRepository, mockFlightRepository);
        //when
        toTest.toggleGroup(0);
        //then
        assertThat(group.isDone()).isEqualTo(!beforeToggle);

    }
    private FlightRepository flightRepositoryReturning(boolean result) {
        var mockFlightRepository = mock(FlightRepository.class);
        when(mockFlightRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(result);
        return mockFlightRepository;
    }
}