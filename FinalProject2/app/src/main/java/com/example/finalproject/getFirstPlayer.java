package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class getFirstPlayer extends AppCompatActivity implements View.OnClickListener, SensorEventListener {

    private Button ready_btn;
    private EditText curr_team;
    private TextView instructions;
    private static final int SHAKE_DURATION = 3000;
    private static final float THRESHOLD = 2.0f;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private long startTime;
    private float accumulatedForce;
    private int readingCount;

    private float playerOneForce = 0;
    private float playerTwoForce = 0;
    int curr_player = 1;

    private Team teamOne;
    private Team teamTwo;
    private int[][] imageIds;
    private int currRound;
    private double[][] imageCoordinates;
    private String[] imageNames;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_first_player);
        ready_btn = (Button) findViewById(R.id.buttonReady);
        ready_btn.setOnClickListener(this);
        curr_team = (EditText) findViewById(R.id.editTextTeam);
        instructions = (TextView) findViewById(R.id.instructionsText);
        sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        curr_team.setText("Team 1");
        // Receive invitation to open from other activity
        Intent myIntent = getIntent();

        // Store received team objects
        teamOne = (Team) myIntent.getSerializableExtra("Team 1 Object");
        teamTwo = (Team) myIntent.getSerializableExtra("Team 2 Object");

        // Store received 2-D array of images
        imageIds = (int[][]) myIntent.getSerializableExtra("Images Array");

        // Retrieve current round and who is up
        currRound = (int) myIntent.getSerializableExtra("currRound");

        // Store image coordinates and names
        imageCoordinates = (double[][]) myIntent.getSerializableExtra("imageCoordinates");
        imageNames = (String[]) myIntent.getSerializableExtra("imageNames");

    }

    public void startShakeDetection() {
        startTime = System.currentTimeMillis();
        accumulatedForce = 0.0f;
        readingCount = 0;

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopShakeDetection() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onClick(View view) {
        startShakeDetection();
        ready_btn.setEnabled(false);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            // calculate the mean acceleration throughout all the sensors
            float force = (float) Math.sqrt(x * x + y * y + z * z);
            accumulatedForce += force;
            readingCount++;
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - startTime;
            if (elapsedTime >= SHAKE_DURATION) {
                // Calculate the average force
                float averageForce = accumulatedForce / readingCount;
                if(curr_player == 1){
                    playerOneForce = averageForce;
                    ready_btn.setEnabled(true);
                    curr_player = 2;
                    curr_team.setText("Team 2");
                    stopShakeDetection();
                }
                else{
                    playerTwoForce = averageForce;
                    stopShakeDetection();

                    if(playerOneForce >= playerTwoForce){
                        teamOne.setStartsUp(true);
                    }
                    else{
                        teamTwo.setStartsUp(true);
                    }

                    // Connecting the 2 activities
                    Intent myIntent = new Intent(this, MainActivity2.class);

                    // Sending over team objects to next activity
                    myIntent.putExtra("Team 1 Object", teamOne);
                    myIntent.putExtra("Team 2 Object", teamTwo);

                    // Sending over array of images to next activity
                    myIntent.putExtra("Images Array", imageIds);

                    // Send over the current round an integer value for who is up
                    myIntent.putExtra("currRound", currRound);

                    // Send over arrays of coordinates and image names
                    myIntent.putExtra("imageCoordinates", imageCoordinates);
                    myIntent.putExtra("imageNames", imageNames);
                    int winner;
                    if(playerOneForce >= playerTwoForce){
                        winner = 0;
                    }
                    else{
                        winner = 1;
                    }
                    myIntent.putExtra("whoIsUp", winner);

                    // Open up next activity
                    startActivity(myIntent);
                }

                startTime = currentTime;
                accumulatedForce = 0.0f;
                readingCount = 0;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}