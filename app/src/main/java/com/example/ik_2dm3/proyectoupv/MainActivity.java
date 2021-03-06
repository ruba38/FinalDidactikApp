package com.example.ik_2dm3.proyectoupv;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public boolean admin = false;
    private Button idBtnMainAjustes, idBtnIzquierda, idBtnDerecha, idBtnContinuar, idBtnInicio, idBtnReiniciar;
    private Button btnPopupInicioSi, btnPopupInicioNo, btnPopupInicioContinuar, btnPopupInicioReiniciar;
    private TextView idTextViewLugar, idTextViewProgreso, idTextViewInicioMensaje;
    private int posicionArray = 0;
    private int Lugar;
    private int contador = 0;
    private ArrayList<lugares> arrayLugares = new ArrayList<lugares>();
    private ArrayList<puntos> arrayPuntos = new ArrayList<puntos>();
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private int ContVisibles = 0;
    private Dialog inicioPopup;
    private ajustes objetoAjustes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        inicioPopup = new Dialog(this);


        objetoAjustes = new ajustes(getBaseContext());
        Log.d("ajustes", "sonido= " + objetoAjustes.sonido + " // musica= " + objetoAjustes.musica + " // mapa=" + objetoAjustes.mapa + " // idioma=" + objetoAjustes.idioma);
        idBtnMainAjustes = (Button) findViewById(R.id.idBtnMainAjustes);
        idBtnDerecha = (Button) findViewById(R.id.idBtnDerecha);
        idBtnInicio = (Button) findViewById(R.id.idBtnInicio);


        DatabaseAccess databaseAccess = new DatabaseAccess(getBaseContext());
        List<lugares> arrayLugares = (List<lugares>) databaseAccess.getLugares();
        databaseAccess.close();

        idTextViewLugar = (TextView) findViewById(R.id.idTextViewLugar);
        idTextViewProgreso = (TextView) findViewById(R.id.idTextViewProgreso);


        idTextViewLugar.setText(arrayLugares.get(posicionArray).getNombre());
        Lugar = arrayLugares.get(posicionArray).getIdLugar();
        mostrarProgreso(Lugar);

        //Boton Pruebas
        /*pruebas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), Presentaciones.class);
                startActivityForResult(i, 4);
            }
        });*/

        //BOTON AJUSTES

        idBtnMainAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AjustesActivity.class);
                startActivityForResult(i,11);

            }
        });

        //BOTON IZQUIERDA
        idBtnIzquierda = (Button) findViewById(R.id.idBtnIzquierda);
        idBtnIzquierda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                posicionArray = posicionArray - 1;

                if (posicionArray < 0) {
                    posicionArray = arrayLugares.size() - 1;
                    idTextViewLugar.setText(arrayLugares.get(posicionArray).getNombre());
                } else {
                    idTextViewLugar.setText(arrayLugares.get(posicionArray).getNombre());
                }

                Lugar = arrayLugares.get(posicionArray).getIdLugar();
                mostrarProgreso(Lugar);

            }
        });
        //BOTON DERECHA

        idBtnDerecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                posicionArray = posicionArray + 1;

                if (posicionArray > arrayLugares.size() - 1) {
                    posicionArray = 0
                    ;
                    idTextViewLugar.setText(arrayLugares.get(posicionArray).getNombre());
                } else {
                    idTextViewLugar.setText(arrayLugares.get(posicionArray).getNombre());
                }
                Lugar = arrayLugares.get(posicionArray).getIdLugar();
                mostrarProgreso(Lugar);

            }

        });
        //BOTON INICIAR

        idBtnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NombreLugar = arrayLugares.get(posicionArray).getNombre();
                //AL CLICKAR SOBRE EL PUNTO SE ABRIRA EL POPUP DEL PUNTO
                String mensajeContinuar = "PARTIDA BAT DAGO HASITA " + NombreLugar.toUpperCase() + "N, JARRAITU NAHI DUZU?";
                String mensajeReiniciar = "ZEGURU ZAUDE BERRABIARAZI NAHI DUZULA?";
                inicioPopup.setContentView(R.layout.popup_inicio);//abrir layout que contiene el popup

                btnPopupInicioSi = (Button) inicioPopup.findViewById(R.id.btnPopupInicioSi);
                btnPopupInicioNo = (Button) inicioPopup.findViewById(R.id.btnPopupInicioNo);
                btnPopupInicioContinuar = (Button) inicioPopup.findViewById(R.id.btnPopupInicioContinuar);
                btnPopupInicioReiniciar = (Button) inicioPopup.findViewById(R.id.btnPopupInicioReiniciar);

                btnPopupInicioSi.setVisibility(View.GONE);
                btnPopupInicioNo.setVisibility(View.GONE);
                //INTRODUCIMOS MENSAJE
                idTextViewInicioMensaje = inicioPopup.findViewById(R.id.idTextViewInicioMensaje);
                idTextViewInicioMensaje.setText(mensajeContinuar);

                //CONTINUAR
                btnPopupInicioContinuar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getBaseContext(), MapaActivity.class);
                        i.putExtra("idLugar", Lugar);
                        i.putExtra("Descargar", false);

                        inicioPopup.dismiss();
                        databaseAccess.setLugar(Lugar);
                        startActivityForResult(i, 1);
                        databaseAccess.close();
                    }
                });
                //REINICIAR
                btnPopupInicioReiniciar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        idTextViewInicioMensaje.setText(mensajeReiniciar);
                        btnPopupInicioContinuar.setVisibility(View.GONE);
                        btnPopupInicioReiniciar.setVisibility(View.GONE);
                        btnPopupInicioSi.setVisibility(View.VISIBLE);
                        btnPopupInicioNo.setVisibility(View.VISIBLE);
                    }
                });
                //SI
                btnPopupInicioSi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseAccess.resetApp(Lugar);
                        databaseAccess.setAdmin(0);
                        databaseAccess.setFinal(0);
                        Intent i = new Intent(getBaseContext(), Kaixo.class);
                        i.putExtra("idLugar", Lugar);
                        inicioPopup.dismiss();
                        startActivityForResult(i, 2);
                        databaseAccess.close();

                    }
                });
                //NO
                btnPopupInicioNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        idTextViewInicioMensaje.setText(mensajeContinuar);
                        btnPopupInicioContinuar.setVisibility(View.VISIBLE);
                        btnPopupInicioReiniciar.setVisibility(View.VISIBLE);
                        btnPopupInicioSi.setVisibility(View.GONE);
                        btnPopupInicioNo.setVisibility(View.GONE);
                    }
                });

                //PONER EL FONDO DEL POPUP TRASPARENTE
                inicioPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //NO PERMITIR QUE AL TOCAR FUERA DEL MISMO SE CIERRE
                inicioPopup.setCanceledOnTouchOutside(false);
                //MOSTRAR POPUP
                if (getProgreso(Lugar) > 0) {
                    inicioPopup.show();
                } else {
                    Intent i = new Intent(getBaseContext(), Kaixo.class);
                    i.putExtra("idLugar", Lugar);
                    startActivityForResult(i, 3);
                }
            }
        });

        if (arrayLugares.size() == 1) {
            idBtnIzquierda.setVisibility(View.GONE);
            idBtnDerecha.setVisibility(View.GONE);
        }

        //IR A Camara
