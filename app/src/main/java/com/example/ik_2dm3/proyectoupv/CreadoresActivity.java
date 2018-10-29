package com.example.ik_2dm3.proyectoupv;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreadoresActivity extends Activity {
    private Button idBtnCreadoresCerrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creadores);

        idBtnCreadoresCerrar = (Button) findViewById(R.id.idBtnCreadoresCerrar);
        idBtnCreadoresCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();}

        });
    }

}
