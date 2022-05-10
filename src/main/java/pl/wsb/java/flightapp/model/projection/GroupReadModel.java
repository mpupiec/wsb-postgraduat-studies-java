package pl.wsb.java.flightapp.model.projection;

import pl.wsb.java.flightapp.model.Flight;
import pl.wsb.java.flightapp.model.FlightGroup;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupReadModel {
    private int id;
    private String description;
    /**
     * first LocalDateTime departureTime in queue
     */
    private LocalDateTime departureTime;
    private Set<GroupFlightReadModel> flights;

    public GroupReadModel(FlightGroup source) {
        id = source.getId();
        description = source.getDescription();
        source.getFlights().stream().map(Flight::getDepartureTime)
                .min(LocalDateTime::compareTo).ifPresent(date->departureTime=date);
        flights = source.getFlights().stream()
                .map(GroupFlightReadModel::new)
                .collect(Collectors.toSet());
    }

    public void setId(final int id) {this.id = id;}

    public int getId() {return id;}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public Set<GroupFlightReadModel> getFlights() {
        return flights;
    }

    public void setFlights(Set<GroupFlightReadModel> flights) {
        this.flights = flights;
    }


}
