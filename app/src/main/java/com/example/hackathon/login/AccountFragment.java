package com.example.hackathon.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hackathon.R;

public class AccountFragment extends Fragment {

    String[] settingList  = {"Your Profile   >","Log Out   >"};
    ListView listView ;

    Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_fragment, container, false);

        listView = view.findViewById(R.id.accountListview);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,settingList);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position)
                {
                    case 0:
                        intent = new Intent(getContext(),YourProfileActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        SplashActivity.sharedPrefrencesHandler.setLoggedIn(false);
                        intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                        break;
                }

            }
        });

        return view;
    }
}