package com.example.ik_2dm3.proyectoupv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Actividad_41_astrazaharra extends AppCompatActivity {
    public View fondo41;
    public ImageView ast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_41_astrazaharra);
        getSupportActionBar().hide();
        ast= findViewById(R.id.astrazar);
        ast.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
              /*  Prueba.setText("Touch coordinates : " +
                        String.valueOf(event.getX()) + "x" + String.valueOf(event.getY()));*/

                return true;
            }});
    }
}
