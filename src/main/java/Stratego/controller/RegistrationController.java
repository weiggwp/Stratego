package Stratego.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import Stratego.model.User;
import Stratego.service.UserService;
import Stratego.dto.UserDto;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserDto userRegistrationDto() {
        return new UserDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "register";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result,
                                      Model model) {
        String errorMessge = null;
        User existing = userService.findByUsername(userDto.getUsername());
        if (result.hasErrors()) {
            errorMessge =  "Username or Password is invalid !";
        }

        else if (existing != null) {
            errorMessge = "Username occupied! Please use another username.";
        }
        model.addAttribute("errorMessge", errorMessge);
        if (errorMessge!=null) {
            return "register";
        }

        userService.register(userDto);

        return "redirect:/login";
    }
}