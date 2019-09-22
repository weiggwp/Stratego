package Stratego.logic.src;

import Stratego.board.Move;
import Stratego.board.arrangement;

import java.util.ArrayList;

//a game has a board and moves associated with it
public class Game {
    private Board board;    //a unique board per game
    private ArrayList<Move> moves;  //variable length of moves
    private long GameID; //
    int gameWinner;
    private String err_msg;

    public Game(long id)
    {
        board = new Board();
        moves = new ArrayList<>();
        GameID = id; //
        gameWinner = 0;
        err_msg = "";
        load_board();
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
    public String move(int startingX, int startingY, int endingX, int endingY,char color){

        System.out.println();System.out.println();
        board.displayGameBoard();

        if (!isLegalMove(startingX,startingY,endingX,endingY,color)) {
            return "illegal";
        }
        int result=attack(board.getPieceAtLocation(startingX,startingY).getUnit(),board.getPieceAtLocation(endingX,endingY).getUnit());
        if (result==0){
            char a = board.getPieceAtLocation(endingX,endingY).getUnit();
            //char a = gameboard[endingX][endingY].getUnit();
            //gameboard[endingX][endingY].newPiece(gameboard[startingX][startingY]);
            board.redefinePieceInfo(startingX,startingY, endingX,endingY);
            board.clearPieceInfo(startingX,startingY);//gameboard[startingX][startingY].reset();

            return "win "+a; // mover wins
        }
        else if (result==1){
            char a = board.getPieceAtLocation(startingX,startingY).getUnit();
                    //gameboard[startingX][startingY].getUnit();
            board.clearPieceInfo(startingX,startingY);
            //gameboard[startingX][startingY].reset();
            return "lose "+a; // mover's opponent wins
        }
        else if (result==2){
            //tie and clear both
            char a = board.getPieceAtLocation(endingX,endingY).getUnit();
                    //gameboard[endingX][endingY].getUnit();
            board.clearPieceInfo(startingX,startingY);
            board.clearPieceInfo(endingX,endingY);
            //gameboard[startingX][startingY].reset();
           // gameboard[endingX][endingY].reset();


            return "draw " +a;
        }
        else if (result==3){

            gameWinner=1;
            return "flag"; //mover wins the videogame
        }
        if (result==4){
            board.redefinePieceInfo(startingX,startingY,endingX,endingY);
            //gameboard[endingX][endingY].newPiece(gameboard[startingX][startingY]);
            board.clearPieceInfo(startingX,startingY);
            //gameboard[startingX][startingY].reset();
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

        /*if (gameboard[startingX][startingY].getColor()!=color) {
            System.out.println("invalid starting color");
            return false;
        }//if moving a piece not owned by player...*/
        //if (gameboard[endingX][endingY].isLake()){
        if(board.getPieceAtLocation(endingX,endingY).isLake())
        {
            err_msg="cant move to lake";
            System.out.println("cant move to lake");
            return false;
        }
        //if moving into lake...
        //if (gameboard[endingX][endingY].getColor()==gameboard[startingX][startingY].getColor()) {
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
