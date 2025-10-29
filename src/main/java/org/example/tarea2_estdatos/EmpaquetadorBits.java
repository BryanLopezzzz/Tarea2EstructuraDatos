package org.example.tarea2_estdatos;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class EmpaquetadorBits {

    public void escribirArchivoComprimido(String rutaSalida,
                                          ArrayList<ParCaracterCodigo> tablaCodigos,
                                          byte[] bitsCodificados,
                                          int numBitsCodificados,
                                          int longitudTextoOriginal) throws IOException {

        try (DataOutputStream salida = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(rutaSalida)))) {

            salida.writeInt(tablaCodigos.size());

            for (ParCaracterCodigo par : tablaCodigos) {
                String caracterStr = String.valueOf(par.getCaracter());
                byte[] bytesCaracter = caracterStr.getBytes(StandardCharsets.UTF_8);
                salida.writeInt(bytesCaracter.length);
                salida.write(bytesCaracter);
                salida.writeInt(par.getFrecuencia());
            }
            salida.writeInt(longitudTextoOriginal);
            salida.writeInt(numBitsCodificados);

            salida.write(bitsCodificados);
        }
    }

    public DatosDescompresion leerArchivoComprimido(String rutaComprimido) throws IOException {
        ArrayList<ParCaracterCodigo> tablaCodigos;
        byte[] bitsDesempaquetados;

        try (DataInputStream entrada = new DataInputStream(
                new BufferedInputStream(new FileInputStream(rutaComprimido)))) {

            int numSimbolos = entrada.readInt();

            List<ParCaracterFrecuencia> frecuencias = new ArrayList<>();
            for (int i = 0; i < numSimbolos; i++) {
                int longitudBytes = entrada.readInt();
                byte[] bytesCaracter = new byte[longitudBytes];
                entrada.readFully(bytesCaracter);
                char simbolo = new String(bytesCaracter, StandardCharsets.UTF_8).charAt(0);
                int frecuencia = entrada.readInt();
                frecuencias.add(new ParCaracterFrecuencia(simbolo, frecuencia));
            }

            ArbolHuffman arbol = new ArbolHuffman();
            arbol.construirArbol(frecuencias);
            tablaCodigos = arbol.getTablaCodigos();

            System.out.println("  Árbol de Huffman reconstruido");
            System.out.println(" " + tablaCodigos.size() + " códigos regenerados");

            int longitudOriginal = entrada.readInt();
            int numBitsTotales = entrada.readInt();

            int numBytes = (numBitsTotales + 7) / 8;
            byte[] bytesComprimidos = new byte[numBytes];
            entrada.readFully(bytesComprimidos);

            bitsDesempaquetados = bytesComprimidos;
        }

        return new DatosDescompresion(tablaCodigos, bitsDesempaquetados);
    }

    public static class DatosDescompresion {
        private ArrayList<ParCaracterCodigo> tablaCodigos;
        private byte[] bits;

        public DatosDescompresion(ArrayList<ParCaracterCodigo> tablaCodigos, byte[] bits) {
            this.tablaCodigos = tablaCodigos;
            this.bits = bits;
        }

        public ArrayList<ParCaracterCodigo> getTablaCodigos() {
            return tablaCodigos;
        }

        public byte[] getBits() {
            return bits;
        }
    }
}