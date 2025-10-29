package org.example.tarea2_estdatos;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CompresorHuffman {
    private ArchivoManager archivoManager;
    private byte[] bytesOriginales;

    public CompresorHuffman() {
        archivoManager = new ArchivoManager();
    }

    public void comprimir(String rutaEntrada, String rutaSalida) throws IOException {
        long tiempoInicio = System.currentTimeMillis();

        System.out.println("\nCargando archivo...");
        bytesOriginales = archivoManager.leerArchivoBytes(rutaEntrada);

        System.out.println("Calculando frecuencias...");
        AnalizadorFrecuenciasBytes analizador = new AnalizadorFrecuenciasBytes(bytesOriginales);
        analizador.calcularFrecuencias();
        analizador.mostrarTabla();

        System.out.println("\nConstruyendo árbol de Huffman...");
        ArbolHuffmanBytes arbol = new ArbolHuffmanBytes();
        arbol.construirArbol(analizador.getTablaFrecuencias());

        System.out.println("Generando códigos...");
        CodificadorBytes codificador = new CodificadorBytes(arbol.getTablaCodigos());
        codificador.mostrarCodigos(analizador.getTablaFrecuencias());

        System.out.println("\nCodificando bytes...");
        int[] numBits = new int[1];
        byte[] bytesCodificados = codificador.codificarBytesABits(bytesOriginales, numBits);

        System.out.println("Guardando archivo comprimido...");
        EmpaquetadorBits empaquetador = new EmpaquetadorBits();
        empaquetador.escribirArchivoComprimido(
                rutaSalida,
                arbol.getTablaCodigos(),
                bytesCodificados,
                numBits[0]
        );

        long tiempoFin = System.currentTimeMillis();

        Estadisticas stats = new Estadisticas();
        stats.mostrarEstadisticasCompresion(bytesOriginales, rutaSalida, tiempoFin - tiempoInicio);
    }

    public void descomprimir(String rutaComprimido, String rutaSalida) throws IOException {
        long tiempoInicio = System.currentTimeMillis();

        System.out.println("\nLeyendo archivo comprimido...");
        EmpaquetadorBits empaquetador = new EmpaquetadorBits();
        EmpaquetadorBits.DatosDescompresion datos = empaquetador.leerArchivoComprimido(rutaComprimido);

        System.out.println("Decodificando bits...");
        Decodificador decodificador = new Decodificador(datos.getTablaCodigos());
        byte[] bytesDescomprimidos = decodificador.decodificarBitsABytes(
                datos.getBits(),
                datos.getNumBitsTotales()
        );

        System.out.println("Guardando archivo descomprimido...");
        archivoManager.escribirArchivoBytes(rutaSalida, bytesDescomprimidos);

        long tiempoFin = System.currentTimeMillis();

        Estadisticas stats = new Estadisticas();
        stats.mostrarEstadisticasDescompresion(tiempoFin - tiempoInicio);
    }

    public byte[] getBytesOriginales() {
        return bytesOriginales;
    }
}