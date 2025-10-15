package org.example.tarea2_estdatos;

import java.io.*;
import java.util.ArrayList;

public class EmpaquetadorBits {

    public void escribirArchivoComprimido(String rutaSalida,
                                          ArrayList<ParCaracterCodigo> tablaCodigos,
                                          String bitsCodificados,
                                          int longitudTextoOriginal) throws IOException {

        try (DataOutputStream salida = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(rutaSalida)))) {

            salida.writeInt(tablaCodigos.size());

            for (int i = 0; i < tablaCodigos.size(); i++) {
                ParCaracterCodigo par = tablaCodigos.get(i);
                salida.writeChar(par.getCaracter());
                salida.writeUTF(par.getCodigo());
            }

            salida.writeInt(longitudTextoOriginal);

            salida.writeInt(bitsCodificados.length());

            int numBytes = (bitsCodificados.length() + 7) / 8;

            for (int i = 0; i < numBytes; i++) {
                int inicioBit = i * 8;
                int finBit = Math.min(inicioBit + 8, bitsCodificados.length());
                String byteString = bitsCodificados.substring(inicioBit, finBit);

                while (byteString.length() < 8) {
                    byteString += "0";
                }

                byte byteValor = (byte) Integer.parseInt(byteString, 2);
                salida.writeByte(byteValor);
            }
        }
    }

    public DatosDescompresion leerArchivoComprimido(String rutaComprimido) throws IOException {
        ArrayList<ParCaracterCodigo> tablaCodigos = new ArrayList<>();
        String bitsDesempaquetados;

        try (DataInputStream entrada = new DataInputStream(
                new BufferedInputStream(new FileInputStream(rutaComprimido)))) {

            int numSimbolos = entrada.readInt();

            for (int i = 0; i < numSimbolos; i++) {
                char simbolo = entrada.readChar();
                String codigo = entrada.readUTF();
                tablaCodigos.add(new ParCaracterCodigo(simbolo, codigo));
            }

            int longitudOriginal = entrada.readInt();

            int numBitsTotales = entrada.readInt();

            StringBuilder bitsTotales = new StringBuilder();
            int numBytes = (numBitsTotales + 7) / 8;

            for (int i = 0; i < numBytes; i++) {
                byte byteValor = entrada.readByte();
                String byteString = String.format("%8s",
                        Integer.toBinaryString(byteValor & 0xFF)).replace(' ', '0');
                bitsTotales.append(byteString);
            }

            bitsDesempaquetados = bitsTotales.substring(0, numBitsTotales);
        }

        return new DatosDescompresion(tablaCodigos, bitsDesempaquetados);
    }

    public static class DatosDescompresion {
        private ArrayList<ParCaracterCodigo> tablaCodigos;
        private String bits;

        public DatosDescompresion(ArrayList<ParCaracterCodigo> tablaCodigos, String bits) {
            this.tablaCodigos = tablaCodigos;
            this.bits = bits;
        }

        public ArrayList<ParCaracterCodigo> getTablaCodigos() {
            return tablaCodigos;
        }

        public String getBits() {
            return bits;
        }
    }
}