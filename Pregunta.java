package com.example.registro_app;

import java.util.List;

public class Pregunta {

    public String PreguntaTexto ;
    public List<Respuesta> Respuestas ;
    public String Dificult ;
    public String Gender ;


    public Pregunta(String preguntaTexto, List<Respuesta> respuestas, String dificult, String gender) {
        this.PreguntaTexto = preguntaTexto;
        this.Respuestas = respuestas;
        this.Dificult = dificult;
        this.Gender = gender;
    }


    public String getPreguntaTexto() {
        return PreguntaTexto;
    }

    public void setPreguntaTexto(String preguntaTexto) {
        PreguntaTexto = preguntaTexto;
    }

    public List<Respuesta> getRespuestas() {
        return Respuestas;
    }

    public void setRespuestas(List<Respuesta> respuestas) {
        Respuestas = respuestas;
    }

    public String getDificult() {
        return Dificult;
    }

    public void setDificult(String dificult) {
        Dificult = dificult;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    @Override
    public String toString() {
        return "Pregunta{" +
                "PreguntaTexto='" + PreguntaTexto + '\'' +
                ", Respuestas=" + Respuestas +
                ", Dificult='" + Dificult + '\'' +
                ", Gender='" + Gender + '\'' +
                '}';
    }
}
