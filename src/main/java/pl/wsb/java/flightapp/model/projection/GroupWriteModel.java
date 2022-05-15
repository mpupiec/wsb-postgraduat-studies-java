package pl.wsb.java.flightapp.model.projection;

import pl.wsb.java.flightapp.model.FlightGroup;
import pl.wsb.java.flightapp.model.User;

import java.util.Set;
import java.util.stream.Collectors;

public class GroupWriteModel {
    private String description;
    private Set<GroupFlightWriteModel> flights;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<GroupFlightWriteModel> getFlights() {
        return flights;
    }

    public void setFlights(Set<GroupFlightWriteModel> flights) {
        this.flights = flights;
    }

    public FlightGroup toGroup(User user) {
        var result = new FlightGroup();
        result.setDescription(description);
        result.setFlights(
                flights.stream()
                        .map(source -> source.toFlight(result))
                        .collect(Collectors.toSet())
        );
        result.setUser(user);
        return result;
    }

}