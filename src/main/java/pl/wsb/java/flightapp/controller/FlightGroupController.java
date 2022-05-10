package pl.wsb.java.flightapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.java.flightapp.logic.FlightGroupService;
import pl.wsb.java.flightapp.logic.FlightService;
import pl.wsb.java.flightapp.model.Flight;
import pl.wsb.java.flightapp.model.FlightRepository;
import pl.wsb.java.flightapp.model.projection.GroupReadModel;
import pl.wsb.java.flightapp.model.projection.GroupWriteModel;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/groups")
public class FlightGroupController {
    private static final Logger logger = LoggerFactory.getLogger(FlightGroupController.class);
    private final FlightGroupService service;
    private final FlightRepository repository;


    FlightGroupController(final FlightRepository repository, final FlightGroupService service){
        this.repository = repository;
        this.service = service;
    }

    @PostMapping
    ResponseEntity<GroupReadModel> createGroup (@RequestBody @Valid GroupWriteModel toCreate){
        GroupReadModel result = service.createGroup(toCreate);
        return ResponseEntity.created(URI.create("/"+ result.getId())).body(result);
    }

    @GetMapping
    ResponseEntity<List<GroupReadModel>>readAllGroups(){
        return ResponseEntity.ok(service.readAll());
    }
//    @GetMapping
//    ResponseEntity<List<Flight>>readAllFlights(Pageable page){
//        return ResponseEntity.ok(repository.findAll(page).getContent());
//    }
//    @GetMapping("/{id}")
//    ResponseEntity<Flight> readTask(@PathVariable int id){
//        return repository.findById(id).
//                map(ResponseEntity::ok).
//                orElse(ResponseEntity.notFound().build());
//    }

//    @GetMapping("/search/done")
//    ResponseEntity<List<Flight>> readDoneFlights(@RequestParam(defaultValue = "true") boolean state){
//        return ResponseEntity.ok(
//                repository.findByDone(state)
//        );
//    }
//
    @GetMapping("/{id}")
    ResponseEntity<List<Flight>> readAllFlightsFromGroup(@PathVariable int id){
        return ResponseEntity.ok(repository.findAllByGroup_Id(id));
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleGroup(@PathVariable int id){
        service.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalState(IllegalStateException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
