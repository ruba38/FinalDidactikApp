package com.example.ik_2dm3.proyectoupv;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class Actividad_20_PresentacionAndraMari extends AppCompatActivity {
private MediaPlayer Oihaltxo;
private TextView text1, texto2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_20__presentacion_andra_mari);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        text1 = findViewById(R.id.Texto1);
        texto2 = findViewById(R.id.texto2);
        texto2.setVisibility(View.INVISIBLE);
        Oihaltxo = MediaPlayer.create(Actividad_20_PresentacionAndraMari.this, R.raw.klipandramari);
        Oihaltxo.start();


        text1.postDelayed(new Runnable() {
                public void run() {
                    text1.setVisibility(View.INVISIBLE);
                    texto2.setVisibility(View.VISIBLE);
            }
        }, 17500);

        text1.postDelayed(new Runnable() {
            public void run() {
              finish();
            }
        }, 33000);
    }
}

