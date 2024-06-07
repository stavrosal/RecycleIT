package com.example.recycleproject;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import static com.example.recycleproject.MainActivity.myIp;

import java.io.IOException;

public class BestStatisticsActivity extends AppCompatActivity {

    TextView user1, user2, user3, points1, points2, points3;
    String url = "http://" + myIp + "/RecycleIT/getBestStatistics.php";

    String user_points[] = new String[3];

    String usernames[] = new String[3];

    public void onClickBackArrow(View v){
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_statistics);

        user1 = findViewById(R.id.n1Txt);
        user2 = findViewById(R.id.n2Txt);
        user3 = findViewById(R.id.n3Txt);
        points1 = findViewById(R.id.p1Txt);
        points2 = findViewById(R.id.p2Txt);
        points3 = findViewById(R.id.p3Txt);

        OKHttpHandler okHttpHandler = new OKHttpHandler();
        try {
            String response = okHttpHandler.getBestStatistics(url, usernames, user_points);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        user1.setText(usernames[0]);
        user2.setText(usernames[1]);
        user3.setText(usernames[2]);
        points1.setText(user_points[0]);
        points2.setText(user_points[1]);
        points3.setText(user_points[2]);

    }


}
