package com.example.ik_2dm3.proyectoupv;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class TextWatcherUsoMercado implements TextWatcher {
    private View view;

    public TextWatcherUsoMercado(View view) {
        this.view = view;
    }

   @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

            Actividad_32_Precios.CambiarTotal();

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
