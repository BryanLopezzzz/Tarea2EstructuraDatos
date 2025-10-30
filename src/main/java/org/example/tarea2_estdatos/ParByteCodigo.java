package org.example.tarea2_estdatos;

public class ParByteCodigo {
    private byte[] byteCaracter;
    private String codigo;
    private int frecuencia;

    public ParByteCodigo(byte byteCaracter[], String codigo, int frecuencia) {
        this.byteCaracter = byteCaracter;
        this.codigo = codigo;
        this.frecuencia = frecuencia;
    }

    public byte[] getBytesCaracter() {
        return byteCaracter;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getFrecuencia() {
        return frecuencia;
    }
}