package com.example.ik_2dm3.proyectoupv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Actividad_52_Resultados extends AppCompatActivity {

    private Button sacarFoto;
    private TextView textoGanador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_52__resultados);

        //ocultar barras extras
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        sacarFoto = (Button)findViewById(R.id.btnCamara);
        textoGanador = (TextView) findViewById(R.id.textoGanador);

        String nombre = getIntent().getStringExtra("nombre");
        int numVotos = getIntent().getIntExtra("votos", 0);


        textoGanador.setText("El ganador es\n"+nombre);

        Log.d("resultados", "ER: "+nombre);
        Log.d("resultados", "RR: "+numVotos);

        sacarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), SacarFotos.class);
                startActivityForResult(i, 1);
            }
        });
    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        finish();
    }
}
