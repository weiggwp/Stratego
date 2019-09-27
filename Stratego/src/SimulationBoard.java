public class SimulationBoard {

    public static final int[] BOARD_SIZE = new int[]{10,10};



    private BoardPiece[][] gameboard= new BoardPiece[ BOARD_SIZE[0] ][ BOARD_SIZE[1] ];
    private boolean gameEnded = false;
    private char winner = '0';
    private SimulationMove prev_move = null;

    public SimulationBoard(Board board) {
        for(int i=0;i<BOARD_SIZE[0];i++){
            for(int j=0;j<BOARD_SIZE[1];j++){
                this.gameboard[i][j] = board.getPiece(i,j).clone();

            }
        }
        gameEnded = false;
        winner = '0';
    }

    public BoardPiece[][] getGameboard() {
        return gameboard;
    }

    public void displayGameBoard(){
        for (int i=0; i<10; i++){
            for (int j=0; j<10; j++){
                if (gameboard[i][j].getColor()=='B') {
                    System.out.print((char)27 + "[34m" );
                    System.out.print(gameboard[i][j].getUnit() + " ");
                }
                else if (gameboard[i][j].getColor()=='R') {
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
        System.out.println((char)27 + "[37m" );

    }

    /*Returns false on illegal move, true on legal move.*/
    public boolean isLegalMove(int startingX, int startingY, int endingX, int endingY, char color){
//        System.out.println("moving "+ color+" from ("+ startingX+","+startingY+") to ("+endingX+","+endingY+")");
//        System.out.println("trying to move " +gameboard[startingX][startingY].getUnit()+" to " +gameboard[endingX][endingY].getUnit());
        if (gameboard[startingX][startingY].getUnit()==color||gameboard[startingX][startingY].getUnit()=='F'||
                gameboard[startingX][startingY].getUnit()=='0'||gameboard[startingX][startingY].getUnit()=='X') {
//            System.out.println("invalid starting piece");
            return false;
        }

        /*if (gameboard[startingX][startingY].getColor()!=color) {
            System.out.println("invalid starting color");
            return false;
        }//if moving a piece not owned by player...*/
        if (gameboard[endingX][endingY].isLake()){
//            System.out.println("cant move to lake");
            return false;
        }
        //if moving into lake...
        if (gameboard[endingX][endingY].getColor()==gameboard[startingX][startingY].getColor()) {
//            System.out.println("cant capture friendly unit");
            return false;
        }/* if moving
        onto a space occupied by another piece owned by the player...*/
        if ((Math.abs(startingX-endingX)>=1&& Math.abs(startingY-endingY)>=1)) {
//            System.out.println("cant move diagonally");
            return false;
        }
        if (!gameboard[startingX][startingY].isScout()&&( (Math.abs(startingX-endingX)>=2|| Math.abs(startingY-endingY)>=2)
        )){ //if it moves too far...
//            System.out.println("too far, not a scout");
            return false;
        }
        if (gameboard[startingX][startingY].isScout() ){ //if a scout moves through a unit or lake...
            int starting=0;
            int ending =0;
            boolean horizontal=false;
            if (endingX==startingX){
                starting= Math.min(startingY,endingY);
                ending= Math.max(startingY,endingY);
                horizontal=true;
            }
            else{
                starting= Math.min(startingX,endingX);
                ending= Math.max(startingX,endingX);

            }
//            System.out.println("horizontal is " + horizontal);
//            System.out.println("items are " +startingX+","+startingY+","+endingX+","+endingY);
            for (int i=starting+1; i<ending; i++){

                if (horizontal) {
                    if (!gameboard[startingX][i].isEmpty() || gameboard[startingX][i].isLake()) {
//                        System.out.println("aacollision at " + startingX + " " + i + "; " + gameboard[startingX][i].getUnit());
                        return false;
                    }
                }
                else {
                    if (!gameboard[i][startingY].isEmpty() || gameboard[i][startingY].isLake()) {
//                        System.out.println("bbxcollision at " + i + " " + startingY + "; " + gameboard[i][startingY].getUnit());

                        return false;
                    }
                }
            }
        }


        return true;
    }
//    public static boolean isLegalMove(BoardPiece[][] gameboard, int startingX, int startingY, int endingX, int endingY, char color){
//        System.out.println("moving "+ color+" from ("+ startingX+","+startingY+") to ("+endingX+","+endingY+")");
//        System.out.println("trying to move " +gameboard[startingX][startingY].getUnit()+" to " +gameboard[endingX][endingY].getUnit());
//        if (gameboard[startingX][startingY].getUnit()==color||gameboard[startingX][startingY].getUnit()=='F'||
//                gameboard[startingX][startingY].getUnit()=='0'||gameboard[startingX][startingY].getUnit()=='X') {
//            System.out.println("invalid starting piece");
//            return false;
//        }
//
//        /*if (gameboard[startingX][startingY].getColor()!=color) {
//            System.out.println("invalid starting color");
//            return false;
//        }//if moving a piece not owned by player...*/
//        if (gameboard[endingX][endingY].isLake()){
//            System.out.println("cant move to lake");
//            return false;
//        }
//        //if moving into lake...
//        if (gameboard[endingX][endingY].getColor()==gameboard[startingX][startingY].getColor()) {
//            System.out.println("cant capture friendly unit");
//            return false;
//        }/* if moving
//        onto a space occupied by another piece owned by the player...*/
//        if ((Math.abs(startingX-endingX)>=1&&Math.abs(startingY-endingY)>=1)) {
//            System.out.println("cant move diagonally");
//            return false;
//        }
//        if (!gameboard[startingX][startingY].isScout()&&( (Math.abs(startingX-endingX)>=2||Math.abs(startingY-endingY)>=2)
//        )){ //if it moves too far...
//            System.out.println("too far, not a scout");
//            return false;
//        }
//        if (gameboard[startingX][startingY].isScout() ){ //if a scout moves through a unit or lake...
//            int starting=0;
//            int ending =0;
//            boolean horizontal=false;
//            if (endingX==startingX){
//                starting=Math.min(startingY,endingY);
//                ending=Math.max(startingY,endingY);
//                horizontal=true;
//            }
//            else{
//                starting=Math.min(startingX,endingX);
//                ending=Math.max(startingX,endingX);
//
//            }
//            System.out.println("horizontal is " + horizontal);
//            System.out.println("items are " +startingX+","+startingY+","+endingX+","+endingY);
//            for (int i=starting+1; i<ending; i++){
//
//                if (horizontal) {
//                    if (!gameboard[startingX][i].isEmpty() || gameboard[startingX][i].isLake()) {
//                        System.out.println("aacollision at " + startingX + " " + i + "; " + gameboard[startingX][i].getUnit());
//                        return false;
//                    }
//                }
//                else {
//                    if (!gameboard[i][startingY].isEmpty() || gameboard[i][startingY].isLake()) {
//                        System.out.println("bbxcollision at " + i + " " + startingY + "; " + gameboard[i][startingY].getUnit());
//
//                        return false;
//                    }
//                }
//            }
//        }
//
//        return true;
//    }

    private boolean isDigit(char s){
        if (s>='0'&&s<='9')
            return true;
        return false;
    }

    /*Returns 0 on 1 wins, 1 on 2 wins, 2 on draw, 3 on flag capture, 4 on moving to blank space*/
    public static int attack(char unit1, char unit2){
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

    public void undo_move(){
        //todo: please implement
        SimulationMove  move_to_undo = prev_move;
        gameboard[prev_move.getStart_x()][prev_move.getStart_y()] = prev_move.getStarting_piece();
        gameboard[prev_move.getEnd_x()][prev_move.getEnd_y()] = prev_move.getDestination_piece();
        gameEnded=false;
        winner = '0';
        prev_move = prev_move.getPrev();
    }

    /*Attempt to move unit to a tile*/
    public String move(int startingX, int startingY, int endingX, int endingY, char color){

//        displayGameBoard();

        if (!isLegalMove(startingX,startingY,endingX,endingY,color)) {
//            illegalMove();
            return "illegal";
        }
        SimulationMove sm = new SimulationMove(startingX,startingY,endingX,endingY,
                gameboard[startingX][startingY].clone(),gameboard[endingX][endingY].clone(),color);
        sm.setPrev(prev_move);
        prev_move=sm;

        int result=attack(gameboard[startingX][startingY].getUnit(),gameboard[endingX][endingY].getUnit());
        if (result==0){
            char a = gameboard[endingX][endingY].getUnit();
            gameboard[endingX][endingY].newPiece(gameboard[startingX][startingY]);
            gameboard[startingX][startingY].reset();

            return "win "+ a; // mover wins
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
            char a = gameboard[endingX][endingY].getUnit();
            gameboard[endingX][endingY].newPiece(gameboard[startingX][startingY]);
            gameboard[startingX][startingY].reset();

            gameEnded = true;
            winner = color;
            return "flag"; //mover wins the videogame
        }
        else if (result==4){
            gameboard[endingX][endingY].newPiece(gameboard[startingX][startingY]);
            gameboard[startingX][startingY].reset();
            return "empty"; // mover moved to empty space
        }

        return "This will never happen.";

    }
//    public static BoardPiece[][] move(BoardPiece[][] raw_board, int startingX, int startingY, int endingX, int endingY, char color){
//        BoardPiece[][] new_board = new BoardPiece[BOARD_SIZE[0]][BOARD_SIZE[1]];
//        for(int i=0;i<BOARD_SIZE[0];i++){
//            for (int j=0;j<BOARD_SIZE[1];j++){
//                new_board[i][j] = raw_board[i][j].clone();
//            }
//        }
//
//        if (!isLegalMove(new_board,startingX,startingY,endingX,endingY,color)) {
//            return null;
//        }
//
//        int result=attack(new_board[startingX][startingY].getUnit(),new_board[endingX][endingY].getUnit());
//
//        if (result==0){// mover wins
//            char a = new_board[endingX][endingY].getUnit();
//            new_board[endingX][endingY].newPiece(new_board[startingX][startingY]);
//            new_board[startingX][startingY].reset();
//
//        }
//        else if (result==1){// mover's opponent wins
//            char a = new_board[startingX][startingY].getUnit();
//            new_board[startingX][startingY].reset();
//        }
//        else if (result==2){//both die
//            char a = new_board[endingX][endingY].getUnit();
//            new_board[startingX][startingY].reset();
//            new_board[endingX][endingY].reset();
//        }
//        else if (result==3){//mover wins the videogame
//            char a = new_board[endingX][endingY].getUnit();
//            new_board[endingX][endingY].newPiece(new_board[startingX][startingY]);
//            new_board[startingX][startingY].reset();
//        }
//        else if (result==4){// mover moved to empty space
//            new_board[endingX][endingY].newPiece(new_board[startingX][startingY]);
//            new_board[startingX][startingY].reset();
//        }
//        else{
//            System.out.println("WTGG you entered a never happening else case?");
//        }
//        return new_board;
//
//    }

    public static int[] getBoardSize() {
        return BOARD_SIZE;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }

    public char getWinner() {
        return winner;
    }

    public void setWinner(char winner) {
        this.winner = winner;
    }
    public BoardPiece getPiece(int x, int y){
        return gameboard[x][y];
    }


}
