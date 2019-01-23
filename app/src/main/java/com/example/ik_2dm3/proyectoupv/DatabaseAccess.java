package com.example.ik_2dm3.proyectoupv;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Debug;
import android.util.Log;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class DatabaseAccess extends SQLiteOpenHelper {
    private static String DB_PATH = "/data/data/com.example.ik_2dm3.proyectoupv/databases/";
    private static String DB_NAME = "upv.s3db";
    private static final int DB_VERSION = 3;
    private SQLiteDatabase db;
    // LLAMAMOS A LA FUNCION QUE CREA LA BASE DE DATOS
    public DatabaseAccess(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        startdb(context);
    }
    public void startdb(Context context){
        try{
            createDataBase(context);
            //copyDataBase(context);
        } catch(IOException e){
            Log.d("mytag", "ERROR COPYDATABASE");
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("mytag", "ESTOY EN ONCREATE MYOPENHELPER");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
        //COMPRUEBA SI EXISTE LA BASE DE DATOS, SI EXISTE LA BORRA. DESPUES LLAMA A LA FUNCION QUE COPIA LA BASE DE DATOS
    private void createDataBase (Context context) throws IOException{

        boolean dbExist = checkDataBase();
        SQLiteDatabase db_Read = null;

        if (dbExist){
            //la bd ya existe
           //context.deleteDatabase("bdeprueba.bd");
            // // Log.e("mytag","pre file");
            File dbFile = new File(DB_PATH + DB_NAME);
            // // Log.e("mytag","post file");
            /*if (dbFile.exists()){
                // // Log.e("mytag","entra borrar");
                dbFile.delete();
            }*/
        }
        else {
            // // Log.e("mytag","entra else");
            db_Read = this.getReadableDatabase();
            db_Read.close();
            // // Log.e("mytag","final else");


            try{
                copyDataBase(context);
            }catch (IOException e){
                throw new Error ("Error copiando BD");
            }
        }

    }
    //DEBUELVE TRUE SI LA BASE DE DATOS EXISTE
    public boolean checkDataBase(){
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase(Context context) throws IOException {
        //COPIA LA BASE DE DATOS DESDE EL ARCHIVO .DB

        String DB_PATH = context.getDatabasePath(DB_NAME).getPath();
        Log.d("mytag", "av er"+DB_PATH);
        // Open your local db as the input stream
        InputStream myInput = context.getAssets().open("databases/"+DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
        /*Static.getSharedPreference(context).edit()
                .putInt("DB_VERSION", DB_VERSION).commit();*/
    }
    //############################################################################################## TABLA PUNTOS
    public Object getPuntos(int Lugar){
        //CARGA TODOS LOS DATOS DE LA TABLA LUGARES EN UN ARRAYLIST
        ArrayList <puntos>datoslista = new ArrayList<puntos>();
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM puntos where idLugar="+Lugar+" ORDER BY idLugar", null);
        cursor.moveToFirst();
        for(int i=0;!cursor.isAfterLast();i++) {
            int idPunto= cursor.getInt(cursor.getColumnIndex("idPunto"));
            String nombre= cursor.getString(cursor.getColumnIndex("nombre"));
            double latitud= cursor.getDouble(cursor.getColumnIndex("latitud"));
            double longitud= cursor.getDouble(cursor.getColumnIndex("longitud"));
            String imagen= cursor.getString(cursor.getColumnIndex("imagen"));
            String juego= cursor.getString(cursor.getColumnIndex("juego"));
            int idLugar= cursor.getInt(cursor.getColumnIndex("idLugar"));
            int visible= cursor.getInt(cursor.getColumnIndex("visible"));
            int terminado= cursor.getInt(cursor.getColumnIndex("terminado"));
            int secuencia= cursor.getInt(cursor.getColumnIndex("secuencia"));
            String pista=cursor.getString(cursor.getColumnIndex("pista"));
            puntos d = new puntos(idPunto, nombre, latitud, longitud, imagen,juego,idLugar,visible,terminado,secuencia,pista);
            datoslista.add(d);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return datoslista;
    }

    //VUELVE VISIBLE EL SIGIENTE PUNTO Y MARCA COMO TERMINADO EL ACTUAL
    public void setVisible(int x){
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getWritableDatabase();
        db.execSQL("UPDATE puntos SET visible=1 WHERE idPunto="+x);
        db.close();
    }
    public void setTerminado(int x){
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getWritableDatabase();
        db.execSQL("UPDATE puntos SET terminado=1 WHERE idPunto="+x);
        db.close();

    }
    public int getVisible(int x){
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT visible FROM puntos where idPunto="+x, null);
        cursor.moveToFirst();
        cursor.close();
        db.close();
        return cursor.getInt(cursor.getColumnIndex("visible"));
    }
    public int getTerminado(int x){
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT terminado FROM puntos where idPunto="+x, null);
        cursor.moveToFirst();
        cursor.close();
        db.close();
        return cursor.getInt(cursor.getColumnIndex("terminado"));

    }
    //RESETEA LOS PUNTOS A SU ESTADO ORIGINAL
    public void resetApp(int x){
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getWritableDatabase();
        db.execSQL("UPDATE puntos SET visible=0,terminado=0 WHERE idLugar="+x);
        db.close();

    }
    public void iniciarApp(int x){
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getWritableDatabase();
        db.execSQL("UPDATE puntos SET visible=1 WHERE idLugar="+x+" AND secuencia=1");
        db.close();

    }

    public boolean getTerminadoAnterior(int secuencia,int id) {

        if(secuencia == 1) { return true; }
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT terminado FROM puntos where secuencia="+(secuencia-1)+" And idLugar="+id, null);
        cursor.moveToFirst();

        if (cursor.getInt(cursor.getColumnIndex("terminado")) == 1) {
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }

    public String getPista(int idL,int secuencia){
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT pista FROM puntos where secuencia="+secuencia+" And idLugar="+idL, null);
        cursor.moveToFirst();
        cursor.close();
        db.close();
        return cursor.getString(cursor.getColumnIndex("pista"));
    }

    //PONE TODOS LOS PUNTOS EN VISIBLE
    public void setAllVisible(int x){
        Log.d("mytag", "entra setallvisible");

        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getWritableDatabase();
        db.execSQL("UPDATE puntos SET visible=1 Where idLugar="+x);
        Log.d("mytag", "termina setallvisible");
        db.close();
    }

    //DEVUELVE TODOS LOS PUNTO A SU ESTADO ANTERIOR***
    public void reverseAllVisible(int x){
        Log.d("mytag", "termina reverse");

        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM puntos where idLugar="+x, null);
        Log.d("mytag", "pre do recerse");
        cursor.moveToFirst();
        do{
            Log.d("mytag", "entyra do reverse");
            int id4 = cursor.getInt(cursor.getColumnIndex("idPunto"));
            Log.d("mytag", "muestra id"+ id4);

            if(cursor.getInt(cursor.getColumnIndex("terminado"))==0){
                Log.d("mytag", "entra if do");

                db.execSQL("UPDATE puntos SET visible=0 WHERE idPunto="+id4);
                int prueba = cursor.getInt(cursor.getColumnIndex("idPunto"));
                Log.d("mytag", "prueba curdor: "+prueba);
            }else{
                Log.d("mytag", "entra else do");

            }
            cursor.moveToNext();
        }while(!cursor.isAfterLast());
        cursor.moveToFirst();
        do{
            Log.d("mytag", "entra do 2");

            if(cursor.getInt(cursor.getColumnIndex("terminado"))==0){
                Log.d("mytag", "entra if 2");

                int id = cursor.getInt(cursor.getColumnIndex("idPunto"));
                db.execSQL("UPDATE puntos SET visible=1 WHERE idPunto="+id);
                Log.d("mytag", "termina if 2");
                break;
            }
            cursor.moveToNext();
        }while(!cursor.isAfterLast());
        cursor.close();
        db.close();

    }
    public void setRango(double x,int id){
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getWritableDatabase();
        db.execSQL("UPDATE puntos SET rango="+x+" WHERE idPunto="+id);
        db.close();
    }
    //############################################################################################## TABLA LUGARES
    public Object getLugares(){

        //CARGA TODOS LOS DATOS DE LA TABLA LUGARES EN UN ARRAYLIST
        ArrayList <lugares>datosLugares = new ArrayList<lugares>();
        String myPath = DB_PATH + DB_NAME;
          db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM lugares ORDER BY idLugar", null);
        cursor.moveToFirst();
        for(int i=0;!cursor.isAfterLast();i++) {
            int idLugar= cursor.getInt(cursor.getColumnIndex("idLugar"));
            String nombre= cursor.getString(cursor.getColumnIndex("nombre"));
            double coordenadaLimite1Lat= cursor.getDouble(cursor.getColumnIndex("coordenadaLimite1Lat"));
            double coordenadaLimite1Lon= cursor.getDouble(cursor.getColumnIndex("coordenadaLimite1Lon"));
            double coordenadaLimite2Lat= cursor.getDouble(cursor.getColumnIndex("coordenadaLimite2Lat"));
            double coordenadaLimite2Lon= cursor.getDouble(cursor.getColumnIndex("coordenadaLimite2Lon"));

            lugares d = new lugares(idLugar, nombre, coordenadaLimite1Lat, coordenadaLimite1Lon, coordenadaLimite2Lon,coordenadaLimite2Lat);
            datosLugares.add(d);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return datosLugares;
    }

    public String getImajen(int Lugar){
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT imagen FROM puntos where idPunto="+Lugar, null);
        cursor.moveToFirst();
        String imagen= cursor.getString(cursor.getColumnIndex("imagen"));
        cursor.close();
        db.close();
        return imagen;

    }

    //############################################################################################## TABLA AJUSTES
    public String getnombrejuego(int idpunto){
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT juego FROM puntos where idPunto="+idpunto, null);
        cursor.moveToFirst();
        String nombrejuego= cursor.getString(cursor.getColumnIndex("juego"));
        cursor.close();
        db.close();
        Log.d("mytag", "devolucion juego"+ nombrejuego);

        return nombrejuego;
    }
    public String getAjustes(String opcion){
        int idAjuste=1;
        int sonido=0;
        int musica=0;
        int mapa=0;
        String idioma="";
        //CARGA TODOS LOS DATOS DE LA TABLA LUGARES EN UN ARRAYLIST
        ArrayList <ajustes>datosAjustes = new ArrayList<ajustes>();
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ajustes where idAjuste=1", null);
        cursor.moveToFirst();
        for(int i=0;!cursor.isAfterLast();i++) {
            idAjuste= cursor.getInt(cursor.getColumnIndex("idAjuste"));
            sonido = cursor.getInt(cursor.getColumnIndex("sonido"));
            musica= cursor.getInt(cursor.getColumnIndex("musica"));
            mapa= cursor.getInt(cursor.getColumnIndex("mapa"));
            idioma= cursor.getString(cursor.getColumnIndex("idioma"));



            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        //return datosAjustes;

        switch (opcion) {
            case "idAjuste" :

                return String.valueOf(idAjuste);
            case "sonido" :
                return String.valueOf(sonido);
            case "musica" :
                return String.valueOf(musica);
            case "mapa" :
                return String.valueOf(mapa);
            case "idioma" :
                return idioma;
            default:
                return String.valueOf(0);

        }
    }

    // Obtiene las coordenadas de la zona
    public LatLngBounds getLimiteZona(int id) {

        LatLngBounds zona;
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM lugares WHERE idLugar = id", null);
        cursor.moveToFirst();

        for(int i=0;!cursor.isAfterLast();i++) {
            int idLugar= cursor.getInt(cursor.getColumnIndex("idLugar"));
            String nombre= cursor.getString(cursor.getColumnIndex("nombre"));
            double coordenadaLimite1Lat = cursor.getDouble(cursor.getColumnIndex("coordenadaLimite1Lat"));
            double coordenadaLimite1Lon = cursor.getDouble(cursor.getColumnIndex("coordenadaLimite1Lon"));
            double coordenadaLimite2Lat = cursor.getDouble(cursor.getColumnIndex("coordenadaLimite2Lat"));
            double coordenadaLimite2Lon = cursor.getDouble(cursor.getColumnIndex("coordenadaLimite2Lon"));
            zona  = new LatLngBounds.Builder()
                    .include(new LatLng(coordenadaLimite1Lat, coordenadaLimite1Lon))
                    .include(new LatLng(coordenadaLimite2Lat, coordenadaLimite2Lon))
                    .build();
            cursor.close();
            return zona;
        }

        return null;
    }


}