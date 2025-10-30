package org.example.tarea2_estdatos;

public class ParByteFrecuencia {
    private byte[] byteCaracter;
    private int frecuencia;

    public ParByteFrecuencia(byte[] byteCaracter, int frecuencia) {
        this.byteCaracter = byteCaracter;
        this.frecuencia = frecuencia;
    }

    public byte[] getBytesCaracter() {
        return byteCaracter;
    }

    public int getFrecuencia() {
        return frecuencia;
    }
}