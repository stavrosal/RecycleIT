package com.example.recycleproject;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class ProfileActivity extends AppCompatActivity {

    TextView username, password, profType, progressBarText;
//    TextView mainName;

    ProgressBar pb;

    int points;
    int maxLevel = 100;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        String uname = intent.getExtras().getString("username");
        String pass = intent.getExtras().getString("password");
        String isAdmin = intent.getExtras().getString("isAdmin"); //1 for admin, 0 for simple user
        points = intent.getExtras().getInt("points");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        mainName = findViewById(R.id.usernameMain);
//        mainName.setText(uname);

        username = findViewById(R.id.usernameMain);
        username.setText(uname);

        password = findViewById(R.id.passwordMain);
        password.setText(getAsteriskString(pass.length()));

        profType= findViewById(R.id.profType);
        if (Integer.parseInt(isAdmin) == 1) profType.setText("Administrator");
        else profType.setText("User");

        progressBarText = findViewById(R.id.progressBarText);

        //getPoints from db

        progressBarText.setText("Check out your progress (" + points + "%):");

        pb = findViewById(R.id.progBar);
        pb.setMax(maxLevel);
        pb.setProgress(points);

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
