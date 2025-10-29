package org.example.tarea2_estdatos;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class EmpaquetadorBits {

    public void escribirArchivoComprimido(String rutaSalida,
                                          ArrayList<ParCaracterCodigo> tablaCodigos,
                                          byte[] bitsCodificados,
                                          int numBitsCodificados) throws IOException {

        try (DataOutputStream salida = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(rutaSalida)))) {

            // Escribir número de símbolos
            salida.writeInt(tablaCodigos.size());

            // Escribir tabla de símbolos con sus bytes UTF-8 y frecuencias
            for (ParCaracterCodigo par : tablaCodigos) {
                byte[] bytesCaracter = par.getBytesCaracter();
                salida.writeInt(bytesCaracter.length);
                salida.write(bytesCaracter);
                salida.writeInt(par.getFrecuencia());
            }

            // Escribir número total de bits del mensaje comprimido
            salida.writeInt(numBitsCodificados);

            // Escribir bits comprimidos
            salida.write(bitsCodificados);
        }
    }

    public DatosDescompresion leerArchivoComprimido(String rutaComprimido) throws IOException {
        ArrayList<ParCaracterCodigo> tablaCodigos;
        byte[] bitsComprimidos;
        int numBitsTotales;

        try (DataInputStream entrada = new DataInputStream(
                new BufferedInputStream(new FileInputStream(rutaComprimido)))) {

            // Leer número de símbolos
            int numSimbolos = entrada.readInt();

            // Leer tabla de frecuencias con bytes UTF-8
            List<ParCaracterFrecuenciaBytes> frecuencias = new ArrayList<>();
            for (int i = 0; i < numSimbolos; i++) {
                int longitudBytes = entrada.readInt();
                byte[] bytesCaracter = new byte[longitudBytes];
                entrada.readFully(bytesCaracter);
                int frecuencia = entrada.readInt();
                frecuencias.add(new ParCaracterFrecuenciaBytes(bytesCaracter, frecuencia));
            }

            // Reconstruir árbol y códigos Huffman
            ArbolHuffmanBytes arbol = new ArbolHuffmanBytes();
            arbol.construirArbol(frecuencias);
            tablaCodigos = arbol.getTablaCodigos();

            System.out.println("  Árbol de Huffman reconstruido");
            System.out.println("  " + tablaCodigos.size() + " códigos regenerados");

            // Leer número total de bits
            numBitsTotales = entrada.readInt();

            // Leer bits comprimidos
            int numBytes = (numBitsTotales + 7) / 8;
            bitsComprimidos = new byte[numBytes];
            entrada.readFully(bitsComprimidos);
        }

        return new DatosDescompresion(tablaCodigos, bitsComprimidos, numBitsTotales);
    }

    public static class DatosDescompresion {
        private ArrayList<ParCaracterCodigo> tablaCodigos;
        private byte[] bits;
        private int numBitsTotales;

        public DatosDescompresion(ArrayList<ParCaracterCodigo> tablaCodigos, byte[] bits, int numBitsTotales) {
            this.tablaCodigos = tablaCodigos;
            this.bits = bits;
            this.numBitsTotales = numBitsTotales;
        }

        public ArrayList<ParCaracterCodigo> getTablaCodigos() {
            return tablaCodigos;
        }

        public byte[] getBits() {
            return bits;
        }

        public int getNumBitsTotales() {
            return numBitsTotales;
        }
    }
}