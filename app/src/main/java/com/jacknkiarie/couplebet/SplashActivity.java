package com.jacknkiarie.couplebet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    //splash screen timer
    private static final int SPLASH_TIME_OUT = 3000;
    private static final String TAG = SplashActivity.class.getSimpleName();
    public static final String PREF_IS_FROM_SPLASH = "PREF_IS_FROM_SPLASH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        }, SPLASH_TIME_OUT);
    }
}
