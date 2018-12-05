package com.example.ik_2dm3.proyectoupv;

import android.content.Context;
import android.util.Log;

public class ajustes extends DatabaseAccess {
    int idAjustes;
    int sonido;
    int musica;
    int mapa;
    String idioma;

    public ajustes(Context cosa) {
        super(cosa);
        DatabaseAccess databaseAccess = new DatabaseAccess(cosa);
        this.idAjustes = 1;
        Log.d("ajustes","SIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII"+databaseAccess.getmierda());
        /*this.sonido =1;
        this.musica =1;
        this.mapa =1;
        this.idioma ="hola";
         this.sonido =Integer.parseInt(databaseAccess.getAjustes("sonido"));
       this.musica =Integer.parseInt(databaseAccess.getAjustes("musica"));
        this.mapa =Integer.parseInt(databaseAccess.getAjustes("mapa"));
        this.idioma = (String) databaseAccess.getAjustes("imagen");*/
    }


    public int getIdAjustes() {
        return idAjustes;
    }

    public void setIdAjustes(int idAjustes) {
        this.idAjustes = idAjustes;
    }

    public int getSonido() {
        return sonido;
    }

    public void setSonido(int sonido) {
        this.sonido = sonido;
    }

    public int getMusica() {
        return musica;
    }

    public void setMusica(int musica) {
        this.musica = musica;
    }

    public int getMapa() {
        return mapa;
    }

    public void setMapa(int mapa) {
        this.mapa = mapa;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
}
