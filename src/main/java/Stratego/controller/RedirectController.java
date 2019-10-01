package Stratego.controller;

import Stratego.util.GameIdentifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RedirectController {

    @RequestMapping(value="/to_replay", method = RequestMethod.POST)
    public ModelAndView redirectView(@RequestBody GameIdentifier gameIdentifier, ModelMap model){
        model.addAttribute("GameID", gameIdentifier);
        return new ModelAndView("redirect:/replay", model);
    }

}
