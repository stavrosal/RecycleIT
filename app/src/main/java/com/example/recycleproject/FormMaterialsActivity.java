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

public class FormMaterialsActivity extends AppCompatActivity {

    TextView blue, green, red, yellow;
    String bValue = "0", gValue = "0", rValue = "0", yValue = "0";
    String uname, pass, isAdmin;

    int points;
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

    }


    public void onClickSubmit(View v){
        Intent intent = new Intent(FormMaterialsActivity.this, RecycledItemsActivity.class);
        intent.putExtra("blue", bValue);
        intent.putExtra("green", gValue);
        intent.putExtra("red", rValue);
        intent.putExtra("yellow", yValue);
        intent.putExtra("username", uname);
        intent.putExtra("password", pass);
        intent.putExtra("isAdmin", isAdmin);
        intent.putExtra("points",points);
        startActivity(intent);
    }



    public void onClickBackArrow(View v){
        finish();
    }

    public void onClickUser(View v){
        Intent intent = new Intent(FormMaterialsActivity.this, ProfileActivity.class);
        intent.putExtra("username", uname);
        intent.putExtra("password", pass);
        intent.putExtra("isAdmin", isAdmin);
        intent.putExtra("points", points);
        startActivity(intent);
    }


    //Future change: set currValue on each material to 0, but inside keep current value
    public void onClickMaterialBlue(View v) {
        openAlert("Paper");
    }
    public void onClickMaterialGreen(View v) {
        openAlert("Glass");
    }
    public void onClickMaterialRed(View v) {
        openAlert("Metal");
    }
    public void onClickMaterialYellow(View v) {
        openAlert("Plastic");
    }

    public void openAlert(String material){
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
                    numberDisplay.setText("0");
//                    numberDisplay.setText(currValue);

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
                            numberDisplay.setText(String.valueOf(currentValue + 1));
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
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Get the number from the TextView
                                    String userInput = numberDisplay.getText().toString();

                                    if (material.equals("Paper")) {
                                        blue.setText("Paper: " + userInput);
                                        bValue = userInput;
                                    } else if (material.equals("Glass")) {
                                        green.setText("Glass: " + userInput);
                                        gValue = userInput;
                                    } else if (material.equals("Metal")) {
                                        red.setText("Metal: " + userInput);
                                        rValue = userInput;
                                    } else {
                                        yellow.setText("Plastic: " + userInput);
                                        yValue = userInput;
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
                //Calculate points and send them to progressbar
                points = Integer.parseInt((String) bValue) + Integer.parseInt((String) gValue)
                        + Integer.parseInt((String) rValue) + Integer.parseInt((String) yValue);
            }
        });

    }


}
