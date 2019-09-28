package Stratego.controller;


import Stratego.model.Placement;
import Stratego.model.Reposition;
import Stratego.service.MoveService;
import Stratego.service.PlacementService;
import Stratego.util.GameIdentifier;
import Stratego.util.ReplayObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReplayController {

    @Autowired
    PlacementService placementService;
    @Autowired
    MoveService moveService;

    @RequestMapping(value="/replay", method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity replayPage(@RequestBody GameIdentifier gameIdentifier){

        System.out.print("Hi, getting Lists to reconstruct the game");
        System.out.print("gameId is: " + gameIdentifier.getGameId());

        long gameId = gameIdentifier.getGameId();
        // TODO: Sort moves by their turns
        List<Reposition> moves = moveService.readMoves(gameId);
        List<Placement> placementList = placementService.getPlacements(gameId);
        ReplayObject replayObject = new ReplayObject(moves, placementList);
        return new ResponseEntity<ReplayObject>(replayObject, HttpStatus.OK);

    }


}
