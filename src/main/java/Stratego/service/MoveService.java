package Stratego.service;

import Stratego.model.Move;
import Stratego.repository.MoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MoveService {

    @Autowired
    private MoveRepository moveRepository;

    public void addMoves(Iterable<Move> moves) {
        moveRepository.saveAll(moves);
    }

    public void addMove(Move move) {
        moveRepository.save(move);
    }


    public List<Move> readMoves(long matchId) {
        List<Move> moves = new ArrayList<>();
        for(Move move: moveRepository.findAll()) {
            if (move.getMatchId() == matchId) {
                System.out.println(move);
                moves.add(move);
            }
        }
        return moves;
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
        List<Move> allMoves = readMoves(matchId);
        List<String> pieceLostList = new ArrayList<>();

        if (isPlayer == 1) {
            for (Move move: allMoves) {
                if (!move.getPieceCapturedByOpponent().equals("None"))
                    pieceLostList.add(move.getPieceCapturedByOpponent());
            }
        } else {
            for (Move move: allMoves) {
                if (!move.getPieceCapturedByPlayer().equals("None"))
                    pieceLostList.add(move.getPieceCapturedByPlayer());
            }
        }

        return pieceLostList;
    }
}
