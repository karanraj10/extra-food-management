package com.example.hackathon.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackathon.Init;
import com.example.hackathon.R;
import com.example.hackathon.handlers.DatabaseHandler;
import com.example.hackathon.login.LoginActivity;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    TextInputEditText emailEdit, passwordEdit, nameEdit, mobileEdit, addressEdit,cityEdit;
    Button signupButton;

    RadioGroup radioGroup;
    RadioButton selected;


    TextView loginLink;
    String email, password, name, mobile, address, type,city;

    String url = Init.ip+"Signup.php";

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        emailEdit = findViewById(R.id.email_signup);
        passwordEdit = findViewById(R.id.password_signup);
        nameEdit = findViewById(R.id.name_signup);
        mobileEdit = findViewById(R.id.mobile_signup);
        addressEdit = findViewById(R.id.address_signup);
        cityEdit = findViewById(R.id.city_signup);

        radioGroup = findViewById(R.id.user_type_radio_group_Signup);

        type = "NGO";

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selected = findViewById(checkedId);
                type = selected.getText().toString();
            }
        });

        signupButton = findViewById(R.id.signup_button_signup);

        loginLink = findViewById(R.id.login_link_signup);

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gotoLogin();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupOperation();
            }
        });
    }
    private void gotoOtp() {
        intent = new Intent(this,SendOtpSignupActivity.class);
        intent.putExtra("email",email);
        startActivity(intent);
    }

    private void gotoLogin() {
        intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void signupOperation()
    {
        email = emailEdit.getText().toString();
        password = passwordEdit.getText().toString();
        name = nameEdit.getText().toString();
        mobile = mobileEdit.getText().toString();
        address = addressEdit.getText().toString();
        city = cityEdit.getText().toString();

        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || mobile.isEmpty() || address.isEmpty() || city.isEmpty())
        {
            Toast.makeText(this,"Please Provide All Details !",Toast.LENGTH_SHORT).show();
        }
        else if (password.length() < 8)
        {
            Toast.makeText(this,"Password Should Be 8 Character Long",Toast.LENGTH_SHORT).show();
        }
        else
        {
            databaseOperation();
            gotoOtp();
        }
    }

    private void databaseOperation()
    {
        Map<String ,String> values = new HashMap<>();
        values.put("email",email);
        values.put("password",password);
        values.put("name",name);
        values.put("address",address);
        values.put("type",type);
        values.put("city",city);
        values.put("mobile",mobile);

        DatabaseHandler databaseHandler = new DatabaseHandler(SignupActivity.this,url) {
            @Override
            public void getResponse(String response) throws Exception {
                Toast.makeText(SignupActivity.this,new JSONObject(response).getString("message"),Toast.LENGTH_SHORT).show();
            }
        };

        databaseHandler.putValues(values);

        databaseHandler.execute();
    }
}
