package org.example.tarea2_estdatos;

import java.io.IOException;
import java.util.Scanner;

public class SistemaCompresor { //Hice esta clase para tener el menor llamado posible en el main
    private final CompresorHuffman compresor;
    private final Scanner scanner;

    public SistemaCompresor() {
        this.compresor = new CompresorHuffman();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        ejecutarMenu();
        scanner.close();
    }

    private void ejecutarMenu() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerOpcion();
            procesarOpcion(opcion);
        } while (opcion != 3);
    }

    private void mostrarMenu() {
        System.out.println("\n┌─────────────────────────────────────────────────────┐");
        System.out.println("│     COMPRESOR/DESCOMPRESOR HUFFMAN                  │");
        System.out.println("└─────────────────────────────────────────────────────┘");
        System.out.println("\n  [1] Comprimir archivo");
        System.out.println("  [2] Descomprimir archivo");
        System.out.println("  [3] Salir\n");
        System.out.print("   Seleccione una opción: ");
    }

    private int leerOpcion() {
        try {
            int opcion = scanner.nextInt();
            scanner.nextLine();
            return opcion;
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }

    private void procesarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> realizarCompresion();
                case 2 -> realizarDescompresion();
                case 3 -> System.out.println("\n  Saliendo del programa...\n");
                default -> System.out.println("\n  Opción no válida\n");
            }
            if (opcion != 3) esperarContinuar();
        } catch (IOException e) {
            System.err.println("\n  Error: " + e.getMessage());
            esperarContinuar();
        } catch (Exception e) {
            System.err.println("\n  Error inesperado: " + e.getMessage());
            scanner.nextLine();
            esperarContinuar();
        }
    }

    private void realizarCompresion() throws IOException {
        System.out.println("\n" + "─".repeat(55));
        System.out.println("  COMPRIMIR ARCHIVO");
        System.out.println("─".repeat(55) + "\n");

        System.out.print("  Archivo origen: ");
        String rutaEntrada = scanner.nextLine().trim();

        System.out.print("  Archivo destino: ");
        String rutaSalida = scanner.nextLine().trim();

        compresor.comprimir(rutaEntrada, rutaSalida);
        System.out.println("\n  Compresión completada exitosamente");
    }

    private void realizarDescompresion() throws IOException {
        System.out.println("\n" + "─".repeat(55));
        System.out.println("  DESCOMPRIMIR ARCHIVO");
        System.out.println("─".repeat(55) + "\n");

        System.out.print("  Archivo comprimido: ");
        String rutaComprimido = scanner.nextLine().trim();

        System.out.print("  Archivo destino: ");
        String rutaSalida = scanner.nextLine().trim();

        compresor.descomprimir(rutaComprimido, rutaSalida);
        System.out.println("\n  Descompresión completada");
        System.out.println("  Guardado en: " + rutaSalida);
    }

    private void esperarContinuar() {
        System.out.print("\n  Presione ENTER para continuar...");
        scanner.nextLine();
    }
}