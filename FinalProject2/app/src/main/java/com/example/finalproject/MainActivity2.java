package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{

    // Creating the team objects
    Team teamOne = new Team();
    Team teamTwo = new Team();

    // Creating 2-D array for storing images and whether or not they have been used
    int [][] imageIds;

    // Create array for image coordinates and image names
    double [][] imageCoordinates;
    String [] imageNames;

    // For displaying the image, the current round, who is up
    private ImageView imageView;

    private TextView currRoundView;

    private TextView whoIsUpView;

    private Button proceedButton;

    Intent myIntent;

    // For storing index of the picture
    int picIndex = 0;


    // For storing the current round. There will be 10 rounds
    int currRound = 0;

    // For storing how many pictures we have looked at. Will use % 2 to see which team is up
    int whoIsUp = 0;

    // For keeping track if we have already used the picture
    boolean pictureUsed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Receive invitation to open from other activity
        Intent myIntent = getIntent();

        // Store received team objects
        teamOne = (Team) myIntent.getSerializableExtra("Team 1 Object");
        teamTwo = (Team) myIntent.getSerializableExtra("Team 2 Object");

        // Store received 2-D array of images
        imageIds = (int[][]) myIntent.getSerializableExtra("Images Array");

        // Retrieve current round and who is up
        currRound = (int) myIntent.getSerializableExtra("currRound");
        whoIsUp = (int) myIntent.getSerializableExtra("whoIsUp");

        // Store image coordinates and names
        imageCoordinates = (double[][]) myIntent.getSerializableExtra("imageCoordinates");
        imageNames = (String[]) myIntent.getSerializableExtra("imageNames");


        // Links widgets to XML file. For displaying
        imageView = (ImageView) findViewById(R.id.imageView);
        currRoundView = (TextView) findViewById(R.id.currRound) ;
        whoIsUpView = (TextView) findViewById(R.id.currTeam);
        proceedButton = (Button) findViewById(R.id.proceedButton);

        currRoundView.setText("Current Round: " + currRound);

        //System.out.println("Who is up: " + whoIsUp);


        // If team 1 gets to start as up
        if(teamOne.getStartsUp() == true){
            //System.out.println("First currRound");
            // Increment the current round
            currRound = currRound + 1;

            // Increment the amount of pictures seen
            whoIsUp = 1;

            // Edit the text panes
            currRoundView.setText("Current Round: " + currRound);
            whoIsUpView.setText("Team: " + teamOne.getName());

            teamOne.setIsUp(true);
            teamTwo.setIsUp(false);

            teamOne.setStartsUp(false);

            // Team 1 gets all even indexes of whoIsUp
            teamOne.setisEven(true);
        }

        // If team2 gets to start as up
        else if(teamTwo.getStartsUp() == true){
            // Increment the current round
            //System.out.println("second curr round");
            currRound = currRound + 1;

            // Increment the amount of pictures seen
            whoIsUp = 1;

            // Edit the text panes
            currRoundView.setText("Current Round: " + currRound);
            // Edit the text pane
            whoIsUpView.setText("Team: " + teamTwo.getName());

            teamTwo.setIsUp(true);
            teamOne.setIsUp(false);

            teamTwo.setStartsUp(false);

            // Team 2 gets all even indexes of whoIsUp
            teamTwo.setisEven(true);
        }
        // Rest of the turns that don't include the starting turn
    else{
            //System.out.println("Who is up: " + whoIsUp);

            // If even index and team 1 is up
            if(whoIsUp % 2 == 0 && teamOne.getisEven() == true){
                //ystem.out.println("1");
                // Increment the current round
                //System.out.println("3rd curr round");
                currRound = currRound + 1;

                // Increment the amount of pictures seen
                whoIsUp = whoIsUp + 1;

                // Edit the text panes
                currRoundView.setText("Current Round: " + currRound);
                whoIsUpView.setText("Team: " + teamOne.getName());

                teamOne.setIsUp(true);
                teamTwo.setIsUp(false);

                // If even index and team 2 is up
            }else if(whoIsUp % 2 == 0 && teamTwo.getisEven() == true){
                //System.out.println("Who is up: " + whoIsUp);
                //System.out.println("2");
                // Increment the current round
                //System.out.println("fourth curr round");
                currRound = currRound + 1;

                // Increment the amount of pictures seen
                whoIsUp = whoIsUp + 1;

                // Edit the text panes
                currRoundView.setText("Current Round: " + currRound);
                // Edit the text pane
                whoIsUpView.setText("Team: " + teamTwo.getName());

                teamTwo.setIsUp(true);
                teamOne.setIsUp(false);
                // If odd index and team 1 is up
            }else if(whoIsUp % 2 == 1 && teamOne.getisEven() == false){
                //System.out.println("3");

                // Increment the amount of pictures seen
                whoIsUp = whoIsUp + 1;


                whoIsUpView.setText("Team: " + teamOne.getName());

                teamOne.setIsUp(true);
                teamTwo.setIsUp(false);
                // If odd index and team 2 is up
            }else if(whoIsUp % 2 == 1 && teamTwo.getisEven() == false){
                //System.out.println("4");
                // Increment the amount of pictures seen
                whoIsUp = whoIsUp + 1;

                // Edit the text pane
                whoIsUpView.setText("Team: " + teamTwo.getName());

                teamTwo.setIsUp(true);
                teamOne.setIsUp(false);
            }
        }


        // While we have not yet found a picture that has not been used
        while(pictureUsed == true){
            // Getting a random number for an index for a picture
            Random rand = new Random();
            picIndex = rand.nextInt(50);

            // If we have not yet used the picture
            if(imageIds[picIndex][1] == 0){
                // Set the picture to used
                imageIds[picIndex][1] = 1;

                // Display the picture
                imageView.setImageResource(imageIds[picIndex][0]);

                // Set to false to exit the loop
                pictureUsed = false;
            }


        }


        proceedButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            // Button is clicked, players want to proceed to the map
            case R.id.proceedButton:Button:
            // Connecting the 2 activities
            myIntent = new Intent(MainActivity2.this, MainActivity3.class);

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

            // Send over index for the picture being used
            myIntent.putExtra("picIndex", picIndex);

            // Open up next activity
            startActivity(myIntent);

            break;
        }
    }
}