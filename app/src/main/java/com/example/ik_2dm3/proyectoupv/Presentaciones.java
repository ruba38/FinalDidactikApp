package com.example.ik_2dm3.proyectoupv;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Presentaciones extends AppCompatActivity {
    private MediaPlayer Sonido;
    private ImageView FondoTexto, BotonRepetir, BotonAtras ,Fondo;
    private Button BotonaAlante;
    private TextView Textos;
    private int IdMusica, idPuntoJuego,Contador;
    private String idJuego;
    private String T1, T2, T3, T4, T5, T6;
   // private Activity Actividad;
    private ArrayList<String> TodoText = new ArrayList<String>();
    private ArrayList<Integer>  Tiempos = new ArrayList<Integer>();
    private ArrayList<Handler> Runables= new ArrayList<Handler>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentaciones);

        //ocultar barras extras
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        idPuntoJuego = getIntent().getIntExtra("idPuntoJuego", 1);
        idJuego =  getIntent().getStringExtra("NombreJuego");


        //declarar objetos de la interfaz
        Textos = findViewById(R.id.Textos);
        BotonAtras = findViewById(R.id.BotonAtras);
        BotonRepetir = findViewById(R.id.BotonRepetir);
        BotonRepetir.setVisibility(View.INVISIBLE);
        FondoTexto = findViewById(R.id.FondoDelTexto);
        BotonaAlante = findViewById(R.id.BotonAlante);
        BotonaAlante.setVisibility(View.INVISIBLE);

        // el fondo no es una imajen Fondo = findViewById(R.id.Fondo);
        //Boton Atras

        BotonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hacer que mande al mapa una señal de cancelado
            }
        });
        //Sonido & Textos
        switch (idPuntoJuego) {
            case 8:
                Sonido = MediaPlayer.create(getApplicationContext(), R.raw.klipdontello);


                //Textos

                T1 = getString(R.string.Texto101);
                T2 = getString(R.string.Texto102);
                //Añadir los textos al array a utilizar

                TodoText.add(T1);
                TodoText.add(T2);

                //Tienpos
                Tiempos.add(15500);
                Tiempos.add(29000);

                break;
            case 9:

                Sonido = MediaPlayer.create(getApplicationContext(), R.raw.klipandramari);
                //Textos

                T1 = getString(R.string.Texto201);
                T2 = getString(R.string.Texto202);
                //Añadir los textos al array a utilizar

                this.TodoText.add(T1);
                TodoText.add(T2);
                //Tiempos
                Tiempos.add(16000);
                Tiempos.add(31000);
                break;
            case 10:

                Sonido = MediaPlayer.create(getApplicationContext(), R.raw.azoka);
                //Textos

                T1 = getString(R.string.Texto301);
                T2 = getString(R.string.Texto302);
                //Añadir los textos al array a utilizar

                TodoText.add(T1);
                TodoText.add(T2);

                //Tiempos
                Tiempos.add(22000);
                Tiempos.add(42000);
                break;
            case 11:

                Sonido = MediaPlayer.create(getApplicationContext(), R.raw.klipastra);
                //Textos

                T1 = getString(R.string.Texto401);
                T2 = getString(R.string.Texto402);
                //Añadir los textos al array a utilizar

                TodoText.add(T1);
                TodoText.add(T2);
                //Tiempos
                Tiempos.add(17500);
                Tiempos.add(32000);
                break;
            case 12:

                Sonido = MediaPlayer.create(getApplicationContext(), R.raw.juntetxe);
                //Textos

                T1 = getString(R.string.Texto501);
                T2 = getString(R.string.Texto502);
                T3 = getString(R.string.Texto503);
                //Añadir los textos al array a utilizar

                TodoText.add(T1);
                TodoText.add(T2);
                TodoText.add(T3);
                //Tiempos
                Tiempos.add(20000);
                Tiempos.add(38000);
                Tiempos.add(52000);
                break;
            case 13:

                Sonido = MediaPlayer.create(getApplicationContext(), R.raw.arbola);
                //Textos

                T1 = getString(R.string.Texto601);
                T2 = getString(R.string.Texto602);
                //Añadir los textos al array a utilizar

                TodoText.add(T1);
                TodoText.add(T2);

                //Tiempos
                Tiempos.add(14000);
                Tiempos.add(32000);
                break;
            case 14:
                Sonido = MediaPlayer.create(getApplicationContext(), R.raw.picasso);
                //Textos
                T1 = getString(R.string.Texto701);
                T2 = getString(R.string.Texto702);
                T3 = getString(R.string.Texto703);
                T4 = getString(R.string.Texto704);
                T5 = getString(R.string.Texto705);
                T6 = getString(R.string.Texto706);
                //Añadir los textos al array a utilizar
                TodoText.add(T1);
                TodoText.add(T2);
                TodoText.add(T3);
                TodoText.add(T4);
                TodoText.add(T5);
                TodoText.add(T6);
                //Tiempos
                Tiempos.add(13000);
                Tiempos.add(25000);
                Tiempos.add(39000);
                Tiempos.add(52500);
                Tiempos.add(65000);
                break;
                default:
                    Sonido = MediaPlayer.create(getApplicationContext(), R.raw.klipdontello);

                    //Actividad
                    //Textos

                    T1 = getString(R.string.Texto101);
                    T2 = getString(R.string.Texto102);
                    //Añadir los textos al array a utilizar

                    TodoText.add(T1);
                    TodoText.add(T2);

                    //Tienpos
                    Tiempos.add(17500);
                    Tiempos.add(32000);
                    break;
        }


        //Iniciar audios y textos
        Ejecuccion(idPuntoJuego);


    }

    //Metodo al Emprezar la Activity Para reporduccior Audios y  Camiar los textos
    public void Ejecuccion(int idPunto) {
        BotonAtras.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Sonido.release();
                finish();
            }
        });
        BotonRepetir.setVisibility(View.INVISIBLE);
        BotonRepetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ejecuccion(idPunto);
            }
        });
        BotonaAlante.setVisibility(View.INVISIBLE);
        BotonaAlante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = null;
                Log.d("Juego", idJuego);
                  try {
                      i = new Intent(getBaseContext(), Class.forName(idJuego));
                  } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                      }
                      Sonido.stop();
                  startActivityForResult(i,1);
            }
        });
        if (!Sonido.isPlaying()) {
            Sonido.start();
           Handler CambiarTexto = new Handler();
           Handler AbilitaraBotones = new Handler();
            Contador =0;
            Textos.setText(TodoText.get(Contador));
            while(Contador  < Tiempos.size()){
                Log.d("Tiempos",Tiempos.get(Contador)+"Entra en admin cheked///////////////////////////////////////////////////////");
                CambiarTexto.postDelayed(new Runnable() {

                public void run() {
                    Textos.setText(TodoText.get(Contador-1));

                }
        }, Tiempos.get(Contador));
            Contador++;

            }
            BotonRepetir.setVisibility(View.VISIBLE);
            BotonaAlante.setVisibility(View.VISIBLE);
        }


    }
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            /*if (resultCode==RESULT_OK) {
                finish();
            }else {*/
                finish();
            /*}*/

        }
    }

}
