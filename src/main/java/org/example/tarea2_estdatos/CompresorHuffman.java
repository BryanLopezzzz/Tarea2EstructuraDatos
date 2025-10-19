package org.example.tarea2_estdatos;

import java.io.*;
import java.util.Scanner;

public class CompresorHuffman {
    private ManejadorArchivos manejadorArchivos;
    private String textoOriginal;

    public CompresorHuffman() {
        manejadorArchivos = new ManejadorArchivos();
    }

    public void comprimir(String rutaEntrada, String rutaSalida) throws IOException {
        long tiempoInicio = System.currentTimeMillis();

        System.out.println("\nCargando archivo...");
        textoOriginal = manejadorArchivos.leerArchivoTexto(rutaEntrada);

        System.out.println("Calculando frecuencias...");
        AnalizadorFrecuencias analizador = new AnalizadorFrecuencias(textoOriginal);
        analizador.calcularFrecuencias();
        analizador.mostrarTabla();

        System.out.println("\nConstruyendo árbol de Huffman...");
        ArbolHuffman arbol = new ArbolHuffman();
        arbol.construirArbol(analizador.getTablaFrecuencias());

        System.out.println("Generando códigos...");
        Codificador codificador = new Codificador(arbol.getTablaCodigos());
        codificador.mostrarCodigos(analizador.getTablaFrecuencias());

        System.out.println("\nCodificando texto...");
        String bitsCodificados = codificador.codificarTexto(textoOriginal);

        System.out.println("Empaquetando bits y guardando archivo...");
        EmpaquetadorBits empaquetador = new EmpaquetadorBits();
        empaquetador.escribirArchivoComprimido(
                rutaSalida,
                arbol.getTablaCodigos(),
                bitsCodificados,
                textoOriginal.length()
        );

        long tiempoFin = System.currentTimeMillis();

        Estadisticas stats = new Estadisticas();
        stats.mostrarEstadisticasCompresion(textoOriginal, rutaSalida, tiempoFin - tiempoInicio);
    }

    public void descomprimir(String rutaComprimido, String rutaSalida) throws IOException {
        long tiempoInicio = System.currentTimeMillis();

        System.out.println("\nLeyendo archivo comprimido...");
        EmpaquetadorBits empaquetador = new EmpaquetadorBits();
        EmpaquetadorBits.DatosDescompresion datos = empaquetador.leerArchivoComprimido(rutaComprimido);

        System.out.println("Decodificando bits...");
        Decodificador decodificador = new Decodificador(datos.getTablaCodigos());
        String textoDescomprimido = decodificador.decodificarBits(datos.getBits());

        System.out.println("Guardando archivo descomprimido...");
        manejadorArchivos.escribirArchivoTexto(rutaSalida, textoDescomprimido);

        long tiempoFin = System.currentTimeMillis();

        Estadisticas stats = new Estadisticas();
        stats.mostrarEstadisticasDescompresion(tiempoFin - tiempoInicio);
    }

    public String getTextoOriginal() {
        return textoOriginal;
    }

}