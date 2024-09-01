package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity6 extends AppCompatActivity implements View.OnClickListener{

    // Creating the team objects
    Team teamOne = new Team();
    Team teamTwo = new Team();

    private TextView teamOneScoreView;

    private TextView teamTwoScoreView;

    private TextView whoIsWinnerView;

    private Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        // Links widgets to XML file. For displaying
        teamOneScoreView = (TextView) findViewById(R.id.teamOneScore) ;
        teamTwoScoreView = (TextView) findViewById(R.id.teamTwoScore);
        whoIsWinnerView = (TextView) findViewById(R.id.whoIsWinner);
        exitButton = (Button) findViewById(R.id.exitButton);

        // Receive invitation to open from other activity
        Intent myIntent = getIntent();

        // Store received team objects
        teamOne = (Team) myIntent.getSerializableExtra("Team 1 Object");
        teamTwo = (Team) myIntent.getSerializableExtra("Team 2 Object");

        teamOneScoreView.setText(teamOne.getName() + ": " + teamOne.getScore());
        teamTwoScoreView.setText(teamTwo.getName() + ": " + teamTwo.getScore());

        // If team 1 has a greater score, team 2 is the winner
        if(teamOne.getScore() > teamTwo.getScore()){
            whoIsWinnerView.setText(teamTwo.getName() + " is the Winner!");
        // If team 2 has a greater score, team 1 is the winner
        }else{
            whoIsWinnerView.setText(teamOne.getName() + " is the Winner!");
        }

        exitButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        // Exit program
        System.exit(0);
    }
}