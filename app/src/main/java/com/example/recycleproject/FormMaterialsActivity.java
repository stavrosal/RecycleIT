package com.example.recycleproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class FormMaterialsActivity extends AppCompatActivity {

    ConstraintLayout layout;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_materials);

        layout = findViewById(R.id.constraintLayout);
    }

    public void onClickBackArrow(View v){
        Intent intent = new Intent(FormMaterialsActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void onClickMaterial(View v){
        CreatepopUpWindow();
    }

    private void CreatepopUpWindow(){
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popUpView = inflater.inflate(R.layout.mainpopup,null);
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        PopupWindow popupWindow = new PopupWindow(popUpView, width, height, focusable);
        layout.post(new Runnable() {
                        @Override
                        public void run() {
                            popupWindow.showAtLocation(layout, Gravity.BOTTOM,0,0);
                        }
                    }
        );
    }
}
