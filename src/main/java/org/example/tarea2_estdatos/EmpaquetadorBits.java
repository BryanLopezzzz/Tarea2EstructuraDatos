package org.example.tarea2_estdatos;

import java.io.*;
import java.util.ArrayList;

public class EmpaquetadorBits {

    public void escribirArchivoComprimido(String rutaSalida,
                                          ArrayList<ParByteCodigo> tablaCodigos,
                                          byte[] bitsCodificados,
                                          int numBitsCodificados) throws IOException {

        try (DataOutputStream salida = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(rutaSalida)))) {

            salida.writeInt(tablaCodigos.size());


            for (ParByteCodigo par : tablaCodigos) {
                byte[] bytesCaracter = par.getBytesCaracter();
                String codigo = par.getCodigo();


                salida.writeInt(bytesCaracter.length);
                salida.write(bytesCaracter);


                salida.writeInt(codigo.length());
                byte[] codigoBytes = convertirCodigoABytes(codigo);
                salida.write(codigoBytes);

                salida.writeInt(par.getFrecuencia());
            }

            salida.writeInt(numBitsCodificados);

            salida.write(bitsCodificados);
        }
    }

    private byte[] convertirCodigoABytes(String codigo) {
        int numBytes = (codigo.length() + 7) / 8;
        byte[] bytes = new byte[numBytes];

        for (int i = 0; i < codigo.length(); i++) {
            if (codigo.charAt(i) == '1') {
                int byteIndex = i / 8;
                int bitIndex = 7 - (i % 8);
                bytes[byteIndex] |= (1 << bitIndex);
            }
        }

        return bytes;
    }

    public DatosDescompresion leerArchivoComprimido(String rutaComprimido) throws IOException {
        ArrayList<ParByteCodigo> tablaCodigos = new ArrayList<>();
        byte[] bitsComprimidos;
        int numBitsTotales;

        try (DataInputStream entrada = new DataInputStream(
                new BufferedInputStream(new FileInputStream(rutaComprimido)))) {

            int numSimbolos = entrada.readInt();

            for (int i = 0; i < numSimbolos; i++) {
                int longitudBytes = entrada.readInt();
                byte[] bytesCaracter = new byte[longitudBytes];
                entrada.readFully(bytesCaracter);

                int longitudCodigo = entrada.readInt();
                int numBytescodigo = (longitudCodigo + 7) / 8;
                byte[] codigoBytes = new byte[numBytescodigo];
                entrada.readFully(codigoBytes);

                String codigo = convertirBytesACodigo(codigoBytes, longitudCodigo);

                int frecuencia = entrada.readInt();

                tablaCodigos.add(new ParByteCodigo(bytesCaracter, codigo, frecuencia));
            }

            System.out.println("  Tabla de códigos cargada");
            System.out.println("  " + tablaCodigos.size() + " códigos leídos");

            numBitsTotales = entrada.readInt();

            int numBytes = (numBitsTotales + 7) / 8;
            bitsComprimidos = new byte[numBytes];
            entrada.readFully(bitsComprimidos);
        }

        return new DatosDescompresion(tablaCodigos, bitsComprimidos, numBitsTotales);
    }

    private String convertirBytesACodigo(byte[] bytes, int longitudBits) {
        StringBuilder codigo = new StringBuilder();

        for (int i = 0; i < longitudBits; i++) {
            int byteIndex = i / 8;
            int bitIndex = 7 - (i % 8);
            int bit = (bytes[byteIndex] >> bitIndex) & 1;
            codigo.append(bit);
        }

        return codigo.toString();
    }

    public static class DatosDescompresion {
        private ArrayList<ParByteCodigo> tablaCodigos;
        private byte[] bits;
        private int numBitsTotales;

        public DatosDescompresion(ArrayList<ParByteCodigo> tablaCodigos, byte[] bits, int numBitsTotales) {
            this.tablaCodigos = tablaCodigos;
            this.bits = bits;
            this.numBitsTotales = numBitsTotales;
        }

        public ArrayList<ParByteCodigo> getTablaCodigos() {
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