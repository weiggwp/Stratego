import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

public class AI {

    public AI() {
    }

    public SimulationMove AI_Move(Board board, char player_color){
        SimulationBoard simulationBoard = new SimulationBoard(board);

        List<SimulationMove> all_moves = calculate_all_possible_moves(simulationBoard,player_color);
        System.out.println("****************1");
        int depth = 3;
        double max_score = Integer.MIN_VALUE;
        SimulationMove best_move = null;

        for (SimulationMove move : all_moves) {
            simulationBoard.move(move.getStart_x(), move.getStart_y(), move.getEnd_x(), move.getEnd_y(), player_color);
            // get the score for current move
            double score = negamax(simulationBoard, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, player_color);
            System.out.println("****************2");

            if (score > max_score) {
                max_score = score;
                best_move = move;
            }
            simulationBoard.undo_move();
        }

        //convert simulation move to move
        return best_move;

    }
    public char reverse_player_color(char color){
        if(color=='R'){
            return 'B';
        }
        else
            return 'R';
    }

//    function negamax(node, depth, α, β, color) is
//    if depth = 0 or node is a terminal node then
//        return color × the heuristic value of node
//
//    childNodes := generateMoves(node)
//    childNodes := orderMoves(childNodes)
//    value := −∞
//    foreach child in childNodes do
//    value := max(value, −negamax(child, depth − 1, −β, −α, −color))
//    α := max(α, value)
//        if α ≥ β then
//            break (* cut-off *)
//            return value
    public double negamax(SimulationBoard board, int depth, double alpha, double beta, char player_color){
        if(depth==0 || board.isGameEnded()){
            System.out.println("****************hit depth");

            return new BoardEvaluator(board).evaluate(player_color);
        }
        List<SimulationMove> all_moves = calculate_all_possible_moves(board,player_color);
        double value = Integer.MIN_VALUE;

        for (SimulationMove move : all_moves) {
            board.move(move.getStart_x(), move.getStart_y(), move.getEnd_x(), move.getEnd_y(), player_color);
            // get the score for current move
            value = max(value, -negamax(board, depth-1, -beta, -alpha, reverse_player_color(player_color)));
            alpha = max(alpha, value);
            if (alpha >= beta) {
                break;
            }
            board.undo_move();
        }
        return value;


    }



    /**
     * Generates all the possible moves on the board
     * @param board: current board
     * @param player_color: which player to move
     * @return A hashmap of list of move for each piece
     */
    public List<SimulationMove> calculate_all_possible_moves(SimulationBoard board, char player_color){
        // for each piece in board, if color == player_color -> calculate_possible_moves
        // save in a disctionary
//        HashMap<BoardPiece, List<Move>> move_map = new HashMap<>();
        List<SimulationMove> all_moves = new ArrayList<>();
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                BoardPiece piece = board.getPiece(i,j);
                if(piece.getColor()==player_color){
                    List<SimulationMove> moves = calculate_all_possible_moves(board,player_color,i,j);
                    all_moves.addAll(moves);
//                    move_map.put(piece,moves);
                }
            }
        }

        return all_moves;


    }
    //TODO: Test calculate_all_possible_moves
    public List<SimulationMove> calculate_all_possible_moves(SimulationBoard board, char player_color, int piece_X, int piece_Y){
        // get the piece, move in 4 directions
        // if its a 2/scout, try move further

        List<SimulationMove> moves = new ArrayList<>();
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
                    moves.add(new SimulationMove( piece_X, piece_Y, piece_X_new, piece_Y_new, piece, board.getPiece(piece_X_new,piece_Y_new),player_color));
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
                    moves.add(new SimulationMove( piece_X, piece_Y, piece_X_new, piece_Y_new, piece, board.getPiece(piece_X_new,piece_Y_new),player_color));

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
                    moves.add(new SimulationMove( piece_X, piece_Y, piece_X_new, piece_Y_new, piece, board.getPiece(piece_X_new,piece_Y_new),player_color));

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
                    moves.add(new SimulationMove( piece_X, piece_Y, piece_X_new, piece_Y_new, piece, board.getPiece(piece_X_new,piece_Y_new),player_color));

                }
            }while(unit==2 && piece_Y_new>0);

        }


        return moves;







    }

//    For each move M in All_possible_moves:

}
