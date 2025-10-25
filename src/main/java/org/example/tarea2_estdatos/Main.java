package org.example.tarea2_estdatos;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CompresorHuffman compresor = new CompresorHuffman();
        int opcion;

        do {
            try {
                mostrarMenu();
                opcion = leerOpcion(scanner);

                switch (opcion) {
                    case 1:
                        realizarCompresion(scanner, compresor);
                        break;

                    case 2:
                        realizarDescompresion(scanner, compresor);
                        break;

                    case 3:
                        System.out.println("Saliendo del programa...\n");
                        break;

                    default:
                        System.out.println("\n Opción no válida. Intente de nuevo.\n");
                }

                if (opcion != 3) {
                    esperarEnter(scanner);
                }

            } catch (IOException e) {
                System.err.println("\n Error: " + e.getMessage());
                esperarEnter(scanner);
                opcion = 0;
            } catch (Exception e) {
                System.err.println("\n Error inesperado: " + e.getMessage());
                scanner.nextLine();
                esperarEnter(scanner);
                opcion = 0;
            }

        } while (opcion != 3);

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║       COMPRESOR/DESCOMPRESOR HUFFMAN                   ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("  1.  Comprimir archivo");
        System.out.println("  2.  Descomprimir archivo");
        System.out.println("  3.  Salir");
        System.out.println();
        System.out.print("Seleccione una opción: ");
    }

    private static int leerOpcion(Scanner scanner) {
        try {
            int opcion = scanner.nextInt();
            scanner.nextLine();
            return opcion;
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }

    private static void realizarCompresion(Scanner scanner, CompresorHuffman compresor) throws IOException {
        System.out.println("\n" + "=".repeat(56));
        System.out.println("               COMPRIMIR ARCHIVO");
        System.out.println("=".repeat(56));

        System.out.print("\nIngrese la ruta del archivo a comprimir: ");
        String rutaEntrada = scanner.nextLine().trim();

        System.out.print("Ingrese la ruta del archivo comprimido: ");
        String rutaSalida = scanner.nextLine().trim();

        compresor.comprimir(rutaEntrada, rutaSalida);
        System.out.println("\n Compresión completada exitosamente!");
    }

    private static void realizarDescompresion(Scanner scanner, CompresorHuffman compresor) throws IOException {
        System.out.println("\n" + "=".repeat(56));
        System.out.println("             DESCOMPRIMIR ARCHIVO");
        System.out.println("=".repeat(56));

        System.out.print("\nIngrese la ruta del archivo comprimido: ");
        String rutaComprimido = scanner.nextLine().trim();

        System.out.print("Ingrese la ruta del archivo de salida: ");
        String rutaSalida = scanner.nextLine().trim();

        compresor.descomprimir(rutaComprimido, rutaSalida);
        System.out.println("\n Descompresión completada exitosamente!");
        System.out.println(" Archivo guardado en: " + rutaSalida);
    }

    private static void esperarEnter(Scanner scanner) {
        System.out.println("\n" + "─".repeat(56));
        System.out.print("Presione ENTER para continuar...");
        scanner.nextLine();
        System.out.println("\n");
    }
}