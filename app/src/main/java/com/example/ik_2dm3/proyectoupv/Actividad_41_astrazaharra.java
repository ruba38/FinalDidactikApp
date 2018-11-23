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
    public int c1,c2,c3,c4,c5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_41_astrazaharra);
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
               /* pr.setText("Touch coordinates : " +
                        String.valueOf(event.getX()) + "x" + String.valueOf(event.getY()));*/
                if(event.getX()>437.0000&&event.getX()<523.0000&&event.getY()>855.0000&&event.getY()<962.0000){
                    sir1.setVisibility(View.VISIBLE);
                    c1=1;
                }
                if(event.getX()>869.0000&&event.getX()<921.0000&&event.getY()>1012.0000&&event.getY()<1075.0000){
                    sir2.setVisibility(View.VISIBLE);
                    c2=1;
                }
                if(event.getX()>948.0000&&event.getX()<1053.0000&&event.getY()>672.0000&&event.getY()<772.0000){
                    sir3.setVisibility(View.VISIBLE);
                    c3=1;
                }
                if(event.getX()>670.0000&&event.getX()<744.0000&&event.getY()>837.0000&&event.getY()<914.0000){
                    sir4.setVisibility(View.VISIBLE);
                    c4=1;
                }
                if(event.getX()>634.0000&&event.getX()<691.0000&&event.getY()>1123.0000&&event.getY()<1189.0000){
                    sir5.setVisibility(View.VISIBLE);
                    c5=1;
                }
                if(c1==1&&c2==1&&c3==1&&c4==1&&c5==1) {
                    Intent i = new Intent(getBaseContext(), Actividad_42_astraberria.class);
                    startActivity(i);
                }
                return true;
            }});
    }



}
