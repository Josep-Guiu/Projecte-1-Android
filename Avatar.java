package com.example.registro_app;

import java.io.Serializable;

public class Avatar implements Serializable {

    public String Nombre;
    public String Gender;
    public int PuntuacionMin;
    public int PuntuacionMax;
    public String Ruta;

    public Avatar(String nombre, String gender, int puntuacionMin, int puntuacionMax, String ruta) {
        Nombre = nombre;
        Gender = gender;
        PuntuacionMin = puntuacionMin;
        PuntuacionMax = puntuacionMax;
        Ruta = ruta;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public int getPuntuacionMin() {
        return PuntuacionMin;
    }

    public void setPuntuacionMin(int puntuacionMin) {
        PuntuacionMin = puntuacionMin;
    }

    public int getPuntuacionMax() {
        return PuntuacionMax;
    }

    public void setPuntuacionMax(int puntuacionMax) {
        PuntuacionMax = puntuacionMax;
    }

    public String getRuta() {
        return Ruta;
    }

    public void setRuta(String ruta) {
        Ruta = ruta;
    }

}
