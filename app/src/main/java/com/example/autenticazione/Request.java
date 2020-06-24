package com.example.autenticazione;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.sql.Time;
import java.util.Date;

public class Request {
    private int id_request;
    private String username_entrant;
    private String username_incumbent;
    private String submissionTime;
    private Date submissionDate;
    private String auto_model_entrant;
    private String auto_model_incumbent;
    private String auto_color_entrant;
    private String auto_color_incumbent;
    private long longi;
    private long lat;
    private float rating_incumbent;
    private String auto_licensePlate_entrant;
    private String auto_licensePlate_incumbent;
    private boolean status;

    public Request(String submissionTime, String username_incumbent, String auto_licensePlate_incumbent,
                   String auto_model_incumbent, String auto_color_incumbent, float rating_incumbent, boolean status) {
        this.submissionTime = submissionTime;
        this.username_incumbent = username_incumbent;
        this.auto_licensePlate_incumbent = auto_licensePlate_incumbent;
        this.auto_model_incumbent = auto_model_incumbent;
        this.auto_color_incumbent = auto_color_incumbent;
        this.rating_incumbent = rating_incumbent;
        this.status = status;
    }

    public String getSubmissionTime(){
        return submissionTime;
    }
    public String getUsername_incumbent(){
        return username_incumbent;
    }
    public String getAuto_licensePlate_incumbent(){
        return auto_licensePlate_incumbent;
    }
    public String getAuto_model_incumbent(){
        return auto_model_incumbent;
    }
    public String getAuto_color_incumbent(){
        return auto_color_incumbent;
    }
    public float getRatingIncumbent(){
        return rating_incumbent;
    }
    public boolean getStatus(){
        return status;
    }



}






