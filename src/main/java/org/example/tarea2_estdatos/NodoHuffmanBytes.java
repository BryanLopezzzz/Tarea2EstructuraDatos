package org.example.tarea2_estdatos;

import java.io.Serializable;

public class NodoHuffmanBytes implements Serializable {
    private byte[] bytesCaracter;
    private int frecuencia;
    private NodoHuffmanBytes izquierdo;
    private NodoHuffmanBytes derecho;

    public NodoHuffmanBytes(byte[] bytesCaracter, int frecuencia) {
        this.bytesCaracter = bytesCaracter;
        this.frecuencia = frecuencia;
        this.izquierdo = null;
        this.derecho = null;
    }

    public NodoHuffmanBytes(int frecuencia, NodoHuffmanBytes izquierdo, NodoHuffmanBytes derecho) {
        this.bytesCaracter = null;
        this.frecuencia = frecuencia;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }

    public byte[] getBytesCaracter() {
        return bytesCaracter;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public NodoHuffmanBytes getIzquierdo() {
        return izquierdo;
    }

    public NodoHuffmanBytes getDerecho() {
        return derecho;
    }
}