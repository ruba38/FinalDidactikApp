package com.example.ik_2dm3.proyectoupv;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Actividad_51_Botaketa extends AppCompatActivity {
    private TextView EgindakoBotoak;
    private ImageView BotatuEman;
    private int ContadorVotos;
    private EditText AgregarNombre;
    private String Fallo ="@string/OrdescariarenIzena";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_51__botaketa);

        //ocultar barras extras
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        EgindakoBotoak = findViewById(R.id.TexEgindakobotoak);
        EgindakoBotoak.setText("Egindako Botoak : 0");
        BotatuEman = findViewById(R.id.Carta);
        AgregarNombre = findViewById(R.id.Agregartexto);
        BotatuEman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AgregarNombre.getText().toString() != Fallo ){
                    ContadorVotos++;
                    EgindakoBotoak.setText("Egindako Botoak : " + ContadorVotos);
                }

            }
        });

    }
}


