package com.example.ik_2dm3.proyectoupv;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Kaixo extends AppCompatActivity {
    private ConstraintLayout idLayoutKaixo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaixo);

        final MediaPlayer kaixo = MediaPlayer.create(Kaixo.this, R.raw.kaixo);
        kaixo.start();
        idLayoutKaixo = findViewById(R.id.idLayoutKaixo);
        idLayoutKaixo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent (getBaseContext(),Actividad_1_Udaletxea.class);
                kaixo.stop();
                startActivity(i);}
            }
        );

    }
}

