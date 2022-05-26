package com.example.registro_app;

public class Respuesta {
    String Texto;
    boolean IsTrue;

    public Respuesta(String Texto, boolean IsTrue) {
        Texto = Texto;
        this.IsTrue = IsTrue;
    }

    public String getTexto() {
        return Texto;
    }

    public boolean isTrue() {
        return IsTrue;
    }


    public void setTexto(String Texto) {
        Texto = Texto;
    }

    public void setTrue(boolean aTrue) {
        IsTrue = aTrue;
    }

    @Override
    public String toString() {
        return "Respuesta{" +
                "Texto='" + Texto + '\'' +
                ", isTrue=" + IsTrue +
                '}';
    }
}
