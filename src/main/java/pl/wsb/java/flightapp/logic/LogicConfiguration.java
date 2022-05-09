package pl.wsb.java.flightapp.logic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import pl.wsb.java.flightapp.FlightConfigurationProperties;
import pl.wsb.java.flightapp.model.FlightGroupRepository;
import pl.wsb.java.flightapp.model.FlightRepository;
import pl.wsb.java.flightapp.model.UserRepository;

@Configuration
@ImportResource
public class LogicConfiguration {

    @Bean
    UserService userService(
           final UserRepository repository,
            final FlightGroupRepository flightGroupRepository,
            final FlightConfigurationProperties config

            ){
        return new UserService(repository, flightGroupRepository, config);
    }

    @Bean
    FlightGroupService flightGroupService(
            final FlightGroupRepository flightGroupRepository,
            final FlightRepository flightRepository,
            final FlightConfigurationProperties config
            ){
        return  new FlightGroupService(flightGroupRepository, flightRepository);
    }
}
