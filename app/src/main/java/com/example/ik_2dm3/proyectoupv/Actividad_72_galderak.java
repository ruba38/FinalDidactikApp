package com.example.ik_2dm3.proyectoupv;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Actividad_72_galderak extends AppCompatActivity {
    public Dialog gal1, gal2, ondo;
    public ImageView galderafondoa1, galderafondoa2, osoondofondoa;
    int idPuntoJuego;
    public Button bot1, bot2;
    private EditText erantzuna;
    private Toast toast;
    String textua, piccaso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_72_galderak);
        idPuntoJuego = getIntent().getIntExtra("idPuntoJuego", 0);
        gal1= new Dialog(this);
        gal2= new Dialog(this);
        ondo= new Dialog(this);
        gal1.setContentView(R.layout.popup_galdera1);
        gal2.setContentView(R.layout.popup_galdera2);
        ondo.setContentView(R.layout.popup_ondo);
        gal1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        gal2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ondo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        osoondofondoa = (ImageView) ondo.findViewById(R.id.osoondofondoa);
        erantzuna = (EditText) gal1.findViewById(R.id.erantzuna);
        bot1= (Button) gal1.findViewById(R.id.gal1button);
        bot2 = (Button) gal2.findViewById(R.id.jarraitu);
        toast = Toast.makeText(getBaseContext(), "hori ez da erantzun egokia", Toast.LENGTH_SHORT);
        piccaso="Piccaso";
    }
    protected void onStart(){
        super.onStart();
        gal1.show();
        bot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textua=erantzuna.getText().toString();
                if (textua.equals(piccaso)){
                    gal1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    gal1.dismiss();
                    ondo.show();
                    setResult(Activity.RESULT_OK);
                }else{
                    toast.setText(erantzuna.getText().toString());
                    toast.show();
                }
            }
        });
        osoondofondoa.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ondo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                gal2.show();
                setResult(Activity.RESULT_CANCELED);
                return true;

            }
        });

        bot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gal2.dismiss();
                ondo.dismiss();
                Intent i = new Intent(getBaseContext(), SacarFotos.class);
                i.putExtra("idPuntoJuego",idPuntoJuego);
                startActivityForResult(i, 10);
                finish();
            }
        });
    }
}
