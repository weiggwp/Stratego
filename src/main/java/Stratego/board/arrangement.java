package Stratego.board;


import Stratego.logic.src.Board;
import Stratego.logic.src.BoardPiece;
import Stratego.model.Placement;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

//shuffles and gives positions of pieces
public class arrangement {
    private static ArrayList<BoardPiece> blue;
    private static ArrayList<BoardPiece> red;
    private static String selected;
    public ArrayList getBlue(){
        return blue;
    }
    public ArrayList getRed(){
        return red;
    }
    public arrangement()
    {
        blue = create_pieces(false,'R');
        red = create_pieces(true,'B');


    }
    public arrangement(int a, int b){

        blue = new ArrayList<>();
        red = new ArrayList<>();
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

            return true;
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

    public static BoardPiece getPiece(int row,int col)
    {
        if(row<5)//blue
        {
            int pos = (row)*(10) + col;

            return blue.get(pos);
        }
        else
        {
            int ro = row-6;
            int pos = ro*10+col;
            return red.get(pos);
        }
    }
    public static String assign(int row,int col)
    {

        if(row<5)//blue
        {
            int pos = (row-1)*(10) + (col-1);

            return (blue.get(pos)).toString();
        }
        else
        {
            int ro = row-7;
            int pos = ro*10+col-1;
            return (red.get(pos)).toString();
        }

    }
    /*
     There are twice unique pieces in total for each user
     this method creates them in order with respect to their image src
     do the same as create_pieces except now we populate the arraylist with objects containing value+image_src
     */
    public void create_pieces_placement(ArrayList<Placement> lis){



        String src="../images/pieces/piece"; // directory
        String start="";
        String ext=".png";

       // System.out.println("MAKING B");







        System.out.println("size is " + lis.size());
        for (int i=0; i<lis.size(); i++){
            BoardPiece bp = new BoardPiece();
            bp.setColor(lis.get(i).getIsPlayer()==1?'R':'B');

            bp.setUnit(lis.get(i).getPieceName());
            bp.setImg_src(src + start + pieceToSrc(bp.getUnit(),lis.get(i).getIsPlayer()==1?'R':'B') + ext);
            //System.out.println("item at " +i +" has src of " +bp.getImg_src());
            if (bp.getColor()=='B'){
                blue.add(bp);
            }
            else red.add(bp);
        }
       // System.out.println("blue size is " +blue.size() + " and red size is " + red.size());

    }
    private String pieceToSrc(char piece, char color){
        if (color=='B'){
            if (piece=='1')
                return"2";
            if (piece=='2')
                return "12";
            if (piece=='3')
                return "15";
            if (piece=='4')
                return "16";
            if (piece=='5')
                return "17";
            if (piece=='6')
                return "18";
            if (piece=='7')
                return "14";
            if (piece=='8')
                return "13";
            if (piece=='9')
                return "4";
            if (piece=='M')
                return "3";
            if (piece=='F')
                return "1";
            if (piece=='B')
                return "11";
        }
        else{
            if (piece=='1')
                return"22";
            if (piece=='2')
                return "72";
            if (piece=='3')
                return "75";
            if (piece=='4')
                return "76";
            if (piece=='5')
                return "77";
            if (piece=='6')
                return "78";
            if (piece=='7')
                return "74";
            if (piece=='8')
                return "73";
            if (piece=='9')
                return "24";
            if (piece=='M')
                return "23";
            if (piece=='F')
                return "21";
            if (piece=='B')
                return "71";
        }
        return "";
    }
    private ArrayList<BoardPiece> create_pieces(boolean user, char color)
    {
     //  System.out.println("MAKING A");
        ArrayList<BoardPiece> collect = new ArrayList<BoardPiece>();

        String src="../images/pieces/piece"; // directory
        String start="";
        String ext=".png";

        if(user)   //blue pieces start at offset 21
            start="2";
        //flag goes last
        BoardPiece flag = new BoardPiece('F',src+start+"1"+ext,color);
        collect.add(new BoardPiece('1',src+start+'2'+ext,color));     //spy
        collect.add(new BoardPiece('M',src+start+'3'+ext,color));       //10
        collect.add(new BoardPiece('9',src+start+'4'+ext,color));       //9

        char[] values={'B','2','8','7','3','4','5','6'};
        int[] counts ={6,8,2,3,5,4,4,4};
        if(!user)
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