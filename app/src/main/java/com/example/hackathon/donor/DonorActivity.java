package com.example.hackathon.donor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.hackathon.R;
import com.example.hackathon.data.MyRequestData;
import com.example.hackathon.login.AccountFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DonorActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;
    public static BottomNavigationView bottom;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donor_activity);

        fragmentManager = getSupportFragmentManager();
        bottom = findViewById(R.id.donor_bottomNav);

        bottom.setSelectedItemId(R.id.home_menu_donor);
        fragmentManager.beginTransaction().add(R.id.donor_container,new HomeFragment(),null).commit();

        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.home_menu_donor :
                        fragment = new HomeFragment();
                        break;

                    case R.id.request_menu_donor:
                        fragment = new RequestList();
                        break;

                    case R.id.account_menu_donor:
                        fragment = new AccountFragment();
                        break;

                }
                fragmentManager.beginTransaction().replace(R.id.donor_container,fragment,null).commit();
                return true;
            }
        });
    }
}