package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private Button proceedButton;

    private EditText editTextTeamOne, editTextTeamTwo;

    // For storing the user input for what will be their team name
    String userInputTeamOne = "Team One";
    String userInputTeamTwo = "Team Two";

    // Creating the team objects
    Team teamOne = new Team();
    Team teamTwo = new Team();

    // Stores image and whether or not it has been used
    int [][] imageIds = {{R.drawable.z1, 0}, {R.drawable.z2, 0}, {R.drawable.z3, 0}, {R.drawable.z4, 0},
                         {R.drawable.z5, 0}, {R.drawable.z6, 0}, {R.drawable.z7, 0}, {R.drawable.z8, 0},
                         {R.drawable.z9, 0}, {R.drawable.z10, 0}, {R.drawable.z11, 0}, {R.drawable.z12, 0},
                         {R.drawable.z13, 0}, {R.drawable.z14, 0}, {R.drawable.z15, 0}, {R.drawable.z16, 0},
                         {R.drawable.z17, 0}, {R.drawable.z18, 0}, {R.drawable.z19, 0}, {R.drawable.z20, 0},
                         {R.drawable.z21, 0}, {R.drawable.z22, 0}, {R.drawable.z23, 0}, {R.drawable.z24, 0},
                         {R.drawable.z25, 0}, {R.drawable.z26, 0}, {R.drawable.z27, 0}, {R.drawable.z28, 0},
                         {R.drawable.z29, 0}, {R.drawable.z30, 0}, {R.drawable.z31, 0}, {R.drawable.z32, 0},
                         {R.drawable.z33, 0}, {R.drawable.z34, 0}, {R.drawable.z35, 0}, {R.drawable.z36, 0},
                         {R.drawable.z37, 0}, {R.drawable.z38, 0}, {R.drawable.z39, 0}, {R.drawable.z40, 0},
                         {R.drawable.z41, 0}, {R.drawable.z42, 0}, {R.drawable.z43, 0}, {R.drawable.z44, 0},
                         {R.drawable.z45, 0}, {R.drawable.z46, 0}, {R.drawable.z47, 0}, {R.drawable.z48, 0},
                         {R.drawable.z21, 49}, {R.drawable.z50, 0}};


    // Store the coordinates for all of the images
    double [][] imageCoordinates = { {39.2779, -76.6227},{36.1069, -112.1126}, {28.3852, -81.5639}, {37.8199, -122.4783},
            {47.6205, -122.3493}, {43.8789, -103.4598}, {44.5251, -110.8385}, {39.9496, -75.1503}, {39.948, -75.150},
            {38.2023, -85.7708}, {34.0195, -118.4912}, {40.3428, -105.6836}, {37.8267, -122.4233}, {48.8584, 2.2945},
            {40.4319, 116.5674}, {41.8902, 12.4922}, {40.6892, -74.0445}, {29.9792, 31.1342}, {-13.1631, -72.5450},
            {37.9715, 23.7269}, {27.1750, 78.0422}, {3.1579, 101.7120}, {51.1788, -1.8261}, {25.1972, 55.2744},
            {37.1760, -3.5875}, {-22.9519, -43.2106}, {41.4040, 2.1744}, {52.5163, 13.3778}, {51.5081, -0.0759},
            {29.6578, 91.1172}, {47.5576, 10.7498}, {-17.9249, 25.8572}, {41.9022, 12.4539}, {34.1341, -118.3215},
            {43.7231, 10.3966}, {51.5055, -0.0754}, {52.9719, -9.4309}, {55.2408, -6.5116}, {38.7879, -9.3909},
            {48.6361, -1.5110}, {43.3864, -8.4061}, {30.3285, 35.4419}, {51.4995, -0.1248}, {21.4225, 39.8262},
            {-27.1210, -109.3667}, {36.8796, -111.5108}, {44.8621, -0.5170}, {34.1184, -118.3004}, {44.4917, -63.9167},
            {34.6870, 135.5259} };


    // Stores the names for all of the images
    String [] imageNames = {"Baltimore Ravens Stadium", "Grand Canyon", "Disney World, Florida", "Golden Gate Bridge",
            "Space Needle, Seattle", "Mount Rushmore", "Grand Prismatic Spring", "Yellowstone National Park",
            "Liberty Bell", "Oahu, Hawaii", "Kentucky Derby", "Rocky Mountain National Park", "Alcatraz Island",
            "Eiffel Tower", "Great Wall of China", "Roman Colosseum", "Statue of Liberty", "Pyramids of Giza",
            "Machu Picchu", "Acropolis", "Taj Mahal", "Petronas Towers", "Stonehenge", "Burj Khalifa", "The Alhambra",
            "The Christ the Redeemer Statue", "The Sagrada Familia", "The Brandenburg Gate", "The Tower of London",
            "The Potala Palace", "The Neuschwanstein Castle", "Victoria Falls", "The St. Peter's Basilica",
            "The Hollywood Sign", "The Leaning Tower of Pisa", "The Tower Bridge", "The Cliffs of Moher",
            "The Giant's Causeway", "The Pena Palace", "The Mont Saint-Michel", "The Tower of Hercules", "Petra, Jordan",
            "The Palace of Westminster", "The Great Mosque of Mecca", "Easter Island", "Horseshoe Bend",
            "Cite du Vin", "Griffith Observatory", "Peggy's Point Lighthouse", "Osaka Castle"};


    // For storing the current round. There will be 10 rounds
    int currRound = 0;

    // For storing how many pictures we have looked at. Will use % 2 to see which team is up
    int whoIsUp = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Links widgets to XML file. For displaying
        editTextTeamOne = (EditText) findViewById(R.id.Team1);
        editTextTeamTwo = (EditText) findViewById(R.id.Team2);
        proceedButton = (Button) findViewById(R.id.proceedButton);


        proceedButton.setOnClickListener(this);
    }

    public void onClick(View v){

        switch(v.getId()){
            // Button is clicked, players want to proceed to the game
            case R.id.proceedButton:

                // Store team names
                userInputTeamOne = editTextTeamOne.getText().toString();
                userInputTeamTwo = editTextTeamTwo.getText().toString();

                // Put team names into team objects
                teamOne.setName(userInputTeamOne);
                teamTwo.setName(userInputTeamTwo);


                // Connecting the 2 activities
                Intent myIntent = new Intent(MainActivity.this, getFirstPlayer.class);

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

                break;


        }
    }
}