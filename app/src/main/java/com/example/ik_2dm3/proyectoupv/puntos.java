package com.example.ik_2dm3.proyectoupv;

public class puntos {
    private int idPunto;
    private String nombre;
    private double latitud;
    private double longitud;
    private String imagen;
    private String texto;
    private String audio;
    private String juego;
    private int visible;
    private int terminado;
    private long rango;


    public puntos(int lugarid, String nombre, double latitud, double longitud, String texto, String imagen, String audio, String juego, long rango, int visible, int terminado){
        this.idPunto =lugarid;
        this.nombre=nombre;
        this.longitud=longitud;
        this.latitud=latitud;
        this.imagen=imagen;
        this.texto=texto;
        this.audio=audio;
        this.juego=juego;
        this.visible=visible;
        this.terminado=terminado;
        this.rango = rango;
    }
    public int getlugarid(){
        return idPunto;
    }
    public String getnombre(){
        return nombre;
    }

    public double getlatitud(){
        return latitud;
    }
    public double getlongitud(){
        return longitud;
    }
    public String getimagen(){
        return imagen;
    }
    public String gettexto(){
        return texto;
    }
    public String getaudio()
    {
        return audio;
    }
    public String getjuego()
    {
        return juego;
    }
    public int getvisible(){
    return visible;
    }
    public int getterminado(){
        return terminado;
    }
    public long getRango() {
        return rango;
    }
    public void setRango(long rango) {
        this.rango = rango;
    }
}
