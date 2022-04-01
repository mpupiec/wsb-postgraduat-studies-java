package pl.wsb.java.flightapp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("flight")
public class FlightConfigurationProperties {
    private AirportField airportField;

    public AirportField getAirportField() {
        return airportField;
    }

    public void setAirportField(AirportField airportField) {
        this.airportField = airportField;
    }

    public static class AirportField {
        private boolean allowMultipleFlights;

        public boolean isAllowMultipleFlights() {
            return allowMultipleFlights;
        }

        public void setAllowMultipleFlights(boolean allowMultipleFlights) {
            this.allowMultipleFlights = allowMultipleFlights;
        }
    }

}
