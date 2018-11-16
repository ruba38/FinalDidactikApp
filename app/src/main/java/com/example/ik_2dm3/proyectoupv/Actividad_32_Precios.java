package com.example.ik_2dm3.proyectoupv;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Actividad_32_Precios extends Activity {
    public TextView KipulaZelda;
    public TextView AzenarioZelda;
    public TextView IndabaZelda;
    public TextView TxorizoZelda;
    public TextView Total;
     public double KipulaN;
    public double AzenarioN;
    public double IndabakN;
    public double TxorizoN;
    BucleCalcularTotal a = new BucleCalcularTotal(KipulaN, AzenarioN, IndabakN,TxorizoN);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_32__precio);
        KipulaZelda = findViewById(R.id.KipulapPrezio) ;
        AzenarioZelda = findViewById(R.id.AzenarioPrezio) ;
        IndabaZelda = findViewById(R.id.IndabakPrezio);
        TxorizoZelda = findViewById(R.id.TxorizoPrezio);



        Total = findViewById(R.id.Total);
        Handler CambiartextO = new Handler();
        CambiartextO.postDelayed(new Runnable(){
                                     @Override
                                     public void run() {
            try {
                KipulaN = Double.valueOf(KipulaZelda.getText().toString());
                AzenarioN = Double.valueOf(AzenarioZelda.getText().toString());
                IndabakN = Double.valueOf(IndabaZelda.getText().toString());
                TxorizoN = Double.valueOf(TxorizoZelda.getText().toString());
            }catch (Exception e){}
                                        Total.setText(String.valueOf(a.getTotal()));
                                     }
                                 }, 100000
        );




    }

}
