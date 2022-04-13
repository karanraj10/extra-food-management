package com.example.hackathon.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hackathon.Init;
import com.example.hackathon.R;
import com.example.hackathon.handlers.DatabaseHandler;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SendOtpSignupActivity extends AppCompatActivity {

    TextInputEditText otpEdit;
    Button registerButton;
    String otp, email;

    Intent intent;

    String url= Init.ip+"AcceptOtpSignup.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_otp_signup_activity);

        Intent getData = getIntent();
        email = getData.getStringExtra("email");

        otpEdit = findViewById(R.id.otp_signup);
        registerButton = findViewById(R.id.register_button_signup);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                otp = otpEdit.getText().toString();

                if (otp.isEmpty())
                {
                    Toast.makeText(SendOtpSignupActivity.this,"Otp Should Not Be Empty !",Toast.LENGTH_SHORT).show();
                }
                else 
                {
                    databaseOperation();
                }
            }
        });

    }

    private void databaseOperation() {

        Map<String, String> values = new HashMap<>();
        values.put("otp", otp);
        values.put("email",email);

        DatabaseHandler databaseHandler = new DatabaseHandler(SendOtpSignupActivity.this, url) {
            @Override
            public void getResponse(String response) throws Exception {
                JSONObject object = new JSONObject(response);

                if(object.getString("status").equals("0")) {
                    Toast.makeText(SendOtpSignupActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SendOtpSignupActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                    gotoLogin();
                }
            }
        };

        databaseHandler.putValues(values);

        databaseHandler.execute();
    }
    private void gotoLogin()
    {
        intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}