package com.example.autenticazione;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;

public class MainActivity<ImageView> extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Toolbar toolbar;
    private TextView tvCredito;
    private ImageView ivFind;
    private ImageView ivNotify;
    private ImageView ivManage;
    private ImageView ivRemember;
    private ConstraintLayout mainLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sfondo

        mainLayout = (ConstraintLayout) this.findViewById(R.id.mainLayout);
        mainLayout.setBackgroundColor(getResources().getColor(R.color.colorBackground));

        mAuth = FirebaseAuth.getInstance();
        setTitle("Ciao, " + mAuth.getCurrentUser().getDisplayName());
        init();
    }

    public void init() {
        //riferimenti a tutte le iv e le tv
        ivFind = (ImageView) findViewById(R.id.ivFind);
        ivNotify = (ImageView) findViewById(R.id.ivNotify);
        ivManage = (ImageView) findViewById(R.id.ivManage);
        ivRemember = (ImageView) findViewById(R.id.ivRemember);
        tvCredito = (TextView) findViewById(R.id.tvCredito);

        // Aggiungo la toolbar
        toolbar = (Toolbar) findViewById(R.id.home_bar);

        //settaggio colore testo toolbar
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBackground));
        setSupportActionBar(toolbar);
    }

        /*
        Questo è il menu che ho creato come directory. Si può utilizzare n volte nell'app e sempre con questo codice. Abbiamo lasciato genericamente
        le possibilità come item1, item2, item3. Dopo aver messo la toolbar in ogni activity tramite <include , si può: 1) usare il menu, ricordando
        però che oltre i 3 elementi viene per forza 1 elemento + menu dei 3 pallini 2) mettere gli elementi nella toolbar dall'editor grafico mettendo
        dei vincoli connessi alla toolbar. Dipende quindi da quanta roba vogliamo mettere su. In questo codice sample ci sono 3 item, ma si possono
        togliere o aggiungere a piacimento e all'interno del file html specificare anche le iconcine (che io ho usato quella verde).
         */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    //dove andiamo con i vari item del menu?

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                //istruzione per l'item1, ad esempio Edit profile
                Intent intent = new Intent (this, EditProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.item2:
                //istruzione per l'item1, ad esempio Impostazioni
                Intent intent2 = new Intent(this, UserSettings.class);
                startActivity(intent2);
                return true;
            case R.id.item3:
                mAuth.signOut();
                Intent intent3 = new Intent(this, LoginActivity.class);
                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getMap(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }


    public void getNotifyActivity(View view) {
        Intent intent = new Intent(this, NotifyActivity.class);
        startActivity(intent);
    }

    public void getReservationsActivity(View view) {
        Intent intent = new Intent(this, ReservationsActivity.class);
        startActivity(intent);
    }

}


