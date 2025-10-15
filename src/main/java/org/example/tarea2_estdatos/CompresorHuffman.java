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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CompresorHuffman compresor = new CompresorHuffman();

        try {
            System.out.println("=== COMPRESOR/DESCOMPRESOR HUFFMAN ===\n");
            System.out.println("1. Comprimir archivo");
            System.out.println("2. Descomprimir archivo");
            System.out.print("\nSeleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            if (opcion == 1) {
                System.out.print("Ingrese la ruta del archivo a comprimir: ");
                String rutaEntrada = scanner.nextLine();

                System.out.print("Ingrese la ruta del archivo comprimido (ej: salida.huf): ");
                String rutaSalida = scanner.nextLine();

                compresor.comprimir(rutaEntrada, rutaSalida);
                System.out.println("\n¡Compresión completada exitosamente!");

            } else if (opcion == 2) {
                System.out.print("Ingrese la ruta del archivo comprimido: ");
                String rutaComprimido = scanner.nextLine();

                System.out.print("Ingrese la ruta del archivo de salida: ");
                String rutaSalida = scanner.nextLine();

                compresor.descomprimir(rutaComprimido, rutaSalida);
                System.out.println("\nDescompresión completada exitosamente!");
                System.out.println("Archivo guardado en: " + rutaSalida);

            } else {
                System.out.println("Opción no válida.");
            }

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}