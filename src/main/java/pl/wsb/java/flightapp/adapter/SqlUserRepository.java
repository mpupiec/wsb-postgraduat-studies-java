package pl.wsb.java.flightapp.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.wsb.java.flightapp.model.FlightGroup;
import pl.wsb.java.flightapp.model.FlightGroupRepository;
import pl.wsb.java.flightapp.model.User;
import pl.wsb.java.flightapp.model.UserRepository;

import java.util.List;

@Repository
public interface SqlUserRepository extends UserRepository, JpaRepository<User,Integer> {
    @Override
    @Query("select distinct u from User u join fetch u.details")
    List<User> findAll();
}
