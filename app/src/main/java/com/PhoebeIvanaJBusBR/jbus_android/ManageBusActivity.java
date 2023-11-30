package com.PhoebeIvanaJBusBR.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.PhoebeIvanaJBusBR.jbus_android.model.Bus;
import com.PhoebeIvanaJBusBR.jbus_android.request.BaseApiService;
import com.PhoebeIvanaJBusBR.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageBusActivity extends AppCompatActivity {
    private ListView myListBus = null;
    private List<Bus> myListData = new ArrayList<>();
    private BaseApiService mApiService;
    private Context mContext;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_bus_activity);
        myListBus = findViewById(R.id.ListViewManageBus);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        handleMyBusView();
    }

    protected void handleMyBusView(){
        mApiService.getMyBus(LoginActivity.loggedAccount.id).enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                  Toast.makeText(mContext,"" + response.body(),Toast.LENGTH_SHORT).show();
                  myListData = response.body();
//                  Toast.makeText(mContext,myListData.size(),Toast.LENGTH_SHORT).show();
                MyListBusAdapter busAdapter = new MyListBusAdapter(mContext, myListData);
                myListBus.setAdapter(busAdapter);
            }
            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private class MyListBusAdapter extends ArrayAdapter<Bus> {
        private ImageView imageCalendar = null;
        public MyListBusAdapter(@NonNull Context context, @NonNull List<Bus> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View currentItemView = convertView;
            if (currentItemView == null) {
                currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.my_bus_view, parent, false);
            }
            Bus currentBus = getItem(position);
            TextView textView1 = currentItemView.findViewById(R.id.myBusView);
            textView1.setText(currentBus.name);
            ImageView imageView1 = currentItemView.findViewById(R.id.imageCalendar);
            imageView1.setOnClickListener(e->{moveActivity(mContext,BusScheduleActivity.class);});
            return currentItemView;
        }
    }
    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx,cls);
        startActivity(intent);
    }
    private void viewToast(Context ctx, String message){
        Toast.makeText(ctx,message,Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_activity, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addButton) {
            moveActivity(this,AddBusActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
