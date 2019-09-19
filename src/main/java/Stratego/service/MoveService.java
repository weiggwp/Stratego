package Stratego.service;

import Stratego.model.Reposition;
import Stratego.repository.MoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MoveService {

    @Autowired
    private MoveRepository moveRepository;

    public void addMoves(Iterable<Reposition> moves) {
        moveRepository.saveAll(moves);
    }

    public void addMove(Reposition reposition) {
        moveRepository.save(reposition);
    }


    public List<Reposition> readMoves(long matchId) {
        List<Reposition> repositions = new ArrayList<>();
        for(Reposition reposition : moveRepository.findAll()) {
            if (reposition.getMatchId() == matchId) {
                System.out.println(reposition);
                repositions.add(reposition);
            }
        }
        return repositions;
    }

    public void updateMove() {
        // not needed? can't regret moves during game

    }

    public void deleteMove() {
        // not needed?

    }

    public List<String> getOverallPieceLostPlayer(long matchId) {
        return findLostPieces(matchId, 1);
    }

    public List<String> getOverallPieceLostOpponent(long matchId) {
        return findLostPieces(matchId, 0);
    }

    private List<String> findLostPieces(long matchId, int isPlayer) {
        List<Reposition> allRepositions = readMoves(matchId);
        List<String> pieceLostList = new ArrayList<>();

        if (isPlayer == 1) {
            for (Reposition reposition : allRepositions) {
                if (!reposition.getPieceCapturedByOpponent().equals("None"))
                    pieceLostList.add(reposition.getPieceCapturedByOpponent());
            }
        } else {
            for (Reposition reposition : allRepositions) {
                if (!reposition.getPieceCapturedByPlayer().equals("None"))
                    pieceLostList.add(reposition.getPieceCapturedByPlayer());
            }
        }

        return pieceLostList;
    }
}
