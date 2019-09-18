package Stratego.logic.src;

public class BoardPiece {

    char unit;// 1-9, M (marshall), F (flag),B (bomb), 0 for empty, W for lake
    char color; // R or B when occupied by a tile,0 otherwise

    public void reset(){
        unit='0';
        color='0';
    }
    public void newPiece(BoardPiece b){
        unit=b.getUnit();
        color=b.getColor();
    }
    public char getUnit() {
        return unit;
    }

    public void setUnit(char unit) {
        this.unit = unit;
    }

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public BoardPiece(char unit, char color){
        this.unit=unit;
        this.color=color;
    }
    boolean isEmpty(){
        return unit=='0';
    }
    boolean isLake(){
        return unit=='W';
    }
    boolean isScout(){
        return unit=='2';
    }

}
