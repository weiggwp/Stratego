package Stratego.controller;

import Stratego.board.Move;
import Stratego.board.Move_status;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@RestController
public class boardController {
    private long GameID=0;
    private int move_num = 0;

    @Autowired
    PlacementService placementService;


    Game game;
    @GetMapping("/board")
    public ModelAndView greeting(Model model) {
        int count = 10;
        int inner = 10;
        //boardController control = new boardController();
        game = new Game(++GameID);
        move_num = 0;
        long gameId = GameID;
        Board board = game.getBoard();

        ArrayList<Placement> placements = new ArrayList<>();


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
                    placements.add(placement);

                }

            }
        }
        // have a new thread to add placements to database as it was slowing down board loading by 20s
        Thread t = new Thread(new PlacementsToDBRunnable(placementService,placements));
        t.start();

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
    public ResponseEntity<Move> move(@RequestBody Move m)
    // RequestBody String some)
    {
//        System.out.println(m.getStart_x()+","+m.getStart_y()+","+m.getEnd_x()+","+m.getEnd_y());
//        System.out.println("moving");
       // String status=
        Move_status stat = game.move(m);
        m.setGameID(GameID);
        m.setMoveNum(move_num++);
                //(m.getStart_x(),m.getStart_y(),m.getEnd_x(),m.getEnd_y(),m.getColor());
        //game.setCurrent_move(m);//  filling in move_status from game
        if(stat.isIs_valid_move())    //not a valid move
        {
            //computer move comes in another http request

            m.setStatus(stat);
            game.madeMove(m);

            return new ResponseEntity<Move>(m,HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<Move>(game.makeIllegalMove(m),HttpStatus.OK);
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

    @RequestMapping(value = "/get_board", method = RequestMethod.POST )
    @ResponseBody
    public ResponseEntity getBoard()
    {

        return new ResponseEntity<BoardPiece[][]>(game.getBoard().getBoard(),HttpStatus.OK);


    }

    @RequestMapping(value = "/get_AI", method = RequestMethod.POST )
    @ResponseBody
    public ResponseEntity getAI()
    {
        Move ai_move = game.getAIMove('R');
        ai_move.setGameID(GameID);
        ai_move.setMoveNum(move_num++);
        return new ResponseEntity<Move>(ai_move,HttpStatus.OK);

    }
    @RequestMapping(value = "/get_AIPlayer", method = RequestMethod.POST )
    @ResponseBody
    public ResponseEntity getAIPlayer()
    {
        Move ai_move = game.getAIMove('B');
        ai_move.setGameID(GameID);
        ai_move.setMoveNum(move_num++);
        return new ResponseEntity<Move>(ai_move,HttpStatus.OK);


    }
}

class PlacementsToDBRunnable implements Runnable {
    private final ArrayList<Placement> placements;
    private final PlacementService placementService;
    public PlacementsToDBRunnable(PlacementService placementService, ArrayList<Placement> placements) {
        // store parameter for later user
        this.placementService = placementService;
        this.placements = placements;
    }

    public void run() {
        placementService.addPlacements(placements);
//        System.out.println("placements added");
    }
}