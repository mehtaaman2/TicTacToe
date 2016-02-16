package com.example.aman_mehta.tictactoe;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.AvoidXfermode;
import android.widget.Toast;

/**
 * Created by aman_mehta on 22-Aug-15.
 */
public class scoredb extends SQLiteOpenHelper
{

    public static final String DB_NAME="Scores.db";
    public static final String TABLE1_NAME="Single_player";
    public static final String TABLE2_NAME="Multi_player";
    public static final String T1_COL_1="x_wins";
    public static final String T1_COL_2="o_wins";
    public static final String T1_COL_3="draws";
    public static final String T1_COL_4="player_name";
    public static final String T2_COL_1="x_wins";
    public static final String T2_COL_2="o_wins";
    public static final String T2_COL_3="draws";
    public static final String T2_COL_4="player1_name";
    public static final String T2_COL_5="player2_name";
    public scoredb(Context context) {
        super(context, DB_NAME, null, 1);
        SQLiteDatabase db= this.getWritableDatabase();
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE1_NAME+" ( "+T1_COL_1+" INTEGER(4), "+T1_COL_2+" INTEGER(4), "+T1_COL_3+" INTEGER(4), "+T1_COL_4+" TEXT NOT NULL )");
        db.execSQL("create table "+TABLE2_NAME+" ( "+T2_COL_1+" INTEGER(4), "+T2_COL_2+" INTEGER(4), "+T2_COL_3+" INTEGER(4), "+T2_COL_4+" TEXT NOT NULL , "+ T2_COL_5 +" TEXT NOT NULL )");
        db.execSQL("insert into "+TABLE1_NAME+ " ("+T1_COL_1+","+T1_COL_2+","+T1_COL_3+","+T1_COL_4+") values (0,0,0,'Player')");
        db.execSQL("insert into " + TABLE2_NAME + " (" + T2_COL_1 + "," + T2_COL_2 + "," + T2_COL_3 + "," + T2_COL_4 + "," + T2_COL_5 + ") values (0,0,0,'Player 1','Player 2');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        onCreate(db);

    }
    public void spxw()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("update " + TABLE1_NAME + " set " + T1_COL_1 + "=" + T1_COL_1 + "+1");
        db.close();
    }
    public void spow()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("update "+TABLE1_NAME+" set "+ T1_COL_2+"="+T1_COL_2+"+1");
        db.close();

    }
    public void spdraw()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("update " + TABLE1_NAME + " set " + T1_COL_3 + "=" + T1_COL_3 + "+1");
        db.close();
    }
    public void mpxw()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("update " + TABLE2_NAME + " set " + T2_COL_1 + "=" + T2_COL_1 + "+1");
        db.close();
    }
    public void mpow()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("update " + TABLE2_NAME + " set " + T2_COL_2 + "=" + T2_COL_2 + "+1");
        db.close();

    }
    public void mpdraw()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("update " + TABLE1_NAME + " set " + T2_COL_3 + "=" + T2_COL_3 + "+1");
        db.close();
    }
    public Cursor getspdata()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from "+TABLE1_NAME,null);
       // db.close();
        return res;
    }
    public Cursor getmpdata()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from "+TABLE2_NAME,null);
        //db.close();
        return res;
    }
    public void reset_scores()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("update " + TABLE1_NAME + " set " + T1_COL_1 + "=0," + T1_COL_2 + "=0," + T1_COL_3 + "=0");
        db.execSQL("update " + TABLE2_NAME + " set " + T2_COL_1 + "=0," + T2_COL_2 + "=0," + T2_COL_3 + "=0");
        db.close();
    }
    public String getplayer_name()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res= db.rawQuery("select player_name from " + TABLE1_NAME, null);
       String name=new String();
        res.moveToNext();
        name=res.getString(0);
        db.close();
        res.close();
        return name;
    }
    public String getplayer1_name()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res= db.rawQuery("select player1_name from " + TABLE2_NAME, null);
        String name=new String();
        res.moveToNext();
        name=res.getString(0);
        db.close();
        res.close();
        return name;
    }
    public String getplayer2_name()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res= db.rawQuery("select player2_name from " + TABLE2_NAME, null);
        String name=new String();
        res.moveToNext();
        name=res.getString(0);
        db.close();
        res.close();
        return name;
    }
    public void setplayer_name(String s)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("update " + TABLE1_NAME + " set " + T1_COL_4 + " ='" + s+"'");
        db.close();
    }
    public void setplayer1_name(String s)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("update " + TABLE2_NAME + " set " + T2_COL_4 + " ='" + s+"'");
        db.close();
    }
    public void setplayer2_name(String s)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("update " + TABLE2_NAME + " set " + T2_COL_5 + " ='" + s+"'");
        db.close();
    }
    public void reset_names()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("update " + TABLE1_NAME + " set " + T1_COL_4 + "= 'Player'");
        db.execSQL("update " + TABLE2_NAME + " set " + T2_COL_4 + "= 'Player 1'," + T2_COL_5 + "='Player 2'" );
        db.close();
    }
}
