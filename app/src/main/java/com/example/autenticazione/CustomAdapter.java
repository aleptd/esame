package com.example.autenticazione;

import android.content.ClipData;
import android.content.Context;
import android.preference.PreferenceActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Reservation> {
    private int resourceLayout;
    private Context mContext;

    public CustomAdapter(@NonNull Context context, int resource, Reservation[] reservations) {
        super(context, resource, reservations);
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

        Reservation p = getItem(position);
        if(p != null) {
            TextView t0 = (TextView) v.findViewById(R.id.tvTimeMeeting);
            TextView t1 = (TextView) v.findViewById(R.id.tvUsernameEntrant);
            TextView t2 = (TextView) v.findViewById(R.id.tvLicensePlateEntrant);
            TextView t3 = (TextView) v.findViewById(R.id.tvModelEntrant);
            TextView t4 = (TextView) v.findViewById(R.id.tvColorEntrant);
            RatingBar ratingBar = (RatingBar) v.findViewById(R.id.ratingEntrant);
            ratingBar.setNumStars(3);
            if(t0 != null) {
                t0.setText(p.getTimeMeeting());
            }
            if (t1 != null) {
                t1.setText(p.getUsername_entrant());
            }
            if (t2 != null) {
                t2.setText(p.getLicensePlate_entrant());
            }
            if(t3 != null) {
                t3.setText(p.getModel_entrant());
            }
            if(t4 != null){
                t4.setText(p.getColor_entrant());
            }
            if(ratingBar != null) {
                ratingBar.setRating(p.getRating_entrant());
            }
        }

        return v;
    }
}
