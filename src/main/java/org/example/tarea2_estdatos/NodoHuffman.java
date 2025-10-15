package org.example.tarea2_estdatos;

import java.io.Serializable;

public class NodoHuffman implements Serializable {
    private char caracter;
    private int frecuencia;
    private NodoHuffman izquierdo;
    private NodoHuffman derecho;

    public NodoHuffman(char caracter, int frecuencia) {
        this.caracter = caracter;
        this.frecuencia = frecuencia;
        this.izquierdo = null;
        this.derecho = null;
    }

    public NodoHuffman(int frecuencia, NodoHuffman izquierdo, NodoHuffman derecho) {
        this.caracter = '\0';
        this.frecuencia = frecuencia;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }

    public char getCaracter() {
        return caracter;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public NodoHuffman getIzquierdo() {
        return izquierdo;
    }

    public NodoHuffman getDerecho() {
        return derecho;
    }
}
