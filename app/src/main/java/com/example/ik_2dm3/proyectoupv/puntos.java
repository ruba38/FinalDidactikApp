package com.example.ik_2dm3.proyectoupv;

public class puntos {
    private int idPunto;
    private String nombre;
    private double latitud;
    private double longitud;
    private String imagen;
    private String juego;
    private int idLugar;
    private int visible;
    private int terminado;
    private int secuencia;
    private String pista;



    public puntos(int idPunto, String nombre, double latitud, double longitud,String imagen,String juego,int idLugar,int visible, int terminado,int secuencia,String pista){
        this.idPunto =idPunto;
        this.nombre=nombre;
        this.longitud=longitud;
        this.latitud=latitud;
        this.imagen=imagen;
        this.juego=juego;
        this.idLugar=idLugar;
        this.visible=visible;
        this.terminado=terminado;
        this.secuencia=secuencia;
        this.pista=pista;
    }
    public int getidPunto(){
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
    public int getidLugar(){
        return idLugar;
    }
    public String getjuego()
    {
        return juego;
    }
    public int getSecuencia(){return secuencia;}
    public int getvisible(){
    return visible;
    }
    public int getterminado(){
        return terminado;
    }
    public String getpista(){
        return pista;
    }


}
