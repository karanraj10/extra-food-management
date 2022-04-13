package com.example.hackathon.ngo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hackathon.Init;
import com.example.hackathon.R;
import com.example.hackathon.handlers.DatabaseHandler;
import com.example.hackathon.login.SplashActivity;
import com.google.android.gms.common.util.JsonUtils;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AcceptRequestActivity extends AppCompatActivity {

    String rno,city;

    String url = Init.ip+"NeedyAreaNGO.php";

    List<String> areas;
    Spinner spinner;

    String area,vcontact;

    TextInputEditText v_contact ;

    Button accept ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accept_request_activity);

        rno = getIntent().getStringExtra("rno");
        city = getIntent().getStringExtra("city");

        areas = new ArrayList<>();

        v_contact = findViewById(R.id.acceptrequest_vcontact);

        spinner= findViewById(R.id.acceptrequest_area_spinner);

        accept = findViewById(R.id.acceptrequest_acceptButton);

        Map<String,String> values = new HashMap<>();
        values.put("email",SplashActivity.sharedPrefrencesHandler.getEmail());
        values.put("city",city);

        final DatabaseHandler databaseHandler = new DatabaseHandler(AcceptRequestActivity.this,url) {
            @Override
            public void getResponse(String response) throws Exception {

                JSONArray jsonArray =new JSONArray(response);

                for(int i = 0 ;i<jsonArray.length();i++)
                {
                    JSONObject object  = jsonArray.getJSONObject(i);
                    areas.add(object.getString("area"));

                }
                area = areas.get(0);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AcceptRequestActivity.this,android.R.layout.simple_spinner_item,areas);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);

            }
        };



        databaseHandler.putValues(values);
        databaseHandler.execute();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                area = areas.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                area = areas.get(0);
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url1 = Init.ip+"AcceptRequest.php";
                vcontact = v_contact.getText().toString();

                Map<String,String> value =new HashMap<>();
                value.put("rno",rno);
                value.put("vcontact",vcontact);
                value.put("area",area);

                DatabaseHandler databaseHandler1 = new DatabaseHandler(AcceptRequestActivity.this,url1) {
                    @Override
                    public void getResponse(String response) throws Exception {

                        Toast.makeText(AcceptRequestActivity.this,response,Toast.LENGTH_SHORT).show();
                        finish();
                    }
                };

                databaseHandler1.putValues(value);
                databaseHandler1.execute();
            }
        });




    }
}