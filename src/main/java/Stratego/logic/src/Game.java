package Stratego.logic.src;

import Stratego.board.Move;
import Stratego.board.Move_status;
import Stratego.board.Round;
import Stratego.board.arrangement;

import java.util.ArrayList;
import java.util.HashMap;

//a game has a board and moves associated with it
public class Game {
    private Board board;    //a unique board per game
    private ArrayList<Round> moves;  //variable length of moves
    private long GameID; //
    private int gameWinner;
    private String err_msg;
    private Move current_move;
    private Move_status move_stat;
    private AI ai = new AI();
    //HashMap<String, ArrayList> piecesLost;    - will be updating per move

    public Game(long id)
    {
        board = new Board();
        moves = new ArrayList<>();
        GameID = id; //
        gameWinner = 0;
        err_msg = "";
        load_board();
        move_stat = new Move_status();
       /* piecesLost = new HashMap<>();
        piecesLost.put("R",new ArrayList());
        piecesLost.put("B",new ArrayList());
        */

    }
    public Move_status getMoveInfo()
    {
        return this.move_stat;
    }
    public void setCurrent_move(Move move)
    {
        this.current_move = move;
    }
    public String getErr_msg()
    {
        return this.getErr_msg();
    }
    public int gameEnded()
    {
        return this.gameWinner;
    }

    public Board getBoard(){
        return this.board;
    }

    public arrangement getGameSetup()
    {
        return this.board.getSetup();
    }
    public void load_board()
    {
        board.initializeGameboard();    //protected access, all logic classes can use it
    }

