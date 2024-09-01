package com.example.assignment3_jkeyser;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity6 extends AppCompatActivity implements View.OnClickListener{

    private static final String API_CONFIRM = "https://p5qp2sj485.execute-api.us-east-2.amazonaws.com/default/491ConfirmSignUp/491confirmsignup";

    private EditText username, code;
    private Button confirmButton;

    String stringUsername = "";
    String stringCode = "";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        username = (EditText) findViewById(R.id.theUsername3);
        code = (EditText) findViewById(R.id.theCode3);

        confirmButton = (Button) findViewById(R.id.registerButton2);

        confirmButton.setOnClickListener(this);
    }

    //@Override
    public void onClick(View view) {
        if (view.getId() == R.id.registerButton2){
            System.out.println("NICE!");
            stringUsername = username.getText().toString();
            stringCode = code.getText().toString();


            String[] parameters = new String[3];
            parameters[0] = stringUsername;
            parameters[1] = stringCode;
            // private static final String API_SIGNIN = "some_url"
            parameters[2] = API_CONFIRM;

            WebService temp = new WebService();
            temp.execute(parameters);
        }

    }

    private class WebService extends AsyncTask<String, String, String>{


        //@Override
        protected String doInBackground(String... inputs) {
            try{
                String theUsername = inputs[0];
                String theCode = inputs[1];



                String apiurl = API_CONFIRM;

                // Store username and password in a JSON object
                JSONObject json = new JSONObject();
                json.put("username", theUsername);
                json.put("confirmation_code", theCode);


                String jsonstring = json.toString();

                URL url = new URL (apiurl);
                // Creates a URL connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                // Indicates that the connection will be used for output, meaning that you intend to write data to the server.
                urlConnection.setDoOutput(true);
                // Using POST method
                urlConnection.setRequestMethod("POST");
                // Are sending JSON data in request body
                urlConnection.setRequestProperty("Content-Type", "application/json");

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "utf-8"));

                // Writes the JSON data to the output stream
                writer.write(jsonstring);

                // Buffered data is written to the output stream immediately
                writer.flush();

                writer.close();

                String responseString = "";


                if(urlConnection.getResponseCode() == 200){
                    BufferedReader bread = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                    String temp = "";

                    while((temp = bread.readLine()) != null){
                        responseString += temp;
                    }

                    System.out.println(responseString);

                    //JSONObject readobj = new JSONObject();
                    //readobj.put();


                }else if(urlConnection.getResponseCode() == 500){
                    System.out.println("Either your username or code is incorrect!");
                    responseString = "Either your username or code is incorrect!";

                }

                // Connecting the 2 activities
                Intent myIntent = new Intent(MainActivity6.this, MainActivity5.class);

                // Sending over response string to next activity
                myIntent.putExtra("responseString", responseString);

                // Open up next activity
                startActivity(myIntent);
            }
            catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        //@Override
        protected String doInBackground(Void... voids) {
            return null;
        }
    }
}