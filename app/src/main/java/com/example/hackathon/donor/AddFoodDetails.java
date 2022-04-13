package com.example.hackathon.donor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hackathon.Init;
import com.example.hackathon.R;
import com.example.hackathon.handlers.DatabaseHandler;
import com.example.hackathon.login.SplashActivity;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddFoodDetails extends AppCompatActivity {

    String ngoEmail;
    String url = Init.ip+"AddFoodDetails.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_food_details_activity);

        ngoEmail = getIntent().getStringExtra("email");

        final TextInputEditText fooddetailsEdit = findViewById(R.id.food_details_addfood);

        Button send = findViewById(R.id.send_button_addfood);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> values = new HashMap<>();
                values.put("ngo_email",ngoEmail);
                values.put("donor_email", SplashActivity.sharedPrefrencesHandler.getEmail());
                values.put("fooddetails",fooddetailsEdit.getText().toString());

                DatabaseHandler databaseHandler = new DatabaseHandler(AddFoodDetails.this,url) {
                    @Override
                    public void getResponse(String response) throws Exception {
                        Toast.makeText(AddFoodDetails.this,new JSONObject(response).getString("message"),Toast.LENGTH_SHORT).show();
                        finish();
                    }
                };

                databaseHandler.putValues(values);
                databaseHandler.execute();

            }
        });


    }
}