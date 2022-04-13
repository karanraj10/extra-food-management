package com.example.hackathon.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.hackathon.R;

public class YourProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_profile_activity);

        TextView name = findViewById(R.id.yourprofileName);
        TextView email = findViewById(R.id.yourprofileEmail);
        TextView accountype = findViewById(R.id.yourprofileUsertype);

        name.setText(SplashActivity.sharedPrefrencesHandler.getName());
        email.setText(SplashActivity.sharedPrefrencesHandler.getEmail());
        accountype.setText(SplashActivity.sharedPrefrencesHandler.getType());
    }
}