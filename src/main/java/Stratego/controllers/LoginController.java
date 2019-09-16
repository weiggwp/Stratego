package Stratego.controllers;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import Stratego.UserDto;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController
{
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        String errorMessge = null;
        if(error != null) {
            errorMessge = "Username or Password is incorrect !!";
        }
        if(logout != null) {
            errorMessge = "You have been successfully logged out !!";
        }
        model.addAttribute("errorMessge", errorMessge);
        return "login";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }

//    @RequestMapping(value = "/register", method = RequestMethod.GET)
//    public String showRegistrationForm(WebRequest request, Model model) {
//        UserDto userDto = new UserDto();
//        model.addAttribute("user", userDto);
//        return "register";
//    }

//    public ModelAndView registerUserAccount(
//            @ModelAttribute("user") @Valid UserDto accountDto,
//            BindingResult result, WebRequest request, Errors errors) {
//...
//    }




}

//@Controller
//@RequestMapping("/register")
//public class UserRegistrationController {
//
//    @Autowired
//    private UserService userService;
//
//    @ModelAttribute("user")
//    public UserRegistrationDto userRegistrationDto() {
//        return new UserRegistrationDto();
//    }
//
//    @GetMapping
//    public String showRegistrationForm(Model model) {
//        return "registration";
//    }
//
//    @PostMapping
//    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
//                                      BindingResult result) {
//
//        User existing = userService.findByEmail(userDto.getEmail());
//        if (existing != null) {
//            result.rejectValue("email", null, "There is already an account registered with that email");
//        }
//
//        if (result.hasErrors()) {
//            return "registration";
//        }
//
//        userService.save(userDto);
//        return "redirect:/registration?success";
//    }
//}