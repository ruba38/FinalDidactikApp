package com.example.ik_2dm3.proyectoupv;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.mapbox.mapboxsdk.annotations.PolygonOptions;
import com.mapbox.mapboxsdk.geometry.ILatLng;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class
MapaActivity extends AppCompatActivity implements PermissionsListener, OnMapReadyCallback, LocationEngineListener, MapboxMap.OnMapClickListener {

    // Variables para el mapa
    private MapView mapView;
    private MapboxMap map;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private LocationLayerPlugin locationLayerPlugin;
    private Location originLocation;
    private Location lastlocation;

    // Creacion de objetos
    private Button idBtnMapaAdmin,idBtnMapaAjustes;
    private Dialog puntoPopup,pistaPopup;
    private ImageView BotonCamara;
    //creacion de imagen
    /*private byte[] decodedString;
    private Bitmap decodedByte;

    private BitmapDrawable  drawable;*/
    // Variables de datos
    private ArrayList<MarkerPuntos> PuntosInteres = new ArrayList<MarkerPuntos>();

    private int idPunto,secuencia,newPista=1;
    private boolean admin = false;
    private int idPunto,secuencia;
    private String juego,titulo,imagen,pista;
    private double latitud,longitud;
    private double RangoGeneral=10.0;
    private int Lugar=1;
    private String LugarSeleccionado = "";
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
    private Thread hiloJuego;
    //Imajen PopUp
    private String ImagenPoP;
    private double distanciaArea = 0.0;
    private LatLngBounds coordsLimite;


    // Limite de la camara de la zona sleccionada
    //LatLngBounds coordsLimite;
    //SE EJECUTA NADA MAS ABRIRSE EL MAPA
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.d("mapa", "Punto 0");
        //Recojer admin
        admin= getIntent().getBooleanExtra("Admin",false);
        //QUITAR TITULO DEL LAYOUT
        //ocultar barras extras
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);        // SE CREA LA INSTANCIA DEL MAPA CON LA CLAVE DE ACCESO
        Mapbox.getInstance(this, getString(R.string.access_token));

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

        //CARGA LOS ESTILOS PERSONALIZADOS
        mapView.setStyleUrl("mapbox://styles/mariusinfo/cjosikqh33suu2smegbr6z3gv");


        // ASIGNACION DE OBJETOS
        // BOTONES
        coordenadas = findViewById(R.id.coords);
        idTextViewDistancia = findViewById(R.id.idTextViewDistancia);
        idTextViewProgreso = findViewById(R.id.idTextViewProgreso);
        BotonCamara = findViewById(R.id.CamaraMapa);
        //TEXT VIEW
        idBtnMapaAdmin = findViewById(R.id.idBtnMapaAdmin);
        idBtnMapaAjustes = findViewById(R.id.idBtnMapaAjustes);


        // INSTANCIAR OBJETOS DE BASE DE DATOS
        DatabaseAccess databaseAccess = new DatabaseAccess(this);
        // COMPRUEBA SE LA BASE DE DATOS EXISTSTE EN EL DISPOSITIBO , CREA UNA COPIA EN EL DISPOSITIBO
        databaseAccess.startdb(getBaseContext());
        //CON EL LUGAR INDICADO MIRAMOS SI HAY ALGUN PUNTO VISIBLE SI NO LO HAY PONE EL PRIMERO VISIBLE
        databaseAccess.iniciarApp(Lugar);


        // Cargamos el area de la zona seleccionada
       // coordsLimite = databaseAccess.getLimiteZona(Lugar);

        // BOTON MODO ADMINISTRADOR
        idBtnMapaAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SI ESTA DESACTIVADO SE ACTIVA Y MUESTRA TODOS LOS PUNTOS RESPECTO AL LUGAR SELECIONADO
                if (admin==false) {

                    // Cambia a Visible = true, todos los puntos
                    databaseAccess.setAllVisible(Lugar);
                    admin=true;
                    idBtnMapaAdmin.setBackground(getDrawable(R.drawable.adminverde));
                }
                //SI ESTA ACTIVADO SE DESACTIVA Y OCULTA TODOS LOS PUNTOS MENOS LOS TERMINADOS SI NO HAY TERMINADOS MUESTRA SOLO EL PRIMERO
                else{

                    databaseAccess.reverseAllVisible(Lugar);
                    admin=false;
                    idBtnMapaAdmin.setBackground(getDrawable(R.drawable.adminrojo));
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
        //BOTON CAMARA
        BotonCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent(getBaseContext(),SacarFotos.class);
                startActivityForResult(c,10);
            }
        });

    }

    //*****************************FUNCIONES INDIBIDUALES*******************************************

    //SE EJECUTA CUANDO EL DISPOSITIVO DETECTA QUE AS CAMBIADO TU LOCALIZAZION
    @Override
    public void onLocationChanged(Location location) {

        localizarDistancia(location);

        // Obtenemos la posicion de la persona
        Log.d("long",location.getAltitude()+"cdadadadada"+location.getLongitude()+"falos?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");

       // LatLng pos = new LatLng(location.getAltitude(),location.getLongitude());

        // Si estan fuera de la zona delimitada
        //if(!coordsLimite.contains(pos)) {

            // Pasamos el area a coordenadas y calculamos la distancia
            //LatLng dist = new LatLng(coordsLimite.getNorthEast().getLatitude(), coordsLimite.getSouthWest().getLongitude());
            //distanciaArea = pos.distanceTo(dist);

       // }
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
        map.setMinZoomPreference(16);
        map.setMaxZoomPreference(19.50);
        mapboxMap.setLatLngBoundsForCameraTarget(coordsLimite);

        // RELLENA EL ARRAYLIST CON LOS DATOS DE LOS PUNTOS
        CrearPuntos();

        //CLICKAR SOBRE UNO DE LOS PUNTOS
        mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {

                Log.d("mapa", "Punto 2");

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
                    imagenPopup=puntoPopup.findViewById(R.id.imagenPopup);
                    //int resID = getResources().getIdentifier("ing", "drawable", "com.app");
                    int resID =R.drawable.ing;
                    Log.d("imagen","=>"+resID);

                    imagenPopup.setBackgroundResource(resID);


               // final String prueba2 = new StringBuilder("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").toString();
                DatabaseAccess databaseAccess = new DatabaseAccess(getBaseContext());
                String prueba2 = databaseAccess.getImajen(idPunto);
                databaseAccess.close();
                Log.d("imagen","IMAGEN PRUEBA 22222222222222222222222222222=>"+prueba2);
                byte[] decodedString = Base64.decode(prueba2, Base64.DEFAULT);
                if (prueba2 == null){
                    Log.d("mytag", "RUBEN LELE");
                }
                else {
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,
                            0, decodedString.length);

                    Drawable drawableTop = new BitmapDrawable(getResources(), decodedByte);
                    Log.d("imagen", "imagen=" + decodedByte);
                    imagenPopup.setBackground(drawableTop);
                }


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

                            Log.d("mapa", "Punto 3");

                            //SI ESTA EN RANGO
                            if(enRango(latitud,longitud)) {
                                //OCULTAR EL POPUP
                                puntoPopup.dismiss();
                                //COJER EL NOMBRE DEL JUEGO Y COMBERTIRLO EN LA CLASE DEL JUEGO Q NECESITAMOS ABRIR
                                Intent i = null;
                                //7try {
                                   // i = new Intent(getBaseContext(), Class.forName(nombreJuego));
                                    i = new Intent(getBaseContext(), Presentaciones.class);

                                    i.putExtra("idPuntoJuego",idPunto);
                                Log.d("mapa", "Punto 4");

                                //} catch (ClassNotFoundException e) {
                                 //   e.printStackTrace();
                               // }
                                Intent j=i;

                                //ABRIR JUEGO
                                hiloJuego = new Thread() {
                                    @Override
                                    public void run() {
                                        Log.d("mapa", "Punto 5");

                                        startActivityForResult(j,666);
                                    }
                                };

                                Log.d("mapa", "Paso 6");
                                hiloJuego.start();
                                //startActivityForResult(i,666);
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
        lastlocation = locationEngine.getLastLocation();
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 666){
            hiloJuego.interrupt();
            Log.d("Mapa","result");
            LimpiarPuntos();
            System.gc();
            CrearPuntos();
            newpista();


        }

    }
    public void newpista(){
        DatabaseAccess databaseAccess = new DatabaseAccess(this);

        int comp= databaseAccess.newpista();
        if(newPista<comp){
            newPista++;
        mostrarPista(idTextViewPista);}
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