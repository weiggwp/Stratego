package Stratego.Util;

import Stratego.board.Move;
import Stratego.board.Move_status;
import Stratego.board.Round;
import Stratego.model.Reposition;

import java.util.ArrayList;
import java.util.List;


public class Extractor {

    public static List<Reposition> extractMoves(List<Round> rounds){

        List<Reposition> moves = new ArrayList<>();

        int turn, playerStartX, playerStartY, playerEndX, playerEndY, isPlayer = 1;
        int computerStartX, computerStartY, computerEndX, computerEndY;

        String pieceCapturedByPlayer, pieceCapturedByComputer;
        String pieceCapturedByPlayer_1, pieceCapturedByComputer_1;

        String playerPieceName, computerPieceName;
        long matchId;

        int turns = 0;
        for (int i = 0; i < rounds.size(); i ++ ){
            Round round = rounds.get(i);

            Move playerMove = round.getUser();
            Move computerMove = round.getAi();

            Move_status aiMoveStatus = computerMove.getStatus();
            Move_status playerMoveStatus = playerMove.getStatus();

            playerStartX = playerMove.getStart_x();
            playerStartY = playerMove.getStart_y();
            playerEndX   = playerMove.getEnd_x();
            playerEndY   = playerMove.getEnd_y();
            playerPieceName = playerMoveStatus.getPiece_name();

            computerStartX = computerMove.getStart_x();
            computerStartY = computerMove.getStart_y();
            computerEndX   = computerMove.getEnd_x();
            computerEndY   = computerMove.getEnd_y();
            computerPieceName = aiMoveStatus.getPiece_name();

            matchId = computerMove.getGameID();

            pieceCapturedByPlayer = playerMoveStatus.getPieceCapturedByPlayer();
            pieceCapturedByComputer = playerMoveStatus.getPieceCapturedByComputer();

            pieceCapturedByPlayer_1 = playerMoveStatus.getPieceCapturedByPlayer();
            pieceCapturedByComputer_1 = playerMoveStatus.getPieceCapturedByComputer();


            Reposition move1 = new Reposition(matchId, turns    , playerStartX,
                    playerStartY, playerEndX, playerEndY,
                    playerPieceName, pieceCapturedByPlayer, pieceCapturedByComputer);
            Reposition move2 = new Reposition(matchId, turns ++ , computerStartX,
                    computerStartY, computerEndX, computerEndY,
                    computerPieceName, pieceCapturedByPlayer_1, pieceCapturedByComputer_1);

            moves.add(move1);
            moves.add(move2);
        }
        return moves;
    }

    // TODO: and extractor for placements;
}

