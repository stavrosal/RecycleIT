package com.example.recycleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RecycledItemsActivity extends AppCompatActivity {
    //NextActivity
    TextView valueTxt;
    TextView paperTxt, glassTxt, metalTxt, plasticTxt;

    String uname, pass, isAdmin;
    int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycled_items);

        //Values: Paper, Glass, Metal, Plastic
        //Sent from FormMateriaslActivity

        Intent intent = getIntent();

        String blue = intent.getExtras().getString("blue");
        String green = intent.getExtras().getString("green");
        String red = intent.getExtras().getString("red");
        String yellow = intent.getExtras().getString("yellow");

        uname = intent.getExtras().getString("username");
        pass = intent.getExtras().getString("password");
        isAdmin = intent.getExtras().getString("isAdmin"); //1 for admin, 0 for simple user
        points = intent.getExtras().getInt("points");
        //Print items that have been recycled
        paperTxt = findViewById(R.id.paperNum);
        glassTxt = findViewById(R.id.glassNum);
        metalTxt = findViewById(R.id.metalNum);
        plasticTxt = findViewById(R.id.plasticNum);

        paperTxt.setText(blue);
        glassTxt.setText(green);
        metalTxt.setText(red);
        plasticTxt.setText(yellow);
//
        points = Integer.parseInt((String) paperTxt.getText()) + Integer.parseInt((String) glassTxt.getText())
                + Integer.parseInt((String) metalTxt.getText()) + Integer.parseInt((String) plasticTxt.getText());
    }


    public void onClickBackArrow(View v){
        finish();
    }

    public void onClickBackToUser(View v){
        Intent intent = new Intent(RecycledItemsActivity.this, ProfileActivity.class);
        intent.putExtra("username", uname);
        intent.putExtra("password", pass);
        intent.putExtra("isAdmin", isAdmin);
        intent.putExtra("points", points);
        startActivity(intent);
        finish();
    }
}