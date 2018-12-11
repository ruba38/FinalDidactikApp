package com.example.ik_2dm3.proyectoupv;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapaActivity extends AppCompatActivity implements PermissionsListener, OnMapReadyCallback, LocationEngineListener, MapboxMap.OnMapClickListener {

    // Variables para el mapa
    private MapView mapView;
    private MapboxMap map;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private LocationLayerPlugin locationLayerPlugin;
    private Location originLocation;

    // Creacion de objetos
    private Button idBtnMapaAdmin,idBtnMapaAjustes;
    private Dialog puntoPopup,pistaPopup;
    //creacion de imagen
    /*private byte[] decodedString;
    private Bitmap decodedByte;

    private BitmapDrawable  drawable;*/
    // Variables de datos
    private ArrayList<MarkerPuntos> PuntosInteres = new ArrayList<MarkerPuntos>();

    private boolean admin = false;
    private int idPunto,secuencia;
    private String juego,titulo,imagen,pista;
    private double latitud,longitud;
    private double RangoGeneral=10.0;
    private int Lugar=1;
    // Objetos/Variables de depuracion
    private TextView coordenadas,idTextViewDistancia,idTextViewProgreso,idTextViewMapaProgresoPuntos;
    private int contPuntos=0;
    private double distancia2=0.0;
    private int id_bd;
    private int secuencia2;
    private String metrica;
    private double textoDistancia;
    //DECLARARCIONES
    private Button idBtnPopupCerrar,idBtnPopupJugar;
    private TextView idTextViewPopupTitulo;
    private ImageView imagenPopup;

    private TextView idTextViewPista;
    private Button idBtnCerrarPista;




    // Limite de la camara de la zona sleccionada
    private static final LatLngBounds coordsLimite = new LatLngBounds.Builder()
            .include(new LatLng(43.258316, -2.903066))
            .include(new LatLng(43.256749, -2.908320))
            .build();
    //SE EJECUTA NADA MAS ABRIRSE EL MAPA
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //QUITAR TITULO DEL LAYOUT
        getSupportActionBar().hide();
        // SE CREA LA INSTANCIA DEL MAPA CON LA CLAVE DE ACCESO
        Log.d("descarga", "punto 1");
        Mapbox.getInstance(this, getString(R.string.access_token));

        Log.d("descarga", "Conectado: "+Mapbox.isConnected());

        setContentView(R.layout.activity_mapa);
        //RECOJE EL ID DEL LUGAR SELECIONADO AL PRINCIPIO DE LA APP (MAIN)
        Lugar=getIntent().getIntExtra("idLugar",0);

        // INSTANCIA EL OBJETO POPUP
        puntoPopup = new Dialog(this);
        pistaPopup = new Dialog(this);

        // INSTANCIA LOS OBJETOS DEL MAPA
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        Log.d("descarga", "LiveRegion: "+mapView.getWindowToken());

        //CARGA LOS ESTILOS PERSONALIZADOS
        mapView.setStyleUrl("mapbox://styles/mariusinfo/cjoshzhqg3sb22sme5eyoogt4");


        // ASIGNACION DE OBJETOS
        // BOTONES
        coordenadas = findViewById(R.id.coords);
        idTextViewDistancia = findViewById(R.id.idTextViewDistancia);
        idTextViewProgreso = findViewById(R.id.idTextViewProgreso);
        //TEXT VIEW
        idBtnMapaAdmin = findViewById(R.id.idBtnMapaAdmin);
        idBtnMapaAjustes = findViewById(R.id.idBtnMapaAjustes);


        // INSTANCIAR OBJETOS DE BASE DE DATOS
        DatabaseAccess databaseAccess = new DatabaseAccess(this);
        // COMPRUEBA SE LA BASE DE DATOS EXISTSTE EN EL DISPOSITIBO , CREA UNA COPIA EN EL DISPOSITIBO
        databaseAccess.startdb(getBaseContext());
        //CON EL LUGAR INDICADO MIRAMOS SI HAY ALGUN PUNTO VISIBLE SI NO LO HAY PONE EL PRIMERO VISIBLE
        databaseAccess.iniciarApp(Lugar);


        // BOTON MODO ADMINISTRADOR
        idBtnMapaAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SI ESTA DESACTIVADO SE ACTIVA Y MUESTRA TODOS LOS PUNTOS RESPECTO AL LUGAR SELECIONADO
                if (admin==false) {
                    idBtnMapaAdmin.setText("Admin True");
                    // Cambia a Visible = true, todos los puntos
                    databaseAccess.setAllVisible(Lugar);
                    admin=true;
                }
                //SI ESTA ACTIVADO SE DESACTIVA Y OCULTA TODOS LOS PUNTOS MENOS LOS TERMINADOS SI NO HAY TERMINADOS MUESTRA SOLO EL PRIMERO
                else{
                    idBtnMapaAdmin.setText("Admin False");
                    databaseAccess.reverseAllVisible(Lugar);
                    admin=false;
                }

                // VACIA EL ARRAYLIST QUE CONTIENE LOS DATOS DE LOS PUNTOS
                LimpiarPuntos();

                // RELLENA EL ARRAYLIST CON LOS DATOS DE LOS PUNTOS
                CrearPuntos();
                //CAMBIAR TEXTO DE PUNTOS
                @SuppressLint("MissingPermission") Location ubicacionUsuario = locationEngine.getLastLocation();
                localizarDistancia(ubicacionUsuario);
            }
        });


        //BOTON AJUSTES
        idBtnMapaAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ABRE LA VENTANA DE AJUSTES
                Intent i = new Intent(getBaseContext(), AjustesActivity.class);
                startActivity(i);}
        });
    }
    //*****************************FUNCIONES INDIBIDUALES*******************************************

    //SE EJECUTA CUANDO EL DISPOSITIVO DETECTA QUE AS CAMBIADO TU LOCALIZAZION
    @Override
    public void onLocationChanged(Location location) {

        localizarDistancia(location);
    }
    public void localizarDistancia (Location location) {
        // ACCEDE A LA BASE DE DATOS
        DatabaseAccess databaseAccess =new DatabaseAccess(this);
        // INSTANCIA LOS OBJETOS DE LOCALIZAZION
        Location ubicacionPunto = new Location("");
        Location ubicacionUsuario = new Location("");
        Location ubicacionPunto2 = new Location("");

        //RECOJE LA LATITUD Y LA LONGITUD DE TU UBICACION
        ubicacionUsuario.setLatitude(location.getLatitude());
        ubicacionUsuario.setLongitude(location.getLongitude());
        contPuntos = 0;
        //RECOREMOS EL ARRAY DE PUNTOS ,RECOJIENDO DATOS COMO LA SECUENCIA Y SU LOCALIZAZION
        for(int i = 0; i < PuntosInteres.size(); i++) {
            id_bd = PuntosInteres.get(i).getID_BD();
            secuencia2 = PuntosInteres.get(i).getSecuencia();
            ubicacionPunto.setLatitude(PuntosInteres.get(i).getLatitude());
            ubicacionPunto.setLongitude(PuntosInteres.get(i).getLongitude());

            if (PuntosInteres.get(i).isVisible() == true) {

                contPuntos = contPuntos + 1;
            }
            //SACA LA DIFERENCIA DE DISTANCIA EN METROS ENTRE TU UBICACION Y LA DEL PROXIMO PUNTO
            //PARA ELLO MIRAMOS SI EL ANTERIOR ESTA TERMINADO
            if(databaseAccess.getTerminadoAnterior(secuencia2,Lugar)) {
                distancia2 = ubicacionUsuario.distanceTo(ubicacionPunto);
            }
            //COMPROBAMOS SI AL CAMBIAR DE UBICACION ENTRAS EN RANGO DE ALGUN PUNTO Y SI EL ANTERIOR ESTA TERMINADO LO MOSTRARIA
            if(distancia2 < RangoGeneral && databaseAccess.getTerminadoAnterior(secuencia2,Lugar)){
                databaseAccess.setVisible(id_bd);
                // VACIA EL ARRAYLIST QUE CONTIENE LOS DATOS DE LOS PUNTOS
                LimpiarPuntos();
                // RELLENA EL ARRAYLIST CON LOS DATOS DE LOS PUNTOS
                CrearPuntos();

            }
            //MOSTRAS PROGRESO Y LA DISTANCIA EL PROXIMO PUNTO
            //SI ESTAS A MAS DE 1000 METROS DEL SIGUIENTE PUNTO TE PONE LA DISTANCIA EN KILOMETROS
            if(distancia2>1000){
                metrica="Km";
                textoDistancia=distancia2/1000;
            }
            //SI ESTAS A MENOS DE 1000 METROS DEL SIGUIENTE PUNTO TE PONE LA DISTANCIA EN METROS
            else{
                metrica="m";
                textoDistancia=distancia2;
            }
            //PINTAS LA DISTANCIA CON 2 DECIMALES
            idTextViewDistancia.setText(String.format("%.2f",textoDistancia)+""+metrica);
            //PINTAS EL PROGRESO DE LOS PUNTOS ENCONTRADOS
            idTextViewProgreso.setText(contPuntos+"/"+PuntosInteres.size());
        }
    }

    //COMO HAY QUE ESTAR CERCA DEL PUNTO PARA JUGAR ESTA COMPRUEBA SI EL PUNTO ESTA DENTRO DEL RANGO INDICADO
    public boolean enRango(double latitud,double longitud) {
        //INSTANCIA EL OBJETIO PARA TU UBICACION
        Location ubicacionPunto = new Location("");
        // RECOJE LOS DATOS DE TU UBICACION
        @SuppressLint("MissingPermission") Location ubicacionUsuario = locationEngine.getLastLocation();

        //RECOJE LOS DATOS DE LA UBICACION DEL PUNTO
        ubicacionPunto.setLatitude(latitud);
        ubicacionPunto.setLongitude(longitud);
        //MIRA A CUANTA DISTANCIA ESTA DE TU UBICACION
        double distancia = ubicacionUsuario.distanceTo(ubicacionPunto);
        //SI ESTA EN EL RANGO INDICADO TE DEBUELBE UN TRUE PARA INDICAR QUE SI
        if(distancia<RangoGeneral ){
            return true;
        }
        //SI NO ESTA EN EL RANGO INDICADO TE DEBUELBE UN FALSE PARA INDICAR QUE NO EXCEPTO SI ESTAS EN MODO ADMIN
        else{
            if(admin==true){
                return true;
            }else {
                return false;
            }
        }
    }
    //TODO: IMG ....
    public void toImg(String byteArray){

        /*decodedString = Base64.decode(byteArray, Base64.DEFAULT);
        decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        //Convert bitmap to drawable
        drawable = new BitmapDrawable(getResources(), decodedByte);
        Log.d("mitag","toImg drawable =>"+drawable);*/

    }


    //CARGAR MAPA
    @Override
    public void onMapReady(MapboxMap mapboxMap) {


        // ASIGNAR OBJETO A LA INSTANCIA MAPA
        MapaActivity.this.map = mapboxMap;

        // AÑADIMOS EL LISTENER DEL MAPA
        mapboxMap.addOnMapClickListener(this);

        // HABILITAMOS LA LOCALIZAZION DEL USUARIO
        enableLocation();

        // ZOOM MAXIMO Y MINIMO DEL MAPA Y DELIMITAR MAPA
        //TODO :DESCOMENTAR EN UN FUTURO
       /* map.setMinZoomPreference(16);
        map.setMaxZoomPreference(17.5);
        mapboxMap.setLatLngBoundsForCameraTarget(coordsLimite);*/

        // RELLENA EL ARRAYLIST CON LOS DATOS DE LOS PUNTOS
        CrearPuntos();

        //CLICKAR SOBRE UNO DE LOS PUNTOS
        mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {

                // RECORREMOS EL ARRAY DE MARCADORES
                for(int i = 0; i < PuntosInteres.size(); i++) {
                    // COMPARAMOS LA POSICION DEL MARCADOR CLICKADO CON LAS DEL ARRAY Y SI CONINCIDE RECOJEMOS LOS DATOS DE ESE PUNTO
                    if(marker.getPosition() == PuntosInteres.get(i).getmO().getPosition()) {

                        MarkerPuntos mp = new MarkerPuntos(PuntosInteres.get(i));
                        idPunto=mp.getID_BD();
                        juego=mp.getJuego();
                        titulo=mp.getNombre();
                        latitud=mp.getLatitude();
                        longitud=mp.getLongitude();
                        secuencia=mp.getSecuencia();
                        imagen=mp.getImagen();
                        pista=mp.getPista();
                        break;

                    }
                }
                //AL CLICKAR SOBRE EL PUNTO SE ABRIRA EL POPUP DEL PUNTO
//todo pop
                puntoPopup.setContentView(R.layout.popup_punto);//abrir layout que contiene el popup
                    //INTRODUCIMOS TITULO
                    idTextViewPopupTitulo = puntoPopup.findViewById(R.id.idTextViewPopupTitulo);
                    idTextViewPopupTitulo.setText(titulo);
                    //INTRODUCIMOS IMAGEN
                    /*imagenPopup=puntoPopup.findViewById(R.id.imagenPopup);
                    //int resID = getResources().getIdentifier("ing", "drawable", "com.app");
                    int resID =R.drawable.ing;
                    Log.d("imagen","=>"+resID);

                    imagenPopup.setBackgroundResource(resID);*/







                /*final String prueba2 = new StringBuilder("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").toString();


                byte[] decodedString = Base64.decode(prueba2, Base64.DEFAULT);

                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,
                            0, decodedString.length);

                    Drawable drawableTop = new BitmapDrawable(getResources(), decodedByte);
                    Log.d("imagen","imagen="+decodedByte);
                    imagenPopup.setBackgroundDrawable(drawableTop);*/


















                    //ENLAZAMOS EL JUEGO AL BOTON JUGAR
                    idBtnPopupJugar = (Button) puntoPopup.findViewById(R.id.idBtnPopupJugar);
                        //COMPROBAR SI ESTA EN RANGO, SI NO LO ESTA CAMBIA COLOR DEL BOTON JUGAR Y EL TEXTO DEL MISMO
                        if(enRango(latitud,longitud)==false) {
                            idBtnPopupJugar.setBackgroundColor(getColor(R.color.Desabilitado));
                        }
                    //AL CLICKAR SOBRE EL BOTON JUGAR
                    idBtnPopupJugar.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            //SI ESTA EN RANGO
                            if(enRango(latitud,longitud)) {
                                //OCULTAR EL POPUP
                                puntoPopup.dismiss();
                                //COJER EL NOMBRE DEL JUEGO Y COMBERTIRLO EN LA CLASE DEL JUEGO Q NECESITAMOS ABRIR
                                String nombreJuego = "com.example.ik_2dm3.proyectoupv." + juego;
                                nombreJuego = nombreJuego.replace(" ", "");
                                Intent i = null;
                                try {
                                    i = new Intent(getBaseContext(), Class.forName(nombreJuego));
                                    i.putExtra("idPuntoJuego",idPunto);
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                                //ABRIR JUEGO
                                startActivityForResult(i,666);
                            }
                            //SI NO ESTAS EN RFANGO TE MUESTRA UN TOAST INDICANDO QUE NO ESTAS EN RANGO
                            else{
                                Context context = getApplicationContext();
                                CharSequence text = "NO ESTAS EN RANGO";
                                int duration = Toast.LENGTH_LONG;

                                Toast toastRango = Toast.makeText(context, text, duration);
                                toastRango.show();
                            }
                        }
                    });

                //CERRAR POPUP AL DAR A LA X
                idBtnPopupCerrar = (Button) puntoPopup.findViewById(R.id.idBtnPopupCerrar);
                idBtnPopupCerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //AL CERRARLO PONDRA EL PUNTO COMO FINALIZADO
                        //PuntoTerminado(idPunto);
                        //OCULTAR POPUP
                        puntoPopup.dismiss();
                    }
                });
                //PONER EL FONDO DEL POPUP TRASPARENTE
                puntoPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //NO PERMITIR QUE AL TOCAR FUERA DEL MISMO SE CIERRE
                puntoPopup.setCanceledOnTouchOutside(false);
                //MOSTRAR POPUP
                puntoPopup.show();
                return false;
            }
        });

    }
    //COMPROBAR SI LA APLICACION TIENE PERMISOS PARA UBICACION ,SI NO LOS TIENE LOS PIDE
    private void enableLocation() {
        if(PermissionsManager.areLocationPermissionsGranted(this)) {
            initializeLocationEngine();
            initializeLocationLayer();
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }
    //SI EL USUARIO ACEPTA DAR PERMISOS DE UBICACION
    @Override
    public void onPermissionResult(boolean granted) {
        if(granted) {
            //COMPRUEBA SI LOS TIENE
            enableLocation();
        }
    }
    //LOCALIZARNOS A NOSOTROS MISMOS
    @SuppressLint("MissingPermission")
    private void initializeLocationEngine() {
        // Creamos el objeto que nos ubicara
        locationEngine = new LocationEngineProvider(getBaseContext()).obtainBestLocationEngineAvailable();

        // Establecemos la prioridad como alta
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);

        // Activamos el rastreo del a ubicacion
        locationEngine.activate();

        // Obtenemos la ultima ubicacion y comprobamos que sea distinto de null
        Location lastlocation = locationEngine.getLastLocation();
        if(lastlocation != null) {
            // Si es distntio, ubicamos la camara en la ubicacion actual
            originLocation = lastlocation;
        } else {
            // Si es null, añadimos el Listener que se encargara de llevar el seguimiento de la ubicacion
            locationEngine.addLocationEngineListener(this);
        }
    }
    //ESTILOS DE NUSTRA PROPIA LOCALIZAZION
    @SuppressLint("WrongConstant")
    private void initializeLocationLayer() {
        // Creamos el objeto del estilo
        locationLayerPlugin = new LocationLayerPlugin(mapView, map, locationEngine);

        // Activamos la localizacion en el estilo
        locationLayerPlugin.setLocationLayerEnabled(true);

        // Hacemos que la camara siga al usuario
        locationLayerPlugin.setCameraMode(CameraMode.TRACKING);

        // Modo de renderizado, normal
        locationLayerPlugin.setRenderMode(RenderMode.NORMAL);
    }
