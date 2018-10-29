package com.example.ik_2dm3.proyectoupv;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public boolean admin=false;
    private Button idBtnMainInicio,idBtnMainCreadores,idBtnMainAjustes;
    int contador=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //IR A MAPA        ######################################################
        idBtnMainInicio = (Button) findViewById(R.id.idBtnMainInicio);
        idBtnMainInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MapaActivity.class);
                startActivity(i);}
        });

        //IR A CREADORES        ######################################################
        idBtnMainCreadores = (Button) findViewById(R.id.idBtnMainCreadores);
        idBtnMainCreadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), CreadoresActivity.class);
                startActivity(i);}
        });

        //IR A AJUSTES        ########################################################
        idBtnMainAjustes = (Button) findViewById(R.id.idBtnMainAjustes);
        idBtnMainAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AjustesActivity.class);
                startActivity(i);}
        });
    }
    public void QuitarGPS(View v) {
     contador ++;
     if(contador==6){
         if(admin==false){
             admin=true;
             contador=0;
             Toast.makeText(getBaseContext(),"Admin = True", Toast.LENGTH_SHORT).show();
         }else{
             admin=false;
             contador=0;
             Toast.makeText(getBaseContext(),"Admin = False", Toast.LENGTH_SHORT).show();
         }
     }

    }
}
