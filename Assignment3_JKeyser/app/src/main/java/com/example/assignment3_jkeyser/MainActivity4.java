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

public class MainActivity4 extends AppCompatActivity implements View.OnClickListener{

    private static final String API_PASSWORD_RESET = "https://otm4o1izk6.execute-api.us-east-2.amazonaws.com/default/491ResetPassword/491resetpassword";

    private EditText username, newPassword;
    private Button loginButton;

    String stringUsername = "";
    String stringNewPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("HERE WE GO");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        username = (EditText) findViewById(R.id.theUsername);
        newPassword = (EditText) findViewById(R.id.thePassword);

        loginButton = (Button) findViewById(R.id.loginButton2);

        loginButton.setOnClickListener(this);
    }

    //@Override
    public void onClick(View view) {
        if (view.getId() == R.id.loginButton2){
            stringUsername = username.getText().toString();
            stringNewPassword = newPassword.getText().toString();

            String[] parameters = new String[3];
            parameters[0] = stringUsername;
            parameters[1] = stringNewPassword;
            // private static final String API_SIGNIN = "some_url"
            parameters[2] = API_PASSWORD_RESET;

            WebService temp = new WebService();
            temp.execute(parameters);
        }

    }

    private class WebService extends AsyncTask<String, String, String>{


        //@Override
        protected String doInBackground(String... inputs) {
            try{
                String theUsername = inputs[0];
                String thePassword = inputs[1];


                String apiurl = API_PASSWORD_RESET;

                // Store username and password in a JSON object
                JSONObject json = new JSONObject();
                json.put("username", theUsername);
                json.put("new_password", thePassword);

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

                System.out.println(jsonstring);

                // Buffered data is written to the output stream immediately
                writer.flush();

                writer.close();

                System.out.println(urlConnection.getResponseCode());

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
                    System.out.println("A user does not exist with that login user ID!");
                    responseString = "A user does not exist with that login user ID!";
                }

                // Connecting the 2 activities
                Intent myIntent = new Intent(MainActivity4.this, MainActivity5.class);

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