package com.example.hackathon.ngo;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hackathon.R;
import com.example.hackathon.login.SplashActivity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class ReportFragment extends Fragment {


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);


        final String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

        int year = new Date().getYear()+1900;
        int month = new Date().getMonth()+1;
        int date = new Date().getDate()+7;

        String month1 = String.format("%02d", month);

        final String enddate = year+"-"+month1+"-"+date;

        Log.d("TAG", "onCreateView: "+ timeStamp);
        Log.d("TAG", "onCreateView: "+ enddate);


        Button button = view.findViewById(R.id.report_weekly);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ReportActivity.class);
                intent.putExtra("start",timeStamp);
                intent.putExtra("end",enddate);
                intent.putExtra("email", SplashActivity.sharedPrefrencesHandler.getEmail());
                startActivity(intent);
            }
        });


        return view;
    }
}