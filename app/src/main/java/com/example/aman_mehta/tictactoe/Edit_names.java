package com.example.aman_mehta.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Edit_names extends Activity {
EditText p,p1,p2;
    Button save,cancel,reset_names;
    InputFilter f,f1,f2;
    public boolean isEmpty(String s)
    {
        boolean flag=true;
        for(int i=0;i<s.length();i++)
        {
            if(s.charAt(i)!=' ')
            {
                flag=false;
                break;
            }
        }
        return flag;
    }
    public InputFilter setInputFilter(final EditText editText)
    {
        InputFilter filter = new InputFilter() {
            boolean canEnterSpace = false;

            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                boolean keepOriginal = true;
                StringBuilder sb = new StringBuilder(end - start);
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    Log.d("Start,end ", Integer.toString(start) + " , " + Integer.toString(end));
                    if (isCharAllowed(c)) // put your condition here
                        sb.append(c);
                    else
                        keepOriginal = false;
                }
                if (keepOriginal)
                {
                    Log.d("SB keep original ",sb.toString());
                    if(sb.length()==10)
                        return sb;
                    return null;
                }
                else {
                    Log.d("SB ! keep original ",sb.toString());
                    return sb;
                }
            }
            private boolean isCharAllowed(char currentChar)
            {
                if(editText.getText().toString().equals(""))
                    canEnterSpace = false;
                if(currentChar=='\n')
                {
                    Log.d("Entered Character : ","Enter");
                    return false;
                }

                if (Character.isLetterOrDigit(currentChar) || currentChar == '_') {

                    Log.d("Entered Character : ",Character.toString(currentChar));
                    canEnterSpace = true;
                    return true;

                }

                //if(Character.isWhitespace(currentChar) && canEnterSpace) {
                //    return true;
                // }
                if(Character.isWhitespace(currentChar))
                {
                    if(canEnterSpace)
                    {
                        Log.d("Entered Character : ","Space");
                        return true;
                    }
                    else
                    {
                        Log.d("Entered Character : ","Cannot Enter Space");
                        return false;
                    }

                }
                Log.d("Entered Character : ",Character.toString(currentChar)+" outside all!!");
                return true;

            }

        };
        return filter;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_names);
        p=(EditText)findViewById(R.id.sppn);
        p1=(EditText)findViewById(R.id.spp1n);
        p2=(EditText)findViewById(R.id.spp2n);
        save=(Button)findViewById(R.id.btn_save);
        cancel=(Button)findViewById(R.id.btn_cancel);
        reset_names=(Button)findViewById(R.id.btn_reset_names);
        p.setText(Main.mydb.getplayer_name());
        p1.setText(Main.mydb.getplayer1_name());
        p2.setText(Main.mydb.getplayer2_name());
        f=setInputFilter(p);
        f1=setInputFilter(p1);
        f2=setInputFilter(p2);
        p.setFilters(new InputFilter[]{f,new InputFilter.LengthFilter(20)});
        p1.setFilters(new InputFilter[]{f1,new InputFilter.LengthFilter(20)});
        p2.setFilters(new InputFilter[]{f2,new InputFilter.LengthFilter(20)});
        reset_names.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Edit_names.this);
                alert.setMessage("All the names will be reset to defaults. Are you sure to Continue?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Main.mydb.reset_names();
                        Toast.makeText(Edit_names.this, "Names reset to defaults!", Toast.LENGTH_SHORT).show();
                        p.setText(Main.mydb.getplayer_name());
                        p1.setText(Main.mydb.getplayer1_name());
                        p2.setText(Main.mydb.getplayer2_name());
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
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s, s1, s2;
                int flag = 1;
                s = p.getText().toString();
                s1 = p1.getText().toString();
                s2 = p2.getText().toString();
                if (isEmpty(s)) {
                    p.setError("Enter a valid name");
                    flag = 0;
                }
                if (isEmpty(s1)) {
                    p1.setError("Enter a valid name");
                    flag = 0;
                }
                if (isEmpty(s2)) {
                    p2.setError("Enter a valid name");
                    flag = 0;
                }
                if (s1.equals(s2) && !isEmpty(s1) && !isEmpty(s2)) {
                    Toast.makeText(Edit_names.this, "Player 1 and Player 2 cannot have the same name!", Toast.LENGTH_SHORT).show();
                    flag = 0;
                }
                if (flag == 1) {
                    Main.mydb.setplayer_name(s);
                    Main.mydb.setplayer1_name(s1);
                    Main.mydb.setplayer2_name(s2);
                    finish();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_names, menu);
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
