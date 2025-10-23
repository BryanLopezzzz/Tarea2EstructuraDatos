package org.example.tarea2_estdatos;

import java.util.ArrayList;
import java.util.List;

public class ArbolHuffman {
    private NodoHuffman raiz;
    private ArrayList<ParCaracterCodigo> tablaCodigos;

    public ArbolHuffman() {
        tablaCodigos = new ArrayList<>();
    }

    public void construirArbol(List<ParCaracterFrecuencia> tablaFrecuencias) {
        ListaNodos listaNodos = new ListaNodos();

        for (int i = 0; i < tablaFrecuencias.size(); i++) {
            ParCaracterFrecuencia par = tablaFrecuencias.get(i);
            listaNodos.agregar(new NodoHuffman(par.getCaracter(), par.getFrecuencia()));
        }

        if (listaNodos.tamaño() == 1) {
            NodoHuffman unico = listaNodos.extraerMinimo();
            raiz = new NodoHuffman(unico.getFrecuencia(), unico, null);
        } else {
            while (listaNodos.tamaño() > 1) {
                NodoHuffman izquierdo = listaNodos.extraerMinimo();
                NodoHuffman derecho = listaNodos.extraerMinimo();
                NodoHuffman padre = new NodoHuffman(
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
                tablaCodigos.add(new ParCaracterCodigo(raiz.getCaracter(), "0", raiz.getFrecuencia()));
            } else if (raiz.getIzquierdo() != null && raiz.getIzquierdo().esHoja() && raiz.getDerecho() == null) {
                tablaCodigos.add(new ParCaracterCodigo(raiz.getIzquierdo().getCaracter(), "0",raiz.getFrecuencia()));
            } else {
                generarCodigosRecursivo(raiz, "");
            }
        }
    }

    private void generarCodigosRecursivo(NodoHuffman nodo, String codigo) {
        if (nodo == null) return;

        if (nodo.esHoja()) {
            String codigoFinal = codigo.isEmpty() ? "0" : codigo;
            tablaCodigos.add(new ParCaracterCodigo(nodo.getCaracter(), codigoFinal, nodo.getFrecuencia()));
            return;
        }

        generarCodigosRecursivo(nodo.getIzquierdo(), codigo + "0");
        generarCodigosRecursivo(nodo.getDerecho(), codigo + "1");
    }

    public ArrayList<ParCaracterCodigo> getTablaCodigos() {
        return tablaCodigos;
    }

    public NodoHuffman getRaiz() {
        return raiz;
    }
}
