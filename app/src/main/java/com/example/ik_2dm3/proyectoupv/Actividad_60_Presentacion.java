package com.example.ik_2dm3.proyectoupv;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class Actividad_60_Presentacion extends AppCompatActivity {
    private View Fondo60;
    private TextView Texto601;
    private ImageView Splash;
    private TextView Texto602;
    private int Contador;
    public Button Botonrepetir;
    public MediaPlayer oihaltxo;
    int idPuntoJuego;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_60__presentacion);

        idPuntoJuego=getIntent().getIntExtra("idPuntoJuego",0);
        //declarar Contenido
        getSupportActionBar().hide();
        Fondo60 = findViewById(R.id.Fondo60);
        Texto601 = findViewById(R.id.Texto601);
        Texto602 = findViewById(R.id.Texto602);
        Splash = findViewById(R.id.SplasNaranja);
        Splash.setVisibility(View.INVISIBLE);
        Texto601.setVisibility(View.INVISIBLE);
        Texto602.setVisibility(View.INVISIBLE);
        Botonrepetir= findViewById(R.id.BotonRepetopr40);
        Botonrepetir.setVisibility(View.INVISIBLE);
        oihaltxo = MediaPlayer.create(Actividad_60_Presentacion.this, R.raw.kliparbola);
        Fondo60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Audio();
            }
        });
    }
    protected void onDestroy(){
        super.onDestroy();
        oihaltxo.stop();
    }
    public void Audio() {
        Botonrepetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oihaltxo.stop();
                try{
                    oihaltxo.prepare();
                }catch (IOException e){
                    throw new Error ("Error copiando BD");
                }
                Contador = 0;
                Botonrepetir.setVisibility(View.INVISIBLE);
                Texto601.setVisibility(View.VISIBLE);
                Texto602.setVisibility(View.INVISIBLE);
                Audio();
            }
        });
        if (Contador >= 1) {
            oihaltxo.stop();
           /* oihaltxo.pause();
            oihaltxo.release();*/
            Intent i = new Intent(getBaseContext(), Actividad_61_GernikaArbola.class);
            i.putExtra("idPuntoJuego",idPuntoJuego);
            startActivityForResult(i, 10);
            finish();

        }
        //Canbiar el texto
        if (Contador == 0) {
            /*if (oihaltxo.isPlaying() == false) {*/
            oihaltxo.start();
            Handler CambiartextO = new Handler();
            Handler BotonRepetir = new Handler();
            CambiartextO.postDelayed(new Runnable() {
                                         @Override
                                         public void run() {
                                             Texto601.setVisibility(View.INVISIBLE);
                                             Texto602.setVisibility(View.VISIBLE);
                                         }
                                     }, 21500
            );
            BotonRepetir.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Botonrepetir.setVisibility(View.VISIBLE);
                }
            }, 30800);
            /* }*//* else {
                oihaltxo.stop();
            }*/
            Splash.setVisibility(View.VISIBLE);
            Texto601.setVisibility(View.VISIBLE);
            Contador++;
        }
    }
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10){

            Actividad_60_Presentacion.this.finish();

        }
    }
}
