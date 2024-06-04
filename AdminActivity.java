package com.example.recycleproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {
    TextView username;
    TextView password;
    TextView profType;

    int points;
    String uname, pass, isAdmin, userUsername;



    public void onCreate(Bundle savedInstanceState)
    {
        Intent intentAdmin = getIntent();
        uname = intentAdmin.getExtras().getString("username");
        pass = intentAdmin.getExtras().getString("password");
        isAdmin = intentAdmin.getExtras().getString("isAdmin"); //1 for admin, 0 for simple user
        points = intentAdmin.getExtras().getInt("points");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);



        username = findViewById(R.id.usernameMain);
        username.setText(uname);

        password = findViewById(R.id.passwordMain);
        password.setText(getAsteriskString(pass.length()));

        profType= findViewById(R.id.profType);
        if (Integer.parseInt(isAdmin) == 1) profType.setText("Administrator");
        else profType.setText("User");
    }

    public void onClickApprove(View view)
    {
        Intent intent = new Intent(AdminActivity.this, ApproveActivity.class);
        intent.putExtra("username", uname);                     //Admin username
        intent.putExtra("userUsername", userUsername);          //User username
        intent.putExtra("points", points);
        startActivity(intent);

    }


    public static String getAsteriskString(int length) {
        StringBuilder asterisks = new StringBuilder();
        for (int i = 0; i < length; i++) {
            asterisks.append('*');
        }
        return asterisks.toString();
    }
    public void onClickBackArrow(View v){
        finish();
    }

}
