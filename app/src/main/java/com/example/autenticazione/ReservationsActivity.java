package com.example.autenticazione;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

import static com.example.autenticazione.AlarmBroadcastReceiver.ACTION_ALARM;

public class ReservationsActivity extends AppCompatActivity {

    private ImageButton ibEditIncumbent;
    private ImageButton ibDeleteIncumbent;
    private ImageView ivDeleteRequest;
    private ImageView ivAcceptRequest;
    private ImageView ivEntrantCancelRequest;
    private ListView listEntrant;
    private ListView listIncumbent;

    private ImageButton ibBack;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem myRequests;
    private TabItem requestsToMe;
    private TabItem myAvailabilities;
    private ConstraintLayout reservationsLayout;
    public PageAdapter pageAdapter; //NB classe necessaria al funzionamento del tab, creata come classe.



    PendingIntent alarmIntent;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        //sfondo

        reservationsLayout = (ConstraintLayout) this.findViewById(R.id.reservationsLayout);
        reservationsLayout.setBackgroundColor(getResources().getColor(R.color.colorBackground));
        ibBack = (ImageButton)findViewById(R.id.ibBack);

        //tabLayout

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        myRequests = (TabItem) findViewById(R.id.myRequests);
        requestsToMe = (TabItem) findViewById(R.id.requestsToMe);
        myAvailabilities = (TabItem) findViewById(R.id.myAvailabilities);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        pageAdapter = new PageAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    public void goBackToMainActivity(View v) {
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
    }

    public void editAvailability(View v) {
        Intent intent = new Intent(this, NotifyActivity.class);
        startActivity(intent);
    }




    public void setAlarm(View view) {
        //creazione intent da eseguire
        Intent intentToFire = new Intent(getApplicationContext(), AlarmBroadcastReceiver.class);
        intentToFire.setAction(ACTION_ALARM);

        // PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(),0,intentToFire, 0);
        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(),0,intentToFire, 0);

        // AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        triggerAlarmAtAGivenTime();
    }

    public void triggerAlarmAfterGivenTime(){
        //modo 1
        long tenSeconds = SystemClock.elapsedRealtime() +10*1000;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME, tenSeconds, alarmIntent);
    }
    //metodo che si basa sul tempo specifico
    public void triggerAlarmAtAGivenTime(){
        //modo 2
        //per impostare l'orario preciso
        Calendar calendar = Calendar.getInstance();
        //si imposta il tempo sotto forma di millisencondi
        calendar.setTimeInMillis(System.currentTimeMillis());
        //si impostano l'orario i minuti e i secondi
        calendar.set(Calendar.HOUR_OF_DAY, 9); //per far eseguire la notifica in questo istante si passa 9
        //qui si passano i minuti
        calendar.set(Calendar.MINUTE, 54);
        //si passano anche i secondi (se si vuole)
        calendar.set(Calendar.SECOND, 00);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), alarmIntent);

        // al metodo si passa il tempo relativo ai millesecondi del calendario
        salvaInSharedPreferences(Long.toString(calendar.getTimeInMillis()));

    }

    public void cancelAlarm(View view) {

        alarmManager.cancel(alarmIntent);
    }

    //si salva nelle preferenze i millisecondi che l'utente vuole passare
    public void salvaInSharedPreferences(String millis){
        SharedPreferences spref = getSharedPreferences("com.example.autenticazione", MODE_PRIVATE);
        Log.i("mylog", millis);

        SharedPreferences.Editor editor = spref.edit();
        //si salva la data all'interno di data
        editor.putString("data",millis);
        editor.commit();
    }




}