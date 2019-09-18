package Stratego.board;

/*
    This class is used to storing information about unique pieces
    path -> object hashmap to compare values when making a move
    obsolete -> will use BoardPiece class instead
 */
public class piece {
    private char value;
    private String image;
    public piece(char value,String image)
    {
        this.value =value;
        this.image=image;
    }

    public int getValue() {
        return value;
    }
    public String getImage()
    {
        return image;
    }

    public String toString()
    {
        return image;
    }

    public piece clone()
    {
        return new piece(this.value,this.image);
    }
}
