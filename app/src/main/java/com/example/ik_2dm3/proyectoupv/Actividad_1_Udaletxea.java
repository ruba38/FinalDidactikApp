package com.example.ik_2dm3.proyectoupv;

import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.ik_2dm3.proyectoupv.R.*;

public class Actividad_1_Udaletxea extends AppCompatActivity {
    private Button idBottonRepetir_A1_udaletxea;
    private Button idBottonRepetirAudio_A1_udaletxea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_actividad_1__udaletxea);
        getSupportActionBar().hide();

        final MediaPlayer oihaltxo = MediaPlayer.create(Actividad_1_Udaletxea.this, raw.klipdontello);
        oihaltxo.start();
        //Boton repetir
        idBottonRepetir_A1_udaletxea = (Button) findViewById(R.id.idBottonRepetir_A1_udaletxea);
        idBottonRepetir_A1_udaletxea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oihaltxo.release();
                finish();

            }
        });
        idBottonRepetirAudio_A1_udaletxea = (Button) findViewById(R.id.idBottonRepetirAudio_A1_udaletxea);
        idBottonRepetirAudio_A1_udaletxea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oihaltxo.release();
                if(oihaltxo.isPlaying()==false){
                    final MediaPlayer oihaltxo = MediaPlayer.create(Actividad_1_Udaletxea.this, raw.klipdontello);
                    oihaltxo.start();
                }
            }
        });
    }}