package Stratego.controller;

import Stratego.board.Move;
import Stratego.logic.src.Board;

import Stratego.logic.src.Game;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import Stratego.board.arrangement;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class boardController {
    private long GameID=0;
    Game game;
    @GetMapping("/board")
    public ModelAndView greeting(Model model) {
        int count = 10;
        int inner = 10;
        //boardController control = new boardController();
        game = new Game(++GameID);


        //render board.html
        model.addAttribute("count", count);
        model.addAttribute("inner", inner);

        model.addAttribute("pos", game.getGameSetup());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("board");
        return modelAndView;

        //return "board";


    }

    @RequestMapping(value = "/make_move", method = RequestMethod.POST )
    @ResponseBody
    public ResponseEntity<String> move(@RequestBody Move m)
    // RequestBody String some)
    {
        System.out.println(m.getStart_x()+","+m.getStart_y()+","+m.getEnd_x()+","+m.getEnd_y());

        String status=game.move(m.getStart_x(),m.getStart_y(),m.getEnd_x(),m.getEnd_y(),m.getColor());

        return new ResponseEntity<String>(status, HttpStatus.OK);


    }

    @RequestMapping(value = "/swap_piece", method = RequestMethod.POST )
    @ResponseBody
    public ResponseEntity swap(@RequestBody Move m)
    {
        game.swap(m.getStart_x()-1,m.getStart_y()-1,m.getEnd_x()-1,m.getEnd_y()-1);
        //need to save the move in game
        return new ResponseEntity(HttpStatus.OK);




    }
}
