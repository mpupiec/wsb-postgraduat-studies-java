package pl.wsb.java.flightapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.wsb.java.flightapp.logic.UserService;
import pl.wsb.java.flightapp.model.UserDetails;
import pl.wsb.java.flightapp.model.projection.UserWriteModel;

@Controller
@RequestMapping("/users")
class UserController {
    private final UserService service;

    public UserController(final UserService service) {
        this.service = service;
    }

    @GetMapping
    String showUsers(Model model) {
        var userToEdit = new UserWriteModel();
        userToEdit.setLogin("root");
        userToEdit.setPas("root");
        model.addAttribute("user", new UserWriteModel());
        return "users";
    }

    @PostMapping
    String addUser(@ModelAttribute("user") UserWriteModel current, Model model){
        service.save(current);
        model.addAttribute("user", new UserWriteModel());
        model.addAttribute("message", "Add new user / group of users");
        return "users";
    }

    @PostMapping(params = "addUser")
    String addUserDetails(@ModelAttribute("user") UserWriteModel current){
        current.getDetails().add(new UserDetails());
        return "users";
    }
}
