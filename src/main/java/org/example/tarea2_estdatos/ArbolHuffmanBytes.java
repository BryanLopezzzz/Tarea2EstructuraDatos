package org.example.tarea2_estdatos;

import java.util.ArrayList;
import java.util.List;

public class ArbolHuffmanBytes {
    private NodoHuffmanBytes raiz;
    private ArrayList<ParByteCodigo> tablaCodigos;

    public ArbolHuffmanBytes() {
        tablaCodigos = new ArrayList<>();
    }

    public void construirArbol(List<ParByteFrecuencia> tablaFrecuencias) {
        ListaNodos listaNodos = new ListaNodos();

        // Crear nodos hoja para cada carácter UTF-8
        for (int i = 0; i < tablaFrecuencias.size(); i++) {
            ParByteFrecuencia par = tablaFrecuencias.get(i);
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
                tablaCodigos.add(new ParByteCodigo(raiz.getBytesCaracter(), "0", raiz.getFrecuencia()));
            } else if (raiz.getIzquierdo() != null && raiz.getIzquierdo().esHoja() && raiz.getDerecho() == null) {
                tablaCodigos.add(new ParByteCodigo(raiz.getIzquierdo().getBytesCaracter(), "0", raiz.getFrecuencia()));
            } else {
                generarCodigosRecursivo(raiz, "");
            }
        }
    }

    private void generarCodigosRecursivo(NodoHuffmanBytes nodo, String codigo) {
        if (nodo == null) return;

        if (nodo.esHoja()) {
            String codigoFinal = codigo.isEmpty() ? "0" : codigo;
            tablaCodigos.add(new ParByteCodigo(nodo.getBytesCaracter(), codigoFinal, nodo.getFrecuencia()));
            return;
        }

        generarCodigosRecursivo(nodo.getIzquierdo(), codigo + "0");
        generarCodigosRecursivo(nodo.getDerecho(), codigo + "1");
    }

    public ArrayList<ParByteCodigo> getTablaCodigos() {
        return tablaCodigos;
    }

    public NodoHuffmanBytes getRaiz() {
        return raiz;
    }
}