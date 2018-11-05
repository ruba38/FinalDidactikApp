package com.example.ik_2dm3.proyectoupv;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Agurra extends AppCompatActivity {
    private ConstraintLayout idLayoutAgurra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agurra);
        getSupportActionBar().hide();

        final MediaPlayer kaixo = MediaPlayer.create(Agurra.this, R.raw.clipagurra);
        kaixo.start();

<<<<<<< HEAD
=======
        idLayoutAgurra = findViewById(R.id.idLayoutAgurra);
        idLayoutAgurra.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
             Intent i =new Intent (getBaseContext(),MainActivity.class);
             kaixo.stop();
             startActivity(i);}
     }
        );
int karma = 1;
>>>>>>> 575573f208fca07b9677769aaa7b99a0c0383021
    }
}
