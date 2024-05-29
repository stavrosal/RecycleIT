package com.example.recycleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class SplashLoading extends AppCompatActivity {

    String uname, pass, isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_loading);

        Intent intent = getIntent();
        uname = intent.getExtras().getString("username");
        pass = intent.getExtras().getString("password");
        isAdmin = intent.getExtras().getString("isAdmin"); //1 for admin, 0 for simple user

        // on below line we are calling handler to run a task
        // for specific time interval
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // on below line we are
                // creating a new intent
                Intent i = new Intent(SplashLoading.this, FormMaterialsActivity.class);
                i.putExtra("username", uname);
                i.putExtra("password", pass);
                i.putExtra("isAdmin", isAdmin);
                startActivity(i);

                finish();
            }
        }, 2000);

    }

}