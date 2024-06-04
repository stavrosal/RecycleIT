package com.example.recycleproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;


public class ApproveActivity extends AppCompatActivity {

    ProgressBar pb;
    int points, bValue = 0, gValue = 0, rValue = 0, yValue = 0;
    TextView paper, glass, metal, plastic;
    String uname;
    int blue, green, red, yellow;

    String userUsername;

    @SuppressLint("MissingInflatedId")
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        uname = intent.getExtras().getString("username");
        points = intent.getExtras().getInt("points");


        //gets the username of the user that recycled
        this.userUsername = RecycledItemsActivity.uname;

        //gets the amount of products the user recycled
        this.blue = RecycledItemsActivity.blue;
        this.green = RecycledItemsActivity.green;
        this.red = RecycledItemsActivity.red;
        this.yellow = RecycledItemsActivity.yellow;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve);


        TextView userUs = findViewById(R.id.userUsername);
        userUs.setText("User: " + userUsername);


        //sets the values of the products
        paper = findViewById(R.id.papNumTxt);
        glass = findViewById(R.id.glNumTxt);
        metal = findViewById(R.id.metNumTxt);
        plastic = findViewById(R.id.plaNumTxt);

        paper.setText(String.valueOf(blue));
        glass.setText(String.valueOf(green));
        metal.setText(String.valueOf(red));
        plastic.setText(String.valueOf(yellow));

    }


    public void onClickAward(View view){
       // pb = findViewById(R.id.progBar);

        //gets the values of each product
        TextView b = findViewById(R.id.papNumTxt);
        String blueStr = b.getText().toString();
        bValue = Integer.parseInt(blueStr);

        TextView g = findViewById(R.id.glNumTxt);
        String greenStr = g.getText().toString();
        gValue = Integer.parseInt(greenStr);

        TextView r = findViewById(R.id.metNumTxt);
        String redStr = r.getText().toString();
        rValue = Integer.parseInt(redStr);

        TextView y = findViewById(R.id.plaNumTxt);
        String yellowStr = y.getText().toString();
        yValue = Integer.parseInt(yellowStr);

//



        //awards points
        int newAddedPoints = bValue + gValue + rValue + yValue;
        if ((points + newAddedPoints)>100){
            newAddedPoints = - 100 + newAddedPoints;
            points =  (bValue + gValue + rValue + yValue) + points - 100;
//            points =  (newAddedPoints) + points - ceilToNearestHundredUp(newAddedPoints);

        }
        else{
            points += bValue + gValue + rValue + yValue;
        }


        //consumes products
        paper.setText("0");
        glass.setText("0");
        metal.setText("0");
        plastic.setText("0");

        Toast.makeText(getApplicationContext(), "Award Given!", Toast.LENGTH_SHORT).show();


        //save points onto user's account
        String urlPoints = "http://192.168.1.3/RecycleIT/savePoints.php?" + "username=" + userUsername + "&points=" + newAddedPoints;
        try {
            OKHttpHandler okHttpHandler = new OKHttpHandler();
            okHttpHandler.savePoints(urlPoints, userUsername, points);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onClickBackArrow(View v){
        finish();
    }

}
