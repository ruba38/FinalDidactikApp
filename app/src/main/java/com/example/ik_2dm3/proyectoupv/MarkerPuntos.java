package com.example.ik_2dm3.proyectoupv;


import com.mapbox.mapboxsdk.annotations.Annotation;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;

public class MarkerPuntos extends Annotation {

    // Atributos
    private long id;
    private String nombre = "";
    private int ID_BD = 0;
    private long rango = 0;
    private String juego = "";
    private boolean terminado = false;
    private boolean visible = false;
    private MarkerOptions mO;

    // Constructor
    public MarkerPuntos() {
        this.id = 0;
        this.ID_BD = 0;
        this.nombre = "";
        this.rango = 0;
        this.juego = "";
        this.terminado = false;
        this.visible = false;
        this.mO = new MarkerOptions();
    }

    // Constructor copia
    public MarkerPuntos(MarkerPuntos mp) {
        this.id = mp.getId();
        this.ID_BD = mp.getID_BD();
        this.nombre = mp.getNombre();
        this.rango = mp.getRango();
        this.juego = mp.getJuego();
        this.terminado = mp.terminado;
        this.visible = mp.visible;
        this.mO = new MarkerOptions();
//        this.mO.getMarker().setId(mp.getmO().getMarker().getId());
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

    public void setId(long id) {
        this.id = id;
    }

    public Integer getID_BD() {
        return ID_BD;
    }

    public void setID_BD(Integer ID_BD) {
        this.ID_BD = ID_BD;
    }

    public long getRango() {
        return rango;
    }

    public void setRango(long rango) {
        this.rango = rango;
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
}

