package com.PhoebeIvanaJBusBR.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.PhoebeIvanaJBusBR.jbus_android.model.BaseResponse;
import com.PhoebeIvanaJBusBR.jbus_android.model.Renter;
import com.PhoebeIvanaJBusBR.jbus_android.request.BaseApiService;
import com.PhoebeIvanaJBusBR.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRenterActivity  extends AppCompatActivity {
    private EditText companyName,address,phoneNumber;
    private BaseApiService mApiService;
    private Context mContext;
    Button registerButtonListener = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_renter_activity);
        mContext = this;
        mApiService = UtilsApi.getApiService();
        registerButtonListener = findViewById(R.id.renterRegisterButton);
        companyName = findViewById(R.id.companyName);
        address = findViewById(R.id.companyAddress);
        phoneNumber = findViewById(R.id.companyPhoneNumber);
        registerButtonListener.setOnClickListener(e->{handleRegisRenter();});
    }

    protected void handleRegisRenter(){
        String companyNameData = companyName.getText().toString();
        String addressData = address.getText().toString();
        String phoneNUmberData = phoneNumber.getText().toString();

        if (companyNameData.isEmpty() || addressData.isEmpty() || phoneNUmberData.isEmpty()){
            Toast.makeText(mContext, "Field could not be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        mApiService.registerRenter(LoginActivity.loggedAccount.id,companyNameData,addressData,phoneNUmberData).enqueue(new Callback<BaseResponse<Renter>>() {
            @Override
            public void onResponse(Call<BaseResponse<Renter>> call, Response<BaseResponse<Renter>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(mContext, "Register Error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(mContext,response.body().message,Toast.LENGTH_SHORT).show();
                if (response.body().success) moveActivity(mContext, AboutMeActivity.class);
            }

            @Override
            public void onFailure(Call<BaseResponse<Renter>> call, Throwable t) {
                Toast.makeText(mContext,"Problem with the Server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx,cls);
        startActivity(intent);
    }
    private void viewToast(Context ctx, String message){
        Toast.makeText(ctx,message,Toast.LENGTH_SHORT).show();
    }
}
