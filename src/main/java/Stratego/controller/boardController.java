package Stratego.controller;

import Stratego.logic.src.Board;
<<<<<<< HEAD
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
=======
>>>>>>> e69e4b5bcec05759001baa44c3c66668a098b7eb
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        Board game = new Board();
        game.start();
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
        if (!board.isInitialzied()||m.getMoveNum()==0) {
            System.out.println("reinitializing " + board.isInitialzied()+ " " + m.getMoveNum());
            board.initializeGameboard(arr.getBlue(), arr.getRed());
        }
        String status=board.move(m.getStart_x(),m.getStart_y(),m.getEnd_x(),m.getEnd_y(),m.getColor());

        return new ResponseEntity<String>(status, HttpStatus.OK);




    }
}
