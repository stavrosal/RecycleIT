package com.example.recycleproject;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

//    String afro_API = "192.168.2.6";

    //THIS IS THE LATEST VERSION


    //IP changes in main(2), approveActivity(1), registerActivity(1), formMaterials(1)

    EditText usernameInput;
    EditText passwordInput;
    Button loginBtn;
    Button registerBtn;

    int points;



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
            String url = "http://192.168.1.3/RecycleIT/logUser.php?"
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
//                Toast.makeText(getApplicationContext(), "Welcome " + username, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, SplashLoading.class);
//                Intent intent = new Intent(MainActivity.this, FormMaterialsActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                intent.putExtra("isAdmin", String.valueOf(response));
                intent.putExtra("points", getAllPointsPerUser(username));
                startActivity(intent);
                finish();
            }


        }


    }

    private int getAllPointsPerUser(String uname) {
        int responsePoints = -1;
        String urlPoints = "http://192.168.1.3/RecycleIT/getPointsPerUser.php?" + "username=" + uname;
        try {
            OKHttpHandler okHttpHandler = new OKHttpHandler();
            String jsonResponse = okHttpHandler.getPointsPerUser(urlPoints, uname);
            JSONObject jsonObject = new JSONObject(jsonResponse);
            points = jsonObject.getInt("points");
        } catch (Exception e) {
            points=0;
            e.printStackTrace();
        }
        return points;
    }


    public void onClickReg(View view){
        try {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        }catch (Exception e){e.printStackTrace();}
    }

}