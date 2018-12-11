package com.example.ik_2dm3.proyectoupv;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class Actividad_32_Precios extends Activity {
    //Definiciones
    private ImageView Repetir;
    public static TextView KipulaZelda;
    public static TextView AzenarioZelda;
    public static TextView IndabaZelda;
    public static TextView TxorizoZelda;
    public static TextView Total;
    public static ImageView BotonCamara;
    public static double TotalN;
    public static double KipulaN;
    public static double AzenarioN;
    public static double IndabakN;
    public static double TxorizoN;
    public String imagePath;
    public int FotoCatch;
    int idPuntoJuego;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_32__precio);
        idPuntoJuego=getIntent().getIntExtra("idPuntoJuego",0);
        KipulaZelda = findViewById(R.id.KipulapPrezio);
        AzenarioZelda = findViewById(R.id.AzenarioPrezio);
        IndabaZelda = findViewById(R.id.IndabakPrezio);
        TxorizoZelda = findViewById(R.id.TxorizoPrezio);
        Total = findViewById(R.id.Total);
        Total.setText(String.valueOf(TotalN));
        //Se agregan al changedListener las celdas
        KipulaZelda.addTextChangedListener(new TextWatcherUsoMercado(KipulaZelda));
        AzenarioZelda.addTextChangedListener(new TextWatcherUsoMercado(AzenarioZelda));
        IndabaZelda.addTextChangedListener(new TextWatcherUsoMercado(IndabaZelda));
        TxorizoZelda.addTextChangedListener(new TextWatcherUsoMercado(TxorizoZelda));

            //Boton Camara

            BotonCamara = findViewById(R.id.BottonCamaraPrecios);
        BotonCamara.setVisibility(View.INVISIBLE);

        BotonCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SacarFonto= new Intent(getBaseContext(), SacarFotos.class);
                SacarFonto.putExtra("idPuntoJuego",idPuntoJuego);
                //Camara
                startActivityForResult(SacarFonto, 31);
                finish();


            }
        });
    }
    public static void CambiarTotal(){
        try {
            //Se leeran los textos y seran convertidos a Double
            KipulaN = Double.valueOf(String.valueOf(KipulaZelda.getText()));
            AzenarioN = Double.valueOf(String.valueOf(AzenarioZelda.getText()));
            IndabakN = Double.valueOf(String.valueOf(IndabaZelda.getText()));
            TxorizoN = Double.valueOf(String.valueOf(TxorizoZelda.getText()));
            //Se calculara el cosete deacuerdo a la cantidad en gramos por el precio de alimento en por kilos
            TotalN = KipulaN*(0.4) + AzenarioN*(0.08) + IndabakN*(0.4) + TxorizoN*(0.1);
        }catch (Exception e){
            Log.d("Error","Error");
        }
        Total.setText("Prezio Totala:"+String.format("%.2f",TotalN));
                BotonCamara.setVisibility(View.VISIBLE);

    }

    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 31) {
            if (resultCode==RESULT_OK) {
                Intent hori = new Intent(getBaseContext(), horidata.class);
                startActivityForResult(hori,32);
                String mywebsite = (String) data.getExtras().get("result");
            }
        }
        if(requestCode==32){
            finish();
        }
}
}

