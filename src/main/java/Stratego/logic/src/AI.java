package Stratego.logic.src;


import Stratego.board.Move;

import java.util.ArrayList;
import java.util.List;

public class AI {

    public AI() {
    }

    public Move AI_Move(Board board, char player_color){


    }

    public List<Move> calculate_all_possible_moves(Board board, char player_color){
        // for each piece in board, if color == player_color -> calculate_possible_moves


    }

    public List<Move> calculate_all_possible_moves(Board board, char player_color, int piece_X, int piece_Y){
        // get the piece, move in 4 directions
        // if its a 2/scout, try move further

        List<Move> moves = new ArrayList<>();
        //piece to move
        BoardPiece piece = board.getPiece(piece_X,piece_Y);

        char unit = piece.getUnit();

        // if bomb or flag return
        if (unit=='B'|| unit=='F'){
            return moves;
        }

        //destination
        int piece_X_new;
        int piece_Y_new;

        //move left
        if (piece_X>0){
            piece_X_new = piece_X;
            piece_Y_new = piece_Y;
            // perfrom once if not scout, or as many as possible
            do {
                piece_X_new--;
                boolean valid_move = board.isLegalMove(piece_X, piece_Y, piece_X_new, piece_Y_new, player_color);
                if (valid_move) {
                    moves.add(new Move(player_color, piece_X, piece_Y, piece_X_new, piece_Y_new));
                }
            }while(unit==2 && piece_X_new>0);
        }

        //move right
        if (piece_X<9){
            piece_X_new = piece_X;
            piece_Y_new = piece_Y;

            // perfrom once if not scout, or as many as possible
            do {
                piece_X_new++;
                boolean valid_move = board.isLegalMove(piece_X, piece_Y, piece_X_new, piece_Y_new, player_color);
                if (valid_move) {
                    moves.add(new Move(player_color, piece_X, piece_Y, piece_X_new, piece_Y_new));
                }
            }while(unit==2 && piece_X_new<9);

        }

        //move up
        if (piece_Y<9){
            piece_X_new = piece_X;
            piece_Y_new = piece_Y;

            // perfrom once if not scout, or as many as possible
            do {
                piece_Y_new++;
                boolean valid_move = board.isLegalMove(piece_X, piece_Y, piece_X_new, piece_Y_new, player_color);
                if (valid_move) {
                    moves.add(new Move(player_color, piece_X, piece_Y, piece_X_new, piece_Y_new));
                }
            }while(unit==2 && piece_Y_new<9);


        }

        //move down
        if (piece_Y>0){
            piece_X_new = piece_X;
            piece_Y_new = piece_Y;

            // perfrom once if not scout, or as many as possible
            do {
                piece_Y_new--;
                boolean valid_move = board.isLegalMove(piece_X, piece_Y, piece_X_new, piece_Y_new, player_color);
                if (valid_move) {
                    moves.add(new Move(player_color, piece_X, piece_Y, piece_X_new, piece_Y_new));
                }
            }while(unit==2 && piece_Y_new>0);

        }


        return moves;









    }

//    For each move M in All_possible_moves:

}
