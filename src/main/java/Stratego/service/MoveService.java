package Stratego.service;

import Stratego.logic.src.Game;
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
                repositions.add(reposition);
            }
        }
        return repositions;
    }

    public List<Reposition> getMoves(long gameID) {
        return moveRepository.findRepositionByMatchIdOrderByTurnId(gameID);
    }

    public void updateMove() {
        // not needed? can't regret moves during game

    }

    public void deleteMove() {
        // not needed?

    }

    public String getOverallPieceLostPlayer(long matchId) {
        return findLostPieces(matchId, 1);
    }

    public String getOverallPieceLostOpponent(long matchId) {
        return findLostPieces(matchId, 0);
    }

    private String findLostPieces(long matchId, int isPlayer) {
        StringBuilder stringBuilder = new StringBuilder(" ");
        List<Reposition> allRepositions = readMoves(matchId);

        if (isPlayer == 1) {
            for (Reposition reposition : allRepositions) {
                if (reposition.getPieceCapturedByOpponent()!='$')
                    stringBuilder.append(reposition.getPieceCapturedByOpponent());
            }
        } else {
            for (Reposition reposition : allRepositions) {
                if (reposition.getPieceCapturedByPlayer()!='$')
                    stringBuilder.append(reposition.getPieceCapturedByPlayer());
            }
        }

        return stringBuilder.toString();
    }
}
