package Stratego.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import Stratego.board.*;


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
        return "board";
    }


}
