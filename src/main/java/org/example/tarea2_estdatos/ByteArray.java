package org.example.tarea2_estdatos;

import java.util.Arrays;


public class ByteArray { //Clase necesaria para usarla en el HashMap
    //Y para reconocer bien los UTF8
    private final byte[] bytes;
    private final int hashCode;

    public ByteArray(byte[] bytes) {
        this.bytes = bytes;
        this.hashCode = Arrays.hashCode(bytes);
    }

    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ByteArray)) return false;
        ByteArray other = (ByteArray) obj;
        return Arrays.equals(this.bytes, other.bytes);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }
}