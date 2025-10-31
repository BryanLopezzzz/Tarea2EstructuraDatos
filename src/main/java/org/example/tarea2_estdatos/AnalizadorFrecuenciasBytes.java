package org.example.tarea2_estdatos;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalizadorFrecuenciasBytes {
    private List<ParByteFrecuencia> tablaFrecuencias;
    private byte[] bytesTexto;

    public AnalizadorFrecuenciasBytes(byte[] bytesTexto) {
        this.bytesTexto = bytesTexto;
        this.tablaFrecuencias = new ArrayList<>();
    }

    public void calcularFrecuencias() {
        HashMap<ByteArray, Integer> mapaFrecuencias = new HashMap<>();

        int i = 0;
        while (i < bytesTexto.length) {
            int longitudCaracter = obtenerLongitudCaracterUTF8(bytesTexto[i]);

            if (i + longitudCaracter > bytesTexto.length) {
                longitudCaracter = 1;
            }

            byte[] bytesCaracter = extraerBytes(bytesTexto, i, longitudCaracter);

            ByteArray aux = new ByteArray(bytesCaracter);
            mapaFrecuencias.put(aux, mapaFrecuencias.getOrDefault(aux, 0) + 1);

            i += longitudCaracter;
        }

        tablaFrecuencias.clear();
        for (Map.Entry<ByteArray, Integer> entry : mapaFrecuencias.entrySet()) {
            tablaFrecuencias.add(new ParByteFrecuencia(
                    entry.getKey().getBytes(),
                    entry.getValue()
            ));
        }
    }

    private byte[] extraerBytes(byte[] fuente, int inicio, int longitud) {
        byte[] resultado = new byte[longitud];
        for (int i = 0; i < longitud; i++) {
            resultado[i] = fuente[inicio + i];
        }
        return resultado;
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

    public List<ParByteFrecuencia> getTablaFrecuencias() {
        return tablaFrecuencias;
    }

    public void mostrarTabla() {
        System.out.println("\n┌───────────────────────────────────────────────────┐");
        System.out.println("│  TABLA DE FRECUENCIAS                             │");
        System.out.println("├──────────────┬─────────────┬──────────────────────┤");
        System.out.println("│   Símbolo    │  Frecuencia │     Porcentaje       │");
        System.out.println("├──────────────┼─────────────┼──────────────────────┤");

        int totalCaracteres = 0;
        for (ParByteFrecuencia par : tablaFrecuencias) {
            totalCaracteres += par.getFrecuencia();
        }

        ordenarPorFrecuencia();

        for (ParByteFrecuencia par : tablaFrecuencias) {
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

    private List<ParByteFrecuencia> mergeSort(List<ParByteFrecuencia> lista) {
        if (lista.size() <= 1) return lista;

        int medio = lista.size() / 2;
        List<ParByteFrecuencia> izquierda = mergeSort(new ArrayList<>(lista.subList(0, medio)));
        List<ParByteFrecuencia> derecha = mergeSort(new ArrayList<>(lista.subList(medio, lista.size())));

        return merge(izquierda, derecha);
    }

    private List<ParByteFrecuencia> merge(List<ParByteFrecuencia> izquierda,
                                                   List<ParByteFrecuencia> derecha) {
        List<ParByteFrecuencia> resultado = new ArrayList<>();
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
        if (simbolo.equals(" ")) return "espacio";
        return simbolo;
    }
}