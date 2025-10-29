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

            salida.writeInt(tablaCodigos.size());

            for (ParCaracterCodigo par : tablaCodigos) {
                byte[] bytesCaracter = par.getBytesCaracter();
                salida.writeInt(bytesCaracter.length);
                salida.write(bytesCaracter);
                String codigo = par.getCodigo();
                salida.writeUTF(codigo);

                salida.writeInt(par.getFrecuencia());
            }

            salida.writeInt(numBitsCodificados);

            salida.write(bitsCodificados);
        }
    }

    public DatosDescompresion leerArchivoComprimido(String rutaComprimido) throws IOException {
        ArrayList<ParCaracterCodigo> tablaCodigos = new ArrayList<>();
        byte[] bitsComprimidos;
        int numBitsTotales;

        try (DataInputStream entrada = new DataInputStream(
                new BufferedInputStream(new FileInputStream(rutaComprimido)))) {

            int numSimbolos = entrada.readInt();

            for (int i = 0; i < numSimbolos; i++) {
                int longitudBytes = entrada.readInt();
                byte[] bytesCaracter = new byte[longitudBytes];
                entrada.readFully(bytesCaracter);

                String codigo = entrada.readUTF();

                int frecuencia = entrada.readInt();
                tablaCodigos.add(new ParCaracterCodigo(bytesCaracter, codigo, frecuencia));
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