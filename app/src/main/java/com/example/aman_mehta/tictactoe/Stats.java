package com.example.aman_mehta.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Stats extends Activity {
TextView spt,mpt;
    Button reset,btn_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        spt=(TextView)findViewById(R.id.sp_score);
        mpt=(TextView)findViewById(R.id.mp_score);
        reset=(Button)findViewById(R.id.btn_reset);
        btn_edit=(Button)findViewById(R.id.btn_edit);
        spt.setMovementMethod(new ScrollingMovementMethod());
        mpt.setMovementMethod(new ScrollingMovementMethod());
        showspdata();
        showmpdata();
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Stats.this,Edit_names.class);
                startActivity(intent);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert=new AlertDialog.Builder(Stats.this);
                alert.setMessage("All the scores will erased. Are you sure to Continue?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Main.mydb.reset_scores();
                        showmpdata();
                        showspdata();
                        Toast.makeText(Stats.this,"All scores are reset!",Toast.LENGTH_SHORT).show();
                    }
                }).setCancelable(true).setNeutralButton("Cancel", new DialogInterface.OnClickListener() { // define the 'Cancel' button
                    public void onClick(DialogInterface dialog, int which) {
                        //Either of the following two lines should work.
                        dialog.cancel();
                        //dialog.dismiss();
                    }
                });
                alert.setTitle("Confirm");
                alert.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showspdata();
        showmpdata();
    }

    public void showspdata()
{
    Cursor res=Main.mydb.getspdata();
    StringBuffer buffer=new StringBuffer();
    res.moveToNext();
    buffer.append("SINGLE PLAYER\n\n");
    buffer.append(" X wins : "+res.getString(0)+"\n");
    buffer.append(" O wins : "+res.getString(1)+"\n");
    buffer.append(" Draws : " + res.getString(2) + "\n");
    buffer.append(" Player Name : " + res.getString(3) + "\n");
    spt.setText(buffer.toString());
    res.close();
}
    public void showmpdata()
    {
        Cursor res=Main.mydb.getmpdata();
        StringBuffer buffer=new StringBuffer();
        res.moveToNext();
        buffer.append("MULTIPLAYER PLAYER\n\n");
        buffer.append(" X wins : " + res.getString(0) + "\n");
        buffer.append(" O wins : "+res.getString(1)+"\n");
        buffer.append(" Draws : "+res.getString(2)+"\n");
        buffer.append(" Player 1 Name : " + res.getString(3) + "\n");
        buffer.append(" Player 2 Name : " + res.getString(4) + "\n");
        mpt.setText(buffer.toString());
        res.close();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stats, menu);
        return true;
    }

    /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
