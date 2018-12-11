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

import java.io.IOException;

public class Actividad_40_Presentacion extends AppCompatActivity {
    private View Fondo40;
    private TextView Texto401;
    private ImageView Splash;
    private TextView Texto402;
    private int Contador;
    public Button Botonrepetir;
    public MediaPlayer oihaltxo;
    int idPuntoJuego;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_40__presentacion);
          idPuntoJuego=getIntent().getIntExtra("idPuntoJuego",0);
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
        oihaltxo = MediaPlayer.create(Actividad_40_Presentacion.this, R.raw.klipastra);

        Fondo40.setOnClickListener(new View.OnClickListener() {
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
                Texto401.setVisibility(View.VISIBLE);
                Texto402.setVisibility(View.INVISIBLE);
                Audio();
            }
        });
        if (Contador >= 1) {
            oihaltxo.stop();
           /* oihaltxo.pause();
            oihaltxo.release();*/
            Intent i = new Intent(getBaseContext(), Actividad_41_astrazaharra.class);
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
            }, 30800);
            /* }*//* else {
                oihaltxo.stop();
            }*/
            Splash.setVisibility(View.VISIBLE);
            Texto401.setVisibility(View.VISIBLE);
            Contador++;
        }
    }
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10){

            Actividad_40_Presentacion.this.finish();

        }
    }
}
