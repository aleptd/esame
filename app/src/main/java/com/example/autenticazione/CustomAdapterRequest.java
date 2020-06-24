package com.example.autenticazione;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CustomAdapterRequest extends ArrayAdapter<Request> {

    private int resourceLayout;
    private Context mContext;

    public CustomAdapterRequest(@NonNull Context context, int resource, Request[] requests) {
        super(context, resource, requests);
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

        Request r = getItem(position);
        if(r != null) {
            TextView t0 = (TextView) v.findViewById(R.id.tvTimeIncumbent);
            TextView t1 = (TextView) v.findViewById(R.id.tvUsernameIncumbent);
            RatingBar ratingBar2 = v.findViewById(R.id.ratingBar2);
            ratingBar2.setNumStars(3);
            TextView t2 = (TextView) v.findViewById(R.id.tvLicensePlateIncumbent);
            TextView t3 = (TextView) v.findViewById(R.id.tvModelIncumbent);
            TextView t4 = (TextView) v.findViewById(R.id.tvColorIncumbent);
            ImageView ivStatusAccepted = (ImageView) v.findViewById(R.id.ivStatusAccepted);
            ImageView ivStatusRejected = (ImageView) v.findViewById(R.id.ivStatusRejected);
            if(t0 != null) {
                t0.setText(r.getSubmissionTime());
            }
            if (t1 != null) {
                t1.setText(r.getUsername_incumbent());
            }
            if (t2 != null) {
                t2.setText(r.getAuto_licensePlate_incumbent());
            }
            if(t3 != null) {
                t3.setText(r.getAuto_model_incumbent());
            }
            if(t4 != null) {
                t4.setText(r.getAuto_color_incumbent());
            }
            if(ratingBar2 != null) {
                ratingBar2.setRating(r.getRatingIncumbent());
            }
            if(r.getStatus() == true){
                ivStatusAccepted.setVisibility(View.VISIBLE);
                ivStatusRejected.setVisibility(View.INVISIBLE);
            }
            else if (r.getStatus() == false){
                ivStatusRejected.setVisibility(View.VISIBLE);
                ivStatusAccepted.setVisibility(View.INVISIBLE);
            }
        }

        return v;
    }
}
