package pl.wsb.java.flightapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank(message = "login must be not empty")
    private String login;
    @NotBlank(message = "pas must be not empty")
    private String pas;
    @OneToMany(mappedBy = "user")
    private Set<FlightGroup> groups;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<UserDetails> details;

    public User() {
    }


    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getLogin() {return login;}

    public void setLogin(String login) {this.login = login;}

    public String getPas() {return pas;}

    public void setPas(String pas) {this.pas = pas;}

    Set<FlightGroup> getGroups() {return groups;}

    void setGroups(Set<FlightGroup> groups) {this.groups = groups;}

    public Set<UserDetails> getDetails() {return details;}

    public void setDetails(Set<UserDetails> details) {this.details = details;}
}
