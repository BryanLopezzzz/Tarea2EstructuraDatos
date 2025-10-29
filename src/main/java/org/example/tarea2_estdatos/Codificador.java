package org.example.tarea2_estdatos;

import java.util.ArrayList;
import java.util.List;

public class Codificador {
    private ArrayList<ParCaracterCodigo> tablaCodigos;

    public Codificador(ArrayList<ParCaracterCodigo> tablaCodigos) {
        this.tablaCodigos = tablaCodigos;
    }

    public String codificarTexto(String texto) {
        StringBuilder bitsCodificados = new StringBuilder();
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            String codigo = buscarCodigo(c);
            if (codigo != null) {
                bitsCodificados.append(codigo);
            }
        }
        return bitsCodificados.toString();
    }

    private String buscarCodigo(char caracter) {
        for (int i = 0; i < tablaCodigos.size(); i++) {
            if (tablaCodigos.get(i).getCaracter() == caracter) {
                return tablaCodigos.get(i).getCodigo();
            }
        }
        return null;
    }

    public void mostrarCodigos(List<ParCaracterFrecuencia> tablaFrecuencias) {
        System.out.println("\n┌──────────────────────────────────────────────────────────┐");
        System.out.println("│  CÓDIGOS HUFFMAN                                         │");
        System.out.println("├──────────────┬─────────────┬────────────────┬────────────┤");
        System.out.println("│   Símbolo    │  Frecuencia │     Código     │    Bits    │");
        System.out.println("├──────────────┼─────────────┼────────────────┼────────────┤");

        ordenarPorLongitud();

        for (int i = 0; i < tablaCodigos.size(); i++) {
            ParCaracterCodigo par = tablaCodigos.get(i);
            char simbolo = par.getCaracter();
            String codigo = par.getCodigo();
            int frecuencia = buscarFrecuencia(simbolo, tablaFrecuencias);

            String simboloMostrar = obtenerRepresentacionSimbolo(simbolo);
            String barraProgreso = generarBarraProgreso(codigo.length());

            System.out.printf("│  %-10s  │    %5d    │  %-12s  │     %2d     │%n",
                    simboloMostrar, frecuencia, codigo, codigo.length());
        }

        System.out.println("└──────────────┴─────────────┴────────────────┴────────────┘");
    }

    private String generarBarraProgreso(int longitud) {
        int maxBarra = 10;
        int filled = Math.min(longitud, maxBarra);
        return "█".repeat(filled) + "░".repeat(maxBarra - filled);
    }

    private int buscarFrecuencia(char caracter, List<ParCaracterFrecuencia> tablaFrecuencias) {
        for (int i = 0; i < tablaFrecuencias.size(); i++) {
            if (tablaFrecuencias.get(i).getCaracter() == caracter) {
                return tablaFrecuencias.get(i).getFrecuencia();
            }
        }
        return 0;
    }

    private void ordenarPorLongitud() {
        for (int i = 0; i < tablaCodigos.size() - 1; i++) {
            for (int j = 0; j < tablaCodigos.size() - i - 1; j++) {
                if (tablaCodigos.get(j).getCodigo().length() > tablaCodigos.get(j + 1).getCodigo().length()) {
                    ParCaracterCodigo temp = tablaCodigos.get(j);
                    tablaCodigos.set(j, tablaCodigos.get(j + 1));
                    tablaCodigos.set(j + 1, temp);
                }
            }
        }
    }

    private String obtenerRepresentacionSimbolo(char simbolo) {
        if (simbolo == '\n') return "↵";
        if (simbolo == '\t') return "⇥";
        if (simbolo == '\r') return "⏎";
        if (simbolo == ' ') return "·espacio";
        return String.valueOf(simbolo);
    }
}