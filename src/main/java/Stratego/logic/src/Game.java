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
    private ArrayList<Move> moves;  //variable length of moves
    private long GameID; //
    private int gameWinner;
    private String err_msg;

    private AI ai;
    //HashMap<String, ArrayList> piecesLost;    - will be updating per move

    public Game(long id)
    {
        board = new Board();
        moves = new ArrayList<Move>();
        GameID = id; //
        gameWinner = 0;
        err_msg = "";
        load_board();
        ai = new AI();
        //move_stat = new Move_status();
       /* piecesLost = new HashMap<>();
        piecesLost.put("R",new ArrayList());
        piecesLost.put("B",new ArrayList());
        */

    }

    public long getGameID(){return this.GameID;}
    public String getErr_msg()
    {
        return this.getErr_msg();
    }
    public int gameEnded()
    {
        return this.gameWinner;
    }
    public void setGameEnded(int i){
        gameWinner=i;
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
    public void madeMove(Move m)    //also should have move status
    {
        this.moves.add(m);
    }
    public boolean madeLoopMove(int startingX, int startingY, int endingX, int endingY,char color)
    {
        //[1,1]->[1,2]      initial
        // [1,2]->[1,1]     second
        // [1,1]-> [1,2] (current attempted move)
        if(moves.size()<(2*3))//6 moves
            return false;   //need at least 2 rounds of moves
        String current_start = startingX+","+startingY;
        String current_end = endingX+","+endingY;

        Move lastMove = moves.get(moves.size()-2); //get the last valid move this person made
        Move secondMove = moves.get(moves.size()-4);
        Move firstMove = moves.get(moves.size()-6);
        if(lastMove.movedBack(startingX,startingY,endingX,endingY)
        && secondMove.movedBack(lastMove.getStart_x(),lastMove.getStart_y(),lastMove.getEnd_x(),lastMove.getEnd_y())
        && firstMove.movedBack(secondMove.getStart_x(),secondMove.getStart_y(),secondMove.getEnd_x(),secondMove.getEnd_y()))
        {
//            if(current_start.equals(firstMove.getStart()) && current_end.equals(firstMove.getEnd()))
//            {
//                return true;
//            }
//            return false;
            return true;
        }
        return false;

    }
    public Move makeIllegalMove(Move m)
    {
        m.setStatus(new Move_status(this.err_msg));
        return m;
    }
    public void capture(Move_status move_stat,char color,char piece)
    {
        if(color=='R')//computer
        {
            move_stat.setPieceCapturedByComputer(piece);
        }
        else//user
        {
            move_stat.setPieceCapturedByPlayer(piece);
        }

    }
    /*
    Calls ai's ai move method to evaluate and return best move
    validate move and set status and add move to the list
     */
    public Move getAIMove(char color){
        SimulationMove sm =ai.AI_Move(board,color,moves);
        //Move_status stat = new Move_status();
        //stat.setIs_valid_move(true);
        //stat.setImage_src(board.getPieceAtLocation(sm.getStart_x(),sm.getStart_y()).getImg_src());
        Move m = new Move(GameID,sm.getStart_x(),sm.getStart_y(),sm.getEnd_x(),sm.getEnd_y(),color,null);
        Move_status stat = move(m);
        m.setStatus(stat);
        this.moves.add(m);  //made a move
        return m;
    }
    //public Move_status getMove_stat()
    public Move_status move(Move move)   //front-end JSON for player, and backend ai prefills field
    {
        //doesnt need to return a string
        int startingX = move.getStart_x();
        int startingY = move.getStart_y();
        int endingX = move.getEnd_x();
        int endingY = move.getEnd_y();
        char color = move.getColor();
        Move_status move_stat = new Move_status("");
        //move.setStatus(move_stat);

//        System.out.println();System.out.println();

        if (!isLegalMove(startingX,startingY,endingX,endingY,color)) {

            move_stat.setError_message(err_msg);   //by default invalid move
//            System.out.println(err_msg);
            return move_stat;
            //return "illegal";
        }
        int result=attack(board.getPieceAtLocation(startingX,startingY).getUnit(),board.getPieceAtLocation(endingX,endingY).getUnit());
        move_stat.setFight_result(result);
        move_stat.setIs_valid_move(true);

        BoardPiece you = board.getPieceAtLocation(startingX,startingY);
        BoardPiece opponent = board.getPieceAtLocation(endingX,endingY);

        move_stat.setPiece_name(you.getUnit());
        board.boardAction(result,color,you,opponent);//do it after the pieces have updated

        //you are the attacker, and the opponent the defender
        if (result==0){//winning case

            capture(move_stat,color,opponent.getUnit());   //return unit for now
            String a = color=='B'?opponent.getImg_src():you.getImg_src();//opponent's piece
            //String a = you.getImg_src();

            move_stat.setImage_src(a);

            board.redefinePieceInfo(startingX,startingY, endingX,endingY);
            board.setPieceRevealed(endingX,endingY,true);
            board.clearPieceInfo(startingX,startingY);//gameboard[startingX][startingY].reset();
            //return "win "+a; // mover wins
        }
        else if (result==1){
            //lose
            //String a = opponent.getImg_src();//opponent's piece
            String a = color=='B'?opponent.getImg_src():you.getImg_src();
            capture(move_stat,(color=='R')?'B':'R',you.getUnit());//you got captured by opponent color

                    //board.getPieceAtLocation(startingX,startingY).getImg_src();
            move_stat.setImage_src(a);
//            System.out.println("winning source: "+a);
            board.setPieceRevealed(endingX,endingY,true);
            board.clearPieceInfo(startingX,startingY);
            //return "lose "+a; // mover's opponent wins
        }
        else if (result==2){
            //tie and clear both
//            BoardPiece opponent = board.getPieceAtLocation(endingX,endingY);
           // String a = opponent.getImg_src();

                    //board.getPieceAtLocation(endingX,endingY).getImg_src();
            String a = color=='B'?opponent.getImg_src():you.getImg_src();
            capture(move_stat,color,opponent.getUnit());//you captured the opponent's unit
            capture(move_stat,(color=='R')?'B':'R',you.getUnit());//opponent captured your piece as well
            move_stat.setImage_src(a);
            board.clearPieceInfo(startingX,startingY);
            board.clearPieceInfo(endingX,endingY);

        }
        else if (result==3){
            capture(move_stat,color,opponent.getUnit());
            board.redefinePieceInfo(startingX,startingY,endingX,endingY);//captured flag
            board.setPieceRevealed(endingX,endingY,true);
            board.clearPieceInfo(startingX,startingY);
            gameWinner=1;
            move_stat.gameEnded();
            move_stat.setGame_result("win");
           // return "flag"; //mover wins the videogame

        }
        else if (result==4){
            board.redefinePieceInfo(startingX,startingY,endingX,endingY);
            board.clearPieceInfo(startingX,startingY);
            //return "empty"; // mover moved to empty space

        }

        char opponent_color = (color=='R')?'B':'R';
        boolean you_can_move = hasMovable(color);
        boolean oppo_can_move = hasMovable(opponent_color);
        if(!you_can_move&&!oppo_can_move)
            //both are not movable
        {
            move_stat.gameEnded();
            move_stat.setGame_result("draw");
        } else if (!you_can_move) {
            //your color doesnt have movable
            if(!board.canWin(opponent_color))
            {//opponent cannot capture you
                move_stat.gameEnded();
                move_stat.setGame_result("draw");
            }
            else
            {
                move_stat.gameEnded();
                move_stat.setGame_result("lost");
//                System.out.println("you have no movable pieces, and you've lost");

            }
        }
        else if(!oppo_can_move)
        {
            if(!board.canWin(color))
            {//opponent cannot capture you
                move_stat.gameEnded();
                move_stat.setGame_result("draw");
            }
            else
                {
                move_stat.gameEnded();
                move_stat.setGame_result("win");
            }
        }
        else if(!board.canWin(color) && !board.canWin(opponent_color))//if you cant win
        {
            //if both cant win
            move_stat.gameEnded();
            move_stat.setGame_result("draw");
        }
        //if either party cannot win, he/she can still strive to get a draw
        board.displayGameBoard();
        return move_stat;
    }

    //you win if you capture all the opponent's movable pieces
    public boolean hasMovable(char color)
    {

            ArrayList remain = board.getSetup().getPieceList(color); //computer
            for( Object piece : remain)
            {
                BoardPiece p = ((BoardPiece)piece);
                if(isMovable(p.getX(),p.getY()))
                    return true;
                //if has movable, even one, return true, else keep iterating
            }
            return false;
    }
    /*
            if surrounded by friendly pieces includes surrounded by
     */
    public boolean isMovable(int x,int y)
    {
        BoardPiece piece = board.getPieceAtLocation(x,y);
        //bombs and flag are unmovable, no need to check
        if(piece.getUnit()=='B' || piece.getUnit()=='F')
            return false;
        if(x>0)//check top
        {

            if(isLegalMove(x,y,x-1,y,piece.getColor()))
                return true;    //can move

        }
        if(x<9)//check bot
        {
            if(isLegalMove(x,y,x+1,y,piece.getColor()))
                return true;
        }
        if(y>0)//check left
        {
            if(isLegalMove(x,y,x,y-1,piece.getColor()))
                return true;
        }
        if(y<9)
        {
            if(isLegalMove(x,y,x,y+1,piece.getColor()))
                return true;

        }
        return false;//if none of the moves are valid,
    }
//    public boolean isLegalMove(int startingX, int startingY, int endingX, int endingY, char color)
//    {
//        return isLegalMove(board,startingX,startingY,endingX,endingY,color);
//    }
    /*Returns false on illegal move, true on legal move.*/
    public boolean isLegalMove( int startingX, int startingY, int endingX, int endingY, char color){
        if (//board.getPieceAtLocation(startingX,startingY).getColor()!=color
                 board.getPieceAtLocation(startingX,startingY).getUnit()=='F'
                || board.getPieceAtLocation(startingX,startingY).getUnit()=='0'
                || board.getPieceAtLocation(startingX,startingY).getUnit()=='X')
        {
            //gameboard[startingX][startingY].getUnit()==color||gameboard[startingX][startingY].getUnit()=='F'||
                //gameboard[startingX][startingY].getUnit()=='0'||gameboard[startingX][startingY].getUnit()=='X') {
            err_msg="invalid starting piece";
            return false;
        }

        if(madeLoopMove(startingX,startingY,endingX,endingY,color))
        {
            err_msg="illegal: making repeated moves";
            return false;
        }



        if(board.getPieceAtLocation(endingX,endingY).isLake())
        {
            err_msg="cant move to lake";
            return false;
        }
        //if moving into lake...

        if(board.getPieceAtLocation(endingX,endingY).getColor()==board.getPieceAtLocation(startingX,startingY).getColor())
        {
            err_msg="cant capture friendly unit";
            return false;
        }/* if moving
        onto a space occupied by another piece owned by the player...*/
        if ((Math.abs(startingX-endingX)>=1&&Math.abs(startingY-endingY)>=1)) {
            err_msg="cant move diagonally";
            return false;
        }
        if(!board.getPieceAtLocation(startingX,startingY).isScout() && ( (Math.abs(startingX-endingX)>=2||Math.abs(startingY-endingY)>=2)))
        {
            err_msg="too far, not a scout";
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
            for (int i=starting+1; i<ending; i++){

                if (horizontal) {
                    //if (!gameboard[startingX][i].isEmpty() || gameboard[startingX][i].isLake()) {
                    if(!board.getPieceAtLocation(startingX,i).isEmpty() || board.getPieceAtLocation(startingX,i).isLake())
                    {
                        err_msg = "collision ahead";
//                        System.out.println("collision at " + startingX + " " + i + "; " + board.getPieceAtLocation(startingX,i).getUnit());
                        return false;
                    }
                }
                else {
                    //if (!gameboard[i][startingY].isEmpty() || gameboard[i][startingY].isLake()) {
                    if(!board.getPieceAtLocation(i,startingY).isEmpty()|| board.getPieceAtLocation(i,startingY).isLake()){
                        err_msg = "collision ahead";
//                        System.out.println("collision at " + i + " " + startingY + "; " + board.getPieceAtLocation(i,startingY).getUnit());

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
