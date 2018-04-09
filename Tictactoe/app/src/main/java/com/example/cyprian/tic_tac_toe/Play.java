package com.example.cyprian.tic_tac_toe;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Play extends AppCompatActivity {
    TableLayout tl;
    Map<TextView,String> map = new HashMap<>();
    String compLetter;
    //TextView tvMap;
    boolean gameOver = false;
    int boxesfilled=0;
    TextView xWins;
    TextView oWins;
    TextView draw;
    int Xwins=0;
    int Owins=0;
    int Draw=0;
    SharedPreferences spX;
    SharedPreferences.Editor editX;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);

        tl = (TableLayout) findViewById(R.id.tablelayout);
        xWins = (TextView)findViewById(R.id.xwins);
        oWins = (TextView)findViewById(R.id.owins);
        draw = (TextView)findViewById(R.id.draw);
        spX = getSharedPreferences("xWins", MODE_PRIVATE);
        editX = spX.edit();
        createRows();
        createTextViews();
        calculateWins();
        if(Variables.noOfPlayers==1) {
            onePlayer();
        }


    }

    public void calculateWins(){
        SharedPreferences spX = getSharedPreferences("XSCORES", MODE_PRIVATE);
        //SharedPreferences spO = getSharedPreferences("OSCORES", MODE_PRIVATE);
        //SharedPreferences spD = getSharedPreferences("DSCORES", MODE_PRIVATE);
        int xWins1 = spX.getInt("xWins", 0);
        //int oWins1=spO.getInt("oWins", 0);
        //int draw1 =spD.getInt("draw", 0);
        xWins.setText(" X Wins =" +xWins1);
       // oWins.setText(" O Wins =" +oWins1);
       // draw.setText(" Draw =" +draw1);
    }

    public void createRows(){

       for(int i=0; i< Variables.boardType;i++)

        {
            TableRow row = new TableRow(Play.this);
            TableLayout.LayoutParams lp = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,Variables.tableSize/Variables.boardType);
            lp.setMargins(10,5,0,5);
            if(i==0){
                lp.setMargins(10,10,0,5);
            }

            else if (i==Variables.boardType-1){
                lp.setMargins(10,5,0,10);
            }
            row.setLayoutParams(lp);
            tl.addView(row);
        }
    }



    public void createTextViews() {

        for (int j=0;j< tl.getChildCount();j++) {
              TableRow row = (TableRow)tl.getChildAt(j);
            for (int i = 0; i < Variables.boardType; i++) {
                TextView tv = new TextView(Play.this);
                tv.setBackgroundColor(Color.WHITE);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(Variables.tableSize/Variables.boardType, Variables.tableSize/Variables.boardType);
                lp.setMargins(0, 0, 10, 0);
                tv.setLayoutParams(lp);
                tv.setText("");
                row.addView(tv);


            }
        }
    }

    public void onePlayer(){
        if(Variables.letter == "O"){
            compLetter = "X";
        }

        else if(Variables.letter == "X"){
            compLetter = "O";
        }


        for(int k =0;k< tl.getChildCount(); k++)
        {
          TableRow row1 = (TableRow)tl.getChildAt(k);

          for(int m = 0; m< Variables.boardType; m++)
          {
              TextView tv1 = (TextView)row1.getChildAt(m);
              map.put(tv1,tv1.getText().toString());
          }
        }

         for(final TextView tvMap : map.keySet()){
                tvMap.setClickable(true);

                tvMap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(gameOver==false) {
                            tvMap.setText(Variables.letter);
                            map.remove(tvMap);
                            tvMap.setGravity(Gravity.CENTER);
                            tvMap.setTextColor(getResources().getColor(R.color.colorPrimary));
                            boxesfilled++;
                            checkWinner();
                            calculateWins();
                            checkDraw();
                            resetBoard();

                        }
                        final Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Object[] keysTv = map.keySet().toArray();
                                if(gameOver==false){
                                if(map.size() !=0) {
                                    Object randomKey = keysTv[new Random().nextInt(keysTv.length)];
                                    TextView compTv = (TextView) randomKey;
                                    compTv.setClickable(false);
                                    compTv.setText(compLetter);
                                    compTv.setGravity(Gravity.CENTER);
                                    compTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                                    map.remove(compTv);
                                    boxesfilled++;
                                    checkWinner();
                                    calculateWins();
                                    checkDraw();
                                    resetBoard();
                                }
                                }
                            }
                        }, 500);


                    }
                });
            }
       // }
        //}
    }

    public boolean checkWinner(){
       if(CheckRows()== true || CheckColumns()==true || CheckDiagonals()==true) {
           return true;
       }
       return false;
    }

    private boolean CheckRows() {
       for(int n=0;n<Variables.boardType;n++){
           TableRow row2 = (TableRow)tl.getChildAt(n);
           for(int p=0;p< row2.getChildCount();p++)
           {
               TextView tv3 = (TextView) row2.getChildAt(p);
               int equals =0;
               for(int q=0;q< row2.getChildCount();q++) {
                   TextView tv4 = (TextView) row2.getChildAt(q);
                   if(tv3.getText().toString().equals(tv4.getText().toString()) && tv3.getText().toString() != ""){
                       equals++;
                   }
               }
               if(equals==row2.getChildCount()) {
                   Toast.makeText(Play.this, tv3.getText().toString() + " WINS", Toast.LENGTH_SHORT).show();
                   if(tv3.getText().toString() == "X"){
                       Xwins++;
                       saveScores();
                   }

                   else if(tv3.getText().toString() == "O"){
                       Owins++;
                       saveScores();
                   }
                   gameOver = true;
                   return true;
               }

           }
       }
        return  false;
    }

    private boolean CheckColumns() {
       for(int i=0;i<Variables.boardType;i++){
           TableRow row1= (TableRow)tl.getChildAt(i);
           for(int k=0;k<row1.getChildCount();k++){
               TextView tv1 = (TextView) row1.getChildAt(k);
               int equals=0;
               for(int j=0;j<Variables.boardType;j++){
                   TableRow row2= (TableRow)tl.getChildAt(j);
                   TextView tv2 = (TextView)row2.getChildAt(k);
                   if(tv1.getText().toString().equals(tv2.getText().toString())&& tv1.getText().toString() != ""){
                       equals++;
                   }
               }
               if(equals==row1.getChildCount()) {
                   Toast.makeText(Play.this, tv1.getText().toString() + " WINS", Toast.LENGTH_SHORT).show();
                   if(tv1.getText().toString() == "X"){
                       Xwins++;
                       saveScores();
                   }

                   else if(tv1.getText().toString() == "O"){
                       Owins++;
                       saveScores();
                   }
                   gameOver = true;
                   return true;
               }
           }



       }

        return false;
    }

    private boolean CheckDiagonals() {
        TableRow row1 = (TableRow) tl.getChildAt(0);
        TextView first = (TextView) row1.getChildAt(0);
        TextView last = (TextView)row1.getChildAt(Variables.boardType-1);
        int equalsFirst=0;
        int equalsLast=0;

            for(int i=0; i< Variables.boardType;i++)
            {
                TableRow row2 = (TableRow) tl.getChildAt(i);
                //for(int k=0;k<row2.getChildCount();k++){
                    TextView tv2= (TextView) row2.getChildAt(i);
                    if(tv2.getText().toString().equals(first.getText().toString())&& first.getText().toString() != "")
                    {
                        equalsFirst++;
                    }
                //}
            }
        if(equalsFirst==Variables.boardType){
            Toast.makeText(Play.this, first.getText().toString() + " WINS", Toast.LENGTH_SHORT).show();
            if(first.getText().toString() == "X"){
                Xwins++;
                saveScores();
            }

            else if(first.getText().toString() == "O"){
                Owins++;
                saveScores();
            }
            gameOver = true;
                return true;
        }

        for(int j= 0; j<Variables.boardType; j++)
        {
            TableRow row3 = (TableRow)tl.getChildAt(j);
           // for(int k=0;k<row3.getChildCount();k++){
                TextView tv2= (TextView) row3.getChildAt(Variables.boardType-1-j);
                if(tv2.getText().toString().equals(last.getText().toString())&& last.getText().toString() != "")
                {
                    equalsLast++;
                }
           // }
        }
        if(equalsLast==Variables.boardType){
            Toast.makeText(Play.this, last.getText().toString() + " WINS", Toast.LENGTH_SHORT).show();
            if(last.getText().toString() == "X"){
                Xwins++;
                saveScores();
            }

            else if(last.getText().toString() == "O"){
                Owins++;
                saveScores();
            }
            gameOver = true;
            return true;
        }
        return false;
        }



    private void checkDraw(){
        if(boxesfilled == Math.pow(Variables.boardType,2)&& checkWinner()==false){
            Toast.makeText(Play.this, "DRAW", Toast.LENGTH_SHORT).show();
            Draw++;
            saveScores();
            gameOver=true;
        }

    }

    public void resetBoard() {
        if (gameOver == true){
            final Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }},1000);
        }

    }

    public void saveScores() {
        // save data into share SharePreference

        //SharedPreferences spO = getSharedPreferences("OSCORES", MODE_PRIVATE);
        //SharedPreferences spD = getSharedPreferences("DSCORES", MODE_PRIVATE);

        //SharedPreferences.Editor editO= spO.edit();
        //SharedPreferences.Editor editD = spD.edit();

        //editO.putInt("oWins", Owins);
        //editD.putInt("draw", Draw);
        editX.putInt("xWins", Xwins);
        editX.apply();
        //editO.apply();
        //editD.apply();
    }




}
