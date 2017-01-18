package com.example.android.scarnedice;

import android.support.v7.app.AppCompatActivity;


import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvPlayerTurnScore,tvComputerOverallScore,tvPlayerOverallScore,tvStatus;
    private ImageView ivDiceFace;
    private Button btHold,btRoll,btReset;

    private int computerTurnScore,computerOverallScore,playerTurnScore,playerOverallScore;
    private int[] diceFaces = {R.drawable.dice1,R.drawable.dice2,R.drawable.dice3,R.drawable.dice4,R.drawable.dice5,R.drawable.dice6};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPlayerTurnScore = (TextView) findViewById(R.id.tvPlayerTurnScore);
        tvPlayerOverallScore = (TextView) findViewById(R.id.tvPlayerOverallScore);
        tvComputerOverallScore = (TextView) findViewById(R.id.tvComputerOverallScore);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        ivDiceFace = (ImageView) findViewById(R.id.ivDiceFace);
        btHold = (Button) findViewById(R.id.btHold);
        btRoll = (Button) findViewById(R.id.btRoll);
        btReset = (Button) findViewById(R.id.btReset);

        btHold.setOnClickListener(this);
        btRoll.setOnClickListener(this);
        btReset.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btHold:
                hold();
                break;
            case R.id.btRoll:
                roll();
                break;
            case R.id.btReset:
                reset();
                break;
        }

    }
    private void roll(){
        btHold.setEnabled(true);
        tvPlayerTurnScore.setVisibility(View.VISIBLE);
        int rollNumber = getDiceFaceNumber();
        Toast.makeText(this, "Rolled"+ rollNumber, Toast.LENGTH_SHORT).show();
        if(rollNumber != 1){
            playerTurnScore= rollNumber;
            playerOverallScore += rollNumber;
            tvPlayerTurnScore.setText("player's turn score is:"+playerTurnScore);
            tvPlayerOverallScore.setText("player's overall score:"+playerOverallScore);

        }else {
            playerTurnScore = 0 ;
            tvPlayerTurnScore.setText("player's turn score is:"+playerTurnScore);
            tvPlayerOverallScore.setText("player's overall score"+playerOverallScore);
            computerTurn();
        }
        if(playerOverallScore>=10)
            winP();
    }
    private int getDiceFaceNumber(){
        Random random = new Random();
        int i = random.nextInt(6);
        ivDiceFace.setImageResource(diceFaces[i]);
        return  i+1;
    }
    private void playerTurn(){
        resetPlayerTurnScore();
        Toast.makeText(this, "Your turn", Toast.LENGTH_SHORT).show();
        btHold.setEnabled(false);
        btRoll.setEnabled(true);
        if(playerOverallScore>=10)
            winP();
    }
    private void reset(){
        tvStatus.setText("STATUS");
        tvPlayerTurnScore.setVisibility(View.VISIBLE);
        tvComputerOverallScore.setVisibility(View.VISIBLE);
        btHold.setEnabled(false);
        btRoll.setEnabled(true);
        playerOverallScore = 0;
        playerTurnScore=0;
        computerOverallScore=0;
        computerTurnScore=0;
        tvPlayerOverallScore.setText("Player's overall score:" + playerOverallScore);
        tvComputerOverallScore.setText("computer's overall score:" + computerOverallScore);
        tvPlayerTurnScore.setVisibility(View.INVISIBLE);
        ivDiceFace.setImageResource(diceFaces[0]);
    }
    private void hold(){

        playerTurnScore = 0;
        tvPlayerOverallScore.setText("player's overall score:" + playerOverallScore);
        computerTurn();
    }
    private void computerTurn(){
        Toast.makeText(this, "computer's turn", Toast.LENGTH_SHORT).show();
        resetPlayerTurnScore();
        btHold.setEnabled(false);
        btRoll.setEnabled(false);

        final Random random = new Random();
        while(true){
            int rollNumber = getDiceFaceNumber();
            Toast.makeText(this, "Rolled:"+ rollNumber, Toast.LENGTH_SHORT).show();
            if (rollNumber!=1){
                computerTurnScore = rollNumber;

                boolean hold = random.nextBoolean();
                if(hold){
                    computerOverallScore += computerTurnScore;
                    break;
                }
            }else{
                computerOverallScore += computerTurnScore;
                computerTurnScore=0;
                break;
            }

        }
        tvComputerOverallScore.setText("computer's score:"+computerOverallScore);
        if(computerOverallScore>=10)
            winC();
        playerTurn();
    }
    private void resetPlayerTurnScore(){
        playerTurnScore=0;
        tvPlayerTurnScore.setText("player's turn score:"+playerTurnScore);
    }
    private void winP() {
        Toast.makeText(this, "YOU WON!", Toast.LENGTH_LONG).show();
        reset();
    }

    private void winC() {
        Toast.makeText(this, "Computer WON!", Toast.LENGTH_LONG).show();
        reset();
    }


}
