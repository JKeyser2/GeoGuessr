package com.example.assignment3_jkeyser;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button loginButton, registerButton, resetPasswordButton, confirmButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);
        resetPasswordButton = (Button) findViewById(R.id.resetPasswordButton);
        confirmButton = (Button) findViewById(R.id.confirmSignup);
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        resetPasswordButton.setOnClickListener(this);
        confirmButton.setOnClickListener(this);



    }

    public void onClick(View v) {
        if (v.getId() == R.id.registerButton) {
            // Handle login button click
            Intent myIntent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(myIntent);
        } else if (v.getId() == R.id.loginButton) {
            System.out.println("VERY EPIC");
            // Handle register button click
            Intent myIntent = new Intent(MainActivity.this, MainActivity3.class);
            startActivity(myIntent);
        } else if (v.getId() == R.id.resetPasswordButton) {
            System.out.println("RESET");
            // Handle reset password button click
            Intent myIntent = new Intent(MainActivity.this, MainActivity4.class);
            startActivity(myIntent);
        }
          else if (v.getId() == R.id.confirmSignup) {
            // Handle reset password button click
            Intent myIntent = new Intent(MainActivity.this, MainActivity6.class);
            startActivity(myIntent);
        }
    }



}