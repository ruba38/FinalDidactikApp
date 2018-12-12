package com.example.ik_2dm3.proyectoupv;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Actividad_31_Mercatua extends Activity {
    ImageView Varria;
    int Cont=0;
    int idPuntoJuego;

    //kipula.setOnlongClickListener(dragListener);
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("mapa", "Juego 0");

        //DEFINIR IMAJENES
        setContentView(R.layout.activity_actividad_3__mercatua);
        idPuntoJuego=getIntent().getIntExtra("idPuntoJuego",0);
        ImageView kipula = findViewById(R.id.kipula);//
        ImageView uraza = findViewById(R.id.uraza);//
        ImageView koliflora = findViewById(R.id.koliflora);
        ImageView Indabak = findViewById(R.id.Indabak);
        ImageView pipergorria = findViewById(R.id.pipergorria);
        ImageView txocolate = findViewById(R.id.txocolate);
        ImageView Tomate = findViewById(R.id.Tomate);
        ImageView azenarioa = findViewById(R.id.azenarioa);
        ImageView txorizo = findViewById(R.id.txorizo);
        ImageView Zesta = findViewById(R.id.Zesta);
        //Recojer clicl listeners
        kipula.setOnLongClickListener(ClickListener);
        uraza.setOnLongClickListener(ClickListener);
        koliflora.setOnLongClickListener(ClickListener);
        Indabak.setOnLongClickListener(ClickListener);
        pipergorria.setOnLongClickListener(ClickListener);
        txocolate.setOnLongClickListener(ClickListener);
        azenarioa.setOnLongClickListener(ClickListener);
        txorizo.setOnLongClickListener(ClickListener);
        Tomate.setOnLongClickListener(ClickListener);
        Zesta.setOnDragListener(dragListener);
    }
    //LONGCLICK LISTENER

    View.OnLongClickListener ClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder myShadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(data, myShadowBuilder, v, 0);

            Log.d("mapa", "Juego 1");

            return false;
        }
    };

    final View.OnDragListener dragListener = new View.OnDragListener() {
        //ACCIONES QUE HACE DESPUES DEL LONGCLICK
        @Override
        public boolean onDrag(View v, DragEvent event) {
            final View view = (View) event.getLocalState();
            int dragEvent = event.getAction();

            Log.d("mapa", "Juego 2");

            switch (dragEvent) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                    //ACCION QUE HACE CUANDO TERMINA EL DRAG I LOS SUELTAS EN ALGUNA BIÑETA CON DRAG LSITENER
                case DragEvent.ACTION_DROP:
                    //Meter los alimentos en la bolsa
                    if(view.getId()== R.id.Tomate){
                        Toast.makeText(getBaseContext(),R.string.TextoToast1,Toast.LENGTH_LONG).show();
                    }
                    if(view.getId()== R.id.txocolate){
                        Toast.makeText(getBaseContext(),R.string.TextoToast1,Toast.LENGTH_LONG).show();
                    }
                    if(view.getId()== R.id.pipergorria){
                        Toast.makeText(getBaseContext(),R.string.TextoToast1,Toast.LENGTH_LONG).show();
                    }
                    if(view.getId()== R.id.uraza){
                        Toast.makeText(getBaseContext(),R.string.TextoToast1,Toast.LENGTH_LONG).show();
                    }
                    if(view.getId()== R.id.koliflora){
                        Toast.makeText(getBaseContext(),R.string.TextoToast1,Toast.LENGTH_LONG).show();
                    }
                    if (view.getId() == R.id.kipula) {
                        Varria = findViewById(R.id.kipula);
                        Varria.setVisibility(view.GONE);
                        Cont ++;
                    }
                    if (view.getId() == R.id.Indabak) {
                        Varria = findViewById(R.id.Indabak);
                        Varria.setVisibility(view.GONE);
                        Cont ++;
                    }
                    if (view.getId() == R.id.txorizo) {
                        Varria = findViewById(R.id.txorizo);
                        Varria.setVisibility(view.GONE);
                        Cont ++;
                    }
                    if (view.getId() == R.id.azenarioa) {
                        Varria = findViewById(R.id.azenarioa);
                        Varria.setVisibility(view.GONE);
                        Cont ++;
                    }
                    if(Cont ==4){
                        Intent i = new Intent(getBaseContext(),Actividad_32_Precios.class);
                        i.putExtra("idPuntoJuego",idPuntoJuego);
                        startActivityForResult(i, 22);
                        finish();

                    }
                    break;
            }
            return true;
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        Log.d("mapa", "Juego 3");


        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 22) {
            if (resultCode==RESULT_OK){
                finish();
        }
    }
    }
}




