package Stratego.logic.src;

import Stratego.board.arrangement;

public class Board {

    private BoardPiece[][] gameboard;
    //int gameWinner=0; //0 means no winner yet
    private arrangement setup;
    private String err_msg;
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
    public void boardAction(int i,char color,BoardPiece one,BoardPiece two)
    {
        char opponent_color = (color=='R')?'B':'R';
        if(i==0)//win
        {
            setup.capturePiece(color,two,one);  //one captures two
        }
        if(i==1)//lose
        {
            setup.capturePiece(opponent_color, one,two);
        }
        if(i==2)//tie
        {
            setup.tiedMove(color,one,two);
        }
        if(i==4)//move
        {
            setup.MovedPiece(color,one,two);
        }
        setup.printRemainingPieces();
        setup.printLocationOfPieces(color);
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
