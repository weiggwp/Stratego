package Stratego.controller;

import Stratego.logic.src.Board;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import Stratego.board.*;

import Stratego.board.arrangement;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class boardController {
arrangement arr = new arrangement();
Board board=new Board();
    @GetMapping("/board")
    public String greeting(Model model) {
        int count = 10;
        int inner = 10;
        boardController control = new boardController();
        //render board.html
        model.addAttribute("count", count);
        model.addAttribute("inner", inner);

        model.addAttribute("pos", arr);


        return "board";


    }

    @RequestMapping(value = "/post_greet", method = RequestMethod.POST)
    public ResponseEntity<String> post_greet(@RequestBody Move m) throws Exception
    // RequestBody String some)
    {
        System.out.println(m.getStart_x()+","+m.getStart_y()+","+m.getEnd_x()+","+m.getEnd_y());
        if (!board.isInitialzied())
            board.initializeGameboard(arr.getBlue(),arr.getRed());
        boolean success=board.move(m.getStart_x(),m.getStart_y(),m.getEnd_x(),m.getEnd_y());
        if (success)
            return new ResponseEntity<String>("legal" , HttpStatus.OK);
        else
            return new ResponseEntity<String>("illegal" , HttpStatus.OK);

    }
}
