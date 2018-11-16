package com.example.ik_2dm3.proyectoupv;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.Image;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Actividad_2_Drag extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_2__drag);
        final ImageView ImagenDibujar = findViewById(R.id.ImagenDibujar);
       final TextView Prueba = findViewById(R.id.Prueba);
       Canvas bit = new Canvas();

        ImagenDibujar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Prueba.setText("Touch coordinates : " +
                        String.valueOf(event.getX()) + "x" + String.valueOf(event.getY()));
               // onDraw();
                return true;
            }});

    }
    protected void onDraw(Canvas canvas){
      //  super.onDraw(canvas);
        canvas.drawColor(Color.blue(1));

        Rect rectangulo = new Rect();
        rectangulo.set(0,200,canvas.getWidth(),350);
        Paint fondo = new Paint();
        fondo.setColor(Color.BLUE);
        canvas.drawRect(rectangulo,fondo);

    }

}

