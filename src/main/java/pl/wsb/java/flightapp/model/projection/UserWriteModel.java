package pl.wsb.java.flightapp.model.projection;

import pl.wsb.java.flightapp.model.User;
import pl.wsb.java.flightapp.model.UserDetails;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class UserWriteModel {
    @NotBlank(message = "login must be not empty")
    private String login;
    @NotBlank(message = "pas must be not empty")
    private String pas;
    @Valid
    private List<UserDetails> details = new ArrayList<>();

    public UserWriteModel(){
        details.add(new UserDetails());
    }

    public String getLogin() {return login;}

    public void setLogin(String login) {this.login = login;}

    public String getPas() {return pas;}

    public void setPas(String pas) {this.pas = pas;}

    public List<UserDetails> getDetails() {return details;}

    public void setDetails(List<UserDetails> details) {this.details = details;}

    public User toProject(){
        var result = new User();
        result.setLogin(login);
        result.setPas(pas);
        details.forEach(details->details.setUser(result));
        result.setDetails(new HashSet<>(details));
        return result;
    }
}
