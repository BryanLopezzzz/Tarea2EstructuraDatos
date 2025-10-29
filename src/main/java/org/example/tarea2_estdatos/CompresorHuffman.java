package org.example.tarea2_estdatos;

import java.io.*;

public class CompresorHuffman {
    private ArchivoManager archivoManager;
    private String textoOriginal;

    public CompresorHuffman() {
        archivoManager = new ArchivoManager();
    }

    public void comprimir(String rutaEntrada, String rutaSalida) throws IOException {
        long tiempoInicio = System.currentTimeMillis();

        System.out.println("\nCargando archivo...");
        textoOriginal = archivoManager.leerArchivoTexto(rutaEntrada);

        System.out.println("Calculando frecuencias");
        AnalizadorFrecuencias analizador = new AnalizadorFrecuencias(textoOriginal);
        analizador.calcularFrecuencias();
        analizador.mostrarTabla();

        System.out.println("\nConstruyendo árbol de Huffman");
        ArbolHuffman arbol = new ArbolHuffman();
        arbol.construirArbol(analizador.getTablaFrecuencias());

        System.out.println("Generando códigos");
        Codificador codificador = new Codificador(arbol.getTablaCodigos());
        codificador.mostrarCodigos(analizador.getTablaFrecuencias());

        System.out.println("\nCodificando texto...");
        int[] numBits = new int[1];
        byte[] bytesCodificados = codificador.codificarTextoABytes(textoOriginal, numBits);

        System.out.println("Empaquetando bits y guardando archivo...");
        EmpaquetadorBits empaquetador = new EmpaquetadorBits();
        empaquetador.escribirArchivoComprimido(
                rutaSalida,
                arbol.getTablaCodigos(),
                bytesCodificados,
                numBits[0],
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
        byte[] textoDescomprimido = decodificador.decodificarBits(datos.getBits());

        System.out.println("Guardando archivo descomprimido...");
        archivoManager.escribirArchivoTexto(rutaSalida, textoDescomprimido);

        long tiempoFin = System.currentTimeMillis();

        Estadisticas stats = new Estadisticas();
        stats.mostrarEstadisticasDescompresion(tiempoFin - tiempoInicio);
    }

    public String getTextoOriginal() {
        return textoOriginal;
    }

}