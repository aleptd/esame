package com.example.autenticazione;

import java.sql.Time;
import java.util.Date;

public class Reservation {

    private String id_transaction;
    private String longi;
    private String lat;
    private String username_entrant;
    private String username_incumbent;
    private Time endTime;
    private Time startTime;
    private Date date;
    private String note_incumbent;
    private String note_entrant;
    private boolean closingType;
    private int feedback_incumbent;
    private int feedback_entrant;
    private String licensePlate_entrant;
    private String model_entrant;
    private String color_entrant;
    private long rating_entrant;
    private float rating_incumbent;
    private String timeMeeting;
    //manca l'immagine utente entrant

    public Reservation(String timeMeeting, String username_entrant, String licensePlate_entrant, long rating_entrant, String model_entrant, String color_entrant) {
        this.timeMeeting = timeMeeting;
        this.username_entrant = username_entrant;
        this.rating_entrant = rating_entrant;
        this.model_entrant = model_entrant;
        this.color_entrant = color_entrant;
        this.licensePlate_entrant = licensePlate_entrant;
    }

    public String getUsername_entrant() {
        return username_entrant;
    }
    public String getTimeMeeting(){
        return timeMeeting;
    }
    public String getColor_entrant() {
        return color_entrant;
    }
    public String getModel_entrant(){
        return model_entrant;
    }
    public String getLicensePlate_entrant(){
        return licensePlate_entrant;
    }
    public long getRating_entrant() {
        return rating_entrant;
    }
    public String getNote_entrant(){
        return note_entrant;
    }


}
