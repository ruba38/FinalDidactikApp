package com.example.ik_2dm3.proyectoupv;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static java.lang.Thread.sleep;

public class Actividad_61_GernikaArbola extends AppCompatActivity {

    int idPuntoJuego;
    public ImageView pikondoa, pagoa, pinua, haritza, popupfondob, popupfondom;
    public Dialog popuparbolm, popuparbolb,popupgeneral;
    private Dialog back;
    private Button atras, salir;
    boolean hoja=false;
    private TextView textoPopupGeneral;
   // private ImageView imagenclic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_61__gernika_arbola);

        //ocultar barras extras
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        idPuntoJuego = getIntent().getIntExtra("idPuntoJuego", 0);
        pikondoa = (ImageView) findViewById(R.id.pikondoai);
        haritza = (ImageView) findViewById(R.id.haritzai);
        pinua = (ImageView) findViewById(R.id.pinuai);
        pagoa = (ImageView) findViewById(R.id.pagoai);


        popupgeneral= new Dialog(this);
        popupgeneral.setContentView(R.layout.popup_general);
        textoPopupGeneral= popupgeneral.findViewById(R.id.textoPopupGeneral);

        popupgeneral.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        textoPopupGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupgeneral.dismiss();
                if (hoja){
                    Intent i = new Intent(getBaseContext(), SacarFotos.class );
                    i.putExtra("idPuntoJuego",idPuntoJuego);
                    startActivityForResult(i, 11);
                    popupgeneral.dismiss();


                }else{
                    popupgeneral.dismiss();
                }
            }
        });

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
    protected void onStart(){
        super.onStart();


        pikondoa.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupgeneral.show();
                    hoja=false;
                    comprobar(hoja);
                return true;
            }
        });
        haritza.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupgeneral.show();
                 hoja=true;
                comprobar(hoja);

                return true;
            }
        });
        pinua.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupgeneral.show();
                 hoja=false;
                comprobar(hoja);
                return true;
            }
        });
        pagoa.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupgeneral.show();
                 hoja=false;
                comprobar(hoja);
                return true;
            }
        });

       /*popupfondom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupgeneral.dismiss();
                return false;
            }
        });*/




        /*popupgeneral.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupgeneral.dismiss();
                Intent i = new Intent(getBaseContext(), SacarFotos.class);
                i.putExtra("idPuntoJuego",idPuntoJuego);
                startActivityForResult(i, 10);
                finish();
                return false;
            }
        });*/
    }
    protected void comprobar(boolean hoja){

        if (hoja){
            textoPopupGeneral.setText("OSO ONDO");
            textoPopupGeneral.setTextColor(getColor(R.color.vc));

        }else{
            textoPopupGeneral.setText("ZAIATU BERRIRO");
            textoPopupGeneral.setTextColor(getColor(R.color.rc));
        }
    }
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11){
            finish();
            Actividad_61_GernikaArbola.this.finish();

        }
    }
    @Override
    public void onBackPressed() {
        //your code when back button pressed
        back.show();
    }
}
