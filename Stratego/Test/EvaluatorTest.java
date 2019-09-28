import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class EvaluatorTest {

    @Test
    public void EvenStartingPositionShouldReturn0Score() {
        Board board = new Board();
            try {
                board.initializeGameboard();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            BoardEvaluator evaluator = new BoardEvaluator(new SimulationBoard(board));
            double score = evaluator.evaluate('B');
        // assert statements
        assertEquals(0, score, "board position should be even");
    }

    @Test
    public void RedOnlyFlagShouldReturnPosScore() {
        Board board = new Board();
        try {
            board.initializeGameboard("redlose.txt","board1.txt");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        board.displayGameBoard();

        BoardEvaluator evaluator = new BoardEvaluator(new SimulationBoard(board));
        double score = evaluator.evaluate('B');
        System.out.println("Score:"+score);// score should be 0
        // assert statements
        assertTrue(score>0,"score should be positive");
    }

    @Test
    public void RedOnly10ShouldReturnPosScore() {
        Board board = new Board();
        try {
            board.initializeCustomGameboard("board3.txt", "board3color.txt");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        board.displayGameBoard();

        BoardEvaluator evaluator = new BoardEvaluator(new SimulationBoard(board));
        double score = evaluator.evaluate('B');
        System.out.println("Score:"+score);// score should be 0
        // assert statements
        assertTrue(score>0,"score should be positive");
    }

    @Test
    public void PossibleMoveTestfor2ReturnPosScore() {
        Board board = new Board();
        try {
            board.initializeCustomGameboard("board3.txt", "board3color.txt");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        AI ai = new AI();
        SimulationMove move = ai.AI_Move(board,'B');
        board.displayGameBoard();
        System.out.println(String.format("move %s to %d,%d",move.getStarting_piece().getUnit(),move.getEnd_x(),move.getEnd_y() ));// score should be 0
        // assert statements
//        assertTrue(score>0,"score should be positive");
    }
    @Test
    public void PossibleMoveTestfor2AllClear() {
        Board board = new Board();
        try {
            board.initializeCustomGameboard("board3.txt", "board3color.txt");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        board.displayGameBoard();

        AI ai = new AI();
        SimulationBoard simulationBoard = new SimulationBoard(board);
        List<SimulationMove> all_moves =  ai.calculate_all_possible_moves(simulationBoard,'B',3,1);
        System.out.println(all_moves);

        // assert statements
        assertEquals(all_moves.size(),2*9,"not all moves considered");

        // all pieces
        all_moves =  ai.calculate_all_possible_moves(simulationBoard,'B');
        System.out.println(all_moves);

        // assert statements
        assertEquals(all_moves.size(),2*9+2*4,"not all moves considered");
    }

    @Test
    public void PossibleMoveTestfor2EnemyBlocking() {
        Board board = new Board();
        try {
            board.initializeCustomGameboard("board3.txt", "board3color.txt");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        BoardPiece p = board.getPiece(3,3);
        p.setColor('R');
        p.setUnit('2');
        board.displayGameBoard();

        AI ai = new AI();
        SimulationBoard simulationBoard = new SimulationBoard(board);
        List<SimulationMove> all_moves =  ai.calculate_all_possible_moves(simulationBoard,'B',3,1);
        System.out.println(all_moves);

        // assert statements
        assertEquals(9+3,all_moves.size(),"not all moves considered");
    }
    public void PossibleMoveTestfor2FriendBlocking() {
        Board board = new Board();
        try {
            board.initializeCustomGameboard("board3.txt", "board3color.txt");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        BoardPiece p = board.getPiece(3,3);
        p.setColor('B');
        p.setUnit('2');
        board.displayGameBoard();

        AI ai = new AI();
        SimulationBoard simulationBoard = new SimulationBoard(board);
        List<SimulationMove> all_moves =  ai.calculate_all_possible_moves(simulationBoard,'B',3,1);
        System.out.println(all_moves);

        // assert statements
        assertEquals(9+2,all_moves.size(),"not all moves considered");
    }
    @Test
    public void PossibleMoveTestfor2RiverBlocking() {
        Board board = new Board();
        try {
            board.initializeCustomGameboard("board3.txt", "board3color.txt");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        BoardPiece p = board.getPiece(3,3);
        p.setColor('B');
        p.setUnit('2');
        board.displayGameBoard();

        AI ai = new AI();
        SimulationBoard simulationBoard = new SimulationBoard(board);
        List<SimulationMove> all_moves =  ai.calculate_all_possible_moves(simulationBoard,'B',3,3);
        System.out.println(all_moves);

        // assert statements
        assertEquals(7+3,all_moves.size(),"not all moves considered");
    }
    @Test
    public void NoPossibleMoveTestfor2() {
        Board board = new Board();
        try {
            board.initializeCustomGameboard("board3.txt", "board3color.txt");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        BoardPiece p = board.getPiece(3,3);
        p.setColor('B');
        p.setUnit('2');
        BoardPiece p1 = board.getPiece(3,2);
        p1.setColor('B');
        p1.setUnit('2');

        p1 = board.getPiece(2,2);
        p1.setColor('B');
        p1.setUnit('2');

        board.displayGameBoard();

        AI ai = new AI();
        SimulationBoard simulationBoard = new SimulationBoard(board);
        List<SimulationMove> all_moves =  ai.calculate_all_possible_moves(simulationBoard,'B',3,2);
        System.out.println(all_moves);

        // assert statements
        assertEquals(0,all_moves.size(),"not all moves considered");
    }

    @Test
    public void BestMoveTestfor2() {
        Board board = new Board();
        try {
            board.initializeCustomGameboard("board3.txt", "board3color.txt");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        BoardPiece p = board.getPiece(3,3);
        p.setColor('B');
        p.setUnit('4');
        BoardPiece p1 = board.getPiece(3,2);
        p1.setColor('B');
        p1.setUnit('3');

        p1 = board.getPiece(2,2);
        p1.setColor('R');
        p1.setUnit('2');

        board.displayGameBoard();

        AI ai = new AI();
        SimulationMove move = ai.AI_Move(board,'R');
        System.out.println(move);

        // assert statements
        assertEquals(2,move.getEnd_x(),"not all moves considered");
        assertTrue(move.getEnd_y()>=8,"not all moves considered");
    }

    @Test
    public void gameShouldEndedTest() {
        Board board = new Board();
        try {
            board.initializeCustomGameboard("board3.txt", "board3color.txt");
        }
        catch (Exception e){
            e.printStackTrace();
        }
//        BoardPiece p = board.getPiece(3,3);
//        p.setColor('B');
//        p.setUnit('4');
        BoardPiece p1 = board.getPiece(3,2);
        p1.setColor('B');
        p1.setUnit('3');

//        p1 = board.getPiece(2,2);
//        p1.setColor('R');
//        p1.setUnit('2');

        BoardPiece p = board.getPiece(9,7);
        p.setUnit('3');
        p.setColor('R');
        board.displayGameBoard();

        p = board.getPiece(9,8);
        p.setUnit('3');
        p.setColor('B');
        board.displayGameBoard();

        AI ai = new AI();
        SimulationMove move = ai.AI_Move(board,'R',2);
        System.out.println(move);

        // assert statements
        assertEquals(9,move.getEnd_x(),"not all moves considered");
    }

    @Test
    public void startingPositionAI_Move() {
        Board board = new Board();
        try {
            board.initializeGameboard();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        board.displayGameBoard();
//        BoardEvaluator evaluator = new BoardEvaluator(new SimulationBoard(board));
//        double score = evaluator.evaluate('B');
//         assert statements
//        assertEquals(0, score, "board position should be even");
        while(board.gameWinner!=1){

        AI ai = new AI();
        SimulationMove move = ai.AI_Move(board,'R');
        board.move(move.getStart_x(),move.getStart_y(),move.getEnd_x(),move.getEnd_y(),move.getColor());
        board.displayGameBoard();
        move = ai.AI_Move(board,'B');
        board.move(move.getStart_x(),move.getStart_y(),move.getEnd_x(),move.getEnd_y(),move.getColor());
        board.displayGameBoard();
        }

//        System.out.println(move);
    }
}