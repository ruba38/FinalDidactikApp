package com.example.ik_2dm3.proyectoupv;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
        //COMPRUEBA SI EXISTE LA BASE DE DATOS, SI EXISTE LA BORRA. DESPUES LLAMA A LA FUNCION QUE COPIA LA BASE DE DATOS
    private void createDataBase (Context context) throws IOException{
        boolean dbExist = checkDataBase();
        SQLiteDatabase db_Read = null;
        if (dbExist){
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists()){
                dbFile.delete();
            }
        }
        else {
            db_Read = this.getReadableDatabase();
            db_Read.close();
        }
        try{
            copyDataBase(context);
        }catch (IOException e){
            throw new Error ("Error copiando BD");
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
        InputStream myInput = context.getAssets().open("databases/"+DB_NAME);
        String outFileName = DB_PATH;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }


    public Object getLugares(){
        //CARGA TODOS LOS DATOS DE LA TABLA LUGARES EN UN ARRAYLIST
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

    //VUELVE VISIBLE EL SIGIENTE PUNTO Y MARCA COMO TERMINADO EL ACTUAL******CORREGIR
    public void setvisible(int x){
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getWritableDatabase();
          int id= x+1;
          db.execSQL("UPDATE puntos SET visible=1 WHERE idPunto="+id);
    }

    public void setTerminado(int x){
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getWritableDatabase();
        db.execSQL("UPDATE puntos SET terminado=1 WHERE idPunto="+x);
    }

    //PONE TODOS LOS PUNTOS EN VISIBLE
    public void setAllVisible(){
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getWritableDatabase();
        db.execSQL("UPDATE puntos SET visible=1");
        db.close();
    }

    //DEVUELVE TODOS LOS PUNTO A SU ESTADO ANTERIOR***
    public void reverseAllVisible(){
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM puntos", null);
        cursor.moveToFirst();
        do{
            int id4 = cursor.getInt(cursor.getColumnIndex("idPunto"));
            if(cursor.getInt(cursor.getColumnIndex("terminado"))==0){
                db.execSQL("UPDATE puntos SET visible=0 WHERE idPunto="+id4);
                int prueba = cursor.getInt(cursor.getColumnIndex("idPunto"));
            }
            cursor.moveToNext();
        }while(!cursor.isAfterLast());
        cursor.moveToFirst();
        do{
            if(cursor.getInt(cursor.getColumnIndex("terminado"))==0){
                int id = cursor.getInt(cursor.getColumnIndex("idPunto"));
                db.execSQL("UPDATE puntos SET visible=1 WHERE idPunto="+id);
                break;
            }
            cursor.moveToNext();
        }while(!cursor.isAfterLast());
        cursor.close();
        db.close();
    }
}