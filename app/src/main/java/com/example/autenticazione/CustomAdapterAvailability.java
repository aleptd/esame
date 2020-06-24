package com.example.autenticazione;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CustomAdapterAvailability extends ArrayAdapter<Availability>{

        private int resourceLayout;
        private Context mContext;

    public CustomAdapterAvailability(@NonNull Context context, int resource, Availability[] availabilities) {
        super(context, resource, availabilities);
        this.resourceLayout = resource;
        this.mContext = context;
    }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null)
        {
            LayoutInflater inflater;
            inflater = LayoutInflater.from(mContext);
            v=inflater.inflate(resourceLayout, null);
        }

        Availability a = getItem(position);
        if(a != null) {
            TextView t0 = (TextView) v.findViewById(R.id.tvTimeAvailability);
            TextView t1 = (TextView) v.findViewById(R.id.tvLicensePlateIncumbent);
            TextView t2 = (TextView) v.findViewById(R.id.tvModelIncumbent);
            TextView t3 = (TextView) v.findViewById(R.id.tvColorIncumbent);
            if(t0 != null) {
                t0.setText(a.getTime());
            }
            if (t1 != null) {
                t1.setText(a.getAuto_licensePlate_incumbent());
            }
            if (t2 != null) {
                t2.setText(a.getAuto_model_incumbent());
            }
            if(t3 != null) {
                t3.setText(a.getAuto_color_incumbent());
            }
        }

        return v;
    }
}
