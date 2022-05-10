package pl.wsb.java.flightapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import pl.wsb.java.flightapp.model.Flight;
import pl.wsb.java.flightapp.model.FlightGroup;
import pl.wsb.java.flightapp.model.FlightRepository;


import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.*;

@Configuration
public class TestConfiguration {
    @Bean
    @Primary
    @Profile("!integration")
    DataSource e2etestDataSource(){
        var result = new DriverManagerDataSource("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1","sa", "");
        result.setDriverClassName("org.h2.Driver");
        return result;
    }

    @Bean
    @Primary
    @Profile("integration")
    FlightRepository testRepo(){
        return new FlightRepository() {

            private Map<Integer, Flight> flights = new HashMap<>();

            @Override
            public List<Flight> findAll() {
                return new ArrayList<>(flights.values());
            }

            @Override
            public Page<Flight> findAll(Pageable page) {
                return null;
            }

            @Override
            public Optional<Flight> findById(Integer id) {
                return Optional.ofNullable(flights.get(id));
            }

            @Override
            public boolean existsById(Integer id) {
                return flights.containsKey(id);
            }

            @Override
            public boolean existsByDoneIsFalseAndGroup_Id(Integer groupId) {
                return false;
            }

            @Override
            public List<Flight> findByDone(boolean done) {
                return null;
            }

            @Override
            public Flight save(Flight entity) {
                int key = flights.size() + 1;
                try {
                    var field = Flight.class.getDeclaredField("id");
                    field.setAccessible(true);
                    field.set(entity,key);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                flights.put(key, entity);
                return flights.get(key);
            }

            @Override
            public List<Flight> findAllByGroup_Id(Integer groupId) {
                return List.of();
            }
        };
    }
}
