package com.example.ik_2dm3.proyectoupv;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Actividad_20_PresentacionAndraMari extends AppCompatActivity {
    private MediaPlayer Oihaltxo;
    private TextView Texto2;
    private ImageView botonrepetir, botonatras,Fondo;
    private boolean Bug;
    private int idPuntoJuego;
    private Button Aurrera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_20__presentacion_andra_mari);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Texto2 = findViewById(R.id.Texto200);
        idPuntoJuego=getIntent().getIntExtra("idPuntoJuego",0);
        botonrepetir = findViewById(R.id.Botonrepetir2);
        botonatras = findViewById(R.id.BottonAtras2);
        botonrepetir.setVisibility(View.INVISIBLE);
        Oihaltxo = MediaPlayer.create(Actividad_20_PresentacionAndraMari.this, R.raw.klipandramari);
        //Aurrera = findViewById(R.id.Aurrrera);
        Aurrera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), Actividad_21_EncontrarIglesia.class);
                i.putExtra("idPuntoJuego", idPuntoJuego);
                startActivityForResult(i, 10);

            }
        });

        Audio();
    }

    public void Audio() {
        botonrepetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                botonrepetir.setVisibility(View.INVISIBLE);
                Texto2.setText(getString(R.string.Texto201));
                Audio();
            }
        });
        //Canbiar el texto
            if (Oihaltxo.isPlaying() == false) {
                Oihaltxo.start();
                Handler CambiartextO = new Handler();
                Handler BotonRepetir = new Handler();
                CambiartextO.postDelayed(new Runnable() {
                                             @Override
                                             public void run() {
                                                 Texto2.setText(getString(R.string.Texto202));
                                             }
                                         }, 15500
                );
                BotonRepetir.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        botonrepetir.setVisibility(View.VISIBLE);
                    }
                }, 30000);
            } else
                Oihaltxo.stop();
            }



    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            Actividad_20_PresentacionAndraMari.this.finish();

        }
    }
}