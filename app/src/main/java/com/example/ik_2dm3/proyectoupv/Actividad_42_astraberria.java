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
    public int c1,c2,c3,c4,c5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_42_astraberria);
        getSupportActionBar().hide();
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
        astraber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                /*pr.setText("Touch coordinates : " +
                        String.valueOf(event.getX()) + "x" + String.valueOf(event.getY()));*/
                if(event.getX()>863.0000&&event.getX()<999.0000&&event.getY()>792.0000&&event.getY()<952.0000){
                    sir1.setVisibility(View.VISIBLE);
                    c1=1;
                    Log.e("mytag","en5tra");
                }
                if(event.getX()>898.0000&&event.getX()<1056.0000&&event.getY()>993.0000&&event.getY()<1156.0000){
                    sir2.setVisibility(View.VISIBLE);
                    c2=1;
                }
                if(event.getX()>745.0000&&event.getX()<788.0000&&event.getY()>1065.0000&&event.getY()<1149.0000){
                    sir3.setVisibility(View.VISIBLE);
                    c3=1;
                }
                if(event.getX()>354.0000&&event.getX()<493.0000&&event.getY()>901.0000&&event.getY()<1049.0000){
                    sir4.setVisibility(View.VISIBLE);
                    c4=1;
                }
                if(event.getX()>189.0000&&event.getX()<302.0000&&event.getY()>1437.0000&&event.getY()<1511.0000){
                    sir5.setVisibility(View.VISIBLE);
                    c5=1;
                }
                if(c1==1&&c2==1&&c3==1&&c4==1&&c5==1){
                    Intent i = new Intent(getBaseContext(), Agurra.class);
                    startActivity(i);
                }
                return true;
            }});

    }
}
