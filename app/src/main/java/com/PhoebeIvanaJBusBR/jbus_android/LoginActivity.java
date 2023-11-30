package com.PhoebeIvanaJBusBR.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.PhoebeIvanaJBusBR.jbus_android.model.Account;
import com.PhoebeIvanaJBusBR.jbus_android.model.BaseResponse;
import com.PhoebeIvanaJBusBR.jbus_android.request.BaseApiService;
import com.PhoebeIvanaJBusBR.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextView registerNow = null;
    private Button loginButton = null;
    private EditText inputEmail = null;
    private EditText inputPassword = null;
    BaseApiService mApiService = UtilsApi.getApiService();
    Context mContext;
    static Account loggedAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputEmail = findViewById(R.id.emailInput);
        inputPassword = findViewById(R.id.passInput);
        registerNow = findViewById(R.id.registerNow);
        loginButton = findViewById(R.id.addBusButton2);
        mContext = this;
        registerNow.setOnClickListener(e->{moveActivity(this, RegisterActivity.class);});
        loginButton.setOnClickListener(e->{handleInput();});
        getSupportActionBar().hide();
    }
    private void handleInput(){
        String tempInputEmail = inputEmail.getText().toString();
        String tempInputPassword = inputPassword.getText().toString();
        if (tempInputEmail.isEmpty()){
            Toast.makeText(this, "Input Email Success!",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(tempInputPassword.isEmpty()){
            Toast.makeText(this, "Input Password Failed!",Toast.LENGTH_SHORT).show();
            return;
        }
        mApiService.loginAccount(tempInputEmail,tempInputPassword).enqueue(new Callback<BaseResponse<Account>>() {
            @Override
            public void onResponse(Call<BaseResponse<Account>> call, Response<BaseResponse<Account>> response) {
                BaseResponse<Account> responAcc = response.body();
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext,"Login Failed, not accepting Respond!", Toast.LENGTH_SHORT).show();
                }
                else if (response.isSuccessful()) {
                    if(responAcc.success){
                        Toast.makeText(mContext,"Welcome !", Toast.LENGTH_SHORT).show();
                        loggedAccount = responAcc.payload;
                        moveActivity(mContext, MainActivity.class);
                    }
                    else{
                        Toast.makeText(mContext,"Login Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Account>> call, Throwable t)  {
                Toast.makeText(mContext,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
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