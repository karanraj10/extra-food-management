package com.example.hackathon;

import android.app.Application;
import android.util.Log;

import com.onesignal.OneSignal;

public class Init extends Application {

    public static String ip = "http://192.168.0.104/hackathon/";

    @Override
    public void onCreate() {
        super.onCreate();
        
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(false)
                .init();

        Log.d("INIT", "onCreate: INITIALIZED");

    }
}
