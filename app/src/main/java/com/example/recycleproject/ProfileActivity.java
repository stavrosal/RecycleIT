package com.example.recycleproject;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class ProfileActivity extends AppCompatActivity {

    TextView username;
    TextView mainName;
    TextView profType;

    ProgressBar pb;

    int points;
    int maxLevel = 100;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        String uname = intent.getExtras().getString("username");
        String pass = intent.getExtras().getString("password");
        String isAdmin = intent.getExtras().getString("isAdmin"); //1 for admin, 0 for simple user


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mainName = findViewById(R.id.usernameMain);
        mainName.setText(uname);

        username = findViewById(R.id.usernameMain);
        username.setText(uname);

        profType= findViewById(R.id.profType);
        if (Integer.parseInt(isAdmin) == 1) profType.setText("Administrator");
        else profType.setText("User");

        pb = findViewById(R.id.progBar);
        pb.setMax(maxLevel);
        pb.setProgress(points);


    }

    public void onClickRecycle(View v){
        Intent intent = new Intent(ProfileActivity.this, FormMaterialsActivity.class);
       startActivity(intent);
    }
}
