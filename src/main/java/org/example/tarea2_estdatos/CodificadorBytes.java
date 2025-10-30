package org.example.tarea2_estdatos;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CodificadorBytes {
    private ArrayList<ParByteCodigo> tablaCodigos;
    private HashMap<ByteArray, String> mapaCodigos;

    public CodificadorBytes(ArrayList<ParByteCodigo> tablaCodigos) {
        this.tablaCodigos = tablaCodigos;

        this.mapaCodigos = new HashMap<>();
        for (ParByteCodigo par : tablaCodigos) {
            ByteArray wrapper = new ByteArray(par.getBytesCaracter());
            mapaCodigos.put(wrapper, par.getCodigo());
        }
    }

    public byte[] codificarBytesABits(byte[] bytesTexto, int[] numBitsOut) {
        Bit bitBuffer = new Bit();

        int i = 0;
        while (i < bytesTexto.length) {
            int longitudCaracter = obtenerLongitudCaracterUTF8(bytesTexto[i]);

            if (i + longitudCaracter > bytesTexto.length) {
                longitudCaracter = 1;
            }

            byte[] bytesCaracter = extraerBytes(bytesTexto, i, longitudCaracter);

            ByteArray wrapper = new ByteArray(bytesCaracter);
            String codigo = mapaCodigos.get(wrapper);

            if (codigo != null) {
                for (int j = 0; j < codigo.length(); j++) {
                    bitBuffer.escribirBit(codigo.charAt(j) == '1' ? 1 : 0);
                }
            }

            i += longitudCaracter;
        }

        bitBuffer.completar();
        numBitsOut[0] = bitBuffer.obtenerTotalBits();
        return bitBuffer.obtenerBytes();
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

    public void mostrarCodigos(List<ParByteFrecuencia> tablaFrecuencias) {
        System.out.println("\n┌──────────────────────────────────────────────────────────┐");
        System.out.println("│  CÓDIGOS HUFFMAN                                         │");
        System.out.println("├──────────────┬─────────────┬────────────────┬────────────┤");
        System.out.println("│   Símbolo    │  Frecuencia │     Código     │    Bits    │");
        System.out.println("├──────────────┼─────────────┼────────────────┼────────────┤");

        ordenarPorLongitud();

        for (int i = 0; i < tablaCodigos.size(); i++) {
            ParByteCodigo par = tablaCodigos.get(i);
            String simbolo = new String(par.getBytesCaracter(), StandardCharsets.UTF_8);
            String codigo = par.getCodigo();
            int frecuencia = buscarFrecuencia(par.getBytesCaracter(), tablaFrecuencias);

            String simboloMostrar = obtenerRepresentacionSimbolo(simbolo);

            System.out.printf("│  %-10s  │    %5d    │  %-12s  │     %2d     │%n",
                    simboloMostrar, frecuencia, codigo, codigo.length());
        }

        System.out.println("└──────────────┴─────────────┴────────────────┴────────────┘");
    }

    private int buscarFrecuencia(byte[] bytesCaracter, List<ParByteFrecuencia> tablaFrecuencias) {
        for (int i = 0; i < tablaFrecuencias.size(); i++) {
            if (Arrays.equals(tablaFrecuencias.get(i).getBytesCaracter(), bytesCaracter)) {
                return tablaFrecuencias.get(i).getFrecuencia();
            }
        }
        return 0;
    }

    private void ordenarPorLongitud() {
        for (int i = 0; i < tablaCodigos.size() - 1; i++) {
            for (int j = 0; j < tablaCodigos.size() - i - 1; j++) {
                if (tablaCodigos.get(j).getCodigo().length() > tablaCodigos.get(j + 1).getCodigo().length()) {
                    ParByteCodigo temp = tablaCodigos.get(j);
                    tablaCodigos.set(j, tablaCodigos.get(j + 1));
                    tablaCodigos.set(j + 1, temp);
                }
            }
        }
    }

    private String obtenerRepresentacionSimbolo(String simbolo) {
        if (simbolo.equals("\n")) return "enter";
        if (simbolo.equals("\t")) return "tab";
        if (simbolo.equals("\r")) return "return";
        if (simbolo.equals(" ")) return "espacio";
        return simbolo;
    }
}