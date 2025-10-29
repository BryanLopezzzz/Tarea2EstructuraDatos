package org.example.tarea2_estdatos;

import java.util.ArrayList;

public class Decodificador {
    private ArrayList<ParCaracterCodigo> tablaCodigos;

    public Decodificador(ArrayList<ParCaracterCodigo> tablaCodigos) {
        this.tablaCodigos = tablaCodigos;
    }

    public byte[] decodificarBits(byte[] bits) {
        ArrayList<Byte> textoDescodificado = new ArrayList<>();
        StringBuilder codigoActual = new StringBuilder();

        for (int i = 0; i < bits.length; i++) {
            byte b = bits[i];

            for (int j = 7; j >= 0; j--) {
                int bit = (b >> j) & 1;
                codigoActual.append(bit == 1 ? '1' : '0');

                byte caracter = buscarCaracter(codigoActual);
                if (caracter != 0) {
                    textoDescodificado.add(caracter);
                    codigoActual.setLength(0);
                }
            }
        }

        byte[] resultado = new byte[textoDescodificado.size()];
        for (int i = 0; i < textoDescodificado.size(); i++) {
            resultado[i] = textoDescodificado.get(i);
        }

        return resultado;
    }

    private byte buscarCaracter(StringBuilder codigo) {
        for (int i = 0; i < tablaCodigos.size(); i++) {
            ParCaracterCodigo par = tablaCodigos.get(i);
            String codigoPar = par.getCodigo();
            if (codigoPar.equals(codigo.toString())) {
                return (byte) par.getCaracter();
            }
        }
        return 0;
    }
}