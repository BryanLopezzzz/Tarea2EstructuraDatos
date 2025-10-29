package org.example.tarea2_estdatos;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ArchivoManager {

    public String leerArchivoTexto(String rutaArchivo) throws IOException {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader lector = new BufferedReader(
                new InputStreamReader(new FileInputStream(rutaArchivo), StandardCharsets.UTF_8))) {

            String linea;
            while ((linea = lector.readLine()) != null) {
                contenido.append(linea).append('\n');
            }
        }

        if (contenido.length() > 0 && contenido.charAt(contenido.length() - 1) == '\n') {
            contenido.deleteCharAt(contenido.length() - 1);
        }

        return contenido.toString();
    }

    public void escribirArchivoTexto(String rutaArchivo, String contenido) throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(rutaArchivo), StandardCharsets.UTF_8))) {
            escritor.write(contenido);
        }
    }

    public byte[] leerArchivoBytes(String rutaArchivo) throws IOException {
        return Files.readAllBytes(Paths.get(rutaArchivo));
    }

    public void escribirArchivoBytes(String rutaArchivo, byte[] datos) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(rutaArchivo)) {
            fos.write(datos);
        }
    }
}