package com.pcr.dexter.scarnes_dice;

import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private int yourOverAllScore = 0;
    private int yourTurnScore;
    private int compOverAllScore = 0;
    private int comTurnScore;
    private Random random = new Random();

    ImageView diceView;

    TextView ComputerScoreValue;
    TextView yourScoreValue;
    TextView yourTurnScoreValue;
    TextView yourTurnScoreLable;
    TextView compTurnScoreValue;
    TextView compTurnScoreLable;

    Button rollButton;
    Button holdButton;
    Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rollButton = (Button)findViewById(R.id.rollButton);
        holdButton = (Button) findViewById(R.id.holdButton);
        resetButton = (Button) findViewById(R.id.resetButton);

        ComputerScoreValue = (TextView) findViewById(R.id.computerScoreValue);
        yourScoreValue = (TextView) findViewById(R.id.yourScoreValue);
        yourTurnScoreValue = (TextView) findViewById(R.id.yourTurnScoreValue);
        yourTurnScoreLable = (TextView) findViewById(R.id.yourTurnScoreLable);
        compTurnScoreLable = (TextView) findViewById(R.id.compTurnScoreLable);
        compTurnScoreValue = (TextView) findViewById(R.id.compTurnScoreValue);


        diceView = (ImageView) findViewById(R.id.diceView);


        rollButton.setOnClickListener(this);
        holdButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);

        rollingDice(6,1);
        yourTurn();

    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rollButton:
                int score = rollingDice(6,1);
                if(score == 1){
                    yourTurnScore = 1;
                    yourOverAllScore+=1;
                    yourTurnScoreValue.setText(String.valueOf(yourTurnScore));
                    yourScoreValue.setText(String.valueOf(yourOverAllScore));
                    Toast.makeText(getApplicationContext(),"You got 1",Toast.LENGTH_SHORT).show();
                    computerTurn();
                }
                else{
                    yourTurnScore+=score;
                    yourOverAllScore+=score;
                    yourTurnScoreValue.setText(String.valueOf(yourTurnScore));
                }
                break;
            case R.id.holdButton:
                yourOverAllScore += yourTurnScore;
                yourScoreValue.setText((String.valueOf(yourOverAllScore)));
                if(yourOverAllScore >= 100){
                    Toast.makeText(getApplicationContext(),"You Win! Keep Playing....!!!",Toast.LENGTH_SHORT).show();
                    startNewGame();
                }else {
                    computerTurn();
                }
                break;
            case R.id.resetButton:
                resetGame();
                break;
        }
    }

    private void computerTurn() {
        Toast.makeText(getApplicationContext(),"Computer Turn",Toast.LENGTH_SHORT).show();
        comTurnScore = 0;
        compTurnScoreValue.setText(String.valueOf(comTurnScore));
        rollButton.setEnabled(false);
        holdButton.setEnabled(false);
        resetButton.setEnabled(false);
        yourTurnScoreValue.setVisibility(View.INVISIBLE);
        yourTurnScoreLable.setVisibility(View.INVISIBLE);
        compTurnScoreLable.setVisibility(View.VISIBLE);
        compTurnScoreValue.setVisibility(View.VISIBLE);


        final android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                computerPlays();
            }
        },500);
    }

    private void computerPlays() {
        int score;
        boolean mode=false;
        if(!mode) {
            score = rollingDice(6, 1);
        }
        else{
            score = rollingDice(5,2);
        }
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {}
        },1000);
        if(score == 1){
            comTurnScore = 0;
            compTurnScoreValue.setText(String.valueOf(comTurnScore));
            Toast.makeText(getApplicationContext(),"Computer got 1",Toast.LENGTH_SHORT).show();
            yourTurn();
        }
        else {
            comTurnScore += score;
            compTurnScoreValue.setText(String.valueOf(comTurnScore));
            if (compOverAllScore + comTurnScore >= 100) {
                computerHold();
            } else {
                if (comTurnScore >= 15) {
                    computerHold();
                } else {
                    handler = new android.os.Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            computerPlays();
                        }
                    }, 500);
                }
            }
        }
    }


    private void computerHold() {
        Toast.makeText(getApplicationContext(),"Computer Holds",Toast.LENGTH_SHORT).show();
        compOverAllScore += comTurnScore;
        ComputerScoreValue.setText(String.valueOf(compOverAllScore));
        if(compOverAllScore >= 100){
            Toast.makeText(getApplicationContext(),"Computer Wins! Keep Trying",Toast.LENGTH_SHORT).show();
            final android.os.Handler handler = new android.os.Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startNewGame();
                }
            },500);
        }else{
            final android.os.Handler handler = new android.os.Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    yourTurn();;
                }
            },500);
        }
    }

    private void startNewGame() {
        Toast.makeText(getApplicationContext(),"Starting New Game!",Toast.LENGTH_SHORT).show();
        rollingDice(6,1);
        resetGame();
    }

    private void resetGame() {
        compOverAllScore = 0;
        yourOverAllScore = 0;
        ComputerScoreValue.setText(String.valueOf(compOverAllScore));
        yourScoreValue.setText(String.valueOf(yourOverAllScore));
        yourTurn();
    }


    private void yourTurn() {
        Toast.makeText(getApplicationContext(),"Your Turn",Toast.LENGTH_SHORT).show();
        yourTurnScore = 0;
        yourTurnScoreValue.setText(String.valueOf(yourTurnScore));
        yourTurnScoreValue.setVisibility(View.VISIBLE);
        yourTurnScoreLable.setVisibility(View.VISIBLE);
        compTurnScoreLable.setVisibility(View.INVISIBLE);
        compTurnScoreValue.setVisibility(View.INVISIBLE);

        rollButton.setEnabled(true);
        holdButton.setEnabled(true);
        resetButton.setEnabled(true);
    }
    private int rollingDice(int x,int y) {
        int diceFront = random.nextInt(x)+y;
        switch (diceFront){
            case 1:
                diceView.setImageResource(R.drawable.dice1);
                break;
            case 2:
                diceView.setImageResource(R.drawable.dice2);
                break;
            case 3:
                diceView.setImageResource(R.drawable.dice3);
                break;
            case 4:
                diceView.setImageResource(R.drawable.dice4);
                break;
            case 5:
                diceView.setImageResource(R.drawable.dice5);
                break;
            case 6:
                diceView.setImageResource(R.drawable.dice6);
                break;
        }
        return diceFront;
    }

}