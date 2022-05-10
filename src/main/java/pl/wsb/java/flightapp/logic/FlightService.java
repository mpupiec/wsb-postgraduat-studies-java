package pl.wsb.java.flightapp.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.wsb.java.flightapp.model.Flight;
import pl.wsb.java.flightapp.model.FlightRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class FlightService {
    private static final Logger logger = LoggerFactory.getLogger(FlightService.class);
    private final FlightRepository repository;

    FlightService(final FlightRepository repository){
        this.repository=repository;
    }

    @Async
    public CompletableFuture<List<Flight>> findAllAsync(){
        logger.info("Async find!");
        return CompletableFuture.supplyAsync(repository::findAll);
    }

}
