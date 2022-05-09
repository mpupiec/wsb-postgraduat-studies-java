package pl.wsb.java.flightapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import pl.wsb.java.flightapp.model.Flight;
import pl.wsb.java.flightapp.model.FlightRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FlightControllerE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    FlightRepository repo;

    @Test
    void httpGet_returnsAllFlights(){
        //given
        var initial = repo.findAll().size();
        repo.save(new Flight("test1", LocalDateTime.now(), "test3", "test4"));
        repo.save(new Flight("test5", LocalDateTime.now(), "test7", "test8"));
        //when
        Flight[] result = restTemplate.getForObject("http://localhost:"+ port + "/flights",Flight[].class );
        //then
        assertThat(result).hasSize(initial+2);

    }
}