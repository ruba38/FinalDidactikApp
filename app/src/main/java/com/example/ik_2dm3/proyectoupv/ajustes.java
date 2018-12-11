package com.example.ik_2dm3.proyectoupv;

import android.content.Context;

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