//todo:3
   //CUANDO SE TERMINA UN PUNTO
    private void PuntoTerminado(int idPunto){
        //CONECTAMOS CON LA BASE DE DATOS
        DatabaseAccess databaseAccess =new DatabaseAccess(this);
        // CAMBIAMOS AL PUNTO ESE LA OPCION COMO TERMINADO
        databaseAccess.setTerminado(idPunto);
        // VACIA EL ARRAYLIST QUE CONTIENE LOS DATOS DE LOS PUNTOS
        LimpiarPuntos();

        // RELLENA EL ARRAYLIST CON LOS DATOS DE LOS PUNTOS
        CrearPuntos();
        @SuppressLint("MissingPermission") Location ubicacionUsuario = locationEngine.getLastLocation();
        localizarDistancia(ubicacionUsuario);
    }
//todo:1
    private void CrearPuntos() {

        if(!PuntosInteres.isEmpty()) { PuntosInteres.clear(); }
        // Creamos los iconos
        Icon icon1 = IconFactory.getInstance(MapaActivity.this).fromResource(R.drawable.exclamatin2);
        Icon icon2 = IconFactory.getInstance(MapaActivity.this).fromResource(R.drawable.completado2);

        // Cargamos los puntos de la base de datos en un array
        DatabaseAccess databaseAccess = new DatabaseAccess(getBaseContext());
        List<puntos> arrayPuntos = (List<puntos>) databaseAccess.getPuntos(Lugar);

        databaseAccess.close();
        // Creamos el objeto del punto
        MarkerPuntos marca;

        for (int i = 0; i < arrayPuntos.size(); i++) {

            // Instanciamos el objeto para cada uno de los puntos y le pasamos los datos de la base de datos
            marca = new MarkerPuntos();
            marca.getmO().setPosition(new LatLng(arrayPuntos.get(i).getlatitud(), arrayPuntos.get(i).getlongitud()));
            marca.setID_BD(arrayPuntos.get(i).getidPunto());
            marca.setJuego(arrayPuntos.get(i).getjuego());
            marca.setNombre(arrayPuntos.get(i).getnombre());
            marca.setSecuencia(arrayPuntos.get(i).getSecuencia());

            if(arrayPuntos.get(i).getterminado() == 0) {
                marca.setTerminado(false);
            } else {
                marca.setTerminado(true);
            }

            if(arrayPuntos.get(i).getvisible() == 0) {
                marca.setVisible(false);
            } else {
                marca.setVisible(true);
            }

            // Si el punto en concreto esta terminado, cargamos uno u otro icono
            if (arrayPuntos.get(i).getterminado() == 1) {
                marca.getmO().setIcon(icon2);
            } else {
                marca.getmO().setIcon(icon1);
            }

            // Añadimos los puntos a un ArrayList
            PuntosInteres.add(marca);

            if (PuntosInteres.get(i).isVisible() == true) {
                // Una vez cargados todos los objetos en  el ArrayList los añadimos al mapa
                map.addMarker(PuntosInteres.get(i).getmO());
            }
        }
    }

    private void LimpiarPuntos(){
        // Pasamos por cada uno de los marcadores del mapa y los borramos.
        for(int i = 0; i < PuntosInteres.size(); i++) {
            map.removeMarker(PuntosInteres.get(i).getmO().getMarker());
        }
        // Limpiamos el ArrayList para cuando se vuelvan a crear los puntos
        PuntosInteres.clear();
    }

    //TODO:mostrarPista...
    public void mostrarPista(View v){
        String textoPista;
        DatabaseAccess databaseAccess = new DatabaseAccess(getBaseContext());
        int cont=1;
        for(int i = 0; i < PuntosInteres.size()-1; i++) {
            Log.d("pista","entrea if terminado=>"+i+""+PuntosInteres.get(i).isTerminado());
           if(PuntosInteres.get(i).isTerminado()){
               cont=cont+1;
               Log.d("pista","entrea if terminado=>"+cont);
           }
        }
        if(cont!=PuntosInteres.size()) {
            textoPista = databaseAccess.getPista(Lugar, cont);
        }else{
            textoPista ="Amaituta";
        }

        pistaPopup.setContentView(R.layout.popup_pista);//abrir layout que contiene el popup
        //INTRODUCIMOS TEXTO
        idTextViewPista = pistaPopup.findViewById(R.id.idTextViewPista);
        idTextViewPista.setText(textoPista);
        //CERRAR POPUP AL DAR A LA X
        idBtnCerrarPista = (Button) pistaPopup.findViewById(R.id.idBtnCerrarPista);
        idBtnCerrarPista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OCULTAR POPUP
                pistaPopup.dismiss();
            }
        });
        //PONER EL FONDO DEL POPUP TRASPARENTE
        pistaPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //NO PERMITIR QUE AL TOCAR FUERA DEL MISMO SE CIERRE
        pistaPopup.setCanceledOnTouchOutside(false);
        pistaPopup.show();
    }
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 666){
        Log.d("Mapa","result");
        LimpiarPuntos();
        CrearPuntos();

        }
    }

    //METODOS NO UTILIZADOS PERO NECESARIOS PARA EL FUNCIONAMIENTO CORECTO DE LA APLICACION
    @Override
    public void onMapClick(@NonNull LatLng point) {
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    @SuppressLint("MissingPermission")
    public void onConnected() {
        locationEngine.requestLocationUpdates();
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}