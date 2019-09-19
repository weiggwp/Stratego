package Stratego.logic.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {

private boolean initialized=false;
public boolean isInitialzied(){
    return initialized;
}
    private void displayGameBoard(){
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

    BoardPiece[][] gameboard= new BoardPiece[10][10];
    int gameWinner=0; //0 means no winner yet
    private char readListItem(String s){
        if (s.endsWith("piece1.png")||s.endsWith("piece21.png"))
            return 'F';
        if (s.endsWith("piece2.png")||s.endsWith("piece22.png"))
            return '1';
        if (s.endsWith("piece3.png")||s.endsWith("piece23.png"))
            return 'M';
        if (s.endsWith("piece4.png")||s.endsWith("piece24.png"))
            return '9';
        if (s.endsWith("piece13.png")||s.endsWith("piece73.png"))
            return '8';
        if (s.endsWith("piece14.png")||s.endsWith("piece74.png"))
            return '7';
        if (s.endsWith("piece15.png")||s.endsWith("piece75.png"))
            return '3';
        if (s.endsWith("piece16.png")||s.endsWith("piece76.png"))
            return '4';
        if (s.endsWith("piece17.png")||s.endsWith("piece77.png"))
            return '5';
        if (s.endsWith("piece18.png")||s.endsWith("piece78.png"))
            return '6';
        if (s.endsWith("piece11.png")||s.endsWith("piece71.png"))
            return 'B';
        if (s.endsWith("piece12.png")||s.endsWith("piece72.png"))
            return '2';
        return '0';
    }

    public void initializeGameboard(ArrayList<String> blue, ArrayList<String> red) throws FileNotFoundException {

        //System.out.println(sc.nextLine());
        for (int i=0; i<4; i++){
            for (int j=0; j<10; j++){
               // System.out.println(i+"  "+j);
                gameboard[i][j]=new BoardPiece(readListItem(blue.get(j+i*10)),'R');
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
                gameboard[i][j]=new BoardPiece(readListItem(red.get(j+(i-6)*10)),'B');
            }
        }
        displayGameBoard();
        initialized=true;
    }

    /*Returns false on illegal move, true on legal move.*/
    public boolean isLegalMove(int startingX, int startingY, int endingX, int endingY, char color){
        System.out.println("trying to move " +gameboard[startingX][startingY].getUnit()+" to " +gameboard[endingX][endingY].getUnit());
        if (gameboard[startingX][startingY].getUnit()==color||gameboard[startingX][startingY].getUnit()=='F'||
                gameboard[startingX][startingY].getUnit()=='0'||gameboard[startingX][startingY].getUnit()=='X') {
            System.out.println("invalid starting piece");
            return false;
        }
        if (gameboard[startingX][startingY].getColor()!=color) {
            System.out.println("invalid starting color");
            return false;
        }//if moving a piece not owned by player...
        if (gameboard[endingX][endingY].isLake()){
            System.out.println("cant move to lake");
            return false;
        }
        //if moving into lake...
        if (gameboard[endingX][endingY].getColor()==gameboard[startingX][startingY].getColor()) {
            System.out.println("cant capture friendly unit");
            return false;
        }/* if moving
        onto a space occupied by another piece owned by the player...*/
        if ((Math.abs(startingX-endingX)>=1&&Math.abs(startingY-endingY)>=1)) {
            System.out.println("cant move diagonally");
            return false;
        }
        if (!gameboard[startingX][startingY].isScout()&&( (Math.abs(startingX-endingX)>=2||Math.abs(startingY-endingY)>=2)
                )){ //if it moves too far...
            System.out.println("too far, not a scout");
            return false;
        }
        if (gameboard[startingX][startingY].isScout() ){ //if a scout moves through a unit or lake...
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
                    if (!gameboard[startingX][i].isEmpty() || gameboard[startingX][i].isLake()) {
                        System.out.println("aacollision at " + startingX + " " + i + "; " + gameboard[startingY][i].getUnit());
                        return false;
                    }
                }
                else {
                    if (!gameboard[i][startingY].isEmpty() || gameboard[i][startingY].isLake()) {
                        System.out.println("bbxcollision at " + i + " " + startingX + "; " + gameboard[i][startingX].getUnit());
                        return false;
                    }
                }
            }
        }



        return true;
    }
    private boolean isDigit(char s){
        if (s>='0'&&s<='9')
            return true;
        return false;
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

    /*Dummy method for UI handling of illegal move*/
    private void illegalMove(){
        System.out.println("Illegal move.");
    }

    /*Attempt to move unit to a tile*/
    public String move(int startingX, int startingY, int endingX, int endingY,char color){

        System.out.println();System.out.println();
        displayGameBoard();

        if (!isLegalMove(startingX,startingY,endingX,endingY,color)) {
            illegalMove();
            return "illegal";
        }
        int result=attack(gameboard[startingX][startingY].getUnit(),gameboard[endingX][endingY].getUnit());
        if (result==0){
            char a = gameboard[endingX][endingY].getUnit();
            gameboard[endingX][endingY].newPiece(gameboard[startingX][startingY]);
            gameboard[startingX][startingY].reset();

            return "win "+a; // mover wins
        }
        else if (result==1){
            char a = gameboard[startingX][startingY].getUnit();
            gameboard[startingX][startingY].reset();
            return "lose "+a; // mover's opponent wins
        }
        else if (result==2){
            char a = gameboard[endingX][endingY].getUnit();
            gameboard[startingX][startingY].reset();
            gameboard[endingX][endingY].reset();
            return "draw " +a;
        }
        else if (result==3){
            gameWinner=1;
            return "flag"; //mover wins the videogame
        }
        if (result==4){
            gameboard[endingX][endingY].newPiece(gameboard[startingX][startingY]);
            gameboard[startingX][startingY].reset();
            return "empty"; // mover moved to empty space
        }
        return "This will never happen.";

    }


}
