package com.example.autenticazione;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.view.View.OnClickListener;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.view.MenuInflater;
import android.view.MenuItem;

public class NewAutoActivity extends AppCompatActivity {

    private static final int PERMISSION_SAVE_FILE = 3;
    private static final int PICK_IMAGE_GALLERY = 2;
    private static final int PICK_IMAGE_CAMERA = 1;
    private static final int CREATE_ACTIVITY_RESULT = 1;
    private EditText etNewAutoLicensePlate;
    private EditText etNewAutoModel;
    private ImageView ivNewAutoImage;
    private EditText etNewAutoColor;
    private InputStream inputStreamImg;
    private Auto auto;
    private Bitmap bitmap;
    private Bitmap rotated_bitmap;
    Uri selectedImage;
    private Toolbar toolbar;
    private ConstraintLayout newAutoLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_auto);
        setTitle("Inserisci nuova auto");


        //sfondo

        newAutoLayout = (ConstraintLayout) this.findViewById(R.id.newAutoLayout);
        newAutoLayout.setBackgroundColor(getResources().getColor(R.color.colorBackground));

        //riferimenti agli elementi grafici
        etNewAutoLicensePlate = (EditText) findViewById(R.id.etNewAutoLicensePlate);
        etNewAutoColor = (EditText) findViewById(R.id.etNewAutoColor);
        etNewAutoModel = (EditText) findViewById(R.id.etNewAutoModel);
        toolbar = (Toolbar) findViewById(R.id.home_bar);

        //cambio colore titolo toolbar
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBackground));
        setSupportActionBar(toolbar);

        // codice per modificare o aggiungere i campi

        Bundle autoBundle = getIntent().getBundleExtra("auto");

       if (autoBundle != null) {
            auto = (Auto) autoBundle.getParcelable("auto");
            etNewAutoColor.setText(auto.color);
            etNewAutoLicensePlate.setText(auto.licensePlate);
            etNewAutoModel.setText(auto.model);
        } else {
            auto = new Auto();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_auto_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent data = new Intent();
                Bundle autoBundle = new Bundle();
                auto.licensePlate = etNewAutoLicensePlate.getText().toString();
                auto.model = etNewAutoModel.getText().toString();
                auto.color = etNewAutoColor.getText().toString();
                autoBundle.putParcelable("auto", auto);
                data.putExtra("auto", autoBundle);
                setResult(Activity.RESULT_OK, data);
                finish();
                return true;
            case R.id.item2:
                Intent intent2 = new Intent(this, NotifyActivity.class);
                startActivity(intent2);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //codice per aggiungere foto da camera o da galleria

    public void pickImage(View v) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        PERMISSION_SAVE_FILE);
            }
        } else {
            start();
        }
    }

    //apertura dialog

    public void start() {
        final CharSequence[] options = {"Scatta foto", "Scegli dalla galleria", "Annulla"};
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Scegli un'opzione");
        alertDialog.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Scatta foto")) {
                    dialog.dismiss();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, PICK_IMAGE_CAMERA);
                } else if (options[item].equals("Scegli dalla galleria")) {
                    dialog.dismiss();
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                } else if (options[item].equals("Annulla")) {
                    dialog.dismiss();
                }
            }
        });
        alertDialog.show();
    }

    //caricamento della foto scattata o caricata sulla schermata di registrazione

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        inputStreamImg = null;
        if (requestCode == PICK_IMAGE_CAMERA) {

            Uri selectedImage = data.getData();
            bitmap = (Bitmap) data.getExtras().get("data");
            rotated_bitmap= RotateBitmap(bitmap,90);
            ivNewAutoImage.setImageBitmap(rotated_bitmap);

        } else if (requestCode == PICK_IMAGE_GALLERY) {

            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ivNewAutoImage.setImageBitmap(bitmap);
        }
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    //funzione per convertire bitmap in stringa

    public static String bitmapToString(Bitmap b) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();
        String finalImage = new String((imageInByte));
        return finalImage;
    }

    //funzione per convertire stringa in bitmap

    public Bitmap stringToBitmap(String s){
        try{
            byte [] encodeByte = Base64.decode(s,Base64.DEFAULT);
            Bitmap finalBitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return finalBitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }

}
