package pl.wsb.java.flightapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.wsb.java.flightapp.model.Flight;
import pl.wsb.java.flightapp.model.FlightRepository;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
public class FlightControllerIntergrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FlightRepository repo;

    @Test
    void httpGet_returnsGivenFlight() throws Exception {
        //given
        int id = repo.save(new Flight("test1", LocalDateTime.now(), "test3", "test4")).getId();
        //when + then
        mockMvc.perform(get("/flights/0" + id))
                .andExpect(status().is2xxSuccessful());
    }
}