/*
        buttonCamara = (Button) findViewById(R.id.buttonCamara);
        buttonCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),SacarFotos.class);

                startActivityForResult(i,101);
                //CheckCameraHardware();
                // Intent F = new Intent(getBaseContext(), Fotos.class);
                // startActivity(F);
                // File imagesFolder = new File(Environment.getExternalStorageDirectory(), "MyImages");
                // imagesFolder.mkdirs();
                //  File image = new File(imagesFolder, "image_001.jpg");
                // Uri uriSavedImage = Uri.fromFile(image);
                // i.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                // startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                //ChekCameraHardware

                }
        });

    }*/

        /* protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == 101) {
                String mywebsite = (String) data.getExtras().get("result");

            }
    }*/
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void mostrarProgreso(int x) {
        ContVisibles = 0;
        DatabaseAccess databaseAccess = new DatabaseAccess(getBaseContext());
        arrayPuntos.clear();
        List<puntos> arrayPuntos = (List<puntos>) databaseAccess.getPuntos(x);
        for (int i = 0; i < arrayPuntos.size(); i++) {
            if (arrayPuntos.get(i).getvisible() == 1) {
                ContVisibles = ContVisibles + 1;
            }
        }
        databaseAccess.close();
        idTextViewProgreso.setText(ContVisibles + " / " + (arrayPuntos.size()));

    }

    private int getProgreso(int x) {
        ContVisibles = 0;
        DatabaseAccess databaseAccess = new DatabaseAccess(getBaseContext());
        arrayPuntos.clear();
        List<puntos> arrayPuntos = (List<puntos>) databaseAccess.getPuntos(x);
        for (int i = 0; i < arrayPuntos.size(); i++) {
            if (arrayPuntos.get(i).getvisible() == 1) {
                ContVisibles = ContVisibles + 1;
            }
        }
        databaseAccess.close();
        return ContVisibles;

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 || requestCode == 2 || requestCode == 3) {
            if (resultCode == RESULT_OK) {
                finish();
            }

        }
    }
}
