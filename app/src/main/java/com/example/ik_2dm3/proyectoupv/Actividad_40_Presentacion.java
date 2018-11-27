package com.example.ik_2dm3.proyectoupv;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Actividad_40_Presentacion extends AppCompatActivity {
    private View Fondo40;
    private TextView Texto401;
    private ImageView Splash;
    private TextView Texto402;
    private int Contador;
    public Button Botonrepetir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_40__presentacion);
        //declarar Contenido
        getSupportActionBar().hide();
        Fondo40 = findViewById(R.id.Fondo40);
        Texto401 = findViewById(R.id.Texto401);
        Texto402 = findViewById(R.id.Texto402);
        Splash = findViewById(R.id.SplasNaranja);
        Splash.setVisibility(View.INVISIBLE);
        Texto401.setVisibility(View.INVISIBLE);
        Texto402.setVisibility(View.INVISIBLE);
        Botonrepetir= findViewById(R.id.BotonRepetopr40);
        Botonrepetir.setVisibility(View.INVISIBLE);
        Fondo40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Audio();
            }
        });
    }



    public void Audio() {
        final MediaPlayer oihaltxo = MediaPlayer.create(Actividad_40_Presentacion.this, R.raw.klipastra);

        Botonrepetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contador = 0;
                Botonrepetir.setVisibility(View.INVISIBLE);
                Texto401.setVisibility(View.VISIBLE);
                Texto402.setVisibility(View.INVISIBLE);
                Audio();
            }
        });
        if (Contador >= 0) {
            oihaltxo.stop();
            Intent i = new Intent(getBaseContext(), Actividad_41_astrazaharra.class);
            startActivity(i);

        }
        //Canbiar el texto
        if (Contador == 0) {
            if (oihaltxo.isPlaying() == false) {
                oihaltxo.start();
                Handler CambiartextO = new Handler();
                Handler BotonRepetir = new Handler();
                CambiartextO.postDelayed(new Runnable() {
                                             @Override
                                             public void run() {
                                                 Texto401.setVisibility(View.INVISIBLE);
                                                 Texto402.setVisibility(View.VISIBLE);
                                             }
                                         }, 19500
                );
                BotonRepetir.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Botonrepetir.setVisibility(View.VISIBLE);
                    }
                }, 1000);
            } else {
                oihaltxo.stop();
            }
            Splash.setVisibility(View.VISIBLE);
            Texto401.setVisibility(View.VISIBLE);
            Contador++;
        }
    }
}
