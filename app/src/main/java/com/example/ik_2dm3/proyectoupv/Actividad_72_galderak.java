package com.example.ik_2dm3.proyectoupv;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Actividad_72_galderak extends AppCompatActivity {

    public Dialog popupgaldera;
    private Dialog back;
    private Button atras, salir;
    public ImageView galderafondoa1, galderafondoa2, osoondofondoa;
    int idPuntoJuego;
    public Button btng1, btng2,btng3,btnNext,btnNextF;
public TextView textoPopupGeneral;
    private Toast toast;
    String textua, piccaso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("ppppppppppppppppp0","000");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_72_galderak);
        //ocultar barras extras
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        idPuntoJuego = getIntent().getIntExtra("idPuntoJuego", 0);
        popupgaldera= new Dialog(this);
        popupgaldera.setContentView(R.layout.popup_galdera1);
        popupgaldera.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btng1=(Button) popupgaldera.findViewById(R.id.btng1);
        btng2=(Button) popupgaldera.findViewById(R.id.btng2);
        btng3=(Button) popupgaldera.findViewById(R.id.btng3);
        btnNext=(Button) popupgaldera.findViewById(R.id.btnNext);
        btnNextF=(Button) popupgaldera.findViewById(R.id.btnNextF);
        textoPopupGeneral=(TextView) popupgaldera.findViewById(R.id.textoPopupGeneral);

        btng1.setText("PABLO PICCASO");
        btng2.setText("ARÓSTEGUI BARBIER");
        btng3.setText("LEONARDO DA VINCI");
        btnNext.setVisibility(View.GONE);
        btnNextF.setVisibility(View.GONE);
        //NO PERMITIR QUE AL TOCAR FUERA DEL MISMO SE CIERRE
        popupgaldera.setCanceledOnTouchOutside(false);

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
    }
    int correcto=0;
    protected void onStart(){
        super.onStart();

        popupgaldera.show();
        btng2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(correcto==0) {
                    btng2.setBackground(getDrawable(R.drawable.erroneo));
                    btng1.setBackground(getDrawable(R.drawable.pregunta));
                    btng3.setBackground(getDrawable(R.drawable.pregunta));
                }
            }
        });
        btng1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(correcto==0) {
                    btng1.setBackground(getDrawable(R.drawable.correcto));
                    btng2.setBackground(getDrawable(R.drawable.pregunta));
                    btng3.setBackground(getDrawable(R.drawable.pregunta));
                    correcto=1;
                    btnNext.setVisibility(View.VISIBLE);
                }
            }
        });
        btng3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(correcto==0) {
                        btng3.setBackground(getDrawable(R.drawable.erroneo));
                        btng1.setBackground(getDrawable(R.drawable.pregunta));
                        btng2.setBackground(getDrawable(R.drawable.pregunta));
                    }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(correcto==1) {
                textoPopupGeneral.setText("Bereizten al duzue animaliarik? Eta pertsonarik? Zer uste duzue esan nahiko duela?");
                    btng3.setVisibility(View.GONE);
                    btng1.setVisibility(View.GONE);
                    btng2.setVisibility(View.GONE);
                    correcto=2;
                }else if(correcto==2) {
                    textoPopupGeneral.setText("Argazkia atera !!");
                    btnNext.setBackground(getDrawable(R.drawable.camaraverdepnj));
                    btnNext.setVisibility(View.GONE);
                    btnNextF.setVisibility(View.VISIBLE);

                }
            }
        });
        btnNextF.setOnClickListener(new View.OnClickListener() {
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
    public void onBackPressed() {
        //your code when back button pressed
        back.show();
    }
}
