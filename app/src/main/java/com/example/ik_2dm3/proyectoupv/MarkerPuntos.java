package com.example.ik_2dm3.proyectoupv;


import com.mapbox.mapboxsdk.annotations.Annotation;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;

public class MarkerPuntos extends Annotation {

    // Atributos
    private long id;
    private String nombre = "";
    private int ID_BD = 0;
    private String juego = "";
    private int secuencia = 0;
    private boolean terminado = false;
    private boolean visible = false;
    private MarkerOptions mO;
    private String imagen="";
    private String pista="";

    // Constructor
    public MarkerPuntos() {
        this.id = 0;
        this.ID_BD = 0;
        this.nombre = "";
        this.juego = "";
        this.terminado = false;
        this.visible = false;
        this.secuencia = 0;
        this.imagen="";
        this.pista="";
        this.mO = new MarkerOptions();
    }

    // Constructor copia
    public MarkerPuntos(MarkerPuntos mp) {
        this.id = mp.getId();
        this.ID_BD = mp.getID_BD();
        this.nombre = mp.getNombre();
        this.juego = mp.getJuego();
        this.terminado = mp.terminado;
        this.visible = mp.visible;
        this.secuencia = mp.secuencia;
        this.imagen=mp.imagen;
        this.pista=mp.pista;
        this.mO = new MarkerOptions();
        //this.mO.getMarker().setId(mp.getmO().getMarker().getId());
        this.mO.setPosition(mp.getmO().getPosition());
        this.mO.setIcon(mp.getmO().getIcon());
    }
    public String getNombre() {
        return nombre;
    }

    // Getters y Setters

    public String getJuego() {
        return juego;
    }

    public void setJuego(String juego) {
        this.juego = juego;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getID_BD() {
        return ID_BD;
    }

    public void setID_BD(int ID_BD) {
        this.ID_BD = ID_BD;
    }

    public boolean isTerminado() {
        return terminado;
    }

    public void setTerminado(boolean terminado) {
        this.terminado = terminado;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public MarkerOptions getmO() {
        return mO;
    }

    public void setmO(MarkerOptions mO) {
        this.mO = mO;
    }

    public double getLatitude(){
        return this.getmO().getPosition().getLatitude();
    }
    public double getLongitude(){
        return this.getmO().getPosition().getLongitude();
    }

    public int getSecuencia() {return secuencia;}

    public void setSecuencia(int secuencia) {this.secuencia = secuencia;}

    public String getImagen() {return imagen;}

    public void setImagen(String imagen) {this.imagen = imagen;}

    public String getPista() {
        return pista;
    }

    public void setPista(String pista) {
        this.pista = pista;
    }
}

