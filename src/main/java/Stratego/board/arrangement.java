package Stratego.board;


import Stratego.logic.src.BoardPiece;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

//shuffles and gives positions of pieces
public class arrangement {
    private static ArrayList blue;
    private static ArrayList red;
    private static String selected;
    public arrangement()
    {
        blue = create_pieces(false,'B');
        red = create_pieces(true,'R');


    }

    public static Boolean compare(int row,int col)
    {

        if((row==5 || row==6) && (col==3 || col==4 || col==7 || col==8))
        {
            return true;
        }
        return false;
    }
    public static Boolean getImage(int row)
    {
        if(!(row==5 || row==6))
        {
            return true;
        }
        else
        {

            return false;
        }


    }
    public static Boolean blue(int row)
    {
        return row<5;
    }
    public static Boolean red(int row)
    {
        return row>6;
    }

    public static Boolean blank(int row)
    {
        return row==5||row==6;

    }
    public static String getId(int row,int col)
    {
        return row+"_"+col;
    }
    //public static BoardPiece
    public static String assign(int row,int col)
    {

        if(row<5)//blue
        {
            int pos = (row-1)*(10) + (col-1);

            return blue.get(pos).toString();
        }
        else
        {
            int ro = row-7;
            int pos = ro*10+col-1;
            return red.get(pos).toString();
        }

    }
    /*
     There are twice unique pieces in total for each user
     this method creates them in order with respect to their image src
     do the same as create_pieces except now we populate the arraylist with objects containing value+image_src
     */
    private ArrayList<BoardPiece> create_pieces(boolean user, char color)
    {
        ArrayList<BoardPiece> collect = new ArrayList<BoardPiece>();
        piece[] pieces = new piece[12];
        String src="../images/pieces/piece"; // directory
        String start="";
        String ext=".png";

        if(!user)   //blue pieces start at offset 21
            start="2";
             //flag goes last

        collect.add(new BoardPiece('1',src+start+'2'+ext,color));     //spy
        collect.add(new BoardPiece('M',src+start+'3'+ext,color));       //10
        collect.add(new BoardPiece('9',src+start+'4'+ext,color));       //9

        char[] values={'B','2','8','7','3','4','5','6'};
        int[] counts ={6,8,2,3,5,4,4,4};
        if(user)
            start = "1"; //now beginning to add pieces that repeat more than once
        else
            start = "7";
        for(int i = 1;i<=8;i++)
        {
            int piece_index = i+2;
            int count = counts[i-1];
            char value = values[i-1];

            String img_src= src + start + i + ext;//finish path

            for(int k=0;k<count;k++) {
                collect.add(new BoardPiece(value,img_src,color));  //create new unique objects with same values

            }
            // System.out.println("after adding "+s+" "+collect);
        }
        Collections.shuffle(collect);
        //blue flag goes first row, red flag goes last row
        BoardPiece flag = new BoardPiece('F',src+start+"1"+ext,color);
        if(!user)
            collect.add((new Random()).nextInt(10),flag);
        else
            collect.add((new Random()).nextInt(10)+30,flag);


        return collect;
    }
    //TODO: Instead of storing strings encapsulate it in piece class
    //
    //randomly populates the 40 spots, works for both red and blue team
    private static ArrayList populate(int row)
    {
        ArrayList<String> collect = new ArrayList<String>();
        int[] counts ={6,8,2,3,5,4,4,4};
        String src="../images/pieces/piece"; // directory
        String ext=".png";
        String sr; // relative directory

        // red starts at row 7
        if(row==7)
        {
            sr = src+2;
        }
        else
        {
            sr = src;
        }
        // add single cards first
        String flag = sr+1+ext;
        String spy = sr+2+ext;
        String ten = sr+3+ext;
        String nine=sr+4+ext;
        collect.add(spy);collect.add(ten);collect.add(nine);

        src += row;
        //System.out.println("pre:"+collect);
        for(int i = 1;i<=8;i++)
        {
            int count = counts[i-1];

            String s= src + i +ext;//finish path

            for(int k=0;k<count;k++) {
                collect.add(s);

            }
           // System.out.println("after adding "+s+" "+collect);
        }
        Collections.shuffle(collect);
        //blue flag goes first row, red flag goes last row
        if(row==1)
            collect.add((new Random()).nextInt(10),flag);
        else
            collect.add((new Random()).nextInt(10)+30,flag);

        return collect;

    }
    private static String clone(String s)
    {
        return new String(s);
    }
}
