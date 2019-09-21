package Stratego.service;


import Stratego.model.Match;
import Stratego.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MatchService {

    private static final String WIN  = "win";
    private static final String LOST = "lost";


    @Autowired
    private MatchRepository matchRepository;
    public void addMatch(Match match) {
        matchRepository.save(match);
    }

    public void deleteMatch() {
        // not need?
    }

    public boolean matchExists(long matchId) {

        Optional<Match> match = matchRepository.findById(matchId);
        if (match.isPresent())
            return true;
        return false;
    }

    public void updateMatch() {
        // not need?
    }

    public List<Match> getMatchesByUserId(long userId) {
        /* filter games, only the ones played by userId */
        return matchRepository.findByUserIdOrderByDateDesc(userId);
//        List<Match> matches = new ArrayList<>();
//        for (Match match: matchRepository.findAll()) {
//            if (match.getUserId() == userId){
//                matches.add(match);
//            }
//        }
//        return matches;
    }

    public int countWins(long userId) {
        return countWinsOrLosts(userId, 1);
    }

    public int countLost(long userId) {
        return countWinsOrLosts(userId, 0);
    }

    private int countWinsOrLosts(long userId, int countWin) {
        int cnt = 0;
        List<Match> matches = getMatchesByUserId(userId);
        if (countWin == 1) {
            for (Match match: matches) {
                if (match.getOutcome().equals(WIN))
                    cnt ++;
            }

        } else {
            for (Match match: matches) {
                if (match.getOutcome().equals(LOST))
                    cnt++;
            }
        }
        return cnt;
    }

}
