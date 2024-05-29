package com.example.recycleproject;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    String afro_API = "192.168.2.15";

    EditText usernameInput;
    EditText passwordInput;
    Button loginBtn;
    Button registerBtn;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = findViewById(R.id.inpUsername);
        passwordInput = findViewById(R.id.inpPassword);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);


    }

    public void onClickLogin(View view){
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        int points=0;

        int response = -1;
        int responsePoints = -1;

        Toast myMessage_ = null;
        if (username.isEmpty() || password.isEmpty()){
            myMessage_ = Toast.makeText(getApplicationContext(),
                    "All fields are required", Toast.LENGTH_SHORT);
            myMessage_.show();
        }
        else{
            String url = "http://192.168.2.15/RecycleIT/logUser.php?"
                    +"username=" + username +
                    "&password=" + password;

//            String urlPoints = "http://192.168.2.15/RecycleIT/savePoints.php?"+"username=" + username;
            try{
                OKHttpHandler okHttpHandler = new OKHttpHandler();
                response = okHttpHandler.logUser(url);
//                responsePoints = okHttpHandler.savePoints(urlPoints, username,points);
            } catch(Exception e) {e.printStackTrace();}

//            if (responsePoints == -1) {
//                Toast.makeText(getApplicationContext(), "Failed to save points", Toast.LENGTH_SHORT).show();
//            }
            if (response == -1){
                Toast.makeText(getApplicationContext(),
                        "User not found. Check your credentials", Toast.LENGTH_SHORT).show();
            }else {
//                Toast.makeText(getApplicationContext(), "Welcome " + username, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, SplashLoading.class);
//                Intent intent = new Intent(MainActivity.this, FormMaterialsActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                intent.putExtra("isAdmin", String.valueOf(response));
                startActivity(intent);
            }


        }


    }

    public void onClickReg(View view){
        try {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        }catch (Exception e){e.printStackTrace();}
    }

}