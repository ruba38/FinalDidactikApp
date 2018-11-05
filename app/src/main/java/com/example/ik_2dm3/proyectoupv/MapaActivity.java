package com.example.ik_2dm3.proyectoupv;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MapaActivity extends Activity {
    Dialog puntoPopup;
    Button idBtnMapaAjustes,idBtnMapaPunto1;
    Boolean terminado=false;//cuando inicia el punto esta en falso
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        puntoPopup= new Dialog(this);

        //IR A AJUSTES        ########################################################
        idBtnMapaAjustes = (Button) findViewById(R.id.idBtnMapaAjustes);
        idBtnMapaAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AjustesActivity.class);
                startActivity(i);}
        });
        idBtnMapaPunto1 = (Button) findViewById(R.id.idBtnMapaPunto1);//declara boton punto1
        //mirar si el juego esta terminado
        if(terminado==true){
            idBtnMapaPunto1.setBackgroundResource(R.drawable.completado);//cambia icono boton de exclamacion a tik
        }

    }
    public void ShowPopup(View v) {
        //DECLARARCIONES
        Button idBtnPopupCerrar,idBtnPopupJugar;

        //MOSTRAR POPUP        ######################################################
        puntoPopup.setContentView(R.layout.popup_punto);//abrir layout que contiene el popup

        //JUGAR POPUP
        idBtnPopupJugar = (Button) puntoPopup.findViewById(R.id.idBtnPopupJugar);
        idBtnPopupJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                puntoPopup.dismiss();//oculta el popup
                Intent i =new Intent (getBaseContext(),Actividad_1_Udaletxea.class);
                startActivity(i);
                
            }
        });
        //CERRAR POPUP
        idBtnPopupCerrar = (Button) puntoPopup.findViewById(R.id.idBtnPopupCerrar);//declarar boton cerrar del popup
        idBtnPopupCerrar.setOnClickListener(new View.OnClickListener() {//al dar click se ejecuta esta funcion
            @Override
            public void onClick(View v) {
                puntoPopup.dismiss();//oculta el popup
            }
        });

        puntoPopup.show();//mostar popup


    }


    }

