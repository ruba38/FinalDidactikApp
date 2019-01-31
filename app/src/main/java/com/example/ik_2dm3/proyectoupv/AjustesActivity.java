package com.example.ik_2dm3.proyectoupv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class AjustesActivity extends AppCompatActivity {
    private Button idBtnAjustesCerrar,idBtnAjustesCreadores,idBtnAjustesSalir;
    private CheckBox Admin;
    private Switch sound;
    int opcionAdmin;
    private DatabaseAccess databaseAccess;
    private ImageView logotxur;
    private int contador=0,adminEstate;
    private TextView textView10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        databaseAccess = new DatabaseAccess(this);
        Toast toast1 = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);

        adminEstate=databaseAccess.getAdmin();

        //ocultar barras extras
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        textView10= (TextView) findViewById(R.id.textView10);
        //Sonido
        sound = (Switch) findViewById(R.id.sound);
        if(databaseAccess.getSonido()==1)
            sound.setChecked(true);
        else
            sound.setChecked(false);
        sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                databaseAccess.sonido(sound.isChecked());
                Log.d("mytag","sonido: " + sound.isChecked());
            }
        });
        //Admin.Mod
        //Admin = findViewById(R.id.ChekBoxAdminMod);
        textView10.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(contador>=5&&adminEstate==0){
                    databaseAccess.setAdmin(adminEstate+1);
                    toast1.setText("Modo admin activado");
                    toast1.show();
                    adminEstate=1;

                }else if(adminEstate==1){
                    databaseAccess.setAdmin(adminEstate-1);
                    toast1.setText("Modo admin desactivado");
                    toast1.show();
                    adminEstate=0;

                }else{
                    contador++;
                }

                return false;
            }
        });




        //CERRAR
        idBtnAjustesCerrar = (Button) findViewById(R.id.idBtnAjustesCerrar);
        idBtnAjustesCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
