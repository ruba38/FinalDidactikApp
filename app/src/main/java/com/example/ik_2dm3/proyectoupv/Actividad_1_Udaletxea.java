package com.example.ik_2dm3.proyectoupv;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import static com.example.ik_2dm3.proyectoupv.R.*;

public class Actividad_1_Udaletxea extends AppCompatActivity {
    private Button idBottonRepetir_A1_udaletxea;
    private Button idBottonRepetirAudio_A1_udaletxea;
    private ImageView botoncamara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_actividad_1__udaletxea);
        getSupportActionBar().hide();
        //Audio
        final MediaPlayer oihaltxo = MediaPlayer.create(Actividad_1_Udaletxea.this, raw.klipdontello);
        oihaltxo.start();
        //boton camara
        botoncamara = findViewById(R.id.Botoncamara);
        botoncamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent camara= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            oihaltxo.release();

            startActivityForResult(camara, 100);


            }
        });

        //Boton repetir
        idBottonRepetir_A1_udaletxea = (Button) findViewById(R.id.idBottonRepetir_A1_udaletxea);
        idBottonRepetir_A1_udaletxea.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                oihaltxo.release();
                finish();
            }
        });
        //boton Repetir audio
        idBottonRepetirAudio_A1_udaletxea = (Button) findViewById(R.id.idBottonRepetirAudio_A1_udaletxea);
        idBottonRepetirAudio_A1_udaletxea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oihaltxo.seekTo(0);
                Log.d("sdad", String.valueOf(oihaltxo.isPlaying()));
                if(oihaltxo.isPlaying()==false){
                    oihaltxo.start();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 100) {
                if (resultCode==RESULT_OK)
                Actividad_1_Udaletxea.this.finish();
                Intent horidata= new Intent(getBaseContext(),horidata.class);
                startActivityForResult(horidata,102);
                finish();
            }
        }
    }