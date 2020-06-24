package com.example.autenticazione;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import static com.example.autenticazione.NotificationChannels.CHANNEL_1_ID;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    public static final String ACTION_ALARM = "com.example.autenticazione.ATCION_ALARM";
    private  int notifacationId = 0;
    Context context;
    SharedPreferences prefs = context.getSharedPreferences("com.example.autenticazione", Context.MODE_PRIVATE);


    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        if (ACTION_ALARM.equals(intent.getAction())) {
            Log.i("mylog", "ecco l'alarm");
            Toast.makeText(context, ACTION_ALARM, Toast.LENGTH_SHORT).show();
            sendNotification();
        }
        //in questo caso si è riavviato il nsotro dispositivo
        else if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            Log.i("mylog", "dispositivo riavviato");
            Toast.makeText(context, "BOOT COMPLETED", Toast.LENGTH_LONG).show();

            long data = prefs.getLong("data", 0);
            setAlarm(data);

        }
    }
    private void setAlarm(long data) {
        //per impostare un alarm si specifica l'intent che si vuole generare
        Intent intentToFIre = new Intent(context, AlarmBroadcastReceiver.class);
        intentToFIre.setAction(AlarmBroadcastReceiver.ACTION_ALARM);

        PendingIntent alarmIntent= PendingIntent.getBroadcast(context, 0, intentToFIre, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, data, alarmIntent);  //data rappresenta il tempo in cui si vuole impostare l'alarm
    }

    private void sendNotification(){
        //Accendo lo schermo
        //attraverso questo metodo se lo schermo è spento questo metodo lo dovrebbe avviare
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = Build.VERSION.SDK_INT >= 20 ? pm.isInteractive() : pm.isScreenOn(); // check if screen is on
        if (!isScreenOn) {
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "myApp:notificationLock");
            wl.acquire(3000); //set your time in milliseconds
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                //si impostano le altre impostazioni della notifica
                .setSmallIcon(R.drawable.ic_access_alarm_black_24dp)  //troviamo l'icona che vogliamo
                .setContentTitle("Orario di ritrovo")
                .setContentText("Attenzione mancano pochi minuti")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notifacationId, builder.build());
        notifacationId++;
    }

}