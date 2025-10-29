package org.example.tarea2_estdatos;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ArchivoManager {
    public byte[] leerArchivoBytes(String rutaArchivo) throws IOException {
        return Files.readAllBytes(Paths.get(rutaArchivo));
    }

    public void escribirArchivoBytes(String rutaArchivo, byte[] datos) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(rutaArchivo)) {
            fos.write(datos);
        }
    }
}