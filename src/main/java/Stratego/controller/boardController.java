package Stratego.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< HEAD
import Stratego.board.*;
=======
import Stratego.board.arrangement;
>>>>>>> board and pieces done


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
=======

>>>>>>> board and pieces done
        return "board";
    }


}
