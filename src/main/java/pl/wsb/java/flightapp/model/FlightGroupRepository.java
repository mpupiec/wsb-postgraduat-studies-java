package pl.wsb.java.flightapp.model;

import java.util.List;
import java.util.Optional;

public interface FlightGroupRepository {
    List<FlightGroup> findAll();

    Optional<FlightGroup> findById(Integer id);

    FlightGroup save(FlightGroup entity);
}
