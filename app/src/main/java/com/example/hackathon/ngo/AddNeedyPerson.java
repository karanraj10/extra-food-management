package com.example.hackathon.ngo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackathon.Init;
import com.example.hackathon.R;
import com.example.hackathon.handlers.DatabaseHandler;
import com.example.hackathon.login.SignupActivity;
import com.example.hackathon.login.SplashActivity;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class AddNeedyPerson extends Fragment {

    TextInputEditText nameEdit, mobileEdit, addressEdit, areaEdit, cityEdit;
    Button registerButton;

    String name, mobile, address, city, area;

    String url = Init.ip+"AddNeedyPerson.php";

    Intent intent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_needy_person_fragment,container,false);

        nameEdit = view.findViewById(R.id.name_needy);
        mobileEdit = view.findViewById(R.id.mobile_needy);
        addressEdit = view.findViewById(R.id.address_needy);
        cityEdit = view.findViewById(R.id.city_needy);
        areaEdit = view.findViewById(R.id.area_needy);
        registerButton = view.findViewById(R.id.register_button_needy);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                registerOperation();
            }
        });

        return view;
    }

    private void registerOperation() {

            name = nameEdit.getText().toString();
            mobile = mobileEdit.getText().toString();
            address = addressEdit.getText().toString();
            city = cityEdit.getText().toString();
            area = areaEdit.getText().toString();

            if (name.isEmpty() || mobile.isEmpty() || address.isEmpty() || city.isEmpty() || area.isEmpty())
            {
                Toast.makeText(getContext(),"Please Provide All Details !",Toast.LENGTH_SHORT).show();
            }
            else
            {
                databaseOperation();
            }

    }

    private void databaseOperation() {

        Map<String ,String> values = new HashMap<>();
        values.put("ngoEmail", SplashActivity.sharedPrefrencesHandler.getEmail());
        values.put("name",name);
        values.put("address",address);
        values.put("city",city);
        values.put("area",area);
        values.put("mobile",mobile);

        DatabaseHandler databaseHandler = new DatabaseHandler(getContext(),url) {
            @Override
            public void getResponse(String response) throws Exception {
                Toast.makeText(getContext(),new JSONObject(response).getString("message"),Toast.LENGTH_SHORT).show();
            }
        };

        databaseHandler.putValues(values);

        databaseHandler.execute();
    }
}