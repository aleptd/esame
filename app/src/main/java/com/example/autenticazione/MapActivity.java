package com.example.autenticazione;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private MapView mapView;
    GoogleMap gmap;

    private ImageButton ibBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        initUI();

        createMapView(savedInstanceState);

    }

    public void initUI(){
        ibBack=findViewById(R.id.ibBack);
    }

    public void goBackToMainActivity (View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void createMapView(Bundle savedInstanceState){
        Bundle mapViewBundle = null;
        if (savedInstanceState != null)
            //se savedInstanceState è diverso da null si valorizza mapViewBundle
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);

        //stiamo accedendo alla mapView che abbiamo nel nostro layout
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

    }


    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    //la nostra applicazione resta in attesa finchè non arriva una mappa
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;

        //per visualizzare il traffico nella mappa
        gmap.setTrafficEnabled(true);

        //per modificare il tipo di mappa visibile
        gmap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        //imosta mappe indoor
        gmap.setIndoorEnabled(true);

        //per modificare i setting della mappa
        UiSettings uiSettings = gmap.getUiSettings();
        //su uiSettings si impostano alcuni metodi che cambiano la nostra mappa
        //ad esempio per cambiare i livelli di zoom
        uiSettings.setZoomControlsEnabled(true);
        //si può impostare la bussola
        uiSettings.setCompassEnabled(true);
        //permette di visualizzare diversi livelli indoor
        uiSettings.setIndoorLevelPickerEnabled(true);

        //mostra la maptoolbar che permette di navigare verso una determinata applicazione
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);


        //si può impostare il livello di zoom con Preference
        //gmap.setMinZoomPreference(12);

        // 45 4 7 sono grosso modo le coordinate di Torino
        LatLng city = new LatLng(45.071305, 7.685112);

        //si aggiunge un marker in versione semplificata
        gmap.addMarker(new MarkerOptions().position(city)
        .title("sei qui")
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));



        // si muove la camera su il punto LatLng definito
        //senza zoom
     //   gmap.moveCamera(CameraUpdateFactory.newLatLng(city));

        //con zoom
        //si imposta il livello di zoom manualmente
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(city, 15));

    }
}
