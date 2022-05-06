package pl.wsb.java.flightapp.model.projection;

import pl.wsb.java.flightapp.model.Flight;
import pl.wsb.java.flightapp.model.FlightGroup;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
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

    public FlightGroup toGroup() {
        var result = new FlightGroup();
        result.setDescription(description);
        result.setFlights(
                flights.stream()
                        .map(GroupFlightWriteModel::toFlight)
                        .collect(Collectors.toSet())
        );
        return result;
    }

}