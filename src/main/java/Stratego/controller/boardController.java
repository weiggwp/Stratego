package Stratego.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< HEAD

import Stratego.board.*;

import Stratego.board.arrangement;

=======
import Stratego.board.arrangement;


>>>>>>> 11efd43e7c196492252151482d1b78d8006c569a
@Controller
public class boardController {

    @GetMapping("/board")
    public String greeting(Model model)
    {
        int count = 10;
        int inner = 10;
        boardController control = new boardController();
        //render board.html
        model.addAttribute("count", count);
        model.addAttribute("inner",inner);

        model.addAttribute("pos",new arrangement());

<<<<<<< HEAD

        return "board" ;
=======
        return "board";
>>>>>>> 11efd43e7c196492252151482d1b78d8006c569a
    }


}
