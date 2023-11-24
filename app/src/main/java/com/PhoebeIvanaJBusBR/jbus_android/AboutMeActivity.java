package com.PhoebeIvanaJBusBR.jbus_android;

import static com.PhoebeIvanaJBusBR.jbus_android.LoginActivity.loggedAccount;

import android.content.Context;
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

public class AboutMeActivity extends AppCompatActivity {
    TextView initialTV = null;
    private TextView username, email, balance;
    char logoName;
    BaseApiService mApiService = UtilsApi.getApiService();
    Context mContext;
    private Button topUpButton = null;
    private EditText topUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        initialTV = findViewById(R.id.initial);
        username = findViewById(R.id.usernameAboutMe);
        email = findViewById(R.id.emailAboutMe);
        balance = findViewById(R.id.balanceAboutMe);
        topUp = findViewById(R.id.TopUpID);
        topUpButton = findViewById(R.id.button);
        mContext = this;
        getAccount();
        topUpButton.setOnClickListener(e->{handleTopUp();});
    }

    protected void handleTopUp(){
        mApiService.topUp(loggedAccount.id, Double.parseDouble(topUp.getText().toString())).enqueue(new Callback<BaseResponse<Double>>() {
            @Override
            public void onResponse(Call<BaseResponse<Double>> call, Response<BaseResponse<Double>> response) {
                if(response.isSuccessful()){
                    loggedAccount.balance = response.body().payload;
                    balance.setText("" + (int) loggedAccount.balance);
                    Toast.makeText(mContext,"Berhasil!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(mContext,"Gagal!",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<BaseResponse<Double>> call, Throwable t) {
                Toast.makeText(mContext,"Gagal!",Toast.LENGTH_SHORT).show();
            }
        });
    }
    protected void getAccount() {
        mApiService.getAccountbyId(loggedAccount.id).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext,response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    // if success, store the response body
                    Account responseAccount = response.body();
                    username.setText(responseAccount.name);
                    email.setText(responseAccount.email);
                    initialTV.setText(""+ responseAccount.name.toString().charAt(0));
                    balance.setText("" + responseAccount.balance);
                }
            }


            // method for handling error talking to the server
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
            // do something
                Toast.makeText(mContext,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}