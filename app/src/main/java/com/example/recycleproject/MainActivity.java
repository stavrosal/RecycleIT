package com.example.recycleproject;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    static String myIp= "10.140.7.157";

    //THIS IS THE LATEST VERSION


    //IP changes in MainActivity(3), ApproveActivity(6), RegisterActivity(2), FormMaterialsActivity(3)

    EditText usernameInput;
    EditText passwordInput;
    Button loginBtn;
    Button registerBtn;

    int points;



    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = findViewById(R.id.inpUsername);
        passwordInput = findViewById(R.id.inpPassword);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);

//Show/Hide password
        passwordInput.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP &&
                    event.getRawX() >= (passwordInput.getRight() - passwordInput.getCompoundDrawables()[2].getBounds().width())) {
                // Toggle password visibility
                if (passwordInput.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    // Hide the password
                    passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.visibility_on, 0);
                } else {
                    // Show the password
                    passwordInput.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_off_24, 0);
                }
                return true;
            }
            return false;
        });

//Check if logout occurred
        Intent intent = getIntent();
        if (intent.getBooleanExtra("logout", false)) {
            onLogout();
        }

    }

    //Method for the login procedure -- Using: logUser.php, getPointsPerUser.php
    public void onClickLogin(View view){
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        int response = -1;

        Toast myMessage_ = null;
        if (username.isEmpty() || password.isEmpty()){
            myMessage_ = Toast.makeText(getApplicationContext(),
                    "All fields are required", Toast.LENGTH_SHORT);
            myMessage_.show();
        }
        else{
            String url = "http://" + myIp + "/RecycleIT/logUser.php?"
                    +"username=" + username +
                    "&password=" + password;

            try{
                OKHttpHandler okHttpHandler = new OKHttpHandler();
                response = okHttpHandler.logUser(url);
            } catch(Exception e) {e.printStackTrace();}
            if (response == -1){
                Toast.makeText(getApplicationContext(),
                        "User not found. Check your credentials", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(), "Welcome " + username, Toast.LENGTH_SHORT).show();

                //If admin: don't show SplashLoading
                if (response == 1) {
                    Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    intent.putExtra("isAdmin", String.valueOf(response));
                    intent.putExtra("points", getAllPointsPerUser(username));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, SplashLoading.class);
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    intent.putExtra("isAdmin", String.valueOf(response));
                    intent.putExtra("points", getAllPointsPerUser(username));
                    startActivity(intent);
                }
            }
        }


    }

    //Method that returns total points for each user
    private int getAllPointsPerUser(String uname) {
        String urlPoints = "http://" + myIp + "/RecycleIT/getPointsPerUser.php?" + "username=" + uname;
        try {
            OKHttpHandler okHttpHandler = new OKHttpHandler();
            String jsonResponse = okHttpHandler.getPointsPerUser(urlPoints);
            JSONObject jsonObject = new JSONObject(jsonResponse);
            points = jsonObject.getInt("points");
        } catch (Exception e) {
            points=0;
            e.printStackTrace();
        }
        return points;
    }

    //Method that is called if user/admin wants to register
    public void onClickReg(View view){
        try {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        }catch (Exception e){e.printStackTrace();}
    }

    //If user/admin logged out - clear username and password
    public void onLogout() {
        usernameInput.setText("");
        passwordInput.setText("");
        Toast.makeText(getApplicationContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
    }

}