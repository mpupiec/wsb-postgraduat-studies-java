package pl.wsb.java.flightapp.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
interface SqlFlightRepository extends FlightRepository, JpaRepository<Flight, Integer> {


}