package com.example.ik_2dm3.proyectoupv;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Debug;
import android.util.Log;

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
            Log.e("mytag","pre file");
            File dbFile = new File(DB_PATH + DB_NAME);
            Log.e("mytag","post file");
            if (dbFile.exists()){
                Log.e("mytag","entra borrar");
                dbFile.delete();
            }
        }
        else {
            Log.e("mytag","entra else");
            db_Read = this.getReadableDatabase();
            db_Read.close();
            Log.e("mytag","final else");
        }
        try{
            copyDataBase(context);
        }catch (IOException e){
            throw new Error ("Error copiando BD");
        }
        // }

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
    public Object getPuntos(){
        //CARGA TODOS LOS DATOS DE LA TABLA PUNTOS EN UN ARRAYLIST
        ArrayList <puntos>datoslista = new ArrayList<puntos>();
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM puntos", null);
        cursor.moveToFirst();
        for(int i=0;!cursor.isAfterLast();i++) {
            int lugarid= cursor.getInt(cursor.getColumnIndex("idPunto"));
            String nombre= cursor.getString(cursor.getColumnIndex("nombre"));
            double latitud= cursor.getDouble(cursor.getColumnIndex("latitud"));
            double longitud= cursor.getDouble(cursor.getColumnIndex("longitud"));
            String imagen= cursor.getString(cursor.getColumnIndex("imagen"));
            String texto= cursor.getString(cursor.getColumnIndex("texto"));
            String audio= cursor.getString(cursor.getColumnIndex("audio"));
            String juego= cursor.getString(cursor.getColumnIndex("juego"));
            int visible= cursor.getInt(cursor.getColumnIndex("visible"));
            int terminado= cursor.getInt(cursor.getColumnIndex("terminado"));
            long rango = cursor.getLong((cursor.getColumnIndex("rango")));
            puntos d = new puntos(lugarid, nombre, latitud, longitud, imagen, texto, audio, juego, rango, visible,terminado);
            datoslista.add(d);
            cursor.moveToNext();
        }
        cursor.close();
        return datoslista;
    }
    //VUELVE VISIBLE EL SIGIENTE PUNTO Y MARCA COMO TERMINADO EL ACTUAL
    public void setVisible(int x){
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getWritableDatabase();
        db.execSQL("UPDATE puntos SET visible=1 WHERE idPunto="+x);
    }
    public void setTerminado(int x){
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getWritableDatabase();
        db.execSQL("UPDATE puntos SET terminado=1 WHERE idPunto="+x);
    }
    public int getVisible(int x){
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT visible FROM puntos where idPunto="+x, null);
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex("visible"));
    }
    public int getTerminado(int x){
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT terminado FROM puntos where idPunto="+x, null);
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex("terminado"));

    }
    //RESETEA LOS PUNTOS A SU ESTADO ORIGINAL
    public void resetApp(){


    }

    public boolean getTerminadoAnterior(int id) {

        if(id == 0) { return true; }
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT terminado FROM puntos where idPunto="+(id-1), null);
        cursor.moveToFirst();

        if (cursor.getInt(cursor.getColumnIndex("terminado")) == 1) {
            return true;
        } else {
            return false;
        }

    }

    //PONE TODOS LOS PUNTOS EN VISIBLE
    public void setAllVisible(){
        Log.d("mytag", "entra setallvisible");

        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getWritableDatabase();
        db.execSQL("UPDATE puntos SET visible=1");
        Log.d("mytag", "termina setallvisible");
        db.close();
    }

    //DEVUELVE TODOS LOS PUNTO A SU ESTADO ANTERIOR***
    public void reverseAllVisible(){
        Log.d("mytag", "termina reverse");

        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM puntos", null);
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
    }
}