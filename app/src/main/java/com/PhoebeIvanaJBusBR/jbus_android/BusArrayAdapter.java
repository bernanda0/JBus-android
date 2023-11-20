package com.PhoebeIvanaJBusBR.jbus_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.PhoebeIvanaJBusBR.jbus_android.model.Bus;
import java.util.List;

public class BusArrayAdapter extends ArrayAdapter<Bus> {

    public BusArrayAdapter(@NonNull Context context,  @NonNull List<Bus> objects) {
        super(context, 0, objects);
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.bus_view, parent, false);
        }
        Bus currentBus = getItem(position);
        TextView textView1 = currentItemView.findViewById(R.id.busName);
        textView1.setText(currentBus.name);
        return currentItemView;
    }
}
