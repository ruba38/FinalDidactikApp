package com.example.ik_2dm3.proyectoupv;

import java.util.Objects;

public class JuegoPuzzleParticipantes {

    private String nombre;
    private int numVotos;


    public JuegoPuzzleParticipantes () {
        this.nombre = "";
        this.numVotos = 1;
    }

    public JuegoPuzzleParticipantes (String nombre) {
        this.nombre = nombre;
        this.numVotos = 1;
    }

    public JuegoPuzzleParticipantes (JuegoPuzzleParticipantes p) {
        this.nombre = p.nombre;
        this.numVotos = p.numVotos;
    }


    public String getNombre() {
        return nombre;
    }

    public Integer getNumVotos() {
        return numVotos;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNumVotos(int num) {
        this.numVotos = num;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JuegoPuzzleParticipantes that = (JuegoPuzzleParticipantes) o;
        return Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nombre);
    }
}
