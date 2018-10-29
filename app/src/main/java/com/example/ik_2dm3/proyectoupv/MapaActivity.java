package com.example.ik_2dm3.proyectoupv;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MapaActivity extends Activity {
    Dialog puntoPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        puntoPopup= new Dialog(this);


    }
    public void ShowPopup(View v) {
        //DECLARARCIONES
        Button idBtnPopupCerrar,idBtnMapaPunto1;
        Boolean terminado=false;//cuando inicia el punto esta en falso
        //MOSTRAR POPUP        ######################################################
        puntoPopup.setContentView(R.layout.popup_punto);//abrir layout que contiene el popup
        idBtnPopupCerrar = (Button) puntoPopup.findViewById(R.id.idBtnPopupCerrar);//declarar boton cerrar del popup
        idBtnPopupCerrar.setOnClickListener(new View.OnClickListener() {//al dar click se ejecuta esta funcion
            @Override
            public void onClick(View v) {
                puntoPopup.dismiss();//oculta el popup
            }
        });
        puntoPopup.show();//mostar popup
        idBtnMapaPunto1 = (Button) findViewById(R.id.idBtnMapaPunto1);//declara boton punto1
        //mirar si el juego esta terminado
        if(terminado==true){
            idBtnMapaPunto1.setBackgroundResource(R.drawable.completado);//cambia icono boton de exclamacion a tik
        }
    }


    }

