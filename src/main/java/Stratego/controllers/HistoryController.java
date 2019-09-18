package Stratego.controllers;

import Stratego.model.Match;
import Stratego.service.MatchService;
import Stratego.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

public class HistoryController {

    @Autowired
    private UserService userService;
    @Autowired
    private MatchService matchService;


    @RequestMapping("/history")
    public String history(ModelMap modelMap) {

        // 1) get the matches from database;
        // 2) sort by date
        // 3) send to html

        List<Match> matches = new ArrayList<>();
        matches.add(new Match(1, "Win", 1568775657591L));
        matches.add(new Match(1, "Win", 1568775657591L));
        matches.add(new Match(1, "Win", 1568775657591L));
        matches.add(new Match(1, "Win", 1568775657591L));
        matches.add(new Match(1, "Win", 1568775657591L));
        matches.add(new Match(1, "Win", 1568775657591L));
        modelMap.put("matches", matches);
        return "matches";
    }
}
