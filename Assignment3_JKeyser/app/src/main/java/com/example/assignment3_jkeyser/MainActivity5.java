package com.example.assignment3_jkeyser;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity5 extends AppCompatActivity implements View.OnClickListener{

    private TextView responseStringText;
    private Button goHomeButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        responseStringText = (TextView) findViewById(R.id.responseStringText);

        goHomeButton = (Button) findViewById(R.id.goHome);

        goHomeButton.setOnClickListener(this);

        // Receive invitation to open from other activity
        Intent myIntent = getIntent();

        // Store received response string
        String responseString = (String) myIntent.getSerializableExtra("responseString");

        responseStringText.setText(responseString);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.goHome){
            // Connecting the 2 activities
            Intent myIntent = new Intent(MainActivity5.this, MainActivity.class);


            // Open up next activity
            startActivity(myIntent);
        }
    }
}