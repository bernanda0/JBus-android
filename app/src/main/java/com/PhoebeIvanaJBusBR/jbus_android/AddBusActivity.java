package com.PhoebeIvanaJBusBR.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.PhoebeIvanaJBusBR.jbus_android.model.BaseResponse;
import com.PhoebeIvanaJBusBR.jbus_android.model.Bus;
import com.PhoebeIvanaJBusBR.jbus_android.model.BusType;
import com.PhoebeIvanaJBusBR.jbus_android.model.Facility;
import com.PhoebeIvanaJBusBR.jbus_android.model.Station;
import com.PhoebeIvanaJBusBR.jbus_android.request.BaseApiService;
import com.PhoebeIvanaJBusBR.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBusActivity extends AppCompatActivity {
    private BusType[] busType = BusType.values();
    private BusType selectedBusType;
    private Spinner busTypeSpinner,departureSpinner, arrivalSpinner;
    private List<Station> stationList = new ArrayList<>();
    private int selectedDeptStationID;
    private int selectedArrStationID;
    private BaseApiService mApiService;
    private Context mContext;
    private CheckBox acCheckBox, wifiCheckBox, toiletCheckBox, lcdCheckBox;
    private CheckBox coolboxCheckBox, lunchCheckBox, baggageCheckBox, electricCheckBox;
    private List<Facility> selectedFacilities = new ArrayList<>();
    private Button buttonAddBus = null;
    private EditText busName,capacity,price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bus_activity);
        busTypeSpinner = this.findViewById(R.id.bus_type_dropdown);
        ArrayAdapter adBus = new ArrayAdapter(this, android.R.layout.simple_list_item_1, busType);
        adBus.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        busTypeSpinner.setAdapter(adBus);
        busTypeSpinner.setOnItemSelectedListener(busTypeOISL);
        departureSpinner = this.findViewById(R.id.departure_dd);
        arrivalSpinner = this.findViewById(R.id.arrival_dd);
        acCheckBox = findViewById(R.id.ac_cb);
        wifiCheckBox = findViewById(R.id.wifi_cb);
        toiletCheckBox = findViewById(R.id.toilet_cb);
        lcdCheckBox = findViewById(R.id.lcd_cb);
        coolboxCheckBox = findViewById(R.id.coolbox_cb);
        lunchCheckBox = findViewById(R.id.lunch_cb);
        baggageCheckBox = findViewById(R.id.baggage_cb);
        electricCheckBox = findViewById(R.id.electric_cb);
        buttonAddBus = findViewById(R.id.busButton);
        busName = findViewById(R.id.busName);
        capacity = findViewById(R.id.capacity);
        price = findViewById(R.id.price);
        getAllStation();
        buttonAddBus.setOnClickListener(e->{handleBus();});
    }

    protected void handleBus(){
        String name = busName.getText().toString();
        String capacityS = capacity.getText().toString();
        String priceS = price.getText().toString();
        updateSelectedFacilities();
        mApiService.create(LoginActivity.loggedAccount.id,name,Integer.parseInt(capacityS),selectedFacilities,selectedBusType,Integer.parseInt(priceS),selectedDeptStationID,selectedArrStationID).enqueue(new Callback<BaseResponse<Bus>>() {
            @Override
            public void onResponse(Call<BaseResponse<Bus>> call, Response<BaseResponse<Bus>> response) {
                if(!response.isSuccessful())return;
                moveActivity(mContext,ManageBusActivity.class);
            }

            @Override
            public void onFailure(Call<BaseResponse<Bus>> call, Throwable t) {

            }
        });
    }
    protected void getAllStation(){
        mApiService.getAllStation().enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
                if (!response.isSuccessful()) return;

                stationList = response.body(); //simpan response body ke listStation
                ArrayAdapter deptBus = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, stationList);
                deptBus.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                departureSpinner.setAdapter(deptBus);
                departureSpinner.setOnItemSelectedListener(deptOISL);

                ArrayAdapter arrBus = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, stationList);
                arrBus.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                arrivalSpinner.setAdapter(arrBus);
                arrivalSpinner.setOnItemSelectedListener(arrOISL);
            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {

            }
        });
    }

    AdapterView.OnItemSelectedListener deptOISL = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            // mengisi field selectedBusType sesuai dengan item yang dipilih
            selectedDeptStationID = stationList.get(position).id;
        }
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener arrOISL = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            // mengisi field selectedBusType sesuai dengan item yang dipilih
            selectedArrStationID = stationList.get(position).id;
        }
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    AdapterView.OnItemSelectedListener busTypeOISL = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            // mengisi field selectedBusType sesuai dengan item yang dipilih
                selectedBusType = busType[position];
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
    };
    private void updateSelectedFacilities() {
        selectedFacilities.clear(); // Clear the list before updating

        if (acCheckBox.isChecked()) {
            selectedFacilities.add(Facility.AC);
        }

        if (wifiCheckBox.isChecked()) {
            selectedFacilities.add(Facility.WIFI);
        }

        if (toiletCheckBox.isChecked()) {
            selectedFacilities.add(Facility.TOILET);
        }

        if (lcdCheckBox.isChecked()) {
            selectedFacilities.add(Facility.LCD_TV);
        }

        if (coolboxCheckBox.isChecked()) {
            selectedFacilities.add(Facility.COOL_BOX);
        }

        if (lunchCheckBox.isChecked()) {
            selectedFacilities.add(Facility.LUNCH);
        }

        if (baggageCheckBox.isChecked()) {
            selectedFacilities.add(Facility.LARGE_BAGGAGE);
        }

        if (electricCheckBox.isChecked()) {
            selectedFacilities.add(Facility.ELECTRIC_SOCKET);
        }
    }

    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx,cls);
        startActivity(intent);
    }
    private void viewToast(Context ctx, String message){
        Toast.makeText(ctx,message,Toast.LENGTH_SHORT).show();
    }

}