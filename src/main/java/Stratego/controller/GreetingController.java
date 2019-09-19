package Stratego.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import Stratego.board.Move;

@Controller
public class GreetingController {

    @GetMapping("/greet")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greet";
    }

<<<<<<< HEAD

}
=======
    @RequestMapping(value = "/post_greet", method = RequestMethod.POST)
    public ResponseEntity<String> post_greet(@RequestBody Move m) throws Exception
    // RequestBody String some)
    {

        return new ResponseEntity<String>("hello from server side, ", HttpStatus.OK);

    }
}
>>>>>>> e69e4b5bcec05759001baa44c3c66668a098b7eb
