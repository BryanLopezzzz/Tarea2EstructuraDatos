package org.example.tarea2_estdatos;

import java.util.ArrayList;
import java.util.List;

public class ListaNodos {
    private List<NodoHuffman> nodos;

    public ListaNodos() {
        nodos = new ArrayList<>();
    }

    public void agregar(NodoHuffman nodo) {
        nodos.add(nodo);
        ordenarPorFrecuencia();
    }

    public NodoHuffman extraerMinimo() {
        if (nodos.isEmpty()) {
            return null;
        }
        return nodos.remove(0);
    }

    public int tamaño() {
        return nodos.size();
    }

    public boolean estaVacia() {
        return nodos.isEmpty();
    }

    private void ordenarPorFrecuencia() {
        if (nodos == null || nodos.size() <= 1) return;
        nodos = mergeSort(nodos);
    }

    private List<NodoHuffman> mergeSort(List<NodoHuffman> lista) {
        if (lista.size() <= 1) {
            return lista;
        }

        int medio = lista.size() / 2;
        List<NodoHuffman> izquierda = mergeSort(new ArrayList<>(lista.subList(0, medio)));
        List<NodoHuffman> derecha = mergeSort(new ArrayList<>(lista.subList(medio, lista.size())));

        return merge(izquierda, derecha);
    }

    private List<NodoHuffman> merge(List<NodoHuffman> izquierda, List<NodoHuffman> derecha) {
        List<NodoHuffman> resultado = new ArrayList<>();
        int i = 0, j = 0;

        while (i < izquierda.size() && j < derecha.size()) {
            if (izquierda.get(i).getFrecuencia() <= derecha.get(j).getFrecuencia()) {
                resultado.add(izquierda.get(i));
                i++;
            } else {
                resultado.add(derecha.get(j));
                j++;
            }
        }

        while (i < izquierda.size()) {
            resultado.add(izquierda.get(i));
            i++;
        }

        while (j < derecha.size()) {
            resultado.add(derecha.get(j));
            j++;
        }

        return resultado;
    }
}
