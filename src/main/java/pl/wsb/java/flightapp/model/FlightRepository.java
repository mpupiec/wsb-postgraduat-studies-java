package pl.wsb.java.flightapp.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FlightRepository {
    List<Flight> findAll();

    Page<Flight> findAll(Pageable page);

    Optional<Flight> findById(Integer id);

    boolean existsById(Integer id);

    boolean existsByDoneIsFalseAndGroup_Id(Integer groupId);

    List<Flight> findByDone(boolean done);

    Flight save(Flight entity);
}
