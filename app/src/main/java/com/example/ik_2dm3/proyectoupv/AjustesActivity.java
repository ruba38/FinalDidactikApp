package com.example.ik_2dm3.proyectoupv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;

public class AjustesActivity extends AppCompatActivity {
    private Button idBtnAjustesCerrar,idBtnAjustesCreadores,idBtnAjustesSalir;
    private CheckBox Admin;
    int opcionAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        //ocultar barras extras
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Sonido

        //Admin.Mod
        Admin = findViewById(R.id.ChekBoxAdminMod);
        //CERRAR
        idBtnAjustesCerrar = (Button) findViewById(R.id.idBtnAjustesCerrar);
        idBtnAjustesCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Admin.isChecked()){
                    opcionAdmin = 1;
                   // Log.d("ajustes","Entra en admin cheked///////////////////////////////////////////////////////");

                }
              //  Intent i = new Intent(getBaseContext(),MapaActivity.class);
              //  Log.d("ajustes2","Entra en admin ///////////////////////////////////////////////////////");
              //  int idLugar = getIntent().getIntExtra("idLugar",1);
               // i.putExtra("Admin",opcionAdmin);
               // startActivityForResult(i,1);
               finish();
            }
        });
        //IR A CREADORES
        idBtnAjustesCreadores = (Button) findViewById(R.id.idBtnAjustesCreadores);
        idBtnAjustesCreadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), CreadoresActivity.class);
                startActivity(i);}
        });
        //SALIR
        idBtnAjustesSalir = (Button) findViewById(R.id.idBtnAjustesSalir);
        idBtnAjustesSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), Agurra.class);
                startActivity(i);}
        });

    }
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
        finish();
        }
    }

}
