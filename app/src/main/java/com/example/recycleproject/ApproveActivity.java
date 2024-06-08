package com.example.recycleproject;

import static com.example.recycleproject.MainActivity.myIp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class ApproveActivity extends AppCompatActivity {

    ProgressBar pb;
    int points;
    TextView paper, glass, metal, plastic;
    static String uname;
    String selectedUsername;

    Spinner userSpinner;

    int currPaperPoints, currMetalPoints, currGlassPoints, currPlasticPoints; //from db

    String userUsername;

    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        uname = intent.getExtras().getString("username");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve);

        userSpinner = findViewById(R.id.spinner);
        loadUsernames();

        TextView userUs = findViewById(R.id.userUsername);
        userUs.setText("User:");

        //sets the values of the products
        paper = findViewById(R.id.papNumTxt);
        glass = findViewById(R.id.glNumTxt);
        metal = findViewById(R.id.metNumTxt);
        plastic = findViewById(R.id.plaNumTxt);

    }

    //load all usernames in spinner, create php : getAllUsernames.php
    private void loadUsernames() {
        String url = "http://" + myIp + "/RecycleIT/getAllUsernames.php";

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OKHttpHandler okHttpHandler = new OKHttpHandler();
                    String[] usernames = okHttpHandler.getAllUsernames(url);

                    // Add "-Select-" as the first item
                    List<String> usernamesList = new ArrayList<>();
                    usernamesList.add("-Select-");
                    usernamesList.addAll(Arrays.asList(usernames));
                    String[] usernamesWithDefault = usernamesList.toArray(new String[0]);

                    // Update the UI on the main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(ApproveActivity.this, R.layout.custom_spinner_item, usernamesWithDefault);
                            adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
                            userSpinner.setAdapter(adapter);
                            // Set the default selection to be the first item (the hint)
                            userSpinner.setSelection(0);

                            userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position > 0 && adapter.getItem(0).equals("-Select-")) {
                                        adapter.notifyDataSetChanged(); // Notify the adapter of the data change
                                        selectedUsername = (String) parent.getItemAtPosition(position);
//                                        if(selectedUsername.equals("-Selected-")){
//                                            Toast.makeText(getApplicationContext(), "Please select a user", Toast.LENGTH_SHORT).show();
//                                        }
//                                        else {
                                            getSelectedUsersPoints();
                                            fetchAndDisplayRecycledPoints(selectedUsername);
//                                        }
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    // Optional: Handle case when nothing is selected
                                }
                            });
                        }
                    });
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    // Show error message on the main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ApproveActivity.this, "Failed to load usernames", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();

    }

    public void getSelectedUsersPoints(){
        String url = "http://" + myIp + "/RecycleIT/getPointsPerUser.php?username=" + selectedUsername;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OKHttpHandler okHttpHandler = new OKHttpHandler();
                    String jsonResponse = okHttpHandler.getPointsPerUser(url);
                    JSONObject jsonObject = new JSONObject(jsonResponse);

                    runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            try {
                                points = jsonObject.getInt("points");
                                // Update UI with the fetched points
                                // For example, if you have a TextView to display points:
                                // pointsTextView.setText("Points: " + points);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    // Show error message on the main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ApproveActivity.this, "Failed to load user points", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
    public void fetchAndDisplayRecycledPoints(String selectedUsername) {
        String url = "http://" + myIp + "/RecycleIT/getCurrentRecycled.php";

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OKHttpHandler okHttpHandler = new OKHttpHandler();
                    String jsonResponse = okHttpHandler.getRecycledPointsPerUser(url, selectedUsername);
                    JSONObject jsonObject = new JSONObject(jsonResponse);

                    runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            currPaperPoints = jsonObject.optInt("current_paper"); //optInt returns a default value instead of throwing an exception
                            currGlassPoints = jsonObject.optInt("current_glass");
                            currMetalPoints = jsonObject.optInt("current_metal");
                            currPlasticPoints = jsonObject.optInt("current_plastic");

                            // Display points in TextViews
                            paper.setText(String.valueOf(currPaperPoints));
                            glass.setText(String.valueOf(currGlassPoints));
                            metal.setText(String.valueOf(currMetalPoints));
                            plastic.setText(String.valueOf(currPlasticPoints));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    // Show error message on the main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ApproveActivity.this, "Failed to load recycled points", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    public void onClickAward(View view){
        //gets the values of each product and adds them in var newAddedPoints
        //awards points
//getPoints from db
        int newAddedPoints = currPaperPoints + currGlassPoints + currMetalPoints + currPlasticPoints;
        if ((points + newAddedPoints)>100){
            newAddedPoints = - 100 + newAddedPoints;
            points =  (currPaperPoints + currGlassPoints + currMetalPoints + currPlasticPoints) + points - 100;
//            points = points + newAddedPoints - 100;
//            points = - newAddedPoints;
        }
        else{
            points += currPaperPoints + currGlassPoints + currMetalPoints + currPlasticPoints;
        }
        saveTotalPoints(newAddedPoints);
        saveTotalPointsFromEachMaterial();
        //consumes products
        setCurrentRecycledToZero();
    }

    public void setCurrentRecycledToZero() { //set each material's value to 0, after reward is approved
        String url = "http://" + myIp + "/RecycleIT/saveCurrentRecycled.php";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OKHttpHandler okHttpHandler = new OKHttpHandler();
                    int response = okHttpHandler.resetCurrentRecycledPoints(url, selectedUsername);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response == 1) {
                                paper.setText("0");
                                metal.setText("0");
                                glass.setText("0");
                                plastic.setText("0");
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed to reset recycled points", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ApproveActivity.this, "Failed to reset recycled points", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    //HERE SAVE-ADD POINTS AND ADD ON EACH RECYCLED (NOT CURR)
    public void saveTotalPoints(int newAddedPoints) {
        int response = -1;
        String url = "http://" + myIp + "/RecycleIT/savePoints.php?username=" + selectedUsername + "&points=" + newAddedPoints;

        try {
            OKHttpHandler okHttpHandler = new OKHttpHandler();
            response = okHttpHandler.savePoints(url);
            if (response == -1) {
                Toast.makeText(getApplicationContext(),
                        "Permission to give award denied", Toast.LENGTH_SHORT).show();
            } else {
//                    Toast.makeText(getApplicationContext(), "Award Given!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveTotalPointsFromEachMaterial() {
        int response = -1;
        String url = "http://" + myIp + "/RecycleIT/saveAllRecycled.php?username=" + selectedUsername +
                "&current_paper=" + currPaperPoints + "&current_glass=" + currGlassPoints + "&current_metal="
                + currMetalPoints + "&current_plastic=" + currPlasticPoints;
        try {
            OKHttpHandler okHttpHandler = new OKHttpHandler();
            response = okHttpHandler.saveAllRecycled(url);
            if (response == -1) {
                Toast.makeText(getApplicationContext(),
                        "Permission to give award denied", Toast.LENGTH_SHORT).show();
            }
            if(currPaperPoints != 0 || currGlassPoints != 0 || currMetalPoints != 0 || currPlasticPoints != 0){
                Toast.makeText(getApplicationContext(), "Award given!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "This user has no points to approve!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void onClickBackArrow(View v){
        finish();
    }

}
