package pl.wsb.java.flightapp.adapter;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.wsb.java.flightapp.model.FlightGroup;
import pl.wsb.java.flightapp.model.FlightGroupRepository;

import java.util.List;

public interface SqlFlightGroupRepository extends FlightGroupRepository, JpaRepository<FlightGroup,Integer> {
    @Override
    @Query("from FlightGroup g join fetch g.flights")
    List<FlightGroup> findAll();
}
