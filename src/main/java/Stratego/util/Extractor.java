package Stratego.util;

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

        char pieceCapturedByPlayer, pieceCapturedByComputer;
        char pieceCapturedByPlayer_1, pieceCapturedByComputer_1;

        char playerPieceName, computerPieceName;
        long matchId;

        int moveNum1;
        int moveNum2;

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

//            moveNum1 = playerMove.getMoveNum(); // is this the turn number?
//            moveNum2 = computerMove.getMoveNum();

            computerStartX = computerMove.getStart_x();
            computerStartY = computerMove.getStart_y();
            computerEndX   = computerMove.getEnd_x();
            computerEndY   = computerMove.getEnd_y();
            computerPieceName = aiMoveStatus.getPiece_name();

            matchId = computerMove.getGameID();

            pieceCapturedByPlayer = playerMoveStatus.getPieceCapturedByPlayer();
            pieceCapturedByComputer = playerMoveStatus.getPieceCapturedByComputer();

            pieceCapturedByPlayer_1 = aiMoveStatus.getPieceCapturedByPlayer();
            pieceCapturedByComputer_1 = aiMoveStatus.getPieceCapturedByComputer();


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

    public static List<Reposition> extractPlayerMoves(List<Round> rounds){

        List<Reposition> moves = new ArrayList<>();

        int turn, playerStartX, playerStartY, playerEndX, playerEndY, isPlayer = 1;
        int computerStartX, computerStartY, computerEndX, computerEndY;

        char pieceCapturedByPlayer, pieceCapturedByComputer;
        char pieceCapturedByPlayer_1, pieceCapturedByComputer_1;

        char playerPieceName, computerPieceName;
        long matchId;

        int turns = 0;
        for (int i = 0; i < rounds.size(); i ++ ){
            Round round = rounds.get(i);

            Move playerMove = round.getUser();
            Move_status playerMoveStatus = playerMove.getStatus();

            playerStartX = playerMove.getStart_x();
            playerStartY = playerMove.getStart_y();
            playerEndX   = playerMove.getEnd_x();
            playerEndY   = playerMove.getEnd_y();
            playerPieceName = playerMoveStatus.getPiece_name();

            matchId = playerMove.getGameID();
            pieceCapturedByPlayer = playerMoveStatus.getPieceCapturedByPlayer();
            pieceCapturedByComputer = playerMoveStatus.getPieceCapturedByComputer();

            Reposition move1 = new Reposition(matchId, turns    , playerStartX,
                    playerStartY, playerEndX, playerEndY,
                    playerPieceName, pieceCapturedByPlayer, pieceCapturedByComputer);
            moves.add(move1);
        }
        return moves;
    }


    public static Reposition extractMove(Move m) {

        long gameId = m.getGameID();
        int  turn   = m.getMoveNum();
        int  x      = m.getStart_x();
        int  y      = m.getStart_y();
        int  x_1    = m.getEnd_x();
        int  y_1    = m.getEnd_y();

        Move_status status = m.getStatus();
//        char piece = status.getPiece_name(); // NullPointerException
//        char pieceCapturedByPlayer = status.getPieceCapturedByPlayer(); // NullPointerException
//        char pieceCapturedByComputer = status.getPieceCapturedByComputer(); // NullPointerException

        Reposition move = new Reposition(gameId, turn, x,
                y, x_1, y_1, 'K', ' ', ' ');
        return move;
    }



}

