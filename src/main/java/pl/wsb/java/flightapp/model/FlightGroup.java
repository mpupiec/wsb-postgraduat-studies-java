package pl.wsb.java.flightapp.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "flight_groups")
public class FlightGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank (message = "Flight group's description must be not empty")
    private String description;
    private boolean done;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group" )
    private Set<Flight> flights;
    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;

    public FlightGroup() {
    }

    public int getId() {return id;}

    void setId(int id) {this.id = id;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public boolean isDone() {return done;}

    public void setDone(boolean done) {this.done = done;}

    public Set<Flight> getFlights() {return flights;}

    public void setFlights(Set<Flight> flights) {this.flights = flights;}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
