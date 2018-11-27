package com.example.ik_2dm3.proyectoupv;

public class lugares {
    private int idLugar;
    private String nombre;
    private double coordenadaLimite1Lat;
    private double coordenadaLimite1Lon;
    private double coordenadaLimite2Lat;
    private double coordenadaLimite2Lon;

    public lugares(int idLugar, String nombre, double coordenadaLimite1Lat, double coordenadaLimite1Lon, double coordenadaLimite2Lat, double coordenadaLimite2Lon) {
        this.idLugar = idLugar;
        this.nombre = nombre;
        this.coordenadaLimite1Lat = coordenadaLimite1Lat;
        this.coordenadaLimite1Lon = coordenadaLimite1Lon;
        this.coordenadaLimite2Lat = coordenadaLimite2Lat;
        this.coordenadaLimite2Lon = coordenadaLimite2Lon;
    }

    public int getIdLugar() {
        return idLugar;
    }

    public String getNombre() {
        return nombre;
    }

    public double getCoordenadaLimite1Lat() {
        return coordenadaLimite1Lat;
    }

    public double getCoordenadaLimite1Lon() {
        return coordenadaLimite1Lon;
    }

    public double getCoordenadaLimite2Lat() {
        return coordenadaLimite2Lat;
    }

    public double getCoordenadaLimite2Lon() {
        return coordenadaLimite2Lon;
    }
}

