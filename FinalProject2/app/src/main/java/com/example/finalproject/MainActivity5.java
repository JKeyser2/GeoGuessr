package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity5 extends AppCompatActivity implements View.OnClickListener{
    // For storing user guess for longitude and latitude
    double guessLat = 0;
    double guessLon = 0;

    // Creating the team objects
    Team teamOne = new Team();
    Team teamTwo = new Team();

    // Creating 2-D array for storing images and whether or not they have been used
    int [][] imageIds;

    // Create array for image coordinates and image names
    double [][] imageCoordinates;
    String [] imageNames;

    // For storing the current round. There will be 10 rounds
    int currRound = 0;

    // For storing how many pictures we have looked at. Will use % 2 to see which team is up
    int whoIsUp = 0;

    // Stores current index of picture being used
    int picIndex = 0;

    // For getting distance in meters between player guess and the correct location
    Location playerGuess = new Location("");
    Location correctAnswer = new Location("");


    private TextView locationNameView;

    private TextView amountMilesOffView;

    private TextView teamOneScore;

    private TextView teamTwoScore;

    private TextView nextTeamUp;

    private Button proceedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        // Receive invitation to open from other activity
        final Intent[] myIntent = {getIntent()};

        // Store received team objects
        teamOne = (Team) myIntent[0].getSerializableExtra("Team 1 Object");
        teamTwo = (Team) myIntent[0].getSerializableExtra("Team 2 Object");

        // Store received 2-D array of images
        imageIds = (int[][]) myIntent[0].getSerializableExtra("Images Array");

        // Retrieve current round, who is up, index of picture being used
        currRound = (int) myIntent[0].getSerializableExtra("currRound");
        whoIsUp = (int) myIntent[0].getSerializableExtra("whoIsUp");
        picIndex = (int) myIntent[0].getSerializableExtra("picIndex");

        // Store image coordinates and names
        imageCoordinates = (double[][]) myIntent[0].getSerializableExtra("imageCoordinates");
        imageNames = (String[]) myIntent[0].getSerializableExtra("imageNames");

        // Store user guess for longitude and latitude
        guessLon = (double) myIntent[0].getSerializableExtra("guessLong");
        guessLat = (double) myIntent[0].getSerializableExtra("guessLat");

        // Links widgets to XML file. For displaying
        locationNameView = (TextView) findViewById(R.id.locationName) ;
        amountMilesOffView = (TextView) findViewById(R.id.amountMilesOff);
        teamOneScore = (TextView) findViewById(R.id.teamOneScore);
        teamTwoScore = (TextView) findViewById(R.id.teamTwoScore);
        nextTeamUp = (TextView) findViewById(R.id.nextTeamUp);
        proceedButton = (Button) findViewById(R.id.proceedButton);


        locationNameView.setText("Location: " + imageNames[picIndex]);

        // For getting distance in meters between player guess and actual location
        playerGuess.setLatitude(guessLat);
        playerGuess.setLongitude(guessLon);
        correctAnswer.setLatitude(imageCoordinates[picIndex][0]);
        correctAnswer.setLongitude(imageCoordinates[picIndex][1]);
        float distanceInMeters = playerGuess.distanceTo(correctAnswer);
        float distanceInMiles = (float) (distanceInMeters * 0.000621371);


        amountMilesOffView.setText("You Were Off by " + distanceInMiles + " Miles!");

        // If team 1 was up
        if(teamOne.getIsUp() == true){
            // Update their score
            teamOne.setScore(teamOne.getScore() + distanceInMiles);
        // If team 2 was up
        }else{
            // Update their score
            teamTwo.setScore(teamTwo.getScore() + distanceInMiles);
        }

        // Display scores of each team
        teamOneScore.setText(teamOne.getName() + ": " + teamOne.getScore());
        teamTwoScore.setText(teamTwo.getName() + ": " + teamTwo.getScore());

        // If this is not the last picture
        if(whoIsUp != 20){
            // If team 1 was just up
            if(teamOne.getIsUp() == true){
                nextTeamUp.setText(teamTwo.getName() + " is up Next!");
            // If team 2 was just up
            }else{
              nextTeamUp.setText(teamOne.getName() + " is up Next!");
            }
        // If this is the last picture
        }else{
            nextTeamUp.setText("Click 'Proceed' so we can Crown the Winner!");
        }

        proceedButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        // If the game is not over, go back to MainActivity2
        if(whoIsUp != 20){
            // Connecting the 2 activities
            Intent myIntent = new Intent(MainActivity5.this, MainActivity2.class);

            // Sending over team objects to next activity
            myIntent.putExtra("Team 1 Object", teamOne);
            myIntent.putExtra("Team 2 Object", teamTwo);

            // Sending over array of images to next activity
            myIntent.putExtra("Images Array", imageIds);

            // Send over the current round an integer value for who is up
            myIntent.putExtra("currRound", currRound);
            myIntent.putExtra("whoIsUp", whoIsUp);

            // Send over arrays of coordinates and image names
            myIntent.putExtra("imageCoordinates", imageCoordinates);
            myIntent.putExtra("imageNames", imageNames);

            // Open up next activity
            startActivity(myIntent);
        // If the game is over, go to MainActivity6
        }else{
            // Connecting the 2 activities
            Intent myIntent = new Intent(MainActivity5.this, MainActivity6.class);

            // Sending over team objects to next activity
            myIntent.putExtra("Team 1 Object", teamOne);
            myIntent.putExtra("Team 2 Object", teamTwo);

            // Open up next activity
            startActivity(myIntent);
        }

    }
}