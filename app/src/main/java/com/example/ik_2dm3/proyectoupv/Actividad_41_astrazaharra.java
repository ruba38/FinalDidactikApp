package com.example.ik_2dm3.proyectoupv;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Actividad_41_astrazaharra extends AppCompatActivity {
    public View fondo41;
    public ImageView ast, sir1, sir2, sir3, sir4, sir5;
    public TextView pr;
    public int c1=0,c2=0,c3=0,c4=0,c5=0,he,wi;
    public double c1x1,c1x2,c1y1,c1y2,c2x1,c2x2,c2y1,c2y2,c3x1,c3x2,c3y1,c3y2,c4x1,c4x2,c4y1,c4y2,c5x1,c5x2,c5y1,c5y2;
    int idPuntoJuego;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_41_astrazaharra);
        getSupportActionBar().hide();
        idPuntoJuego = getIntent().getIntExtra("idPuntoJuego", 0);
        ast = findViewById(R.id.astrazar);
        sir1 = findViewById(R.id.sir1);
        sir2 = findViewById(R.id.sir2);
        sir3 = findViewById(R.id.sir3);
        sir4 = findViewById(R.id.sir4);
        sir5 = findViewById(R.id.sir5);
        pr = findViewById(R.id.prueba);
        sir1.setVisibility(View.INVISIBLE);
        sir2.setVisibility(View.INVISIBLE);
        sir3.setVisibility(View.INVISIBLE);
        sir4.setVisibility(View.INVISIBLE);
        sir5.setVisibility(View.INVISIBLE);


        c1x1 = 40.5;
        c1x2 = 48.4;
        c1y1 = 67.6;
        c1y2 = 76.1;
        c2x1 = 79.4;
        c2x2 = 84.4;
        c2y1 = 80;
        c2y2 = 85.1;
        c3x1 = 87.8;
        c3x2 = 97.5;
        c3y1 = 53.2;
        c3y2 = 61.1;
        c4x1 = 62;
        c4x2 = 68.9;
        c4y1 = 66.2;
        c4y2 = 72.3;
        c5x1 = 58.7;
        c5x2 = 64;
        c5y1 = 88.8;
        c5y2 = 94.1;
    }
    protected void onStart(){
        super.onStart();
        ast.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                he=ast.getHeight();
                wi=ast.getWidth();
                // pr.setText(String.valueOf(event.getX()) + "x" + String.valueOf(event.getY())+"tamaño:"+wi+"/"+he+"cord:"+wi*(c1x1/100)+"-"+wi*(c1x2/100)+"/"+he*(c1y1/100)+"-"+he*(c1y2/100));
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
                if(c1==1&&c2==1&&c3==1&&c4==1&&c5==1) {
                    c1=0;c2=0;c3=0;c4=0;c5=0;
                    Intent i = new Intent(getBaseContext(), Actividad_42_astraberria.class);
                    i.putExtra("idPuntoJuego",idPuntoJuego);
                    startActivityForResult(i,10);
                }
                return true;
            }});
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10){

            Actividad_41_astrazaharra.this.finish();

        }
    }

}
