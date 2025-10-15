package org.example.tarea2_estdatos;

import java.util.ArrayList;

public class Decodificador {
    private ArrayList<ParCaracterCodigo> tablaCodigos;

    public Decodificador(ArrayList<ParCaracterCodigo> tablaCodigos) {
        this.tablaCodigos = tablaCodigos;
    }

    public String decodificarBits(String bits) {
        StringBuilder textoDescodificado = new StringBuilder();
        StringBuilder codigoActual = new StringBuilder();

        for (int i = 0; i < bits.length(); i++) {
            codigoActual.append(bits.charAt(i));

            char caracter = buscarCaracter(codigoActual.toString());
            if (caracter != '\0') {
                textoDescodificado.append(caracter);
                codigoActual.setLength(0);
            }
        }

        return textoDescodificado.toString();
    }

    private char buscarCaracter(String codigo) {
        for (int i = 0; i < tablaCodigos.size(); i++) {
            if (tablaCodigos.get(i).getCodigo().equals(codigo)) {
                return tablaCodigos.get(i).getCaracter();
            }
        }
        return '\0';
    }
}