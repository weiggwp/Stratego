package Stratego.controller;

import Stratego.board.Move;
import Stratego.board.Move_status;
import Stratego.board.Round;
import Stratego.logic.src.Board;

import Stratego.logic.src.BoardPiece;
import Stratego.logic.src.Game;
import Stratego.model.Placement;
import Stratego.model.Reposition;
import Stratego.service.MoveService;
import Stratego.service.PlacementService;
import Stratego.util.GameIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import Stratego.board.arrangement;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

@RestController
public class replayController {

    @Autowired
    PlacementService placementService;
    @Autowired
    MoveService moveService;

    @RequestMapping(value = "/replay", method = RequestMethod.POST)
    public ModelAndView replayPage(@RequestBody GameIdentifier gameIdentifier,
                                   ModelMap modelMap, Model model) {
        int count = 10;
        int inner = 10;

        long gameId = gameIdentifier.getGameID();
        List<Reposition> moves = moveService.readMoves(gameId);
        Collections.sort(moves);
        List<Placement> placementList = placementService.getPlacements(gameId);


        //render board.html
        modelMap.put("startConfig", placementList);
        modelMap.put("movesList", moves);

        model.addAttribute("count", count);
        model.addAttribute("inner", inner);
        model.addAttribute("pos", null);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("replay");
        return modelAndView;

    }
}