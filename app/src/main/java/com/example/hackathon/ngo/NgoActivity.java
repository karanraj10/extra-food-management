package com.example.hackathon.ngo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.hackathon.donor.HomeFragment;
import com.example.hackathon.R;
import com.example.hackathon.login.AccountFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NgoActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;
    public static BottomNavigationView bottom;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ngo_activity);

        fragmentManager = getSupportFragmentManager();
        bottom = findViewById(R.id.bottomnav_ngo);

        bottom.setSelectedItemId(R.id.home_menu_ngo);
        fragmentManager.beginTransaction().add(R.id.container_ngo,new AddNeedyPerson(),null).commit();
        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.home_menu_ngo :
                        fragment = new AddNeedyPerson();
                        break;

                    case R.id.request_menu_ngo:
                        fragment = new DonorRequestList();
                        break;

                    case R.id.image_menu_ngo:
                        fragment = new AddImagesFragment();
                        break;

                    case R.id.report_menu_ngo:
                        fragment = new ReportFragment();
                        break;

                    case R.id.account_menu_ngo:
                        fragment = new AccountFragment();
                        break;

                }
                fragmentManager.beginTransaction().replace(R.id.container_ngo,fragment,null).commit();
                return true;
            }
        });
    }
}