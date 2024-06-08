package com.example.recycleproject;

import static com.example.recycleproject.MainActivity.myIp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {

    EditText usernameInput;
    EditText passwordInput;

    RadioGroup rg;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_fragment);

        usernameInput = findViewById(R.id.finalUsername);
        passwordInput = findViewById(R.id.finalPassword);
        rg = findViewById(R.id.userType);
    }


    public void onClickReg(View view){
        Toast myMessage_;
        int response = -1;
        int isAdmin;
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        int selectedId = rg.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        RadioButton userType = (RadioButton) findViewById(selectedId);

        if (username.isEmpty() || password.isEmpty() || userType == null){
            myMessage_ = Toast.makeText(getApplicationContext(),
                    "All fields are required", Toast.LENGTH_LONG);
            myMessage_.show(); //notify user that all fields are mandatory
        }else{
            if (userType.getText().equals("Administrator")) isAdmin = 1;
            else isAdmin = 0;

            String url = "http://" + myIp + "/RecycleIT/regUser.php?"
                    +"username=" + username +
                    "&password=" + password +
                    "&isAdmin=" + isAdmin
                    +"&points=0" //add points variable too
                    +"&paper=0" //add recycledItems variable too
                    +"&glass=0&metal=0&plastic=0"+
                    "&current_paper=0&current_glass=0&current_metal=0&current_plastic=0";

            try {
                OKHttpHandler okHttpHandler = new OKHttpHandler();
                response = okHttpHandler.regUser(url);
            }catch (Exception e) {e.printStackTrace();}

            if (response == -1){
                myMessage_ = Toast.makeText(getApplicationContext(),
                        "User already exists", Toast.LENGTH_LONG);
                myMessage_.show(); //notify user that this account already exists
            }else{
            myMessage_ = Toast.makeText(getApplicationContext(),
                    "Registration as " + userType.getText().toString() + " successful", Toast.LENGTH_LONG);
            myMessage_.show();

                if(isAdmin == 1)
                {
                    try {
                        Intent intent = new Intent(RegisterActivity.this, AdminActivity.class);
                        intent.putExtra("username", username);
                        intent.putExtra("password", password);
                        intent.putExtra("isAdmin", String.valueOf(isAdmin));
                        startActivity(intent); //register is complete, pass the credentials to the next intent and move on
                    }catch (Exception e){e.printStackTrace();}
                }
                else {
                    try {
                        Intent intent = new Intent(RegisterActivity.this, FormMaterialsActivity.class);
                        intent.putExtra("username", username);
                        intent.putExtra("password", password);
                        intent.putExtra("isAdmin", String.valueOf(isAdmin));
                        startActivity(intent); //register is complete, pass the credentials to the next intent and move on
                    }catch (Exception e){e.printStackTrace();}
                }


            }

        }


    }

    public void onClickBack(View v){
        finish();
    }


}
