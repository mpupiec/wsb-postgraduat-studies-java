package pl.wsb.java.flightapp.adapter;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.wsb.java.flightapp.model.FlightGroup;
import pl.wsb.java.flightapp.model.FlightGroupRepository;

import java.util.List;
@Repository
public interface SqlFlightGroupRepository extends FlightGroupRepository, JpaRepository<FlightGroup,Integer> {
    @Override
    @Query("select distinct g from FlightGroup g join fetch g.flights")
    List<FlightGroup> findAll();
    @Override
    boolean existsByDoneIsFalseAndUser_Id(Integer userId);
}
