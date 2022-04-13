package com.example.hackathon.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
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

public class ResetPasswordActivity extends AppCompatActivity {

    TextInputEditText otpEdit, newPasswordEdit;
    Button resetPasswordButton;

    String otp, newPassword, retrivedEmail;

    String url = Init.ip+"ResetPassword.php";

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password_activity);

        intent = getIntent();
        retrivedEmail = intent.getStringExtra("email");


        otpEdit = findViewById(R.id.otp_reset);
        newPasswordEdit = findViewById(R.id.password_reset);

        resetPasswordButton = findViewById(R.id.reset_password_button_reset);

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetPasswordOperation();
            }
        });
    }

    private void resetPasswordOperation()
    {
        otp = otpEdit.getText().toString();
        newPassword = newPasswordEdit.getText().toString();

        if (otp.isEmpty() || newPassword.isEmpty())
        {
            Toast.makeText(this,"Otp & Password Should Not Be Empty !",Toast.LENGTH_SHORT).show();
        }
        else if (newPassword.length() < 8)
        {
            Toast.makeText(this,"Password Should Be 8 Character Long",Toast.LENGTH_SHORT).show();
        }
        else
        {
            databaseOperation();
        }
    }

    private void databaseOperation()
    {
        Map<String, String> values = new HashMap<>();
        values.put("email", retrivedEmail);
        values.put("password",newPassword);
        values.put("otp",otp);

        DatabaseHandler databaseHandler = new DatabaseHandler(ResetPasswordActivity.this, url) {
            @Override
            public void getResponse(String response) throws Exception {
                JSONObject object = new JSONObject(response);

                if(object.getString("status").equals("0")) {
                    Toast.makeText(ResetPasswordActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ResetPasswordActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
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
