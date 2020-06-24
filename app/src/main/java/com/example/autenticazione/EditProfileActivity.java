package com.example.autenticazione;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

public class EditProfileActivity extends AppCompatActivity {

    private ConstraintLayout editProfileLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //sfondo

        editProfileLayout = (ConstraintLayout) this.findViewById(R.id.editProfileLayout);
        editProfileLayout.setBackgroundColor(getResources().getColor(R.color.colorBackground));
    }
}
