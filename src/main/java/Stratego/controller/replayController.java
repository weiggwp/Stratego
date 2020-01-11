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

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import Stratego.board.arrangement;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/replay")
//@RequestMapping("/replay")
public class replayController {

    @Autowired
    PlacementService placementService;
    @Autowired
    MoveService moveService;

    private long gameID;
    @GetMapping(path="/{gameID}")
    public ModelAndView replayPage(@PathVariable long gameID, Model model, ModelMap modelMap) {
        int count = 10;
        int inner = 10;
        this.gameID = gameID;

        List<Placement> placementList = placementService.getPlacements(gameID);
        for(int i=0; i<placementList.size(); i++){
            System.out.println(placementList.get(i).getX()+","+placementList.get(i).getY()+":"+placementList.get(i).getPieceName());
        }
        Collections.sort(placementList);
        for(int i=0; i<placementList.size(); i++){
            System.out.println(placementList.get(i).getX()+","+placementList.get(i).getY()+":"+placementList.get(i).getPieceName());
        }


        //render board.html
       // modelMap.put("startConfig", placementList);
        //modelMap.put("movesList", moves);


//        ArrayList<Placement> list=new ArrayList<>();
//        for (int i=0; i<40; i++)
//            list.add(new Placement(0,i/10,i,'1',0));
//        for (int i=60; i<100; i++)
//            list.add(new Placement(0,i/10,i,'2',1));

        arrangement arr = new arrangement(0,0);
        arr.create_pieces_placement(placementList);


        model.addAttribute("count", count);
        model.addAttribute("inner", inner);


        // change to arr
        model.addAttribute("pos", arr);
        ModelAndView modelAndView = new ModelAndView();


        modelAndView.setViewName("replay");
        return modelAndView;

    }



    @RequestMapping(value = "/get_Movelist", method = RequestMethod.POST )
    @ResponseBody
    public ResponseEntity getMovelist()
    {

        List<Reposition> moves = moveService.getMoves(this.gameID);
        Move[] moveArr = new Move[moves.size()];
        for (int i = 0; i < moves.size();i ++) {

            Reposition reposition = moves.get(i);
//            System.out.println(reposition);

            Move_status moveStatus = new Move_status();
            moveStatus.setIs_valid_move(true);
            moveStatus.setFight_result(reposition.getFightResult());
            Move move = new Move(0, "", reposition.getStartX(), reposition.getStartY(),
                    reposition.getCurX(), reposition.getCurY(), moveStatus);
            moveArr[i] = move;

        }
        return new ResponseEntity<Move[]>(moveArr,HttpStatus.OK);
    }

}





