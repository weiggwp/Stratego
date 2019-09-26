
public class BoardPiece {

    private char unit;// 1-9, M (marshall), F (flag),B (bomb), 0 for empty, W for lake
    private String img_src;
    private char color; // R or B when occupied by a tile,0 otherwise
    private boolean moved;
    private boolean revealed;

    public BoardPiece(char unit, char color){
        this.unit=unit;
        this.color=color;
        this.img_src="";
    }
    public BoardPiece(char unit,String img,char color)
    {
        this.unit=unit;
        this.img_src=img;
        this.color=color;
    }

    public void reset(){
        unit='0';
        color='0';
    }

    public void newPiece(BoardPiece b){
        unit=b.getUnit();
        color=b.getColor();
        img_src=b.toString();
    }
    public String getImg_src(){
        return img_src;
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

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    @Override
    public String toString(){
        return img_src;
    }


    public boolean isEmpty(){
        return unit=='0';
    }

    public BoardPiece(char unit, String img_src, char color, boolean moved, boolean revealed) {
        this.unit = unit;
        this.img_src = img_src;
        this.color = color;
        this.moved = moved;
        this.revealed = revealed;
    }

    public boolean isLake(){
        return unit=='W';
    }
    public boolean isScout(){
        return unit=='2';
    }
    public BoardPiece clone() {
        return new BoardPiece(this.unit,this.img_src,this.color,this.moved,this.revealed);

    }


}
