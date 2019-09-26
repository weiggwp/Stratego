import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;

/**
 * @author: Wei & ChungWai
 * + score for blue team advantage
 */


public class BoardEvaluator {
    public static final double WEIGHT_PIECES       = .5;
    public static final double WEIGHT_ADVANCEMENT  = .1;
    public static final double WEIGHT_FLAG_SAFETY  = .3;
    public static final double WEIGHT_AWARENESS    = .1;

    private static final char RED = 'R', BLUE = 'B', DRAW='D';
    private static final char[] teams = new char[]{RED,BLUE};
    private static final char MARSHAL = 'M', GENERAL = '9', COLONEL ='8', MAJOR ='7', LIEUTENANT ='6', CAPTAIN ='5',
                             SERGEANT ='4', MINER ='3', SCOUT ='2', SPY ='1', BOMB ='B', FLAG ='F',EMPTY = '0';

    private static final String PIECES = "pieces", COUNTS = "piece counts";
    //can be used to get total pieces counts, what pieces are remaining.
    private HashMap<Character, HashMap> team_info = new HashMap<>();

    private HashMap<Character, HashMap> pieces_values = new HashMap<>();
    private HashMap<Character, Double> piece_default_values = new HashMap<Character, Double>(){{
        put(MARSHAL,400.0);
        put(GENERAL,200.0);
        put(COLONEL,100.0);
        put(MAJOR,75.0);
        put(LIEUTENANT,50.0);
        put(CAPTAIN,25.0);
        put(SERGEANT,15.0);
        put(MINER,25.0);
        put(SCOUT,30.0);
        put(SPY,200.0);
        put(BOMB,20.0);
        put(FLAG,10000.0);
        put(EMPTY,0.0);
    }};
    private SimulationBoard board;



    public BoardEvaluator(SimulationBoard board) {
        this.board=board;
        for(char team:teams){
            team_info.put(team,new HashMap<String, Object>());
            team_info.get(team).put(PIECES,new ArrayList<BoardPiece>());
            team_info.get(team).put(COUNTS,new HashMap<Character, Integer>());

            pieces_values.put(team, (HashMap) piece_default_values.clone());
        }

    }

    public double evaluate(char color){
        extract_info();
        regenerate_pieces_values();

        double score = calcuate_overall_score();

        return (color==BLUE)?score:-score;
    }

    private void extract_info(){
        for (int row = 0; row < SimulationBoard.BOARD_SIZE[0]; row ++) {
            for (int col = 0; col < SimulationBoard.BOARD_SIZE[1]; col++) {
                BoardPiece piece = board.getPiece(row, col);

                char color = piece.getColor();
                char unit = piece.getUnit();

                //store pieces
                if(color==RED || color == BLUE){
//                    System.out.println("****************"+color);

                    HashMap info = team_info.get(color);
//                    System.out.println("****************"+info);
                    ArrayList<BoardPiece> pieces = (ArrayList<BoardPiece>) info.get(PIECES);
                    pieces.add(piece);

                    // store pieces counts
                    HashMap<Character, Integer> count_table = (HashMap<Character, Integer>) info.get(COUNTS);
                    int new_count = count_table.getOrDefault(unit, 0) + 1;
                    count_table.put(unit, new_count);
                }


            }
        }
    }
    public void regenerate_pieces_values(){
        // update pieces_value
        for (char team : teams){
            HashMap<Character, Double> value_table = pieces_values.get(team);
//            First feature is multiplying the value of the Marshal (both player and opponent) with 0.8 if the
//            opponent has a Spy on the game board.
            char other_team = switch_player(team);
            if( ( (int)((HashMap)team_info.get(other_team).get(COUNTS)).get('1')) >0 ){
                double M_value = piece_default_values.get(MARSHAL);
                value_table.put(MARSHAL,M_value /2);
            }
            HashMap<Character, Integer> count_table = (HashMap) team_info.get(team).get(COUNTS);
            //• Second feature multiplies the value of the Miners with 4 − #lef t if the number of Miners is less
            //            than three.
            int miner_count = count_table.get(MINER);
            if (miner_count<4){
                double miner_value = piece_default_values.get(MINER);
                value_table.put(MINER, miner_value*(4-miner_count));
            }

            //• Third feature sets the value of the Bomb to half the value of the piece with the highest value.
            // find the piece with highest rank
            char highest = '0';
            char[] ranks = {MARSHAL,GENERAL,COLONEL,MAJOR,LIEUTENANT,CAPTAIN,SERGEANT,MINER,SCOUT,SPY};
            for(char rank :ranks){
                if(count_table.get(rank) !=0){
                    highest=rank;
                    break;
                }
            }
            double highest_value = value_table.get(highest);
            value_table.put(BOMB,highest_value/2);

            //• Fourth feature sets divides the value of the Marshal by two if the opponent has a Spy on the
            //            board.
            // done in first feature


            //• Fifth feature gives a penalty to pieces that are known to the opponent
            // implement this elsewhere

            //• Sixth feature increases the value of a piece when the player has a more pieces of the same type
            //            than the opponent.

        }
    }
    public char switch_player(char color){
        if(color== RED){
            return BLUE;
        }
        else
            return RED;
    }

