package Stratego.logic.src;

import Stratego.board.arrangement;


import java.util.HashMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Board {

    private BoardPiece[][] gameboard;
    private arrangement setup;

    public BoardPiece getPiece(int i, int j){
        return gameboard[i][j];
    }
    public Board()
    {
        this.setup = new arrangement();
        this.gameboard = new BoardPiece[10][10];
        initializeGameboard();
        displayGameBoard();

    }
    public Board(BoardPiece[][] gameboard){
        this.gameboard = gameboard;
    }

    public boolean canWin(char color)//if you have miners left
    {
       char opponent_color = (color=='R')?'B':'R';
       HashMap<Character,Integer> remain = setup.getRemaining(color);
       //get all your remaining pieces count
       if(remain.get('3') ==0 && isFlagSurrounded(opponent_color))
       {
           //you have no miners, and the opponent flag is surrounded by bombs
           return false;
       }
       //if you have no miners, and flag is not surrounded, then you can still win
        //also if you have miners and flag is not even surrounded, you can definitely win..
       return true;

    }

    public boolean isFlagSurrounded(char color)
    {
        BoardPiece flag = this.setup.getFlag(color);
        int x = flag.getX();
        int y = flag.getY();
        if(x>0)//check top
        {
            int top_x = x-1;
            if(gameboard[top_x][y].getUnit()!='B')  //if top is not bomb, it's not surrounded
                return false;
        }
        if(x<9)//check bot
        {
            int bot_x = x+1;
            if(gameboard[bot_x][y].getUnit()!='B')
                return false;
        }
        if(y>0)//check left
        {
            int left_y = y-1;
            if(gameboard[x][left_y].getUnit()!='B')
                return false;
        }
        if(y<9)//check right
        {
            int right_y = y+1;
            if(gameboard[x][right_y].getUnit()!='B')
                return false;

        }
        return true;

    }
    public arrangement getSetup()
    {
        return this.setup;
    }
    public void boardAction(int i,char color,BoardPiece attacker,BoardPiece defender)
    {
        char opponent_color = (color=='R')?'B':'R';
        if(i==0)//win
        {

            setup.capturePiece(color,defender,attacker);  //one captures two
        }
        if(i==1)//lose
        {
            System.out.println(defender.getUnit()+" is capturing "+attacker.getUnit());
            setup.capturePiece(opponent_color, attacker,defender);
        }
        if(i==2)//tie
        {
            setup.tiedMove(color,defender,attacker);
        }
        if(i==4)//move
        {
            setup.MovedPiece(color,attacker,defender);
        }

        //if you won, you're not getting anywhereeee
    }

    public BoardPiece[][] getBoard()
    {
        return this.gameboard;
    }
    protected void clearPieceInfo(int startingX,int startingY)
    {
        this.gameboard[startingX][startingY].reset();
    }
    protected void redefinePieceInfo(int startingX,int startingY, int endingX,int endingY)
    {
        this.gameboard[endingX][endingY].newPiece(this.gameboard[startingX][startingY]);
    }
    protected BoardPiece getPieceAtLocation(int x,int y)
    {
        return this.gameboard[x][y];
    }
    protected void initializeGameboard() {

        for (int i=0; i<4; i++){    //blue set up
            for (int j=0; j<10; j++){
                BoardPiece piece = setup.getPiece(i,j);
                piece.setPlace(i,j);    //set initial coordinate, will need to change when user swaps
                gameboard[i][j]=piece;
            }
        }
        for (int i=4; i<6; i++){
            for (int j=0; j<10; j++){
                if (j==2||j==3||j==6||j==7){
                    BoardPiece piece = new BoardPiece('W','0'); //river piece
                    piece.setPlace(i,j);
                    gameboard[i][j]=piece;

                }
                else
                {
                    BoardPiece piece = new BoardPiece('0','0'); //empty space
                    piece.setPlace(i,j);
                    gameboard[i][j]=piece;
                }

            }
        }

        for (int i=6; i<10; i++){
            for (int j=0; j<10; j++){
                BoardPiece piece = setup.getPiece(i,j);
                piece.setPlace(i,j);    //set initial coordinate, will need to change when user swaps
                gameboard[i][j]=piece;
            }
        }
        initialized = true;
    }


    private boolean initialized=false;
    public boolean isInitialized(){
        return initialized;

    }


    private void initializeCustomGameboard() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("./resources/board2.txt"));
        Scanner sc2 = new Scanner(new File("./resources/board2Colors.txt"));
        //System.out.println(sc.nextLine());
        for (int i=0; i<10; i++) {
            for (int j = 0; j < 10; j++) {
                // System.out.println(i+"  "+j);
                gameboard[i][j] = new BoardPiece(sc.next().charAt(0), sc2.next().charAt(0));
            }
        }
    }


    protected void displayGameBoard(){
        for (int i=0; i<10; i++){
            for (int j=0; j<10; j++){
                if (gameboard[i][j].getColor()=='R') {
                    System.out.print((char)27 + "[34m" );
                    System.out.print(gameboard[i][j].getUnit() + " ");
                }
                else if (gameboard[i][j].getColor()=='B') {
                    System.out.print((char)27 + "[31m" );
                    System.out.print(gameboard[i][j].getUnit() + " ");

                }
                else{
                    System.out.print((char)27 + "[37m" );
                    System.out.print(gameboard[i][j].getUnit() + " ");
                }

            }
            System.out.println();
        }
        System.out.print((char)27 + "[37m" );
}
    public void swap (int startingX, int startingY, int endingX, int endingY)
    {
        System.out.println("swapping "+ startingX +" "+startingY+" with "+ endingX +" "+endingY);
        BoardPiece start = gameboard[startingX][startingY];
        BoardPiece end = gameboard[endingX][endingY];
        start.swapPlaces(end);//swaps both x,y fields
        gameboard[startingX][startingY] = end;
        gameboard[endingX][endingY] = start;
        System.out.println("swapping "+ start.getUnit()+" with "+end.getUnit());
        displayGameBoard();
        setup.printLocationOfPieces('B');
    }

    private boolean isDigit(char s){
        if (s>='0'&&s<='9')
            return true;
        return false;
    }




}
