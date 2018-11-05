package com.example.ik_2dm3.proyectoupv;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Agurra extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agurra);

        MediaPlayer kaixo = MediaPlayer.create(Agurra.this, R.raw.clipagurra);
        kaixo.start();

    }
}
