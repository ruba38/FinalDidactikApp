package com.example.ik_2dm3.proyectoupv;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Agurra extends AppCompatActivity {
    private ConstraintLayout idLayoutAgurra;
    static MediaPlayer agurra=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agurra);
        getSupportActionBar().hide();

        agurra = MediaPlayer.create(Agurra.this, R.raw.clipagurra);
        if(agurra==null) {
            agurra.start();
            agurra = MediaPlayer.create(getApplicationContext(), R.raw.kaixo);
        }
        idLayoutAgurra = findViewById(R.id.idLayoutAgurra);
        idLayoutAgurra.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
             Intent i =new Intent (getBaseContext(),MainActivity.class);
             agurra.stop();
             startActivity(i);}
            }

        );
    }
}