    public void swap(int startingX, int startingY, int endingX, int endingY)
    {
        board.swap(startingX,startingY,endingX,endingY);
    }
    public void madeMove(Round m)    //also should have move status
    {
        this.moves.add(m);
    }
    public boolean madeLoopMove(int startingX, int startingY, int endingX, int endingY,char color)
    {
        //[1,1]->[1,2]      initial
        // [1,2]->[1,1]     second
        // [1,1]-> [1,2] (current attempted move)
        if(moves.size()<2)
            return false;   //need at least 2 moves
        System.out.println("currently in round"+moves.size()+" color:"+color);
        String current_start = startingX+","+startingY;
        String current_end = endingX+","+endingY;
        Move lastMove;
        Move firstMove;
        if(color=='R')  //computer
        {
            lastMove = moves.get(moves.size()-1).getAi(); //get the last valid move user made
            firstMove = moves.get(moves.size()-2).getAi();
        }
        else
        {
            lastMove = moves.get(moves.size()-1).getUser(); //get the last valid move user made
            firstMove = moves.get(moves.size()-2).getUser();
        }

        if(lastMove.movedBack(startingX,startingY,endingX,endingY))
        {
            if(current_start.equals(firstMove.getStart()) && current_end.equals(firstMove.getEnd()))
            {
                return true;
            }
            return false;
        }
        return false;

    }
    public Round makeIllegalMove()
    {
        Round round = new Round();
        round.setUser(current_move);
        round.getUser().setStatus(new Move_status());
        return round;
    }
    public void capture(char color,char piece)
    {
        if(color=='R')//computer
        {
            this.move_stat.setPieceCapturedByComputer(piece);
        }
        else//user
        {
            this.move_stat.setPieceCapturedByPlayer(piece);
        }

    }
    public Move getAIMove(char color){
        SimulationMove sm =ai.AI_Move(board,color);
        Move_status stat = new Move_status();
        stat.setIs_valid_move(true);
        stat.setImage_src(board.getPieceAtLocation(sm.getStart_x(),sm.getStart_y()).getImg_src());
        Move m = new Move(GameID,"ai",sm.getStart_x(),sm.getStart_y(),sm.getEnd_x(),sm.getEnd_y(),stat);
        System.out.println("Ai says move from "+sm.getStart_x()+","+sm.getStart_y()+" to "+sm.getEnd_x()+","+sm.getEnd_y());
        return m;
    }
    public Move_status getMove_stat()
    {
        return this.move_stat;
    }
    public String move(int startingX, int startingY, int endingX, int endingY,char color){

        System.out.println();System.out.println();

        this.move_stat = new Move_status();// make a new reference - not sure if java is by reference or value..
        if (!isLegalMove(startingX,startingY,endingX,endingY,color)) {

            this.move_stat.setError_message(err_msg);   //by default invalid move
            return "illegal";
        }
        int result=attack(board.getPieceAtLocation(startingX,startingY).getUnit(),board.getPieceAtLocation(endingX,endingY).getUnit());
        this.move_stat.setFight_result(result);
        this.move_stat.setIs_valid_move(true);
        BoardPiece you = board.getPieceAtLocation(startingX,startingY);
        this.move_stat.setPiece_name(you.getUnit());
        if (result==0){
            BoardPiece opponent = board.getPieceAtLocation(endingX,endingY);
            capture(color,you.getUnit());   //return unit for now
            String a = color=='B'?opponent.getImg_src():you.getImg_src();//opponent's piece
            System.out.println("returning img src of " +a);
            board.redefinePieceInfo(startingX,startingY, endingX,endingY);
            board.clearPieceInfo(startingX,startingY);//gameboard[startingX][startingY].reset();
            this.move_stat.setImage_src(a);
            board.displayGameBoard();

            return "win "+a; // mover wins
        }
        else if (result==1){
            BoardPiece opponent = board.getPieceAtLocation(endingX,endingY);
            String a = color=='B'?opponent.getImg_src():you.getImg_src();
            capture((color=='R')?'B':'R',you.getUnit());//you got captured by opponent color
                    //board.getPieceAtLocation(startingX,startingY).getImg_src();
            board.clearPieceInfo(startingX,startingY);
            this.move_stat.setImage_src(a);
            System.out.println("src is " +a);
            board.displayGameBoard();
            return "lose "+a; // mover's opponent wins
        }
        else if (result==2){
            //tie and clear both
            BoardPiece opponent = board.getPieceAtLocation(endingX,endingY);
            String a = color=='B'?opponent.getImg_src():you.getImg_src();
                    //board.getPieceAtLocation(endingX,endingY).getImg_src();

            capture(color,opponent.getUnit());//you captured the opponent's unit
            capture((color=='R')?'B':'R',you.getUnit());//opponent captured your piece as well

            board.clearPieceInfo(startingX,startingY);
            board.clearPieceInfo(endingX,endingY);
            this.move_stat.setImage_src(a);
            board.displayGameBoard();
            return "draw " +a;
        }
        else if (result==3){
            board.redefinePieceInfo(startingX,startingY,endingX,endingY);
            board.clearPieceInfo(startingX,startingY);
            gameWinner=1;
            this.move_stat.gameEnded();
            board.displayGameBoard();
            return "flag"; //mover wins the videogame
        }
        if (result==4){
            board.redefinePieceInfo(startingX,startingY,endingX,endingY);
            board.clearPieceInfo(startingX,startingY);
            board.displayGameBoard();
            return "empty"; // mover moved to empty space
        }
        return "This will never happen.";

    }

