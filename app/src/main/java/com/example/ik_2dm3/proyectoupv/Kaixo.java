package com.example.ik_2dm3.proyectoupv;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Kaixo extends AppCompatActivity {
    private ConstraintLayout idLayoutKaixo;
    static MediaPlayer kaixo =null;
    private int Lugar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaixo);
        getSupportActionBar().hide();

        //ocultar barras extras
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Lugar=getIntent().getIntExtra("idLugar",0);
        if(kaixo==null){
            kaixo = MediaPlayer.create(Kaixo.this, R.raw.kaixo);
            kaixo = MediaPlayer.create(getApplicationContext(), R.raw.kaixo);
            kaixo.start();
        }
        idLayoutKaixo = findViewById(R.id.idLayoutKaixo);
        idLayoutKaixo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent (getBaseContext(),MapaActivity.class);
                i.putExtra("idLugar",Lugar);
                kaixo.stop();
                i.putExtra("Descargar", false);
                startActivityForResult(i,103);}

                }
            );
        }
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        if(requestCode==103){
            finish();

        }
        if (requestCode==104);
        finish();
    }

}

