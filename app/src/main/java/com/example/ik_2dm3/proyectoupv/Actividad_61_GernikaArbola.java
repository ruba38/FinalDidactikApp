package com.example.ik_2dm3.proyectoupv;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import static java.lang.Thread.sleep;

public class Actividad_61_GernikaArbola extends AppCompatActivity {

    int idPuntoJuego;
    public ImageView pikondoa, pagoa, pinua, haritza, popupfondob, popupfondom;
    public Dialog popuparbolm, popuparbolb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_61__gernika_arbola);
        idPuntoJuego = getIntent().getIntExtra("idPuntoJuego", 0);
        pikondoa = (ImageView) findViewById(R.id.pikondoai);
        haritza = (ImageView) findViewById(R.id.haritzai);
        pinua = (ImageView) findViewById(R.id.pinuai);
        pagoa = (ImageView) findViewById(R.id.pagoai);
        popuparbolm= new Dialog(this);
        popuparbolb= new Dialog(this);
        popuparbolm.setContentView(R.layout.popup_arbolm);
        popuparbolb.setContentView(R.layout.popup_arbolb);
        popuparbolm.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popuparbolb.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupfondob = (ImageView) popuparbolb.findViewById(R.id.fondoback);
        popupfondom = (ImageView) popuparbolm.findViewById(R.id.fondoback);
    }
    protected void onStart(){
        super.onStart();

        pikondoa.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popuparbolm.show();
                return true;
            }
        });
        haritza.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popuparbolb.show();


                return true;
            }
        });
        pinua.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popuparbolm.show();
                return true;
            }
        });
        pagoa.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popuparbolm.show();
                return true;
            }
        });
        popupfondom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupfondob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popuparbolm.dismiss();
                Intent i = new Intent(getBaseContext(), SacarFotos.class);
                i.putExtra("idPuntoJuego",idPuntoJuego);
                startActivityForResult(i, 10);
                finish();
                return false;
            }
        });
    }
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10){

            Actividad_61_GernikaArbola.this.finish();

        }
    }
}