package pl.wsb.java.flightapp.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wsb.java.flightapp.model.Flight;
import pl.wsb.java.flightapp.model.FlightGroupRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TempService {
    @Autowired
    List<String> temp(FlightGroupRepository repository){
        return repository.findAll().stream()
                .flatMap(flightGroup -> flightGroup.getFlights().stream())
                .map(Flight::getDescription).collect(Collectors.toList());}
}
