package com.example.brenda.tic_tac_toe;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseActivity extends AppCompatActivity {
    Button boardButton3;
    Button boardButton5;
    Button playerButton1;
    Button playerButton2;
    Button letterButtonX;
    Button letterButtonO;
    Button playButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose);

        boardButton3 = (Button)findViewById(R.id.three);
        boardButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //boardButton3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                Variables.boardType=3;
                Variables.tableSize=645;
                boardButton3.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
                boardButton5.setClickable(false);
            }
        });


        boardButton5 = (Button)findViewById(R.id.five);
        boardButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardButton5.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
                Variables.boardType=5;
                Variables.tableSize=1000;
                boardButton3.setClickable(false);
            }
        });


        playerButton1 = (Button)findViewById(R.id.players1);
        playerButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerButton1.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
                Variables.noOfPlayers=1;
                playerButton2.setClickable(false);
            }
        });


        playerButton2 = (Button)findViewById(R.id.players2);
        playerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerButton2.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
                Variables.noOfPlayers=2;
                playerButton1.setClickable(false);
            }
        });



        letterButtonO = (Button)findViewById(R.id.letterO);
        letterButtonO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                letterButtonO.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
                Variables.letter="O";
                letterButtonX.setClickable(false);
            }
        });


        letterButtonX = (Button)findViewById(R.id.letterX);
        letterButtonX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                letterButtonX.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
                Variables.letter="X";
                letterButtonO.setClickable(false);
            }
        });

        playButton = (Button)findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playIntent = new Intent(ChooseActivity.this, Play.class);
                startActivity(playIntent);
            }
        });

    }
}
