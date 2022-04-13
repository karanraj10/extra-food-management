package com.example.hackathon.ngo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.hackathon.Init;
import com.example.hackathon.R;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        WebView webView = findViewById(R.id.web_report);
        webView.loadUrl(Init.ip+"Report.php?start="+getIntent().getStringExtra("start")+"&end="+getIntent().getStringExtra("end")
        +"&email="+getIntent().getStringExtra("email"));
    }
}