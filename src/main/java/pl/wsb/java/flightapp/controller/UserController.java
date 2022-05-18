package pl.wsb.java.flightapp.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.wsb.java.flightapp.logic.UserService;
import pl.wsb.java.flightapp.model.User;
import pl.wsb.java.flightapp.model.UserDetails;
import pl.wsb.java.flightapp.model.projection.UserWriteModel;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

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
    String addUser(
            @ModelAttribute("user") @Valid UserWriteModel current,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()){
            return "users";
        }
        service.save(current);
        model.addAttribute("user", new UserWriteModel());
        model.addAttribute("users", getUsers());
        model.addAttribute("message", "Added new user / group of users");
        return "users";
    }

    @PostMapping(params = "addDetail")
    String addUserDetails(@ModelAttribute("user") UserWriteModel current){
        current.getDetails().add(new UserDetails());
        return "details";
    }

    @PostMapping("/{id}")
    String createGroup(
            @ModelAttribute("user") UserWriteModel current,
            Model model,
            @PathVariable int id,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime departureTime){
        try {
            service.createGroup(departureTime, id);
            model.addAttribute("message", "Added group!");
        }catch (IllegalStateException | IllegalArgumentException e){
            model.addAttribute("message", "Exception during creating group!");
        }
        return "users";
    }


    @ModelAttribute("users")
    List<User> getUsers(){
        return service.readAll();
    }
}
