package Stratego.logic.src;

import Stratego.board.arrangement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Board {

    private BoardPiece[][] gameboard;
    //int gameWinner=0; //0 means no winner yet
    private arrangement setup;
    private String err_msg;

    public BoardPiece getPiece(int i, int j){
        return gameboard[i][j];
    }
    public Board()
    {
        this.setup = new arrangement();
        this.err_msg = "";
        this.gameboard = new BoardPiece[10][10];
        initializeGameboard();
        displayGameBoard();

    }
    public arrangement getSetup()
    {
        return this.setup;
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
               // System.out.println(i+"  "+j);
                gameboard[i][j]=setup.getPiece(i,j);
                        //new BoardPiece(sc.next().charAt(0),'R');
            }
        }
        for (int i=4; i<6; i++){
            for (int j=0; j<10; j++){
                if (j==2||j==3||j==6||j==7){
                    gameboard[i][j]=new BoardPiece('W','0');
                }
                else
                    gameboard[i][j]=new BoardPiece('0','0');
            }
        }

        for (int i=6; i<10; i++){
            for (int j=0; j<10; j++){
                gameboard[i][j]=setup.getPiece(i,j);
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
        gameboard[startingX][startingY] = end;
        gameboard[endingX][endingY] = start;
        System.out.println("swapping "+ start.getUnit()+" with "+end.getUnit());
        displayGameBoard();
    }

    private boolean isDigit(char s){
        if (s>='0'&&s<='9')
            return true;
        return false;
    }


}
