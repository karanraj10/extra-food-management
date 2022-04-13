package com.example.hackathon.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.hackathon.donor.DonorActivity;
import com.example.hackathon.ngo.NgoActivity;
import com.example.hackathon.R;
import com.example.hackathon.handlers.SharedPrefrencesHandler;


public class SplashActivity extends AppCompatActivity {

    Intent intent;

    public static SharedPrefrencesHandler sharedPrefrencesHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        sharedPrefrencesHandler = new SharedPrefrencesHandler(SplashActivity.this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoNextActivity();
            }
        },1000);
    }

    private void gotoNextActivity()
    {
        if(sharedPrefrencesHandler.isLoggedIn() && sharedPrefrencesHandler.getType().equals("NGO"))
        {
            intent = new Intent(this, NgoActivity.class);
            startActivity(intent);
            finish();
        }
        else if(sharedPrefrencesHandler.isLoggedIn() && sharedPrefrencesHandler.getType().equals("Donor")) {
            intent = new Intent(this, DonorActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
