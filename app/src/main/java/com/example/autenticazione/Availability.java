package com.example.autenticazione;

import java.sql.Time;
import java.util.Date;

public class Availability {

    private int id_availability;

    private String longitude;
    private String latitude;
    private String username_incumbent;
    private String auto_color_incumbent;
    private String auto_model_incumbent;
    private String auto_licensePlate_incumbent;
    private String time;
    private Date date;
    private float score_incumbent;

    public Availability(String time, String auto_licensePlate_incumbent, String auto_model_incumbent, String auto_color_incumbent) {
        this.time=time;
        this.auto_licensePlate_incumbent=auto_licensePlate_incumbent;
        this.auto_model_incumbent=auto_model_incumbent;
        this.auto_color_incumbent=auto_color_incumbent;
    }

    public String getTime (){
        return time;
    }

    public String getAuto_color_incumbent(){
        return auto_color_incumbent;
    }

    public String getAuto_licensePlate_incumbent(){
        return auto_licensePlate_incumbent;
    }

    public String getAuto_model_incumbent(){
        return auto_model_incumbent;
    }
}
