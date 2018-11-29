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
    private byte[] decodedString;
    private Bitmap decodedByte;

    private Drawable drawable;
    // Variables de datos
    private ArrayList<MarkerPuntos> PuntosInteres = new ArrayList<MarkerPuntos>();

    private boolean admin = false;
    private int idPunto,secuencia;
    private String juego,titulo,imagen;
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
        Lugar=getIntent().getIntExtra("idLugarKaixo",0);

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
                    // Cambia a Visible = true, todos los puntos
                    databaseAccess.setAllVisible(Lugar);
                    admin=true;
                }
                //SI ESTA ACTIVADO SE DESACTIVA Y OCULTA TODOS LOS PUNTOS MENOS LOS TERMINADOS SI NO HAY TERMINADOS MUESTRA SOLO EL PRIMERO
                else{
                    databaseAccess.reverseAllVisible(Lugar);
                    admin=false;
                }

                // VACIA EL ARRAYLIST QUE CONTIENE LOS DATOS DE LOS PUNTOS
                LimpiarPuntos();

                // RELLENA EL ARRAYLIST CON LOS DATOS DE LOS PUNTOS
                CrearPuntos();
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
        if(distancia<RangoGeneral){
            return true;
        }
        //SI NO ESTA EN EL RANGO INDICADO TE DEBUELBE UN FALSE PARA INDICAR QUE NO
        else{
            return false;
        }
    }
    //TODO: IMG ....
    public void toImg(String byteArray){

        decodedString = Base64.decode(byteArray, Base64.DEFAULT);
        decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        //Convert bitmap to drawable
        drawable = new BitmapDrawable(getResources(), decodedByte);
        Log.d("mitag","toImg drawable =>"+drawable);
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
                        break;

                    }
                }
                //AL CLICKAR SOBRE EL PUNTO SE ABRIRA EL POPUP DEL PUNTO

                puntoPopup.setContentView(R.layout.popup_punto);//abrir layout que contiene el popup
                    //INTRODUCIMOS TITULO
                    idTextViewPopupTitulo = puntoPopup.findViewById(R.id.idTextViewPopupTitulo);
                    idTextViewPopupTitulo.setText(titulo);
                    //INTRODUCIMOS IMAGEN
                    imagenPopup=puntoPopup.findViewById(R.id.imagenPopup);
                    toImg(imagen);
                    imagenPopup.setBackground(drawable);
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
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                                //ABRIR JUEGO
                                startActivity(i);
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
                        PuntoTerminado(idPunto);
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
        String textoPista="ESTA ES UNA PISTA DE PRUEBA , DIRIJETE A LA TORRE MAS ALTA DONDE CADA HORA SUENA LA CAMPANA";
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