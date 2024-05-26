package com.example.recycleproject;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

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

        int response = -1;

        Toast myMessage_ = null;
        if (username.isEmpty() || password.isEmpty()){
            myMessage_ = Toast.makeText(getApplicationContext(),
                    "All fields are required", Toast.LENGTH_SHORT);
            myMessage_.show();
        }
        else{
            String url = "http://192.168.56.1/RecycleIT/logUser.php?"
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

                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
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