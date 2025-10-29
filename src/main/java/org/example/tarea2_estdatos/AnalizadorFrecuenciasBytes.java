package org.example.tarea2_estdatos;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnalizadorFrecuenciasBytes {
    private List<ParCaracterFrecuenciaBytes> tablaFrecuencias;
    private byte[] bytesTexto;

    public AnalizadorFrecuenciasBytes(byte[] bytesTexto) {
        this.bytesTexto = bytesTexto;
        this.tablaFrecuencias = new ArrayList<>();
    }

    public void calcularFrecuencias() {
        tablaFrecuencias.clear();

        int i = 0;
        while (i < bytesTexto.length) {
            int longitudCaracter = obtenerLongitudCaracterUTF8(bytesTexto[i]);

            if (i + longitudCaracter > bytesTexto.length) {
                longitudCaracter = 1;
            }

            byte[] bytesCaracter = new byte[longitudCaracter];
            System.arraycopy(bytesTexto, i, bytesCaracter, 0, longitudCaracter);

            boolean encontrado = false;
            for (int j = 0; j < tablaFrecuencias.size(); j++) {
                if (Arrays.equals(tablaFrecuencias.get(j).getBytesCaracter(), bytesCaracter)) {
                    int nuevaFrecuencia = tablaFrecuencias.get(j).getFrecuencia() + 1;
                    tablaFrecuencias.set(j, new ParCaracterFrecuenciaBytes(bytesCaracter, nuevaFrecuencia));
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                tablaFrecuencias.add(new ParCaracterFrecuenciaBytes(bytesCaracter, 1));
            }

            i += longitudCaracter;
        }
    }

    private int obtenerLongitudCaracterUTF8(byte primerByte) {
        int b = primerByte & 0xFF;

        if ((b & 0x80) == 0) {
            return 1;
        } else if ((b & 0xE0) == 0xC0) {
            return 2;
        } else if ((b & 0xF0) == 0xE0) {
            return 3;
        } else if ((b & 0xF8) == 0xF0) {
            return 4;
        }

        return 1;
    }

    public List<ParCaracterFrecuenciaBytes> getTablaFrecuencias() {
        return tablaFrecuencias;
    }

    public void mostrarTabla() {
        System.out.println("\n┌───────────────────────────────────────────────────┐");
        System.out.println("│  TABLA DE FRECUENCIAS                             │");
        System.out.println("├──────────────┬─────────────┬──────────────────────┤");
        System.out.println("│   Símbolo    │  Frecuencia │     Porcentaje       │");
        System.out.println("├──────────────┼─────────────┼──────────────────────┤");

        int totalCaracteres = 0;
        for (ParCaracterFrecuenciaBytes par : tablaFrecuencias) {
            totalCaracteres += par.getFrecuencia();
        }

        ordenarPorFrecuencia();

        for (ParCaracterFrecuenciaBytes par : tablaFrecuencias) {
            String simbolo = new String(par.getBytesCaracter(), StandardCharsets.UTF_8);
            int frecuencia = par.getFrecuencia();
            double porcentaje = (frecuencia * 100.0) / totalCaracteres;

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

    private List<ParCaracterFrecuenciaBytes> mergeSort(List<ParCaracterFrecuenciaBytes> lista) {
        if (lista.size() <= 1) return lista;

        int medio = lista.size() / 2;
        List<ParCaracterFrecuenciaBytes> izquierda = mergeSort(new ArrayList<>(lista.subList(0, medio)));
        List<ParCaracterFrecuenciaBytes> derecha = mergeSort(new ArrayList<>(lista.subList(medio, lista.size())));

        return merge(izquierda, derecha);
    }

    private List<ParCaracterFrecuenciaBytes> merge(List<ParCaracterFrecuenciaBytes> izquierda,
                                                   List<ParCaracterFrecuenciaBytes> derecha) {
        List<ParCaracterFrecuenciaBytes> resultado = new ArrayList<>();
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

    private String obtenerRepresentacionSimbolo(String simbolo) {
        if (simbolo.equals("\n")) return "enter";
        if (simbolo.equals("\t")) return "tab";
        if (simbolo.equals("\r")) return "return";
        if (simbolo.equals(" ")) return "·espacio";
        return simbolo;
    }
}