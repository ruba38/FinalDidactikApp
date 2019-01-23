package com.example.ik_2dm3.proyectoupv;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Actividad_51_Botaketa extends AppCompatActivity {
    private TextView EgindakoBotoak;
    private ImageView BotatuEman;
    private int ContadorVotos,idPuntoJuego;
    private Dialog back;
    private Button atras, salir;

    private EditText AgregarNombre;
    private String Fallo ="@string/OrdescariarenIzena";
    private Button btnResultados;
    private boolean votado = false;

    private ArrayList<JuegoPuzzleParticipantes> nombres = new ArrayList<JuegoPuzzleParticipantes>();
    private ArrayList<JuegoPuzzleParticipantes> nomRepetidos = new ArrayList<JuegoPuzzleParticipantes>();
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
        btnResultados = findViewById(R.id.btnResultados);

        back = new Dialog(this);
        back.setContentView(R.layout.atras);
        back.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        atras= (Button) back.findViewById(R.id.botreniciar);
        salir= (Button) back.findViewById(R.id.botsalir);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.dismiss();
                Intent i = new Intent(getBaseContext(), Presentaciones.class );
                i.putExtra("idPuntoJuego",idPuntoJuego);
                startActivityForResult(i, 10);
                finish();
            }
        });
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.dismiss();

                finish();
            }
        });
        AgregarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AgregarNombre.setText("");

            }
        });

        AgregarNombre.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    handled = true;
                }
                return handled;
            }
        });

        BotatuEman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            if (AgregarNombre.getText().toString() != Fallo && AgregarNombre.length() > 1 && !AgregarNombre.getText().toString().equals("Ordescariaren Izena")){
                    ContadorVotos++;
                    JuegoPuzzleParticipantes participante = new JuegoPuzzleParticipantes(AgregarNombre.getText().toString().toLowerCase());

                    if(nombres.contains(participante)) {
                        participante = nombres.get(nombres.indexOf(participante));
                        participante.setNumVotos(participante.getNumVotos()+1);
                        nombres.set(nombres.indexOf(participante), participante);
                    } else {
                        nombres.add(participante);
                    }
                    votado = true;

                    EgindakoBotoak.setText("Egindako Botoak : " + ContadorVotos);

                }
            }
        });

        btnResultados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nombres.isEmpty()) {
                    return;
                }

                JuegoPuzzleParticipantes ganador = new JuegoPuzzleParticipantes();
                int votoGanador = 0;
                for(int y = 0; y < nombres.size(); y++) {
                    JuegoPuzzleParticipantes p2 = new JuegoPuzzleParticipantes(nombres.get(y));
                    if(p2.getNumVotos() > votoGanador) {
                        ganador = new JuegoPuzzleParticipantes(p2);
                        votoGanador = ganador.getNumVotos();
                    }
                }

                Intent i = new Intent(getBaseContext(), Actividad_52_Resultados.class);
                i.putExtra("nombre", ganador.getNombre());
                i.putExtra("votos", ganador.getNumVotos());
                startActivityForResult(i, 1);
            }
        });

    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        finish();
    }
    @Override
    public void onBackPressed() {
        //your code when back button pressed
        back.show();
    }
}


