package org.example.tarea2_estdatos;

import java.util.ArrayList;
import java.util.Arrays;

public class Decodificador {
    private ArrayList<ParByteCodigo> tablaCodigos;

    public Decodificador(ArrayList<ParByteCodigo> tablaCodigos) {
        this.tablaCodigos = tablaCodigos;
    }

    public byte[] decodificarBitsABytes(byte[] bitsComprimidos, int numBitsTotales) {
        ArrayList<Byte> bytesResultado = new ArrayList<>();
        BitLector lector = new BitLector(bitsComprimidos, numBitsTotales);
        StringBuilder codigoActual = new StringBuilder();

        while (lector.hayMasBits()) {
            int bit = lector.leerBit();
            if (bit == -1) break;

            codigoActual.append(bit == 1 ? '1' : '0'); //En c++ llegamos a ver el append en los archivos donde
            //se agregaba al final de él. Aqui es similar, se agrega al final pero del string

            byte[] bytesCaracter = buscarBytesCaracter(codigoActual.toString());
            if (bytesCaracter != null) {
                for (byte b : bytesCaracter) {
                    bytesResultado.add(b);
                }
                codigoActual.setLength(0);
            }
        }

        byte[] resultado = new byte[bytesResultado.size()];
        for (int i = 0; i < bytesResultado.size(); i++) {
            resultado[i] = bytesResultado.get(i);
        }
        return resultado;
    }

    private byte[] buscarBytesCaracter(String codigo) {
        for (int i = 0; i < tablaCodigos.size(); i++) {
            ParByteCodigo par = tablaCodigos.get(i);
            if (par.getCodigo().equals(codigo)) {
                return par.getBytesCaracter();
            }
        }
        return null;
    }
}