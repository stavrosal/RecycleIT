package com.example.recycleproject;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONObject;

public class FormMaterialsActivity extends AppCompatActivity {

    TextView blue, green, red, yellow;
    int bValue = 0, gValue = 0, rValue = 0, yValue = 0;
    int paperCount = 0, glassCount = 0, metalCount = 0, plasticCount = 0; //show totan items recycled this time

    String uname, pass, isAdmin;

    Button nextBtn, submitBtn;
    RelativeLayout.LayoutParams params;
    int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_materials);

        blue = findViewById(R.id.blueText);
        green = findViewById(R.id.greenText);
        red = findViewById(R.id.redText);
        yellow = findViewById(R.id.yellowText);

        Intent intent = getIntent();
        uname = intent.getExtras().getString("username");
        pass = intent.getExtras().getString("password");
        isAdmin = intent.getExtras().getString("isAdmin"); //1 for admin, 0 for simple user
        points = intent.getExtras().getInt("points");



//        nextBtn = findViewById(R.id.nextButton);
//        nextBtn.setVisibility(View.INVISIBLE);
//
//        submitBtn = findViewById(R.id.submitButton);
//
//        params = (RelativeLayout.LayoutParams) submitBtn.getLayoutParams();
//
//        params.addRule(RelativeLayout.CENTER_IN_PARENT, 1);
//
//        submitBtn.setLayoutParams(params);

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
////        flagResults = false;
//
//        nextBtn.setVisibility(View.INVISIBLE);
//        submitBtn.setVisibility(View.VISIBLE);
//
//        params = (RelativeLayout.LayoutParams) submitBtn.getLayoutParams();
//
//        params.addRule(RelativeLayout.CENTER_IN_PARENT, 1);
//
//        submitBtn.setLayoutParams(params);
//    }

    // Go to the next activity and pass the total values
    public void onClickNext() {
        Intent intent = new Intent(FormMaterialsActivity.this, RecycledItemsActivity.class);
        intent.putExtra("paper", paperCount);
        intent.putExtra("glass", glassCount);
        intent.putExtra("metal", metalCount);
        intent.putExtra("plastic", plasticCount);
        intent.putExtra("username", uname);
        intent.putExtra("password", pass);
        intent.putExtra("isAdmin", isAdmin);
        intent.putExtra("points", points);
        startActivity(intent);
    }

//    public int ceilToNearestHundredUp(int number) {
//        return (((number + 99) / 100) * 100);
//    }

    // Submit the current values and reset them
    public void onClickSubmit(View v) {
        // Send points to the server

        /*
        int newAddedPoints = bValue + gValue + rValue + yValue;
        if ((points + newAddedPoints)>100){
            newAddedPoints = - 100 + newAddedPoints;
            points =  (bValue + gValue + rValue + yValue) + points - 100;
//            points =  (newAddedPoints) + points - ceilToNearestHundredUp(newAddedPoints);

        }
        else{
            points += bValue + gValue + rValue + yValue;
        }
        String urlPoints = "http://192.168.1.3/RecycleIT/savePoints.php?" + "username=" + uname + "&points=" + newAddedPoints;
        try {
            OKHttpHandler okHttpHandler = new OKHttpHandler();
            okHttpHandler.savePoints(urlPoints, uname, points);
        } catch (Exception e) {
            e.printStackTrace();
        }

         */

//        nextBtn.setVisibility(View.VISIBLE);
//
//        params.addRule(RelativeLayout.CENTER_IN_PARENT, 0); // 0 means false
//
//        submitBtn.setLayoutParams(params);

        // Reset the values
        bValue = 0;
        gValue = 0;
        rValue = 0;
        yValue = 0;
        blue.setText("Paper");
        green.setText("Glass");
        red.setText("Metal");
        yellow.setText("Plastic");
        onClickNext();
//        if ((bValue + gValue + rValue + yValue) == 0) {
//            Toast.makeText(getApplicationContext(),
//                    "You have to recycle first!", Toast.LENGTH_SHORT).show();
//        }
//        onClickNext();


    }

