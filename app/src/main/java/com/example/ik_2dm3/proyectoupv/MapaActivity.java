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
    private Button idBtnMapaAdmin, idBtnMapaLimpiar, idBtnMapaAjustes;
    private Dialog puntoPopup;
    //creacion de imagen
    private byte[] decodedString;
    private Bitmap decodedByte;

    private Drawable drawable;
    // Variables de datos
    private ArrayList<MarkerPuntos> PuntosInteres = new ArrayList<MarkerPuntos>();
    private ArrayList <Location>PuntosLocation = new ArrayList<Location>();
    private boolean admin = false;
    private int idPunto,secuencia;
    private String juego,titulo,imagen;
    private double latitud,longitud;
    private double RangoGeneral=10.0;
    private int Lugar=2;
    // Objetos/Variables de depuracion
    private TextView coordenadas,idTextViewDistancia,idTextViewProgreso,idTextViewMapaProgresoPuntos;
    int contPuntos=0;
    double distancia2=0.0;
    int id_bd;
    int secuencia2;








    // Limite de la camara de la zona sleccionada
    private static final LatLngBounds coordsLimite = new LatLngBounds.Builder()
            .include(new LatLng(43.258316, -2.903066))
            .include(new LatLng(43.256749, -2.908320))
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Quitar titulo
        getSupportActionBar().hide();
        // Se crea la instancia del mapa con la clave de acceso
        Log.d("descarga", "punto 1");
        Mapbox.getInstance(this, getString(R.string.access_token));

        Log.d("descarga", "Conectado: "+Mapbox.isConnected());

        setContentView(R.layout.activity_mapa);

        // Instancia el objeto Popup
        puntoPopup = new Dialog(this);

        // Instanciacion de los objetos del mapa
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        Log.d("descarga", "LiveRegion: "+mapView.getWindowToken());

        // Cargamos el estilo personalizado
        mapView.setStyleUrl("mapbox://styles/mariusinfo/cjoshzhqg3sb22sme5eyoogt4");


        // Asignacion de objetos

        coordenadas = findViewById(R.id.coords);
        idTextViewDistancia = findViewById(R.id.idTextViewDistancia);
        idTextViewProgreso = findViewById(R.id.idTextViewProgreso);
        //idTextViewMapaProgresoPuntos = findViewById(R.id.idTextViewMapaProgresoPuntos);
        idBtnMapaAdmin = findViewById(R.id.idBtnMapaAdmin);
        idBtnMapaLimpiar = findViewById(R.id.idBtnMapaLimpiar);
        idBtnMapaAjustes = findViewById(R.id.idBtnMapaAjustes);

        // Instanciacion de objetos de la base de datos
        DatabaseAccess databaseAccess = new DatabaseAccess(this);
        databaseAccess.startdb(getBaseContext());

        // Botones para administración
        idBtnMapaAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (admin==false) {
                    // Cambia a Visible = true, todos los puntos
                    databaseAccess.setAllVisible();
                    admin=true;
                }else{
                    // Cambia a Visible = false, excepto los que esten terminados y el siguiente
                    databaseAccess.reverseAllVisible();
                    admin=false;
                }

                // Limpiamos los puntos
                LimpiarPuntos();

                // Los volvemos a crear
                CrearPuntos();
            }
        });

        idBtnMapaLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Carga de nuevo la base de datos
                DatabaseAccess databaseAccess = new DatabaseAccess(getBaseContext());
                databaseAccess.startdb(getBaseContext());

                // Limpia los objetos actuales
                LimpiarPuntos();

                // Crea de nuevo los objetos originales
                CrearPuntos();
            }

        });

        idBtnMapaAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AjustesActivity.class);
                startActivity(i);}
        });
    }

    // TODO: Pendiente comentar
    @Override
    public void onLocationChanged(Location location) {

        DatabaseAccess databaseAccess =new DatabaseAccess(this);

        Location ubicacionPunto = new Location("");
        Location ubicacionUsuario = new Location("");
        Location ubicacionPunto2 = new Location("");


        ubicacionUsuario.setLatitude(location.getLatitude());
        ubicacionUsuario.setLongitude(location.getLongitude());

        for(int i = 0; i < PuntosInteres.size(); i++) {
            id_bd=PuntosInteres.get(i).getID_BD();
            secuencia2=PuntosInteres.get(i).getSecuencia();
            ubicacionPunto.setLatitude(PuntosInteres.get(i).getLatitude());
            ubicacionPunto.setLongitude(PuntosInteres.get(i).getLongitude());

            if(databaseAccess.getTerminadoAnterior(secuencia2,Lugar)){
                ubicacionPunto2.setLatitude(PuntosInteres.get(i).getLatitude());
                ubicacionPunto2.setLongitude(PuntosInteres.get(i).getLongitude());

                distancia2 = ubicacionUsuario.distanceTo(ubicacionPunto);
                contPuntos=contPuntos+1;
            }
            double distancia = ubicacionUsuario.distanceTo(ubicacionPunto);

            if(distancia2 < RangoGeneral && databaseAccess.getTerminadoAnterior(secuencia2,Lugar)){
                databaseAccess.setVisible(id_bd);
                LimpiarPuntos();
                CrearPuntos();

            }
            String metrica;
            double textoDistancia;
            if(distancia2>1000){
                metrica="Km";
                textoDistancia=distancia2/1000;
            }else{
                metrica="m";
                textoDistancia=distancia2;
            }
            String tempString=getResources().getText(R.string.PunosDistancia)+""+String.format("%.2f",textoDistancia)+""+metrica+" "+getResources().getText(R.string.PuntoProgreso)+""+contPuntos+"/"+PuntosInteres.size()+" ";
            String tituloDistancia= getResources().getText(R.string.PunosDistancia)+"";
            String Distancia=String.format("%.2f", textoDistancia)+""+metrica+" ";
            String tituloProgreso= getResources().getText(R.string.PuntoProgreso)+"";
            String progreso=""+contPuntos+"/"+PuntosInteres.size();

            SpannableString texto = new SpannableString(tempString);
            texto.setSpan(new StyleSpan(Typeface.BOLD), 0, tituloDistancia.length(), 0);
            texto.setSpan(new StyleSpan(Typeface.NORMAL), tituloDistancia.length(),tituloDistancia.length()+Distancia.length(), 0);
            texto.setSpan(new StyleSpan(Typeface.NORMAL), tituloDistancia.length()+Distancia.length(), tituloDistancia.length()+Distancia.length(), 0);
            texto.setSpan(new StyleSpan(Typeface.BOLD), tituloDistancia.length()+Distancia.length(), tituloDistancia.length()+Distancia.length()+tituloProgreso.length(), 0);
            texto.setSpan(new StyleSpan(Typeface.NORMAL), tituloDistancia.length()+Distancia.length()+tituloProgreso.length(), tituloDistancia.length()+Distancia.length()+tituloProgreso.length()+progreso.length(), 0);

            //coordenadas.setText(texto);
            idTextViewDistancia.setText(String.format("%.2f",textoDistancia)+""+metrica);
            idTextViewProgreso.setText(contPuntos+"/"+PuntosInteres.size());
        }

    }


    //comprobar si el punto esta en rango
    public boolean enRango(double latitud,double longitud) {
        Location ubicacionPunto = new Location("");
        @SuppressLint("MissingPermission") Location ubicacionUsuario = locationEngine.getLastLocation();

        //UBICACION DEL PUNTO
        ubicacionPunto.setLatitude(latitud);
        ubicacionPunto.setLongitude(longitud);
        //mirar si esta en rango
        double distancia = ubicacionUsuario.distanceTo(ubicacionPunto);
        if(distancia<RangoGeneral){
            return true;
        }else{
            return false;
        }
    }

    public void toImg(String byteArray){

        decodedString = Base64.decode(byteArray, Base64.DEFAULT);
        decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        //Convert bitmap to drawable
        drawable = new BitmapDrawable(getResources(), decodedByte);
        Log.d("mitag","toImg drawable =>"+drawable);
    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {

        Log.d("descarga", "punto 2");
        // Asignamos el objeto a la instancia actual
        MapaActivity.this.map = mapboxMap;

        // Añadimos el Listener del mapa
        mapboxMap.addOnMapClickListener(this);

        // Habilitamos la localizacion del usuario
        enableLocation();

        // Zoom min y Max del mapa
       /* map.setMinZoomPreference(16);
        map.setMaxZoomPreference(17.5);
        mapboxMap.setLatLngBoundsForCameraTarget(coordsLimite);*/

        // Creamos los puntos
        CrearPuntos();

        // TODO: Acabar comentarios, implementar juegos
        mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {

                // Recorremos el array que contiene los marcadores
                for(int i = 0; i < PuntosInteres.size(); i++) {
                    // Comprueba si la posicion del marcador clickado coincide con algun punto
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
                //MOSTRAR POPUP
                //DECLARARCIONES
                Button idBtnPopupCerrar,idBtnPopupJugar;
                TextView idTextViewPopupTitulo;
                ImageView imagenPopup;
                puntoPopup.setContentView(R.layout.popup_punto);//abrir layout que contiene el popup
                    //TITULO
                    idTextViewPopupTitulo = puntoPopup.findViewById(R.id.idTextViewPopupTitulo);
                    idTextViewPopupTitulo.setText(titulo);
                    //IMAGEN
                    imagenPopup=puntoPopup.findViewById(R.id.imagenPopup);
                    toImg(imagen);
                    imagenPopup.setBackground(drawable);
                    //JUGAR POPUP
                    idBtnPopupJugar = (Button) puntoPopup.findViewById(R.id.idBtnPopupJugar);
                        if(enRango(latitud,longitud)==false) {
                            idBtnPopupJugar.setBackgroundColor(getColor(R.color.Desabilitado));
                        }
                    idBtnPopupJugar.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if(enRango(latitud,longitud)) {
                                puntoPopup.dismiss();//oculta el popup
                                String nombreJuego = "com.example.ik_2dm3.proyectoupv." + juego;
                                nombreJuego = nombreJuego.replace(" ", "");
                                Intent i = null;
                                try {
                                    i = new Intent(getBaseContext(), Class.forName(nombreJuego));
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }

                                startActivity(i);
                            }else{
                                Context context = getApplicationContext();
                                CharSequence text = "NO ESTAS EN RANGO";
                                int duration = Toast.LENGTH_LONG;

                                Toast toastRango = Toast.makeText(context, text, duration);
                                toastRango.show();
                            }
                        }
                    });

                //CERRAR POPUP
                idBtnPopupCerrar = (Button) puntoPopup.findViewById(R.id.idBtnPopupCerrar);//declarar boton cerrar del popup
                idBtnPopupCerrar.setOnClickListener(new View.OnClickListener() {//al dar click se ejecuta esta funcion
                    @Override
                    public void onClick(View v) {
                        PuntoTerminado(idPunto);
                        puntoPopup.dismiss();//oculta el popup
                    }
                });
                puntoPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                puntoPopup.setCanceledOnTouchOutside(false);
                puntoPopup.show();//mostar popup
                return false;
            }
        });

    }

    private void enableLocation() {
        // Comprueba si la aplicacion tiene permisos para ubicar el dispositivo, si no tiene los pide
        if(PermissionsManager.areLocationPermissionsGranted(this)) {
            initializeLocationEngine();
            initializeLocationLayer();
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onPermissionResult(boolean granted) {
        // Si el usuario acepta dar permiso para utilizar el gps
        if(granted) {
            enableLocation();
        }
    }

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

    // TODO: Comentar, implementar juegos
    private void PuntoTerminado(int idPunto){
        DatabaseAccess databaseAccess =new DatabaseAccess(this);
    /*   databaseAccess.setvisible(idPunto);*/
     databaseAccess.setTerminado(idPunto);
        LimpiarPuntos();
        CrearPuntos();
    }

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

    // Metodos necesarios para que el mapa funcione
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