package org.example.tarea2_estdatos;

import java.util.ArrayList;
import java.util.List;

public class AnalizadorFrecuencias {
    private List<ParCaracterFrecuencia> tablaFrecuencias;
    private String texto;

    public AnalizadorFrecuencias(String texto) {
        this.texto = texto;
        this.tablaFrecuencias = new ArrayList<>();
    }

    public void calcularFrecuencias() {
        tablaFrecuencias.clear();

        texto.codePoints().forEach(cp -> {
            boolean encontrado = false;

            for (int j = 0; j < tablaFrecuencias.size(); j++) {
                if (tablaFrecuencias.get(j).getCaracter() == cp) {
                    int nuevaFrecuencia = tablaFrecuencias.get(j).getFrecuencia() + 1;
                    tablaFrecuencias.set(j, new ParCaracterFrecuencia((char) cp, nuevaFrecuencia));
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                tablaFrecuencias.add(new ParCaracterFrecuencia((char) cp, 1));
            }
        });
    }

    public List<ParCaracterFrecuencia> getTablaFrecuencias() {
        return tablaFrecuencias;
    }

    public void mostrarTabla() {
        System.out.println("\n┌───────────────────────────────────────────────────┐");
        System.out.println("│  TABLA DE FRECUENCIAS                             │");
        System.out.println("├──────────────┬─────────────┬──────────────────────┤");
        System.out.println("│   Símbolo    │  Frecuencia │     Porcentaje       │");
        System.out.println("├──────────────┼─────────────┼──────────────────────┤");

        int total = texto.length();
        ordenarPorFrecuencia();

        for (ParCaracterFrecuencia par : tablaFrecuencias) {
            char simbolo = par.getCaracter();
            int frecuencia = par.getFrecuencia();
            double porcentaje = (frecuencia * 100.0) / total;

            String simboloMostrar = obtenerRepresentacionSimbolo(simbolo);
            System.out.printf("│  %-10s  │    %5d    │       %6.2f%%        │%n",
                    simboloMostrar, frecuencia, porcentaje);
        }

        System.out.println("└──────────────┴─────────────┴──────────────────────┘");
    }

    private void ordenarPorFrecuencia() {
        if (tablaFrecuencias.size() <= 1) return;
        tablaFrecuencias = mergeSort(tablaFrecuencias);
    }

    private List<ParCaracterFrecuencia> mergeSort(List<ParCaracterFrecuencia> lista) {
        if (lista.size() <= 1) return lista;

        int medio = lista.size() / 2;
        List<ParCaracterFrecuencia> izquierda = mergeSort(new ArrayList<>(lista.subList(0, medio)));
        List<ParCaracterFrecuencia> derecha = mergeSort(new ArrayList<>(lista.subList(medio, lista.size())));

        return merge(izquierda, derecha);
    }

    private List<ParCaracterFrecuencia> merge(List<ParCaracterFrecuencia> izquierda, List<ParCaracterFrecuencia> derecha) {
        List<ParCaracterFrecuencia> resultado = new ArrayList<>();
        int i = 0, j = 0;

        while (i < izquierda.size() && j < derecha.size()) {
            if (izquierda.get(i).getFrecuencia() >= derecha.get(j).getFrecuencia()) {
                resultado.add(izquierda.get(i++));
            } else {
                resultado.add(derecha.get(j++));
            }
        }

        while (i < izquierda.size()) resultado.add(izquierda.get(i++));
        while (j < derecha.size()) resultado.add(derecha.get(j++));

        return resultado;
    }

    private String obtenerRepresentacionSimbolo(char simbolo) {
        if (simbolo == '\n') return "↵";
        if (simbolo == '\t') return "⇥";
        if (simbolo == '\r') return "⏎";
        if (simbolo == ' ') return "·espacio";
        return String.valueOf(simbolo);
    }
}