//    public void onClickBackArrow(View v) {
//        finish();
//    }

    public void onClickUser(View v) {
        Intent intent = new Intent(FormMaterialsActivity.this, ProfileActivity.class);
        intent.putExtra("username", uname);
        intent.putExtra("password", pass);
        intent.putExtra("isAdmin", isAdmin);
        intent.putExtra("points", points);
        startActivity(intent);
    }

    public void onClickMaterialBlue(View v) {
        openAlert("Paper", bValue);
    }

    public void onClickMaterialGreen(View v) {
        openAlert("Glass", gValue);
    }

    public void onClickMaterialRed(View v) {
        openAlert("Metal", rValue);
    }

    public void onClickMaterialYellow(View v) {
        openAlert("Plastic", yValue);
    }

    public void openAlert(String material, int currentValue) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing()) {
                    // Create the layout for the AlertDialog
                    LinearLayout layout = new LinearLayout(FormMaterialsActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setPadding(50, 50, 50, 50);

                    // Create the buttons for incrementing and decrementing
                    Button incrementButton = new Button(FormMaterialsActivity.this);
                    incrementButton.setText("+");
                    incrementButton.setTextSize(30);
                    incrementButton.setGravity(Gravity.CENTER);
                    incrementButton.setTypeface(null, Typeface.NORMAL);

                    // Change "+" button's shape
                    incrementButton.setBackground(ContextCompat.getDrawable(FormMaterialsActivity.this, R.drawable.circular_button));
                    incrementButton.setTextColor(Color.WHITE);
                    LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(100, 100);
                    incrementButton.setLayoutParams(buttonLayoutParams);

                    Button decrementButton = new Button(FormMaterialsActivity.this);
                    decrementButton.setText("-");
                    decrementButton.setTextSize(30);
                    decrementButton.setGravity(Gravity.CENTER);
                    decrementButton.setTypeface(null, Typeface.NORMAL);

                    // Change "-" button's shape
                    decrementButton.setBackground(ContextCompat.getDrawable(FormMaterialsActivity.this, R.drawable.circular_button));
                    decrementButton.setTextColor(Color.WHITE);
                    decrementButton.setLayoutParams(buttonLayoutParams);

                    // Create the TextView to display the number
                    final TextView numberDisplay = new TextView(FormMaterialsActivity.this);
                    numberDisplay.setTextSize(24);
                    numberDisplay.setGravity(Gravity.CENTER);
                    numberDisplay.setText(String.valueOf(currentValue));

                    LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    textLayoutParams.setMargins(20, 0, 20, 0); // Add margins to the TextView
                    numberDisplay.setLayoutParams(textLayoutParams);

                    // Create a horizontal layout to hold the buttons and the TextView
                    LinearLayout buttonLayout = new LinearLayout(FormMaterialsActivity.this);
                    buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
                    buttonLayout.setGravity(Gravity.CENTER);

                    // Add views to the horizontal layout in the desired order
                    buttonLayout.addView(decrementButton);
                    buttonLayout.addView(numberDisplay);
                    buttonLayout.addView(incrementButton);

                    // Add the horizontal layout to the main layout
                    layout.addView(buttonLayout);

                    // Add click listeners to the buttons
                    incrementButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int currentValue = Integer.parseInt(numberDisplay.getText().toString());
                            if (currentValue < 100) {
                                numberDisplay.setText(String.valueOf(currentValue + 1));
                            }
                        }
                    });

                    decrementButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int currentValue = Integer.parseInt(numberDisplay.getText().toString());
                            if (currentValue > 0) {
                                numberDisplay.setText(String.valueOf(currentValue - 1));
                            }
                        }
                    });

                    new AlertDialog.Builder(FormMaterialsActivity.this)
                            .setTitle("How many items did you recycle?")
                            .setView(layout)
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int userInput = Integer.parseInt(numberDisplay.getText().toString());
                                    if (material.equals("Paper")) {
                                        bValue = userInput;
                                        paperCount = userInput;
                                        blue.setText("Paper: " + bValue);
                                    } else if (material.equals("Glass")) {
                                        gValue = userInput;
                                        glassCount = userInput;
                                        green.setText("Glass: " + gValue);
                                    } else if (material.equals("Metal")) {
                                        rValue = userInput;
                                        metalCount = userInput;
                                        red.setText("Metal: " + rValue);
                                    } else {
                                        yValue = userInput;
                                        plasticCount = userInput;
                                        yellow.setText("Plastic: " + yValue);
                                    }
                                }


                            })

                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                }
            }
        });
    }
}
