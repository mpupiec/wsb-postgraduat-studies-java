package pl.wsb.java.flightapp.model.projection;

import pl.wsb.java.flightapp.model.Flight;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class GroupFlightWriteModel {
    private String description;
    private LocalDateTime departureTime;
    private String departureAirport;
    private String arrivalAirport;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {this.description = description;}

    public LocalDateTime getDepartureTime() {return departureTime;}

    public void setDepartureTime(LocalDateTime departureTime) {this.departureTime = departureTime;}

    public String getDepartureAirport() {return departureAirport;}

    public void setDepartureAirport(String departureAirport) {this.departureAirport = departureAirport;}

    public String getArrivalAirport() {return arrivalAirport;}

    public void setArrivalAirport(String arrivalAirport) {this.arrivalAirport = arrivalAirport;}

    public Flight toFlight(){
        return new Flight(description,departureTime,departureAirport,arrivalAirport);
    }
}
