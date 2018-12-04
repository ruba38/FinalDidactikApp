package com.example.ik_2dm3.proyectoupv;

import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class Actividad_30_PresentecionM extends AppCompatActivity {
    private View Fondo301;
    private TextView Texto301;
    private ImageView Splash;
    private TextView Texto321;
    private int Contador;
    public Button Botonrepetir;
    private ImageView BotonAtras;
    public MediaPlayer Oihaltxo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_30__presentecion_m);
        //declarar Contenido
        Fondo301 = findViewById(R.id.Fondo301);
        Texto301 = findViewById(R.id.Texto301);
        Texto321 = findViewById(R.id.T321);
        Splash = findViewById(R.id.SplasNaranja);
        Splash.setVisibility(View.INVISIBLE);
        Texto301.setVisibility(View.INVISIBLE);
        Texto321.setVisibility(View.INVISIBLE);
        Botonrepetir= findViewById(R.id.BotonRepetopr30);
        Botonrepetir.setVisibility(View.INVISIBLE);
        // OnClick Fondo
        Fondo301.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Audio();
             }
        });
    //Boton Atras
    BotonAtras= findViewById(R.id.BotonAtras);
    BotonAtras.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Oihaltxo.release();
            finish();
        }
    });
    }
//Metodo responsalbe del audio
    public void Audio(){
         Oihaltxo = MediaPlayer.create(Actividad_30_PresentecionM.this, R.raw.azoka);
        Botonrepetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contador=0;
                Botonrepetir.setVisibility(View.INVISIBLE);
                Texto301.setVisibility(View.VISIBLE);
                Texto321.setVisibility(View.INVISIBLE);
                Audio();
            }
        });
        if (Contador >=1) {
            Oihaltxo.stop();
            Oihaltxo.pause();
            Oihaltxo.release();
            Intent i = new Intent(getBaseContext(), Actividad_31_Mercatua.class);
            startActivityForResult(i, 10);
            finish();
        }
        //Canbiar el texto
        if (Contador == 0) {
            if (Oihaltxo.isPlaying() == false){
                Oihaltxo.start();
                Handler CambiartextO = new Handler();
                Handler BotonRepetir = new Handler();
                CambiartextO.postDelayed(new Runnable(){
                                        @Override
                                        public void run() {
                                            Texto301.setVisibility(View.INVISIBLE);
                                            Texto321.setVisibility(View.VISIBLE);
                                        }
                                    }, 24000
                );
                BotonRepetir.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Botonrepetir.setVisibility(View.VISIBLE);
                    }
                },41000);
            }else{
                Oihaltxo.stop();
            }
            Splash.setVisibility(View.VISIBLE);
            Texto301.setVisibility(View.VISIBLE);
            Contador++;
        }
    }   protected void onActivityResult(int requestCode,
                                        int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10){
            if (resultCode==RESULT_OK){
                Actividad_30_PresentecionM.this.finish();
           }
        }
    }
    protected void OnDestroy(){
        super.onDestroy();
        Oihaltxo.release();
    }
}
