package Stratego.controller;

import Stratego.board.Move;
import Stratego.board.Round;
import Stratego.logic.src.Board;

import Stratego.logic.src.BoardPiece;
import Stratego.logic.src.Game;
import Stratego.model.Placement;
import Stratego.service.PlacementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import Stratego.board.arrangement;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class boardController {
    private long GameID=0;

    @Autowired
    PlacementService placementService;


    Game game;
    @GetMapping("/board")
    public ModelAndView greeting(Model model) {
        int count = 10;
        int inner = 10;
        //boardController control = new boardController();
        game = new Game(++GameID);

        long gameId = GameID;
        Board board = game.getBoard();

        if (board.isInitialized()) {
            BoardPiece[][] boardPiece = board.getBoard();
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    BoardPiece piece = boardPiece[i][j];

                    // attributes to saved
                    int x = i;
                    int y = j;
                    int isPlayer = piece.getColor() == 'R' ? 1 : 0;
                    char pieceName = piece.getUnit();

                    Placement placement = new Placement(gameId, x, y, pieceName, isPlayer);
                    placementService.addPlacement(placement);
                }
            }
        }


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
    public ResponseEntity<Round> move(@RequestBody Move m)
    // RequestBody String some)
    {
        System.out.println(m.getStart_x()+","+m.getStart_y()+","+m.getEnd_x()+","+m.getEnd_y());
        System.out.println("moving");
        String status=game.move(m.getStart_x(),m.getStart_y(),m.getEnd_x(),m.getEnd_y(),m.getColor());
        game.setCurrent_move(m);//  filling in move_status from game
        if(!status.equals("illegal"))
        {
            //TODO: fill in computer move info
            Round round = new Round();//suppose to send back computer move as well, for now empty

            round.setUser(m);//user move
            round.getUser().setStatus(game.getMove_stat());
            game.madeMove(round);

            return new ResponseEntity<Round>(round,HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<Round>(game.makeIllegalMove(),HttpStatus.OK);
        }



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
