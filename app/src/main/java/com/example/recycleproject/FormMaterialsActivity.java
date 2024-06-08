package com.example.recycleproject;


import static com.example.recycleproject.MainActivity.myIp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class FormMaterialsActivity extends AppCompatActivity {

    TextView blue, green, red, yellow;
    int bValue = 0, gValue = 0, rValue = 0, yValue = 0;
    int paperCount = 0, glassCount = 0, metalCount = 0, plasticCount = 0; //show total items recycled this time

    String uname, pass, isAdmin;

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

    }

    //When submit is clicked move to activity RecycledItemsActivity and pass the values of each material
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

//Here currentPoints of each material are saved to db, in order to be approved by an admin
public void saveRecycledPoints() {
    int response = -1;
    String url = "http://" + myIp + "/RecycleIT/saveCurrentRecycled.php?"
            + "username=" + uname
            + "&current_paper=" + paperCount
            + "&current_glass=" + glassCount
            + "&current_metal=" + metalCount
            + "&current_plastic=" + plasticCount;
    try{
    OKHttpHandler okHttpHandler = new OKHttpHandler();
    response = okHttpHandler.saveCurrentRecycledPoints(url);
    if (response == -1){
        Toast.makeText(getApplicationContext(),
                "Error", Toast.LENGTH_SHORT).show();
    }
    }catch (Exception e){e.printStackTrace();}
}
    // Submit the current values and reset them
    public void onClickPreview(View v){
        saveRecycledPoints();
        bValue = 0;
        gValue = 0;
        rValue = 0;
        yValue = 0;
        blue.setText("Paper");
        green.setText("Glass");
        red.setText("Metal");
        yellow.setText("Plastic");
        onClickNext();

    }

    public void onClickLogoutButton(View v) {
            Intent intent = new Intent(FormMaterialsActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("logout", true);
            startActivity(intent);
            finish();
    }

    public void onClickUser(View v) {
        Intent intent = new Intent(FormMaterialsActivity.this, ProfileActivity.class);
        intent.putExtra("username", uname);
        intent.putExtra("password", pass);
        intent.putExtra("isAdmin", isAdmin);
        intent.putExtra("points", points);
        startActivity(intent);
    }
//DIALOG ALERT
    public void onClickMaterialBlue(View v) { //Paper
        openAlert("Paper", bValue);
    }

    public void onClickMaterialGreen(View v) { //Glass
        openAlert("Glass", gValue);
    }

    public void onClickMaterialRed(View v) { //Metal
        openAlert("Metal", rValue);
    }

    public void onClickMaterialYellow(View v) { //Plastic
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

                    // Create the buttons for incrementing
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

                    // Create the buttons for decrementing
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
                            //if ok is clicked
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
                            //if cancel is clicked
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
