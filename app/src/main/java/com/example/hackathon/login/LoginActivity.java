package com.example.hackathon.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackathon.Init;
import com.example.hackathon.ngo.NgoActivity;
import com.example.hackathon.R;
import com.example.hackathon.donor.DonorActivity;
import com.example.hackathon.handlers.DatabaseHandler;
import com.google.android.material.textfield.TextInputEditText;
import com.onesignal.OSSubscriptionObserver;
import com.onesignal.OSSubscriptionStateChanges;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText emailEdit, passwordEdit;
    Button loginButton;

    TextView forgotPasswordLink, signupLink;
    String email, password;

    String url = Init.ip +"Login.php";
    String urlIn = Init.ip+"SetToken.php";

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        emailEdit = findViewById(R.id.email_login);
        passwordEdit = findViewById(R.id.password_login);

        forgotPasswordLink = findViewById(R.id.forgot_password_link_login);
        signupLink = findViewById(R.id.signup_link_login);

        loginButton = findViewById(R.id.login_button_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginOperation();
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gotoSignup();
            }
        });

        forgotPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gotoForgotPassword();
            }
        });

    }

    private void loginOperation() {

        email = emailEdit.getText().toString();
        password = passwordEdit.getText().toString();

        if (email.isEmpty() || password.isEmpty())
        {
            Toast.makeText(this,"Email & Password Should Not Be Empty !",Toast.LENGTH_SHORT).show();
        }
        else
        {
            databaseOperation();
        }

    }

    private void gotoSignup() {
        intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    private void gotoForgotPassword() {
        intent = new Intent(this, SendOtpActivity.class);
        startActivity(intent);
    }

    private void databaseOperation()
    {
        Map<String ,String> values = new HashMap<>();
        values.put("email",email);
        values.put("password",password);

        DatabaseHandler databaseHandler = new DatabaseHandler(LoginActivity.this,url) {
            @Override
            public void getResponse(String response) throws JSONException {
                JSONObject object = new JSONObject(response);

                if(object.getString("status").equals("1"))
                {
                    Toast.makeText(LoginActivity.this,object.getString("message"),Toast.LENGTH_SHORT).show();

                    SplashActivity.sharedPrefrencesHandler.setEmail(email);
                    SplashActivity.sharedPrefrencesHandler.setName(object.getString("name"));
                    SplashActivity.sharedPrefrencesHandler.setMobile(object.getString("mobile"));
                    SplashActivity.sharedPrefrencesHandler.setAddress(object.getString("address"));
                    SplashActivity.sharedPrefrencesHandler.setType(object.getString("type"));
                    SplashActivity.sharedPrefrencesHandler.setCity(object.getString("city"));
                    SplashActivity.sharedPrefrencesHandler.setLoggedIn(true);

                    gotoNextActivity(object.getString("type"));

                    setToken();

                }
                else
                {
                    Toast.makeText(LoginActivity.this,object.getString("message"),Toast.LENGTH_SHORT).show();
                }
            }
        };

        databaseHandler.putValues(values);

        databaseHandler.execute();
    }

    private void gotoNextActivity(String type) {
        if(type.equals("NGO"))
        {
            intent = new Intent(this, NgoActivity.class);
            startActivity(intent);
            finish();
        }
        else if(type.equals("Donor"))
        {
            intent = new Intent(this, DonorActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void setToken()
    {

        OSSubscriptionObserver subscriptionObserver = new OSSubscriptionObserver() {
            @Override
            public void onOSSubscriptionChanged(OSSubscriptionStateChanges stateChanges) {
                if (!stateChanges.getFrom().getSubscribed() && stateChanges.getTo().getSubscribed()){
                    String  userid = stateChanges.getTo().getUserId();
                    Log.d("TTOOKKEENN", "setToken: "+userid);

                    Map<String ,String> values = new HashMap<>();
                    values.put("email",email);
                    values.put("token",userid);



                    DatabaseHandler databaseHandlerIn = new DatabaseHandler(LoginActivity.this,urlIn) {
                        @Override
                        public void getResponse(String response) throws JSONException {

                        }
                    };

                    databaseHandlerIn.putValues(values);
                    databaseHandlerIn.execute();
                }

            }
        };

        OneSignal.addSubscriptionObserver(subscriptionObserver);


    }



}
