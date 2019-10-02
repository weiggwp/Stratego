package Stratego.controller;

import Stratego.board.GameObj;
import Stratego.board.Move;
import Stratego.board.Move_status;
import Stratego.logic.src.Board;

import Stratego.logic.src.BoardPiece;
import Stratego.logic.src.Game;
import Stratego.model.Match;
import Stratego.model.Placement;
import Stratego.model.Reposition;
import Stratego.model.User;
import Stratego.service.MatchService;
import Stratego.service.MoveService;
import Stratego.service.PlacementService;
import Stratego.service.UserService;
import Stratego.util.Extractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.init.RepositoriesPopulatedEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

import java.util.ArrayList;

@RestController
public class boardController {
    private volatile long GameID=0;
    private volatile int move_num = 0;

    @Autowired
    PlacementService placementService;
    @Autowired
    UserService userService;
    @Autowired
    MoveService moveService;
    @Autowired
    MatchService matchService;

    private volatile ArrayList<Game> games = new ArrayList<Game> ();
//    Game game;

    @GetMapping("/board")
    public ModelAndView greeting(Model model) {
        int count = 10;
        int inner = 10;
        //boardController control = new boardController();
        Game game = new Game(GameID++);
        games.add(game);
        long gameId = GameID;
        move_num = 0;   //reset move_num per new game

        //render board.html
        model.addAttribute("count", count);
        model.addAttribute("inner", inner);

        model.addAttribute("pos", game.getGameSetup());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("board");

        return modelAndView;

        //return "board";


    }

    @RequestMapping(value = "/make_move", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Move> move(@RequestBody Move m)
    // RequestBody String some)
    {
        Game game = games.get((int)m.getGameID());
       // String status=
        Move_status stat = game.move(m);
//        m.setGameID(GameID);
        m.setMoveNum(move_num++);
        m.setStatus(stat);

        //game.setCurrent_move(m);//  filling in move_status from game
        if (stat.isIs_valid_move())    //not a valid move
        {
            // computer move comes in another http request

            game.madeMove(m);
//            System.out.println(stat);

            Move_status moveStatus = m.getStatus();
            boolean isGameOver = moveStatus.isGame_ended();
            if (isGameOver) {
                Reposition reposition = Extractor.extractMove(m);
                moveService.addMove(reposition);
                /* game match over, save match */
//                System.out.println("GAME OVER");
                long GameID = m.getGameID();
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                String username = "";
                if (principal instanceof UserDetails) {
                    username = ((UserDetails) principal).getUsername();
                } else {
                    username = principal.toString();
                }
                String gameResult = moveStatus.getGame_result();
                Date date = new Date();
                User user = userService.findByUsername(username);
                long userId = user.getId();
                long unixTime = date.getTime();
                Match match = new Match(GameID, userId, gameResult, unixTime);
                matchService.addMatch(match);
            } else {

                /* save move */
                Reposition move = Extractor.extractMove(m);
//                moveService.addMove(move);
                Thread t = new Thread(new MoveToDBRunnable(moveService, move));
                t.start();


//                Move aiMove = game.getAIMove();
//                Reposition move2 = Extractor.extractMove(aiMove);
//                moveService.addMove(move2);
            }


        }
            return new ResponseEntity<Move>(m, HttpStatus.OK);



    }

    @RequestMapping(value = "/swap_piece", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity swap(@RequestBody Move m) {
        Game game = games.get((int)m.getGameID());
        game.swap(m.getStart_x() - 1, m.getStart_y() - 1, m.getEnd_x() - 1, m.getEnd_y() - 1);
        //need to save the move in game
        return new ResponseEntity(HttpStatus.OK);


    }

    @RequestMapping(value = "/get_board", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity getBoard(@RequestBody GameObj m) {
        Game game = games.get((int)m.getGameID());
        return new ResponseEntity<BoardPiece[][]>(game.getBoard().getBoard(), HttpStatus.OK);


    }

    @RequestMapping(value = "/get_AI", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity getAI(@RequestBody GameObj p) {
        Game game = games.get((int)p.getGameID());
        Move m = game.getAIMove('R');

        Move_status moveStatus = m.getStatus();
        if (m.getStatus().isGame_ended()) {
            long GameID = m.getGameID();
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = "";
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }
            String gameResult = moveStatus.getGame_result();
            Date date = new Date();
            User user = userService.findByUsername(username);
            long userId = user.getId();
            long unixTime = date.getTime();
            Match match = new Match(GameID, userId, gameResult, unixTime);
            matchService.addMatch(match);

        } else {
            m.setMoveNum(move_num++);
            Reposition move = Extractor.extractMove(m);
//            moveService.addMove(move);
            Thread t = new Thread(new MoveToDBRunnable(moveService, move));
            t.start();
        }
        return new ResponseEntity<Move>(m, HttpStatus.OK);

    }


    @RequestMapping(value = "/start_game", method = RequestMethod.POST)
    public ResponseEntity startGame() { // @RequestBody GameObj p
        Game game = games.get((int)GameID);
        Board board = game.getBoard();
        long gameId = game.getGameID();
        BoardPiece[][] boardPiece = board.getBoard();
        ArrayList<Placement> placements = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                BoardPiece piece = boardPiece[i][j];
                // attributes to saved
                int x = i;
                int y = j;
                int isPlayer = piece.getColor() == 'R' ? 1 : 0;
                char pieceName = piece.getUnit();
                if (i<4||i>5)
                    if (pieceName=='0')
                        pieceName='M';

                Placement placement = new Placement(gameId, x, y, pieceName, isPlayer);
                placements.add(placement);
            }
        }

        Thread t = new Thread(new PlacementsToDBRunnable(placementService, placements));
        t.start();

        return new ResponseEntity<Long>(gameId,HttpStatus.OK);
    }
    @RequestMapping(value = "/get_AIPlayer", method = RequestMethod.POST )
    @ResponseBody
    public ResponseEntity getAIPlayer()
    {

        Move m = game.getAIMove('B');
        m.setGameID(GameID);

        Move_status moveStatus = m.getStatus();
        if (m.getStatus().isGame_ended()) {
            long GameID = m.getGameID();
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = "";
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }
            String gameResult = moveStatus.getGame_result();
            Date date = new Date();
            User user = userService.findByUsername(username);
            long userId = user.getId();
            long unixTime = date.getTime();
            Match match = new Match(GameID, userId, gameResult, unixTime);
            matchService.addMatch(match);

        } else {
            m.setMoveNum(move_num++);
            Reposition move = Extractor.extractMove(m);
//            moveService.addMove(move);
            Thread t = new Thread(new MoveToDBRunnable(moveService, move));
            t.start();
        }
        return new ResponseEntity<Move>(m,HttpStatus.OK);


    }

    @RequestMapping(value = "/concede", method = RequestMethod.POST )
    @ResponseBody
    public ResponseEntity concede()
    {
//        game.setGameEnded(0);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        String gameResult = "lose";
        Date date = new Date();
        User user = userService.findByUsername(username);
        long userId = user.getId();
        long unixTime = date.getTime();
        long GameID =game.getGameID();
        Match match = new Match(GameID, userId, gameResult, unixTime);
        matchService.addMatch(match);

        return new ResponseEntity(HttpStatus.OK);
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
class MoveToDBRunnable implements Runnable {
    private final MoveService moveService;
    private final Reposition move;
    public MoveToDBRunnable(MoveService moveService, Reposition move) {
        // store parameter for later user
        this.moveService = moveService;
        this.move = move;
    }

    public void run() {
        moveService.addMove(move);
    }
}