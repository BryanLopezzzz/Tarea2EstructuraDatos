package org.example.tarea2_estdatos;

import java.io.ByteArrayOutputStream;

public class Bit {
    private ByteArrayOutputStream bytesAlmacenados;
    private int bufferBits;
    private int contadorBits;
    private int totalBits;

    public Bit() {
        bytesAlmacenados = new ByteArrayOutputStream();
        bufferBits = 0;
        contadorBits = 0;
        totalBits = 0;
    }

    public void escribirBit(int bit) {
        bufferBits = (bufferBits << 1) | (bit & 1);
        contadorBits++;
        totalBits++;

        if (contadorBits == 8) {
            bytesAlmacenados.write((byte) bufferBits);
            bufferBits = 0;
            contadorBits = 0;
        }
    }

    public void escribirBits(byte[] datos, int numBits) {
        for (int i = 0; i < numBits; i++) {
            int bit = (datos[i / 8] >> (7 - (i % 8))) & 1;
            escribirBit(bit);
        }
    }

    public void completar() {
        if (contadorBits > 0) {
            bufferBits <<= (8 - contadorBits);
            bytesAlmacenados.write((byte) bufferBits);
            bufferBits = 0;
            contadorBits = 0;
        }
    }

    public byte[] obtenerBytes() {
        return bytesAlmacenados.toByteArray();
    }

    public int obtenerTotalBits() {
        return totalBits;
    }
}