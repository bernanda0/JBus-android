package com.PhoebeIvanaJBusBR.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutMeActivity extends AppCompatActivity {
    TextView initialTV = null;
    String username = "Fibi";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        initialTV = findViewById(R.id.initial);
        initialTV.setText(""+ username.charAt(0));
    }

}