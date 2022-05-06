package pl.wsb.java.flightapp.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank (message = "Flight description must be not empty")
    private String description;
    private boolean done;
    private LocalDateTime departureTime;
    @NotBlank (message = "Airport description must be not empty")
    private String departureAirport;
    @NotBlank (message = "Airport description must be not empty")
    private String arrivalAirport;
    @Embedded
    private Audit audit = new Audit();
    @ManyToOne
    @JoinColumn(name ="flight_groups_id")
    private FlightGroup group;


    Flight() {}
        public Flight(String description, LocalDateTime departureTime,
        String departureAirport,String arrivalAirport){
            this.description = description;
            this.departureTime=departureTime;
            this.departureAirport=departureAirport;
            this.arrivalAirport =arrivalAirport;
        }


    public int getId() {return id;}

    void setId(int id) {this.id = id;}

    public String getDescription() {return description;}

    void setDescription(String description) {this.description = description;}

    public boolean isDone() {return done;}

    public void setDone(boolean done) {this.done = done;
    }

    public LocalDateTime getDepartureTime() {return departureTime;}

    public void setDepartureTime(LocalDateTime departureTime) {this.departureTime = departureTime;}

    public String getDepartureAirport() {return departureAirport;}

    public void setDepartureAirport(String departureAirport) {this.departureAirport = departureAirport;}

    public String getArrivalAirport() {return arrivalAirport;}

    public void setArrivalAirport(String arrivalAirport) {this.arrivalAirport = arrivalAirport;}

    public FlightGroup getGroup() {return group;}

    public void setGroup(FlightGroup group) {this.group = group;}

    public void updateFrom(final Flight source){
        description = source.description;
        done = source.done;
        departureTime = source.departureTime;
        departureAirport = source.departureAirport;
        arrivalAirport = source.arrivalAirport;
        group = source.group;

    }


}
