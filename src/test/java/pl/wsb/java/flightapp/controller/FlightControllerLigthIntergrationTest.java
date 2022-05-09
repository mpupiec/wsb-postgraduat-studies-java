package pl.wsb.java.flightapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.wsb.java.flightapp.model.Flight;
import pl.wsb.java.flightapp.model.FlightRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FlightController.class)
@ActiveProfiles("integration")
public class FlightControllerLigthIntergrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightRepository repo;

    @Test
    void httpGet_returnsGivenFlight() throws Exception {
        //given
        when(repo.findById(anyInt()))
                .thenReturn(Optional.of(new Flight("test1", LocalDateTime.now(), "test3", "test4")));

        //when + then
        mockMvc.perform(get("/flights/123"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}
