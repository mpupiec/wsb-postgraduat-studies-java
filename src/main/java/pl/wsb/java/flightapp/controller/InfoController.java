package pl.wsb.java.flightapp.controller;


import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wsb.java.flightapp.FlightConfigurationProperties;

@RestController
@RequestMapping("/info")
public class InfoController {
    private DataSourceProperties dataSource;
    private FlightConfigurationProperties myProp;

    public InfoController(DataSourceProperties dataSource, FlightConfigurationProperties myProp) {
        this.dataSource = dataSource;
        this.myProp = myProp;

}

    @GetMapping("/url")
    String url(){
      return dataSource.getUrl();
    }
    @GetMapping("/prop")
    boolean myProp(){
      return myProp.getAirportField().isAllowMultipleFlights();
    }
}
