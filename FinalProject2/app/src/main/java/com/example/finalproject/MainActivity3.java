package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.finalproject.databinding.ActivityMain3Binding;

public class MainActivity3 extends FragmentActivity implements OnMapReadyCallback {
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

    private GoogleMap mMap;
    private ActivityMain3Binding binding;

    private Button proceedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
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

        //System.out.println("AYE YO");
        //System.out.println(imageCoordinates[0][0]);



        // Links widgets to XML file. For displaying
        proceedButton = (Button) findViewById(R.id.proceedButton);

        // Disable the button until they select a spot on the map
        proceedButton.setEnabled(false);

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        // Stores previously added marker if there is one
        final Marker[] prevMarker = {null};

        // Add a marker wherever the user clicks on the map
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                System.out.println("latLng: " + latLng);

                // Store user guess for longitude and latitude
                guessLat = latLng.latitude;
                guessLon = latLng.longitude;


                // If there is a previous marker, remove it
                if (prevMarker[0] != null) {
                    prevMarker[0].remove();
                }

                // Add a new marker at the clicked position
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Your Guess").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                // Store the marker so that if user wants to change their guess this marker will be removed
                prevMarker[0] = mMap.addMarker(markerOptions);

                // Enable the button as a marker has been added to the map
                proceedButton.setEnabled(true);
            }
        });

        // If player click the proceed button
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println("VERY COOL MY GUY");

                // Connecting the 2 activities
                myIntent[0] = new Intent(MainActivity3.this, MainActivity4.class);

                // Sending over team objects to next activity
                myIntent[0].putExtra("Team 1 Object", teamOne);
                myIntent[0].putExtra("Team 2 Object", teamTwo);

                // Sending over array of images to next activity
                myIntent[0].putExtra("Images Array", imageIds);

                // Send over the current round an integer value for who is up
                myIntent[0].putExtra("currRound", currRound);
                myIntent[0].putExtra("whoIsUp", whoIsUp);

                // Send over arrays of coordinates and image names
                myIntent[0].putExtra("imageCoordinates", imageCoordinates);
                myIntent[0].putExtra("imageNames", imageNames);

                // Send over user guess for longitude and latitude
                myIntent[0].putExtra("guessLong", guessLon);
                myIntent[0].putExtra("guessLat", guessLat);


                // Send over index of current picture being used
                myIntent[0].putExtra("picIndex", picIndex);

                // Open up next activity
                startActivity(myIntent[0]);
            }
        });

    }







}