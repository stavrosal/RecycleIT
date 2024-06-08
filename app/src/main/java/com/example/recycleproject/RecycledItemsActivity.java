package com.example.recycleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class RecycledItemsActivity extends AppCompatActivity {
    TextView paperTxt, glassTxt, metalTxt, plasticTxt;

    static String uname, pass, isAdmin;
    int points;

    static int blue;
    static int green;
    static int red;
    static int yellow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycled_items);

        //Values of: Paper, Glass, Metal, Plastic
        //Sent from FormMaterialsActivity

        Toast.makeText(getApplicationContext(), "Please wait! An admin has to approve your points", Toast.LENGTH_SHORT).show();

        Intent intent = getIntent();

        blue = intent.getExtras().getInt("paper");
        green = intent.getExtras().getInt("glass");
        red = intent.getExtras().getInt("metal");
        yellow = intent.getExtras().getInt("plastic");

        uname = intent.getExtras().getString("username");
        pass = intent.getExtras().getString("password");
        isAdmin = intent.getExtras().getString("isAdmin"); //1 for admin, 0 for simple user
        points = intent.getExtras().getInt("points");

        //Print items that have been recycled
        paperTxt = findViewById(R.id.paperNum);
        glassTxt = findViewById(R.id.glassNum);
        metalTxt = findViewById(R.id.metalNum);
        plasticTxt = findViewById(R.id.plasticNum);

        paperTxt.setText(String.valueOf(blue));
        glassTxt.setText(String.valueOf(green));
        metalTxt.setText(String.valueOf(red));
        plasticTxt.setText(String.valueOf(yellow));
    }


    public void onClickBackArrow(View v){
        finish();
    }

}