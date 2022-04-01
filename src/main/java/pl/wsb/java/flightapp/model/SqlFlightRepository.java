package pl.wsb.java.flightapp.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
interface SqlFlightRepository extends FlightRepository, JpaRepository<Flight, Integer> {
   @Override
   @Query(nativeQuery = true, value = "select count(*) > 0 from flights where id=:id")
    boolean existsById(@Param("id") Integer id);

}