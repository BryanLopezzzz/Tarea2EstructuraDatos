package org.example.tarea2_estdatos;

import java.io.*;

public class Estadisticas {

    public void mostrarEstadisticasCompresion(byte[] bytesOriginales,
                                              String rutaComprimido,
                                              long tiempoCompresion) {
        File archivoComprimido = new File(rutaComprimido);
        long tamañoOriginal = bytesOriginales.length;
        long tamañoComprimido = archivoComprimido.length();
        double proporcion = (tamañoComprimido * 100.0) / tamañoOriginal;
        double ahorro = 100 - proporcion;

        System.out.println("\n┌──────────────────────────────────────────────────────┐");
        System.out.println("│  ESTADÍSTICAS DE COMPRESIÓN                          │");
        System.out.println("├──────────────────────────────────────────────────────┤");
        System.out.printf("│  Tamaño original       →  %8d bytes           │%n", tamañoOriginal);
        System.out.printf("│  Tamaño comprimido     →  %8d bytes           │%n", tamañoComprimido);
        System.out.println("├──────────────────────────────────────────────────────┤");

        if (proporcion <= 100) {
            System.out.printf("│  Proporción            →      %6.2f%%              │%n", proporcion);
            System.out.printf("│  Ahorro                →      %6.2f%%              │%n", ahorro);
        } else {
            System.out.printf("│  Proporción            →      %6.2f%%              │%n", proporcion);
            System.out.printf("│  Expansión             →      %6.2f%%              │%n", Math.abs(ahorro));
        }

        System.out.println("├──────────────────────────────────────────────────────┤");
        System.out.printf("│  Tiempo                →      %6d ms               │%n", tiempoCompresion);
        System.out.println("└──────────────────────────────────────────────────────┘");
    }

    public void mostrarEstadisticasDescompresion(long tiempoDescompresion) {
        System.out.println("\n┌──────────────────────────────────────────────────────┐");
        System.out.println("│  DESCOMPRESIÓN COMPLETADA                            │");
        System.out.println("├──────────────────────────────────────────────────────┤");
        System.out.printf("│  Tiempo                →      %6d ms               │%n", tiempoDescompresion);
        System.out.println("└──────────────────────────────────────────────────────┘");
    }
}