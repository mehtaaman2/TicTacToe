package com.example.aman_mehta.tictactoe;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.display.DisplayManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class Game extends Activity
{
Animation an1,an2,layout_anim;
private ImageButton buttons[];
    String p,p1,p2;
TextView turn_name;
    ImageView win_image;
    RelativeLayout grid;
    private boolean single_player;
    int prog[]=new int[9];
    int boxes_filled;
    boolean your_turn;
    void printArray()
    {
        System.out.println();
        for(int i=0;i<9;i++)
            System.out.print(prog[i]+"   ");
    }
int msg=0;
    public void setallUnClickable()
    {
        for(int i=0;i<9;i++)
            buttons[i].setClickable(false);
    }
    class WaitingThread implements Runnable
    {
        public void run()
        {
            try
            {
                setallUnClickable();
                Thread.sleep(2300);
                Game.this.finish();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
    Thread wait;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        for(int k=0;k<9;k++)
            prog[k]=0;
        if(savedInstanceState != null)
        {
            single_player=(Boolean) savedInstanceState.getSerializable("hashMap");
            prog=(int[]) savedInstanceState.getSerializable("ButtonState");
        }
        if(savedInstanceState==null)
            single_player = getIntent().getExtras().getBoolean("Single Player");
        setContentView(R.layout.activity_game);
        grid=(RelativeLayout)findViewById(R.id.Game_Grid);
        layout_anim= AnimationUtils.loadAnimation(this, R.anim.rotate);
        grid.startAnimation(layout_anim);
        createButtons();
        win_image=(ImageView)findViewById(R.id.LineView);
        for(int k=0;k<9;k++)
        {
            System.out.print("\nProgress : ");
            printArray();
            if(prog[k]==1)
            {
                buttons[k].setImageResource(R.drawable.x_image);
                buttons[k].setClickable(false);
            }
            else if(prog[k]==2)
            {
                buttons[k].setImageResource(R.drawable.o_image);
                buttons[k].setClickable(false);
            }
        }
        turn_name=(TextView)findViewById(R.id.turn_text);
        your_turn=true;

        an1= AnimationUtils.loadAnimation(this, R.anim.abc_fade_in);
        an2= AnimationUtils.loadAnimation(this, R.anim.abc_fade_in);
        p=Main.mydb.getplayer_name();
        p1=Main.mydb.getplayer1_name();
        p2=Main.mydb.getplayer2_name();
        wait=new Thread(new WaitingThread());
        //turn=0;
      //  wait_turn=new Thread(new WaitingTurnThread());
        if(single_player)
            turn_name.setText(p+"'s Turn.");
        else
            turn_name.setText(p1+"'s Turn.");

        getIntent().removeExtra("Single Player");
        boxes_filled=0;
        for(int i=0;i<9;i++)
        {
            buttons[i].setContentDescription(Integer.toString(i));
        }
        for (int i = 0; i <9; i++)
        {
            if(this.buttons[i].isClickable())
            this.buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    GameProgress((ImageButton) v);
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("hashMap", single_player);
        outState.putSerializable("ButtonState",prog);
    }
    public void GameProgress(ImageButton button)
    {

        if (your_turn)
        {
            if(single_player)
                turn_name.setText(p+"'s Turn.");
            else
                turn_name.setText(p2+"'s Turn.");
            button.setImageResource(R.drawable.x_image);
            button.startAnimation(an1);
            button.setClickable(false);
            int x=Integer.parseInt((String) button.getContentDescription());
            prog[x]=1;
            boxes_filled++;
            printArray();
            if(checkWinner(1))
            {
                if(single_player)
                {
                    turn_name.setText(p+" has won the Match!");
                    turn_name.setTextSize((float) 35.0);
                    turn_name.setTextColor(Color.MAGENTA);
                    Toast.makeText(Game.this, p + " wins!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    turn_name.setText(p1+" has won the Match!");
                    turn_name.setTextSize((float)35.0);
                    turn_name.setTextColor(Color.MAGENTA);
                    Toast.makeText(Game.this, p1 + " wins!", Toast.LENGTH_SHORT).show();
                }
                if(single_player)
                Main.mydb.spxw();
                else
                Main.mydb.mpxw();
                wait.start();
            }
            else if(boxes_filled==9)
            {
                printArray();
                turn_name.setText("This Match Is A Draw!");
                turn_name.setTextSize((float)35.0);
                turn_name.setTextColor(Color.MAGENTA);
                Toast.makeText(Game.this,"Match Ends in a draw!",Toast.LENGTH_SHORT).show();
                if(single_player)
                Main.mydb.spdraw();
                else
                    Main.mydb.mpdraw();
                wait.start();
            }
            else
            {
                your_turn = false;
                if(single_player)
                GameProgress(button);
            }
        }
        else
        {
            if(single_player)
            {
                turn_name.setText(p+"'s turn!");
                int next;
                next = about_to_win(2);
                if (next != 10) {
                    prog[next] = 2;
                    boxes_filled++;
                    buttons[next].setImageResource(R.drawable.o_image);
                    buttons[next].setClickable(false);
                    buttons[next].startAnimation(an2);
                    if(checkWinner(2))
                    {
                        turn_name.setText("Computer won the Match!");
                        turn_name.setTextSize((float)35.0);
                        turn_name.setTextColor(Color.MAGENTA);
                        Toast.makeText(Game.this, "Computer wins!", Toast.LENGTH_SHORT).show();
                        Main.mydb.spow();
                        wait.start();
                    }
                } else {
                    next = next_move(1);
                    prog[next] = 2;
                    buttons[next].setImageResource(R.drawable.o_image);
                    buttons[next].startAnimation(an2);
                    buttons[next].setClickable(false);
                    boxes_filled++;

                }
                if (boxes_filled == 9) {
                    printArray();
                    turn_name.setText("This Match Is A Draw!");
                    turn_name.setTextSize((float)35.0);
                    turn_name.setTextColor(Color.MAGENTA);
                    Toast.makeText(Game.this, "Match Ends in a draw!", Toast.LENGTH_SHORT).show();
                    Main.mydb.spdraw();
                    wait.start();
                }
            }
            else
            {

                    turn_name.setText(p1+"'s Turn.");
                button.setImageResource(R.drawable.o_image);
                button.startAnimation(an2);
                button.setClickable(false);
                int x=Integer.parseInt((String) button.getContentDescription());
                prog[x]=2;
                boxes_filled++;
                printArray();
                if(checkWinner(2))
                {
                    turn_name.setText(p2+" has won the Match!");
                    turn_name.setTextSize((float)35.0);
                    turn_name.setTextColor(Color.MAGENTA);
                    Toast.makeText(Game.this,p2+" wins!",Toast.LENGTH_SHORT).show();
                    Main.mydb.mpow();
                    wait.start();
                }
                else if(boxes_filled==9)
                {
                    printArray();
                    turn_name.setText("This Match Is A Draw!");
                    turn_name.setTextSize((float)35.0);
                    turn_name.setTextColor(Color.MAGENTA);
                    Toast.makeText(Game.this,"Match Ends in a draw!",Toast.LENGTH_SHORT).show();
                    Main.mydb.mpdraw();
                    wait.start();
                }

            }
                your_turn = true;

        }
    }
    public boolean checkWinner(int x)
    {
        boolean flag=false;
        if(prog[0]==x && prog[1]==x && prog[2]==x)
        {
            win_image.setImageResource(R.drawable.win_1);
            win_image.setAnimation(AnimationUtils.loadAnimation(this,R.anim.win_anim));
            win_image.bringToFront();
            flag = true;
        }
        else if(prog[3]==x && prog[4]==x && prog[5]==x)
        {
            win_image.setImageResource(R.drawable.win_2);
            win_image.setAnimation(AnimationUtils.loadAnimation(this,R.anim.win_anim));
            win_image.bringToFront();
            flag = true;
        }
        else if(prog[6]==x && prog[7]==x && prog[8]==x)
        {
            win_image.setImageResource(R.drawable.win_3);
            win_image.setAnimation(AnimationUtils.loadAnimation(this,R.anim.win_anim));
            win_image.bringToFront();
            flag = true;
        }
        else if(prog[0]==x && prog[3]==x && prog[6]==x)
        {
            win_image.setImageResource(R.drawable.win_4);
            win_image.setAnimation(AnimationUtils.loadAnimation(this,R.anim.win_anim_ver));
            win_image.bringToFront();
            flag = true;
        }
        else if(prog[1]==x && prog[4]==x && prog[7]==x)
        {
            win_image.setImageResource(R.drawable.win_5);
            win_image.setAnimation(AnimationUtils.loadAnimation(this,R.anim.win_anim_ver));
            win_image.bringToFront();
            flag = true;
        }
        else if(prog[2]==x && prog[5]==x && prog[8]==x)
        {
            win_image.setImageResource(R.drawable.win_6);
            win_image.setAnimation(AnimationUtils.loadAnimation(this,R.anim.win_anim_ver));
            win_image.bringToFront();
            flag = true;
        }
        else if(prog[0]==x && prog[4]==x && prog[8]==x)
        {
            win_image.setImageResource(R.drawable.win_7);
            win_image.setAnimation(AnimationUtils.loadAnimation(this,R.anim.win_anim_diag));
            win_image.bringToFront();
            flag = true;
        }
        else if(prog[2]==x && prog[4]==x && prog[6]==x)
        {
            win_image.setImageResource(R.drawable.win_8);
            win_image.setAnimation(AnimationUtils.loadAnimation(this,R.anim.win_anim_diag_2));
            win_image.bringToFront();
            flag = true;
        }
        return flag;
    }
    public int next_move(int x)
    {
        int i,j,count;
        for(i=0;i<3;i++)
        {
            count=0;
            for(j=i*3;j<3*i+3;j++)
            {
                if(prog[j]==x)
                count++;
            }
            if(count==2)
            {
                for(j=i*3;j<3*i+3;j++)
                {
                    if(prog[j]!=x && buttons[j].isClickable())
                        return j;
                }
            }
        }
        for(i=0;i<3;i++)
        {
            count=0;
            for(j=i;j<9;j+=3)
            {
                if(prog[j]==x)
                    count++;
            }
            if(count==2)
            {
                for(j=i;j<9;j+=3)
                {
                    if(prog[j]!=x && buttons[j].isClickable())
                        return j;
                }
            }
        }
            count=0;
            for(j=0;j<9;j+=4)
            {
                if(prog[j]==x)
                    count++;
            }
            if(count==2)
            {
                for(j=0;j<9;j+=4)
                {
                    if(prog[j]!=x && buttons[j].isClickable())
                        return j;
                }
            }
        count=0;
        for(j=2;j<7;j+=2)
        {
            if(prog[j]==x)
                count++;
        }
        if(count==2)
        {
            for(j=2;j<7;j+=2)
            {
                if(prog[j]!=x && buttons[j].isClickable())
                    return j;
            }
        }
        if(buttons[4].isClickable())
            return 4;

        else
        {
            if(boxes_filled==3)
            {
                if((prog[0]==x && prog[8]==x) || (prog[2]==x && prog[6]==x))
                {
                    int y[]=new int[]{1,3,5,7};
                    Random r1 = new Random();
                    int i2 = r1.nextInt(4);
                    //System.out.println(y[i2]);
                    if (buttons[y[i2]].isClickable())
                        return y[i2];
                }
                else if((prog[0]==x && prog[7]==x) || (prog[2]==x && prog[7]==x) || (prog[1]==x && prog[6]==x) || (prog[1]==x && prog[8]==x))
                {
                    int y[]=new int[]{3,5};
                    Random r1 = new Random();
                    int i2 = r1.nextInt(2);
                    //System.out.println(y[i2]);
                    if (buttons[y[i2]].isClickable())
                        return y[i2];
                }
                else if((prog[3]==x && prog[2]==x) || (prog[3]==x && prog[8]==x) || (prog[5]==x && prog[0]==x) || (prog[5]==x && prog[6]==x))
                {
                    int y[]=new int[]{1,7};
                    Random r1 = new Random();
                    int i2 = r1.nextInt(2);
                   // System.out.println(y[i2]);
                    if (buttons[y[i2]].isClickable())
                        return y[i2];
                }

            }

            int y[]=new int[]{0,2,6,8};
            Random r1 = new Random();
            int i2 = r1.nextInt(4);
           // System.out.println(y[i2]);
            if (buttons[y[i2]].isClickable())
                return y[i2];
            while(true)
            {
                Random r = new Random();
                int i1 = r.nextInt(9);
              //  System.out.println(i1);
                if (buttons[i1].isClickable())
                    return i1;
            }
        }
    }
    public int about_to_win(int x)
    {
        int i,j,count;
        for(i=0;i<3;i++)
        {
            count=0;
            for(j=i*3;j<3*i+3;j++)
            {
                if(prog[j]==x)
                    count++;
            }
            if(count==2)
            {
                for(j=i*3;j<3*i+3;j++)
                {
                    if(prog[j]!=x && buttons[j].isClickable())
                        return j;
                }
            }
        }
        for(i=0;i<3;i++)
        {
            count=0;
            for(j=i;j<9;j+=3)
            {
                if(prog[j]==x)
                    count++;
            }
            if(count==2)
            {
                for(j=i;j<9;j+=3)
                {
                    if(prog[j]!=x && buttons[j].isClickable())
                        return j;
                }
            }
        }
        count=0;
        for(j=0;j<9;j+=4)
        {
            if(prog[j]==x)
                count++;
        }
        if(count==2)
        {
            for(j=0;j<9;j+=4)
            {
                if(prog[j]!=x && buttons[j].isClickable())
                    return j;
            }
        }
        count=0;
        for(j=2;j<7;j+=2)
        {
            if(prog[j]==x)
                count++;
        }
        if(count==2)
        {
            for(j=2;j<7;j+=2)
            {
                if(prog[j]!=x && buttons[j].isClickable())
                    return j;
            }
        }
        return 10;
    }

    public void createButtons()
    {
        buttons = new ImageButton[]{(ImageButton) findViewById(R.id.imageButton), (ImageButton) findViewById(R.id.imageButton2), (ImageButton) findViewById(R.id.imageButton3), (ImageButton) findViewById(R.id.imageButton4), (ImageButton) findViewById(R.id.imageButton5), (ImageButton) findViewById(R.id.imageButton6), (ImageButton) findViewById(R.id.imageButton7), (ImageButton) findViewById(R.id.imageButton8), (ImageButton) findViewById(R.id.imageButton9)};
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
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
