package com.example.ik_2dm3.proyectoupv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Actividad_42_astraberria extends AppCompatActivity {
    public View fondo42;
    public ImageView astraber, sir1, sir2, sir3, sir4, sir5;
    public TextView pr;
    public int c1=0,c2=0,c3=0,c4=0,c5=0,he,wi;
    public double c1x1,c1x2,c1y1,c1y2,c2x1,c2x2,c2y1,c2y2,c3x1,c3x2,c3y1,c3y2,c4x1,c4x2,c4y1,c4y2,c5x1,c5x2,c5y1,c5y2;
    int idPuntoJuego;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_42_astraberria);
        getSupportActionBar().hide();
        idPuntoJuego=getIntent().getIntExtra("idPuntoJuego",0);
        astraber= findViewById(R.id.astraber);
        sir1= findViewById(R.id.sir1);
        sir2= findViewById(R.id.sir2);
        sir3= findViewById(R.id.sir3);
        sir4= findViewById(R.id.sir4);
        sir5= findViewById(R.id.sir5);
        pr = findViewById(R.id.prueba);
        sir1.setVisibility(View.INVISIBLE);
        sir2.setVisibility(View.INVISIBLE);
        sir3.setVisibility(View.INVISIBLE);
        sir4.setVisibility(View.INVISIBLE);
        sir5.setVisibility(View.INVISIBLE);
        he=astraber.getWidth();
        wi=astraber.getHeight();
        c1x1=79.9;c1x2=93;c1y1=51.6;c1y2=62.1;
        c2x1=83.1;c2x2=97.8;c2y1=64.7;c2y2=75.4;
        c3x1=69;c3x2=73;c3y1=69.4;c3y2=74.9;
        c4x1=32.8;c4x2=45.6;c4y1=58.7;c4y2=68.4;
        c5x1=17.5;c5x2=28;c5y1=93.7;c5y2=98.5;
        astraber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                /*;*/
                he=astraber.getHeight();
                wi=astraber.getWidth();
                /*pr.setText("Touch coordinates : " +
                        String.valueOf(event.getX()) + "x" + String.valueOf(event.getY())+"tamaño:"+wi+"/"+he);*/
                if(event.getX()>(wi*(c1x1/100))&&event.getX()<(wi*(c1x2/100))&&event.getY()>(he*(c1y1/100))&&event.getY()<(he*(c1y2/100))){
                    sir1.setVisibility(View.VISIBLE);
                    c1=1;
                }
                if(event.getX()>(wi*(c2x1/100))&&event.getX()<(wi*(c2x2/100))&&event.getY()>(he*(c2y1/100))&&event.getY()<(he*(c2y2/100))){
                    sir2.setVisibility(View.VISIBLE);
                    c2=1;
                }
                if(event.getX()>(wi*(c3x1/100))&&event.getX()<(wi*(c3x2/100))&&event.getY()>(he*(c3y1/100))&&event.getY()<(he*(c3y2/100))){
                    sir3.setVisibility(View.VISIBLE);
                    c3=1;
                }
                if(event.getX()>(wi*(c4x1/100))&&event.getX()<(wi*(c4x2/100))&&event.getY()>(he*(c4y1/100))&&event.getY()<(he*(c4y2/100))){
                    sir4.setVisibility(View.VISIBLE);
                    c4=1;
                }
                if(event.getX()>(wi*(c5x1/100))&&event.getX()<(wi*(c5x2/100))&&event.getY()>(he*(c5y1/100))&&event.getY()<(he*(c5y2/100))){
                    sir5.setVisibility(View.VISIBLE);
                    c5=1;
                }
                if(c1==1&&c2==1&&c3==1&&c4==1&&c5==1){
                    c1=0;c2=0;c3=0;c4=0;c5=0;
                    DatabaseAccess databaseAccess = new DatabaseAccess(getBaseContext());
                    Intent i = new Intent(getBaseContext(), SacarFotos.class);
                    i.putExtra("idPuntoJuego",idPuntoJuego);
                    startActivityForResult(i,10);

                }
                return true;
            }});

    }
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==10)
                Actividad_42_astraberria.this.finish();
    }
}
