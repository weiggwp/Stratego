import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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
//        displayGameBoard();

        BoardEvaluator evaluator = new BoardEvaluator(new SimulationBoard(board));
        double score = evaluator.evaluate('B');
//        System.out.println("Score:"+score);// score should be 0
        // assert statements
        assertTrue(score>0,"score should be positive");
    }


}