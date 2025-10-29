package org.example.tarea2_estdatos;

public class ParCaracterFrecuenciaBytes {
    private byte[] bytesCaracter;
    private int frecuencia;

    public ParCaracterFrecuenciaBytes(byte[] bytesCaracter, int frecuencia) {
        this.bytesCaracter = bytesCaracter;
        this.frecuencia = frecuencia;
    }

    public byte[] getBytesCaracter() {
        return bytesCaracter;
    }

    public int getFrecuencia() {
        return frecuencia;
    }
}