package Stratego.controller;

import Stratego.board.Move;
import Stratego.board.Move_status;
import Stratego.board.Round;
import Stratego.logic.src.Board;

import Stratego.logic.src.BoardPiece;
import Stratego.logic.src.Game;
import Stratego.model.Placement;
import Stratego.service.PlacementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import Stratego.board.arrangement;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.lang.reflect.Array;
import java.util.ArrayList;

@RestController
public class replayController {
    private long GameID=0;

    @Autowired
    PlacementService placementService;


    Game game;
    @GetMapping("/replay")

    public ModelAndView greeting(Model model) {
        int count = 10;
        int inner = 10;
        //boardController control = new boardController();
     /*   game = new Game(++GameID);

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
        }*/

        ArrayList<Placement> list=new ArrayList<>();
        for (int i=0; i<40; i++)
            list.add(new Placement(0,i/10,i,'1',0));
        for (int i=60; i<100; i++)
            list.add(new Placement(0,i/10,i,'2',1));
        arrangement arr = new arrangement(0,0);
        arr.create_pieces_placement(list);
        //render board.html
        model.addAttribute("count", count);
        model.addAttribute("inner", inner);
        // change to arr
        model.addAttribute("pos", arr);

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("replay");
        return modelAndView;

        //return "board";


    }

    @RequestMapping(value = "/get_Movelist", method = RequestMethod.POST )
    @ResponseBody
    public ResponseEntity getMovelist()
    {
        //start,end,color,fight_result,img_src
        Move[] moves = new Move[5];
        Move_status stat1= new Move_status();
        stat1.setIs_valid_move(true);
        stat1.setFight_result(4);
        moves[0] = new Move(0,6,0,5,0,' ', stat1);

        Move_status stat2= new Move_status();
        stat2.setIs_valid_move(true);
        stat2.setFight_result(4);
        moves[1] = new Move(0,3,0,4,0, ' ',stat2);

        Move_status stat3= new Move_status();
        stat3.setIs_valid_move(true);
        stat3.setFight_result(4);
        moves[2] = new Move(0,6,1,5,1,' ', stat3);

        Move_status stat4= new Move_status();
        stat4.setIs_valid_move(true);
        stat4.setFight_result(0);
        moves[3] = new Move(0,4,0,5,0,' ', stat4);

        Move_status stat5= new Move_status();
        stat5.setIs_valid_move(true);
        stat5.setFight_result(1);
        moves[4] = new Move(0,5,1,5,0,' ', stat5);

        return new ResponseEntity<Move[]>(moves,HttpStatus.OK);


    }

}
