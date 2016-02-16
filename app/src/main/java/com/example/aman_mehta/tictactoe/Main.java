package com.example.aman_mehta.tictactoe;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;

import static android.view.View.*;


public class Main extends Activity {
   static scoredb mydb;
    Animation an[],pic_an;
    ImageView main_pic;
Button single_play,multi_play,stats,exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        an=new Animation[4];
        setContentView(R.layout.activity_main);
        mydb = new scoredb(this);
        main_pic=(ImageView) findViewById(R.id.imageView);
        single_play=(Button)findViewById(R.id.singleplayer_btn);
        multi_play=(Button)findViewById(R.id.multiplayer_btn);
        stats=(Button)findViewById(R.id.stats_btn);
        exit=(Button)findViewById(R.id.exit_btn);
        startAnimation();
        onPlayClick();
        onStatsClick();
        onExitClick();
    }
    public void startAnimation()
    {
        pic_an = AnimationUtils.loadAnimation(this, R.anim.new_fade);
        main_pic.startAnimation(pic_an);
        for(int i=0;i<4;i++)
        {
            an[i] = AnimationUtils.loadAnimation(this, R.anim.rotate);
            an[i].setStartOffset(250 * i);
        }
        single_play.startAnimation(an[0]);
        multi_play.startAnimation(an[1]);
        stats.startAnimation(an[2]);
        exit.startAnimation(an[3]);
    }
    public void onPlayClick()
    {

     single_play.setOnClickListener(new OnClickListener() {
         @Override
         public void onClick(View v) {
             try {
                 Intent intent = new Intent("com.example.aman_mehta.tictactoe.Game");
                 intent.putExtra("Single Player", true);
                 startActivity(intent);
             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
     });
        multi_play.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent("com.example.aman_mehta.tictactoe.Game");
                    intent.putExtra("Single Player", false);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        startAnimation();
        super.onResume();
    }

    public void onStatsClick()
    {
        stats.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Main.this,Stats.class);
                startActivity(intent);
            }
        });
    }
    public void onExitClick()
    {
        exit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Main.this.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
