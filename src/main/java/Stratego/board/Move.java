package Stratego.board;

public class Move {
    private long GameID;
//    private String player; //either computer or user
    private int start_x;
    private int start_y;
    private int end_x;
    private int end_y;
    private char color;
    private int moveNum;
    private Move_status status; //response about the attempted move

    public int getMoveNum(){
        return moveNum;
    }
    public void setMoveNum(int moveNum){
        this.moveNum=moveNum;
    }
    public void setColor(char color){
        this.color=color;
    }
    public char getColor(){
        return color;
    }

    public Move(long gameID, int start_x, int start_y, int end_x, int end_y, char color ,Move_status status) {
        GameID = gameID;
        //this.player = player;
        this.start_x = start_x;
        this.start_y = start_y;
        this.end_x = end_x;
        this.end_y = end_y;
        this.color = color;
        this.status = status;
    }
    public String getStart()
    {
        return this.getStart_x()+","+this.getStart_y();
    }
    public String getEnd()
    {
        return this.getEnd_x()+","+this.getEnd_y();
    }
    public boolean movedBack(int startingX, int startingY, int endingX, int endingY)
    {
        String start = startingX+","+startingY;
        String end = endingX+","+endingY;
        //[1,1]->[2,2]   [2,2]->[1,1]
        return this.getEnd().equals(start) && this.getStart().equals(end);
    }


    public int getStart_x() {
        return start_x;
    }

    public void setStart_x(int start_x) {
        this.start_x = start_x;
    }

    public int getStart_y() {
        return start_y;
    }

    public void setStart_y(int start_y) {
        this.start_y = start_y;
    }

    public int getEnd_x() {
        return end_x;
    }

    public void setEnd_x(int end_x) {
        this.end_x = end_x;
    }

    public int getEnd_y() {
        return end_y;
    }

    public void setEnd_y(int end_y) {
        this.end_y = end_y;
    }

    public Move_status getStatus() {
        return status;
    }

    public void setStatus(Move_status status) {
        this.status = status;
    }

    public long getGameID() {
        return GameID;
    }

}



