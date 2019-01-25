package com.example.ik_2dm3.proyectoupv;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

public class horidata extends AppCompatActivity {
    private MediaPlayer oihaltxo;
    int idPuntoJuego;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horidata);

        idPuntoJuego=getIntent().getIntExtra("idPuntoJuego",0);

        //ocultar barras extras
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DatabaseAccess databaseAccess=new DatabaseAccess(getBaseContext());
        databaseAccess.setTerminado(idPuntoJuego);
        int sonido= databaseAccess.getSonido();
        if(sonido==1) {
            oihaltxo = MediaPlayer.create(getBaseContext(), R.raw.horidata);
            oihaltxo.start();
        }
        Handler amaitu= new Handler();
        amaitu.postDelayed(new Runnable(){
            @Override
            public void run() {
                horidata.this.finish();
            }
        }, 4000);

    }
    @Override
    public void onBackPressed() {
        //your code when back button pressed
    }
}
