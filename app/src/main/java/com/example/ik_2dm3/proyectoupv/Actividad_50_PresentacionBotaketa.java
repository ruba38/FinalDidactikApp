package com.example.ik_2dm3.proyectoupv;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Actividad_50_PresentacionBotaketa extends AppCompatActivity {
    private TextView Ta5;
    private MediaPlayer Oihaltxo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_5__presentacion_botaketa);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Oihaltxo = MediaPlayer.create(Actividad_50_PresentacionBotaketa.this, R.raw.juntetxe);
        Oihaltxo.start();



        Ta5 = findViewById(R.id.a5);

        Ta5.postDelayed(new Runnable() {
            public void run() {
            Ta5.setText(getString(R.string.Texto502));
            }
        }, 20000);

        Ta5.postDelayed(new Runnable() {
            public void run() {

                Ta5.setText(getString(R.string.Texto503));
            }
        }, 38000);

        Ta5.postDelayed(new Runnable() {
            public void run() {

            }
        }, 25500);

    }
}