    /*Returns false on illegal move, true on legal move.*/
    public boolean isLegalMove(int startingX, int startingY, int endingX, int endingY, char color){
        System.out.println("moving "+ color+" from ("+ startingX+","+startingY+") to ("+endingX+","+endingY+")");
        //System.out.println("trying to move " +gameboard[startingX][startingY].getUnit()+" to " +gameboard[endingX][endingY].getUnit());
        if (board.getPieceAtLocation(startingX,startingY).getColor()!=color
                || board.getPieceAtLocation(startingX,startingY).getUnit()=='F'
                || board.getPieceAtLocation(startingX,startingY).getUnit()=='0'
                || board.getPieceAtLocation(startingX,startingY).getUnit()=='X')
        {
            //gameboard[startingX][startingY].getUnit()==color||gameboard[startingX][startingY].getUnit()=='F'||
                //gameboard[startingX][startingY].getUnit()=='0'||gameboard[startingX][startingY].getUnit()=='X') {
            err_msg="invalid starting piece";
            System.out.println("invalid starting piece");
            return false;
        }

        /*if(madeLoopMove(startingX,startingY,endingX,endingY,color))
        {
            err_msg="illegal: making repeated moves";
            System.out.println("illegal: making repeated moves");
            return false;
        }*/

        //TODO: madeLoopMove is currently commented out since no ai moves are made in backend , waiting for ai implementation

        /*if (gameboard[startingX][startingY].getColor()!=color) {
            System.out.println("invalid starting color");
            return false;
        }//if moving a piece not owned by player...*/

        if(board.getPieceAtLocation(endingX,endingY).isLake())
        {
            err_msg="cant move to lake";
            System.out.println("cant move to lake");
            return false;
        }
        //if moving into lake...

        if(board.getPieceAtLocation(endingX,endingY).getColor()==board.getPieceAtLocation(startingX,startingY).getColor())
        {
            err_msg="cant capture friendly unit";
            System.out.println("cant capture friendly unit");
            return false;
        }/* if moving
        onto a space occupied by another piece owned by the player...*/
        if ((Math.abs(startingX-endingX)>=1&&Math.abs(startingY-endingY)>=1)) {
            err_msg="cant move diagonally";
            System.out.println("cant move diagonally");
            return false;
        }
        //if (!gameboard[startingX][startingY].isScout()&&( (Math.abs(startingX-endingX)>=2||Math.abs(startingY-endingY)>=2)
        //)){ //if it moves too far...
        if(!board.getPieceAtLocation(startingX,startingY).isScout() && ( (Math.abs(startingX-endingX)>=2||Math.abs(startingY-endingY)>=2)))
        {
            err_msg="too far, not a scout";
            System.out.println("too far, not a scout");
            return false;
        }
        //if (gameboard[startingX][startingY].isScout() ){ //if a scout moves through a unit or lake...
        if(board.getPieceAtLocation(startingX,startingY).isScout())
        {
            int starting=0;
            int ending =0;
            boolean horizontal=false;
            if (endingX==startingX){
                starting=Math.min(startingY,endingY);
                ending=Math.max(startingY,endingY);
                horizontal=true;
            }
            else{
                starting=Math.min(startingX,endingX);
                ending=Math.max(startingX,endingX);

            }
            System.out.println("horizontal is " + horizontal);
            System.out.println("items are " +startingX+","+startingY+","+endingX+","+endingY);
            for (int i=starting+1; i<ending; i++){

                if (horizontal) {
                    //if (!gameboard[startingX][i].isEmpty() || gameboard[startingX][i].isLake()) {
                    if(!board.getPieceAtLocation(startingX,i).isEmpty() || board.getPieceAtLocation(startingX,i).isLake())
                    {
                        err_msg = "collision at " + startingX + " " + i + "; " + board.getPieceAtLocation(startingX,i).getUnit();
                        System.out.println("collision at " + startingX + " " + i + "; " + board.getPieceAtLocation(startingX,i).getUnit());
                        return false;
                    }
                }
                else {
                    //if (!gameboard[i][startingY].isEmpty() || gameboard[i][startingY].isLake()) {
                    if(!board.getPieceAtLocation(i,startingY).isEmpty()|| board.getPieceAtLocation(i,startingY).isLake()){
                        err_msg = "collision at "+i+" "+startingY+ "; " +board.getPieceAtLocation(i,startingY).getUnit();
                        System.out.println("collision at " + i + " " + startingY + "; " + board.getPieceAtLocation(i,startingY).getUnit());

                        return false;
                    }
                }
            }
        }


        err_msg = "";
        return true;
    }
    /*Returns 0 on 1 wins, 1 on 2 wins, 2 on draw, 3 on flag capture, 4 on moving to blank space*/
    public int attack(char unit1, char unit2){
        if (unit2=='0') return 4;
        if (unit1=='3'&&unit2=='B') return 0;
        if (unit1=='1'&&unit2=='M') return 0;
        if (unit2=='F') {

            return 3;
        }
        if (unit2=='B') return 1;
        if (unit1=='M'&&unit2=='M') return 2;
        if (unit1=='M') return 0;
        if (unit1==unit2) return 2;
        if (unit1>unit2) return 0;
        return 1;
    }
}
