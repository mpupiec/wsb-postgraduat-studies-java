package pl.wsb.java.flightapp.logic;

import org.springframework.stereotype.Service;
import pl.wsb.java.flightapp.FlightConfigurationProperties;
import pl.wsb.java.flightapp.model.*;
import pl.wsb.java.flightapp.model.projection.GroupFlightWriteModel;
import pl.wsb.java.flightapp.model.projection.GroupReadModel;
import pl.wsb.java.flightapp.model.projection.GroupWriteModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class UserService {
    private UserRepository repository;
    private FlightGroupRepository flightGroupRepository;
    private FlightConfigurationProperties config;
    private FlightGroupService service;

    public UserService(final UserRepository repository, final FlightGroupRepository flightGroupRepository,
                       final FlightConfigurationProperties config) {
        this.repository = repository;
        this.flightGroupRepository = flightGroupRepository;
        this.config = config;
    }

    public List<User> readAll() {
        return repository.findAll();
    }

    public User save(final User toSave) {
        return repository.save(toSave);
    }

    public GroupReadModel createGroup(LocalDateTime departureTime, int userId) {
        if (!config.getAirportField().isAllowMultipleFlights() && flightGroupRepository.existsByDoneIsFalseAndUser_Id(userId)) {
            throw new IllegalStateException("Only one undone group from user is allowed");
        }
        // do poprawienia lgowanie
        GroupReadModel result = repository.findById(userId)
                .map(user -> {
                    var targetGroup = new GroupWriteModel();
                    targetGroup.setDescription(user.getLogin());
                    targetGroup.setFlights(
                            user.getDetails().stream()
                                    .map(userDetails -> {
                                        var fligh = new GroupFlightWriteModel();
                                    fligh.setDescription(userDetails.getFirstName());
                                    fligh.setDescription(userDetails.getLastName());
                                    fligh.setArrivalAirport(fligh.getArrivalAirport());
                                    fligh.setDepartureAirport(fligh.getDepartureAirport());
                                    fligh.setDepartureTime(fligh.getDepartureTime());
                                    return fligh;
                                    }).collect(Collectors.toSet()
                                    )
                );
                    return service.createGroup(targetGroup);
                }).orElseThrow(() -> new IllegalArgumentException("User with given id not found"));
        return result;
    }
}