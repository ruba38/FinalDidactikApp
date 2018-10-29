package com.example.ik_2dm3.proyectoupv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AjustesActivity extends Activity {
    private Button idBtnAjustesCerrar,idBtnAjustesCreadores,idBtnAjustesSalir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        //CERRAR       ######################################################
        idBtnAjustesCerrar = (Button) findViewById(R.id.idBtnAjustesCerrar);
        idBtnAjustesCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();}

        });

        //IR A CREADORES        ######################################################
        idBtnAjustesCreadores = (Button) findViewById(R.id.idBtnAjustesCreadores);
        idBtnAjustesCreadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), CreadoresActivity.class);
                startActivity(i);}
        });
    }

}
