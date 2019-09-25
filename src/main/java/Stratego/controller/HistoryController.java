package Stratego.controller;

import Stratego.board.Move;
import Stratego.dto.UserDto;
import Stratego.model.Match;
import Stratego.model.Reposition;
import Stratego.model.User;
import Stratego.service.MatchService;
import Stratego.service.MoveService;
import Stratego.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
//@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private UserService userService;
    @Autowired
    private MatchService matchService;
    @Autowired
    private MoveService moveService;


//    @GetMapping(path="/{userName}")
    @GetMapping("/history")
    public String history(Model model, ModelMap modelMap) {
        // 0)
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }


        User user = userService.findByUsername(username);
        long userId = user.getId();
        System.out.print(userId);
        // 1) get the matches from database;
        List<Match> matches = matchService.getMatchesByUserId(userId);


        // 2) sort by date
        // 3) send to html
//        modelMap.put("matches", matchList);

        // fake data, open the same site yield different values don't know why
//        List<Reposition> moves = moveService.readMoves(4);
//        moveService.addMove(new Reposition(4, 0, 1, 1,2,2,"3", "2", "3"));
//        moveService.addMove(new Reposition(4, 0, 3, 4,4,1,"3", "1", "M"));
//        moveService.addMove(new Reposition(4, 0, 2, 5,3,2,"3", "2", "3"));
//        moveService.addMove(new Reposition(4, 0, 1, 1,2,2,"3", "7", "None"));
//        moveService.addMove(new Reposition(4, 0, 1, 1,2,2,"3", "None", "8"));
//        moveService.addMove(new Reposition(4, 0, 1, 1,2,2,"3", "None", "None"));

//        matchService.addMatch(new Match(4,25, "Win", 1568275637511L));
//        matchService.addMatch(new Match(2,25, "Win", 1568172647551L));
//        matchService.addMatch(new Match(1,25, "Lost",1568335627531L));
//        matchService.addMatch(new Match(5,25, "Win", 1568275637511L));
//        matchService.addMatch(new Match(6,25,"Lost", 1562711657181L));
//        matchService.addMatch(new Match(7,25,"Lost", 1568711657181L));

        for (Match match: matches) {
            String str_1 = moveService.getOverallPieceLostPlayer(match.getMatchId());
            String str_2 = moveService.getOverallPieceLostOpponent(match.getMatchId());
            match.setPiecesLostPlayer(str_1);
            match.setPiecesLostComputer(str_2);
        }

        modelMap.put("matches", matches);
        model.addAttribute("userName", username);
        return "matches";
    }
}
