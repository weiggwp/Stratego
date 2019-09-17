package Stratego.board;

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
        blue = populate(1);
        red = populate(7);
        selected = "";

    }

    static int count=0;


    public void select(String id)

    {
        this.selected=id;
    }
    public String getSelected()
    {
        return this.selected;
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
