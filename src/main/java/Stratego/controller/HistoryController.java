package Stratego.controller;

import Stratego.board.Move;
import Stratego.model.Match;
import Stratego.model.Reposition;
import Stratego.model.User;
import Stratego.service.MatchService;
import Stratego.service.MoveService;
import Stratego.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private UserService userService;
    @Autowired
    private MatchService matchService;
    @Autowired
    private MoveService moveService;


    @GetMapping(path="/{userName}")
    public String history(ModelMap modelMap, @PathVariable String userName) {
        // 0)
        User user = userService.findByUsername(userName);
        long userId = user.getId();
        // 1) get the matches from database;
        List<Match> matchList = matchService.getMatchesByUserId(userId);
        // 2) sort by date
        // 3) send to html
//        modelMap.put("matches", matchList);

        // fake data, open the same site will yield duplicated tuples in the table
        moveService.addMove(new Reposition(4, 0, 1, 1,2,2,"3", "2", "3"));
        moveService.addMove(new Reposition(4, 0, 3, 4,4,1,"3", "1", "M"));
        moveService.addMove(new Reposition(4, 0, 2, 5,3,2,"3", "2", "3"));
        moveService.addMove(new Reposition(4, 0, 1, 1,2,2,"3", "7", "None"));
        moveService.addMove(new Reposition(4, 0, 1, 1,2,2,"3", "None", "8"));
        moveService.addMove(new Reposition(4, 0, 1, 1,2,2,"3", "None", "None"));

        List<Match> matches = new ArrayList<>();
        matches.add(new Match(4,1, "Win", 1568275637511L));
        matches.add(new Match(4,1, "Win", 1568772647551L));
        matches.add(new Match(4,1, "Lost",1568735627531L));
        matches.add(new Match(4,1, "Win", 1568774657525L));
        matches.add(new Match(4,1,"Lost", 1568771657181L));
        matches.add(new Match(4,1, "Win", 1568776657411L));

        for (Match match: matches) {
            String str_1 = moveService.getOverallPieceLostPlayer(match.getMatchId());
            String str_2 = moveService.getOverallPieceLostOpponent(match.getMatchId());
            match.setPiecesLostPlayer(str_1);
            match.setPiecesLostComputer(str_2);
        }

        modelMap.put("matches", matches);
        return "matches";
    }
}
