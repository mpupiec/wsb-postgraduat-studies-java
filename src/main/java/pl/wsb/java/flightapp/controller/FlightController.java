package pl.wsb.java.flightapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.java.flightapp.model.Flight;
import pl.wsb.java.flightapp.model.FlightRepository;


import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class FlightController {
    private static final Logger logger = LoggerFactory.getLogger(FlightController.class);
    private final FlightRepository repository;

    FlightController(final FlightRepository repository){
        this.repository = repository;
    }

    @PostMapping("/flights")
    ResponseEntity<Flight> createFlight (@RequestBody @Valid Flight toCreate){
        Flight result = repository.save(toCreate);
        return ResponseEntity.created(URI.create("/"+result.getId())).body(result);
    }

    @GetMapping (value ="/flights", params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Flight>>readAllFlights(){
        logger.warn("Exposing all the flights");
        return ResponseEntity.ok(repository.findAll());
    }
    @GetMapping ("/flights")
    ResponseEntity<List<Flight>>readAllFlights(Pageable page){
        logger.info("Custom pageable");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }
    @GetMapping("/flights/{id}")
    ResponseEntity<Flight> readTask(@PathVariable int id){
        return repository.findById(id).
                map(ResponseEntity::ok).
                orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/flights/{id}")
    ResponseEntity<?> updateFlight(@PathVariable("id") int id, @RequestBody @Valid Flight toUpdate){
        if(!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        toUpdate.setId(id);
        repository.save(toUpdate);
        return ResponseEntity.noContent().build();
    }

}
