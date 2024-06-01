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

        //Values of: Paper, Glass, Metal, Plastic
        //Sent from FormMateriaslActivity

        Intent intent = getIntent();

        int blue = intent.getExtras().getInt("paper");
        int green = intent.getExtras().getInt("glass");
        int red = intent.getExtras().getInt("metal");
        int yellow = intent.getExtras().getInt("plastic");

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

//    public void onClickProgress(View v){
//        // Send points to the server
//        String urlPoints = "http://192.168.2.6/RecycleIT/savePoints.php?" + "username=" + uname + "&points=" + points;
//        try {
//            OKHttpHandler okHttpHandler = new OKHttpHandler();
//            okHttpHandler.savePoints(urlPoints, uname, points);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        Intent intent = new Intent(RecycledItemsActivity.this, ProfileActivity.class);
//        intent.putExtra("username", uname);
//        intent.putExtra("password", pass);
//        intent.putExtra("isAdmin", isAdmin);
//        intent.putExtra("points", points);
//        startActivity(intent);
//    }
}