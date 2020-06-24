package com.example.autenticazione;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotificationChannels extends Application {
    public  static final String CHANNEL_1_ID = "channel1";

    @Override
    public void onCreate() {
        super.onCreate();
        creaNotificationChannel();
    }

    private void creaNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            // creo il canale
            CharSequence nome = "canale1";
            String descrizione = "descrizione canale1";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_1_ID,nome, importance);
            //per impostare la descrizione
            channel.setDescription(descrizione);

            //per far sì che il NotificationManager riceva il canale
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
