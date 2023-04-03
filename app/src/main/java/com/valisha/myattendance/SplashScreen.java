package com.valisha.myattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.valisha.myattendance.UI.Dashboard.Dashboard;

public class SplashScreen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TextView link = findViewById(R.id.link);
       // link.setText(getResources().getString(R.string.link));

        if (link != null) {
            link.setMovementMethod(LinkMovementMethod.getInstance());
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, Dashboard.class));
                finish();
            }
        },3000);
    }
}