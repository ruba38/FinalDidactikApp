package com.example.ik_2dm3.proyectoupv;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Actividad_70_Puzzle extends AppCompatActivity {

    public MediaPlayer Oihaltxo;
    int idPuntoJuego;
    private TextView Texto700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_70__puzzle);
        idPuntoJuego = getIntent().getIntExtra("idPuntoJuego", 0);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Oihaltxo = MediaPlayer.create(Actividad_70_Puzzle.this, R.raw.picasso);
        Oihaltxo.start();
        Texto700 = findViewById(R.id.Texto70);
        Texto700.postDelayed(new Runnable() {
            public void run() {
                Texto700.setText(getString(R.string.Texto702));
            }
        }, 9500);
        Texto700.postDelayed(new Runnable() {
            public void run() {
                Texto700.setText(getString(R.string.Texto703));
        }
        }, 23000);
        Texto700.postDelayed(new Runnable() {
            public void run() {
                Texto700.setText(getString(R.string.Texto704));
            }
        }, 39000);
        Texto700.postDelayed(new Runnable() {
            public void run() {
                Texto700.setText(getString(R.string.Texto705));
            }
        }, 49000);

    }
}