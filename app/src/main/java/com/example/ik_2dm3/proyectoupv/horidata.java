package com.example.ik_2dm3.proyectoupv;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.View;

public class horidata extends Activity {
    private MediaPlayer oihaltxo;
    int idPuntoJuego;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horidata);

        idPuntoJuego=getIntent().getIntExtra("idPuntoJuego",0);

        DatabaseAccess databaseAccess=new DatabaseAccess(getBaseContext());
        databaseAccess.setTerminado(idPuntoJuego);
        oihaltxo = MediaPlayer.create(getBaseContext(), R.raw.horidata);
        oihaltxo.start();

        Handler amaitu= new Handler();
        amaitu.postDelayed(new Runnable(){
            @Override
            public void run() {
                horidata.this.finish();
            }
        }, 5000);

    }

}
