package com.example.ik_2dm3.proyectoupv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Actividad_42_astraberria extends AppCompatActivity {
    public View fondo41;
    public ImageView ast, sir1, sir2, sir3, sir4, sir5;
    public TextView pr;
    public int c1,c2,c3,c4,c5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_42_astraberria);
        getSupportActionBar().hide();
        ast= findViewById(R.id.astrazar);
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
        ast.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                /*pr.setText("Touch coordinates : " +
                        String.valueOf(event.getX()) + "x" + String.valueOf(event.getY()));*/
                if(event.getX()>450.0000&&event.getX()<500.0000&&event.getY()>950.0000&&event.getY()<1100.0000){
                    sir1.setVisibility(View.VISIBLE);
                    c1=1;
                }
                if(event.getX()>945.0000&&event.getX()<977.0000&&event.getY()>1177.0000&&event.getY()<1212.0000){
                    sir2.setVisibility(View.VISIBLE);
                    c2=1;
                }
                if(event.getX()>1020.0000&&event.getX()<1093.0000&&event.getY()>779.0000&&event.getY()<856.0000){
                    sir3.setVisibility(View.VISIBLE);
                    c3=1;
                }
                if(event.getX()>722.0000&&event.getX()<804.0000&&event.getY()>949.0000&&event.getY()<1071.0000){
                    sir4.setVisibility(View.VISIBLE);
                    c4=1;
                }
                if(event.getX()>673.0000&&event.getX()<727.0000&&event.getY()>1288.0000&&event.getY()<1347.0000){
                    sir5.setVisibility(View.VISIBLE);
                    c5=1;
                }
                if(c1==1&&c2==1&&c3==1&&c4==1&&c5==1){
                    sir1.setVisibility(View.INVISIBLE);
                    sir2.setVisibility(View.INVISIBLE);
                    sir3.setVisibility(View.INVISIBLE);
                    sir4.setVisibility(View.INVISIBLE);
                    sir5.setVisibility(View.INVISIBLE);
                }
                return true;
            }});

    }
}
