package org.example.tarea2_estdatos;

import java.util.ArrayList;
import java.util.List;

public class ArbolHuffmanBytes {
    private NodoHuffmanBytes raiz;
    private ArrayList<ParCaracterCodigo> tablaCodigos;

    public ArbolHuffmanBytes() {
        tablaCodigos = new ArrayList<>();
    }

    public void construirArbol(List<ParCaracterFrecuenciaBytes> tablaFrecuencias) {
        ListaNodosBytes listaNodos = new ListaNodosBytes();

        for (int i = 0; i < tablaFrecuencias.size(); i++) {
            ParCaracterFrecuenciaBytes par = tablaFrecuencias.get(i);
            listaNodos.agregar(new NodoHuffmanBytes(par.getBytesCaracter(), par.getFrecuencia()));
        }

        if (listaNodos.tamaño() == 1) {
            NodoHuffmanBytes unico = listaNodos.extraerMinimo();
            raiz = new NodoHuffmanBytes(unico.getFrecuencia(), unico, null);
        } else {
            while (listaNodos.tamaño() > 1) {
                NodoHuffmanBytes izquierdo = listaNodos.extraerMinimo();
                NodoHuffmanBytes derecho = listaNodos.extraerMinimo();
                NodoHuffmanBytes padre = new NodoHuffmanBytes(
                        izquierdo.getFrecuencia() + derecho.getFrecuencia(),
                        izquierdo,
                        derecho
                );
                listaNodos.agregar(padre);
            }
            raiz = listaNodos.extraerMinimo();
        }

        generarCodigos();
    }

    private void generarCodigos() {
        tablaCodigos.clear();
        if (raiz != null) {
            if (raiz.esHoja()) {
                tablaCodigos.add(new ParCaracterCodigo(raiz.getBytesCaracter(), "0", raiz.getFrecuencia()));
            } else if (raiz.getIzquierdo() != null && raiz.getIzquierdo().esHoja() && raiz.getDerecho() == null) {
                tablaCodigos.add(new ParCaracterCodigo(raiz.getIzquierdo().getBytesCaracter(), "0", raiz.getFrecuencia()));
            } else {
                generarCodigosRecursivo(raiz, "");
            }
        }
    }

    private void generarCodigosRecursivo(NodoHuffmanBytes nodo, String codigo) {
        if (nodo == null) return;

        if (nodo.esHoja()) {
            String codigoFinal = codigo.isEmpty() ? "0" : codigo;
            tablaCodigos.add(new ParCaracterCodigo(nodo.getBytesCaracter(), codigoFinal, nodo.getFrecuencia()));
            return;
        }

        generarCodigosRecursivo(nodo.getIzquierdo(), codigo + "0");
        generarCodigosRecursivo(nodo.getDerecho(), codigo + "1");
    }

    public ArrayList<ParCaracterCodigo> getTablaCodigos() {
        return tablaCodigos;
    }

    public NodoHuffmanBytes getRaiz() {
        return raiz;
    }
}