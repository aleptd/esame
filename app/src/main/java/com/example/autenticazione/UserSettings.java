package com.example.autenticazione;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class UserSettings extends AppCompatActivity {

    private ImageButton ibBack;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        initUI();


    }

    public void initUI(){
        ibBack= (ImageButton) findViewById(R.id.ibBack);
    }

    public void backToMainAct(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
