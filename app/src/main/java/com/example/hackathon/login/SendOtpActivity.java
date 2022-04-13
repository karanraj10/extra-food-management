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

import java.util.HashMap;
import java.util.Map;

public class SendOtpActivity extends AppCompatActivity {

    TextInputEditText emailEdit;
    Button sendOtpButton;

    String email;

    String url = Init.ip +"SendOtp.php";

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_otp_activity);

        emailEdit = findViewById(R.id.email_reset);

        sendOtpButton = findViewById(R.id.send_otp_button_reset);

        sendOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOtpOperation();
            }
        });
    }

    private void sendOtpOperation() {

        email = emailEdit.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(this, "Email Should Not Be Empty !", Toast.LENGTH_SHORT).show();
        } else {
            databaseOperation();
            gotoResetPassword();
        }

    }

    private void databaseOperation() {
        Map<String, String> values = new HashMap<>();
        values.put("email", email);

        DatabaseHandler databaseHandler = new DatabaseHandler(SendOtpActivity.this, url) {
            @Override
            public void getResponse(String response) {
                Toast.makeText(SendOtpActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        };

        databaseHandler.putValues(values);

        databaseHandler.execute();
    }

    private void gotoResetPassword()
    {
        intent = new Intent(this,ResetPasswordActivity.class);
        intent.putExtra("email",email);
        startActivity(intent);
    }
}
