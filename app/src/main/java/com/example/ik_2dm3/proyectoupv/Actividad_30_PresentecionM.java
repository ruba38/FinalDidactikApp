package com.example.ik_2dm3.proyectoupv;

import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Actividad_30_PresentecionM extends AppCompatActivity {
    private View Fondo301;
    private TextView Texto301;
    private ImageView Splash;
    private TextView Texto321;
    private int Contador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_30__presentecion_m);
        final MediaPlayer oihaltxo = MediaPlayer.create(getApplicationContext(), R.raw.azoka);
    //declarar Contenido
        Fondo301 = findViewById(R.id.Fondo301);
        Texto301 = findViewById(R.id.Texto301);
        Texto321 = findViewById(R.id.T321);
        Splash = findViewById(R.id.SplasNaranja);
        Splash.setVisibility(View.INVISIBLE);
        Texto301.setVisibility(View.INVISIBLE);
        Texto321.setVisibility(View.INVISIBLE);
     // OnClick
        Fondo301.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Contador >= 1) {
                    oihaltxo.stop();
                    Intent i = new Intent(getBaseContext(), Actividad_31_Mercatua.class);
                    startActivity(i);
                    Contador = 0;
                }
                //Canbiar el texto
                if (Contador == 0) {
                    if (oihaltxo.isPlaying() == false) {
                        oihaltxo.start();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable(){
                                                @Override
                                                public void run() {
                                                    Texto301.setVisibility(View.INVISIBLE);
                                                    Texto321.setVisibility(View.VISIBLE);

                                                }
                                            }, 24000
                        );
                    }
                    Splash.setVisibility(View.VISIBLE);
                    Texto301.setVisibility(View.VISIBLE);
                    Contador++;
                }

            }


        });


    }
}
