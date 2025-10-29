package org.example.tarea2_estdatos;

public class ParCaracterCodigo {
    private int caracter;
    private String codigo;
    private int frecuencia;

    public ParCaracterCodigo(char caracter, String codigo, int frecuencia) {
        this.caracter = caracter;
        this.codigo = codigo;
        this.frecuencia = frecuencia;
    }

    public int getCaracter() {
        return caracter;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getFrecuencia() {
        return frecuencia;
    }
}
