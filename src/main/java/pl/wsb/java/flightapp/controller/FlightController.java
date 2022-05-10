package pl.wsb.java.flightapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.java.flightapp.logic.FlightService;
import pl.wsb.java.flightapp.model.Flight;
import pl.wsb.java.flightapp.model.FlightRepository;


import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/flights")
public class FlightController {
    private static final Logger logger = LoggerFactory.getLogger(FlightController.class);
    private final FlightRepository repository;
    private final FlightService service;

    FlightController(final FlightRepository repository, final FlightService service){
        this.repository = repository;
        this.service = service;
    }

    @PostMapping
    ResponseEntity<Flight> createFlight (@RequestBody @Valid Flight toCreate){
        Flight result = repository.save(toCreate);
        return ResponseEntity.created(URI.create("/"+result.getId())).body(result);
    }

    @GetMapping (params = {"!sort", "!page", "!size"})
    CompletableFuture<ResponseEntity<List<Flight>>>readAllFlights(){
        logger.warn("Exposing all the flights");
        return service.findAllAsync().thenApply(ResponseEntity::ok);
    }
    @GetMapping
    ResponseEntity<List<Flight>>readAllFlights(Pageable page){
        logger.info("Custom pageable");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }
    @GetMapping("/{id}")
    ResponseEntity<Flight> readTask(@PathVariable int id){
        return repository.findById(id).
                map(ResponseEntity::ok).
                orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/done")
    ResponseEntity<List<Flight>> readDoneFlights(@RequestParam(defaultValue = "true") boolean state){
        return ResponseEntity.ok(
                repository.findByDone(state)
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateFlight(@PathVariable("id") int id, @RequestBody @Valid Flight toUpdate){
        if(!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .ifPresent(flight->{
                    flight.updateFrom(toUpdate);
                    repository.save(flight);
                });
        return ResponseEntity.noContent().build();
    }
    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleFlight(@PathVariable("id") int id){
        if(!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .ifPresent(flight->flight.setDone(!flight.isDone()));
        return ResponseEntity.noContent().build();
    }

}