    public double calcuate_overall_score(){
        if( board.isGameEnded()){
            if (board.getWinner() ==BLUE)
            return 1;
            else if (board.getWinner() ==RED)
                return -1;
            else
                return 0;
        }

        double material_score = calculate_material_score();
        double advancement_score = calculate_advancement_score();
        double flag_score = calculate_flag_safety_score();
        double awareness_score = calculate_awareness_score();
        System.out.println(material_score);
        System.out.println(advancement_score);
        System.out.println(flag_score);
        System.out.println(awareness_score);

        return WEIGHT_PIECES * material_score +
                WEIGHT_ADVANCEMENT * advancement_score +
                WEIGHT_FLAG_SAFETY * flag_score +
                WEIGHT_AWARENESS * awareness_score;
    }

    /**
     * revealed: -2 moved: -1 else: 2
     * @return
     */
    private double calculate_awareness_score(){
        HashMap<Character, Double> sums = new HashMap<>();

        //get # of revealed pieces and number of moved pieces
        for (char team:teams) {
            double sum = 0;
            ArrayList<BoardPiece> pieces = (ArrayList<BoardPiece>) team_info.get(team).get(PIECES);
            for (BoardPiece piece : pieces) {
                if(piece.isRevealed())
                    sum -=2; //TODO: maybe consider add value of piece from value_table isntead
                else{
                    if (piece.isMoved()){
                        sum-=1;
                    }
                    else
                        sum+=2;

                }

            }
            sums.put(team, sum);

        }
        double blue_score = sums.get(BLUE);
        double red_score = sums.get(RED);
        return (blue_score-red_score)/(blue_score+red_score);
    }
    public double calculate_material_score(){
        HashMap<Character, Integer> sums = new HashMap<>();

        for (char team : teams){
            int sum = 0;

            HashMap<Character, Integer> count_table = (HashMap<Character, Integer>) this.team_info.get(team).get(COUNTS);
            for (Map.Entry<Character, Integer> entry : count_table.entrySet()) {
                char key = entry.getKey();
                int count = entry.getValue();
                sum+=count*((double)pieces_values.get(team).get(key));
            }
            sums.put(team,sum);
        }

        double blue_score = sums.get(BLUE);
        double red_score = sums.get(RED);
        return (blue_score-red_score)/(blue_score+red_score);
    }

    public double calculate_advancement_score(){
        HashMap<Character, Integer> sums = new HashMap<>();

        for (char team : teams) {
            for (int row = 0; row < SimulationBoard.BOARD_SIZE[0]; row ++) {
                for (int col = 0; col < SimulationBoard.BOARD_SIZE[1]; col++) {
                    BoardPiece piece = board.getPiece(row, col);

                    char color = piece.getColor();
                    char unit = piece.getUnit();

                    int sum = sums.getOrDefault(color, 0);
                    int offset = 0;
                    if (color == BLUE) {
                        offset = SimulationBoard.BOARD_SIZE[0] - 1;
                    }
                    sum += abs(offset - row);
                    sums.put(color,sum);
                }
            }

        }
        double blue_score = sums.get(BLUE);
        double red_score = sums.get(RED);
        return (blue_score-red_score)/(blue_score+red_score);

    }


    public double calculate_flag_safety_score(){
        HashMap<Character, Double> sums = new HashMap<>();
        // find flag
        // for each flag calculate
            for (int row = 0; row < SimulationBoard.BOARD_SIZE[0]; row ++) {
                for (int col = 0; col < SimulationBoard.BOARD_SIZE[1]; col++) {
                    BoardPiece piece = board.getPiece(row, col);

                    char color = piece.getColor();
                    char unit = piece.getUnit();
                    if(unit=='F'){
                        double sum=0;
                        double bomb_score = piece_default_values.get(BOMB);

                        //look left
                        if(col==0){
                            sum += bomb_score*4;
                        }
                        else{
                            BoardPiece p = board.getPiece(row,col-1);
                            double score = safety_score(p,color);
                            sum+=score;
                        }
                        //look right
                        if( col==SimulationBoard.BOARD_SIZE[1]-1){
                            sum += bomb_score*4;
                        }
                        else {
                            BoardPiece p = board.getPiece(row,col+1);
                            double score = safety_score(p,color);
                            sum+=score;
                        }
                        //look up
                        if(row==0 ){
                            sum += bomb_score*4;
                        }
                        else {
                            BoardPiece p = board.getPiece(row,row-1);
                            double score = safety_score(p,color);
                            sum+=score;
                        }

                        //look down
                        if(row==SimulationBoard.BOARD_SIZE[0]-1){
                            sum += bomb_score*4;
                        }
                        else {
                            BoardPiece p = board.getPiece(row,row+1);
                            double score = safety_score(p,color);
                            sum+=score;
                        }
                        //TODO: Consider distance of 2
//                        //look distance of 2, if enermy
//                        int distance = 2;
//                        int[] location = new int[]{row,col};
                        sums.put(color,sum);
                    }


                }

            }
        double blue_score = sums.get(BLUE);
        double red_score = sums.get(RED);
        return (blue_score-red_score)/(blue_score+red_score);



    }
    private double safety_score(BoardPiece p,char color){

        double sum=0;
        double bomb_score = piece_default_values.get(BOMB);
        double flag_score = piece_default_values.get(FLAG);
        if(p.getColor()!=color)
            sum-=flag_score/2;
        else if(p.isEmpty())
            sum-=2*bomb_score;
        else if (p.isRevealed())
            sum-=bomb_score;
        else if (p.isMoved())
            sum-=bomb_score/2;

        if(p.getColor()==color)
            sum += piece_default_values.get(p.getUnit());
        return sum;
    }
    //    private int[][] get_nearly_locations(int[] location, int distance){
//        int[][] new_locations = new int[4][2];
//
//        get_nearly_locations();
//    }

//

}
