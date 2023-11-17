package com.PhoebeIvanaJBusBR.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private TextView registerNow = null;
    private Button loginButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerNow = findViewById(R.id.register_now);
        loginButton = findViewById(R.id.button);
        registerNow.setOnClickListener(e->{moveActivity(this, RegisterActivity.class);});
        loginButton.setOnClickListener(e->{moveActivity(this, MainActivity.class);});
        getSupportActionBar().hide();
    }
    // method untuk melakukan perpindahan activity
    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx,cls);
        startActivity(intent);
    }
    private void viewToast(Context ctx, String message){
        Toast.makeText(ctx,message,Toast.LENGTH_SHORT).show();
    }
}