package org.example.tarea2_estdatos;

public class BitLector {
    private final byte[] bytesAlmacenados;
    private int posicionByte;
    private int posicionBit;
    private final int totalBits;

    public BitLector(byte[] bytesAlmacenados, int totalBits) {
        this.bytesAlmacenados = bytesAlmacenados;
        this.totalBits = totalBits;
        this.posicionByte = 0;
        this.posicionBit = 0;
    }

    public int leerBit() {
        if (posicionByte * 8 + posicionBit >= totalBits) {
            return -1;
        }

        int bit = (bytesAlmacenados[posicionByte] >> (7 - posicionBit)) & 1;
        posicionBit++;

        if (posicionBit == 8) {
            posicionByte++;
            posicionBit = 0;
        }

        return bit;
    }

    public byte[] leerBits(int cantidadBits) {
        int numBytes = (cantidadBits + 7) / 8;
        byte[] resultado = new byte[numBytes];
        for (int i = 0; i < cantidadBits; i++) {
            int bit = leerBit();
            if (bit == -1) break;
            resultado[i / 8] |= (bit << (7 - (i % 8)));
        }
        return resultado;
    }

    public boolean hayMasBits() {
        return posicionByte * 8 + posicionBit < totalBits;
    }
}
