package com.example.ik_2dm3.proyectoupv;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import static com.example.ik_2dm3.proyectoupv.R.*;

public class Actividad_10_Udaletxea extends AppCompatActivity {
    private Button idBottonRepetir_A1_udaletxea;
    private ImageView botoncamara;
    int idPuntoJuego;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_actividad_1__udaletxea);
        getSupportActionBar().hide();
        //ocultar barras extras
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        idPuntoJuego = getIntent().getIntExtra("idPuntoJuego", 0);
        //boton camara
        botoncamara = findViewById(R.id.Botoncamara);
        botoncamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SacarFonto = new Intent(getBaseContext(), SacarFotos.class);
                SacarFonto.putExtra("idPuntoJuego", idPuntoJuego);
                //Camara
                startActivityForResult(SacarFonto, 31);
                finish();


            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 100) {
                if (resultCode==RESULT_OK)
                Actividad_10_Udaletxea.this.finish();
                Intent horidata= new Intent(getBaseContext(),horidata.class);
                startActivityForResult(horidata,102);
                finish();
            }
        }
    }