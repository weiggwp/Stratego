public class SimulationMove {

    private BoardPiece starting_piece;
    private int start_x;
    private int start_y;

    private BoardPiece destination_piece;
    private int end_x;
    private int end_y;

    private char color;

    private SimulationMove prev;
//    private SimulationMove next;

    public BoardPiece getStarting_piece() {
        return starting_piece;
    }

    public void setStarting_piece(BoardPiece starting_piece) {
        this.starting_piece = starting_piece;
    }

    public BoardPiece getDestination_piece() {
        return destination_piece;
    }

    public void setDestination_piece(BoardPiece destination_piece) {
        this.destination_piece = destination_piece;
    }

    public SimulationMove getPrev() {
        return prev;
    }

    public void setPrev(SimulationMove prev) {
        this.prev = prev;
    }




    public void setColor(char color){
        this.color=color;
    }
    public char getColor(){
        return color;
    }

    public SimulationMove(int start_x, int start_y, int end_x, int end_y, BoardPiece starting_piece, BoardPiece destination_piece, char color) {
        this.start_x = start_x;
        this.start_y = start_y;
        this.end_x = end_x;
        this.end_y = end_y;
        this.starting_piece = starting_piece;
        this.destination_piece = destination_piece;
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

    @Override
    public String toString() {
        return
                "x=" + start_x +
                ", y=" + start_y +
                "to x=" + end_x +
                ", y=" + end_y +
                '}';
    }
}



