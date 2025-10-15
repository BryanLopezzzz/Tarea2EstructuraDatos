package org.example.tarea2_estdatos;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Estadisticas {

    public void mostrarEstadisticasCompresion(String textoOriginal,
                                              String rutaComprimido,
                                              long tiempoCompresion) {
        File archivoComprimido = new File(rutaComprimido);
        long tamañoOriginal = textoOriginal.getBytes(StandardCharsets.UTF_8).length;
        long tamañoComprimido = archivoComprimido.length();
        double proporcion = (tamañoComprimido * 100.0) / tamañoOriginal;

        System.out.println("\n=== ESTADÍSTICAS DE COMPRESIÓN ===");
        System.out.println("Tamaño original: " + tamañoOriginal + " bytes");
        System.out.println("Tamaño comprimido: " + tamañoComprimido + " bytes");
        System.out.println("Proporción: " + String.format("%.2f", proporcion) + "%");
        System.out.println("Ahorro: " + String.format("%.2f", 100 - proporcion) + "%");
        System.out.println("Tiempo de compresión: " + tiempoCompresion + " ms");
    }

    public void mostrarEstadisticasDescompresion(long tiempoDescompresion) {
        System.out.println("\n=== DESCOMPRESIÓN COMPLETADA ===");
        System.out.println("Tiempo de descompresión: " + tiempoDescompresion + " ms");
    }
}
