package com.fisiteatro.fisiteatrosystem.view;

import java.util.Scanner;

public class MenuCliente {
    private Scanner scanner;

    public MenuCliente() {
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ CLIENTE ---");
            System.out.println("1. Ver eventos");
            System.out.println("2. Anulación de compra");
            System.out.println("3. Historial de compra");
            System.out.println("4. Regresar al menú principal");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    System.out.println("Mostrando eventos disponibles...");
                    break;
                case 2:
                    System.out.println("Función para anulación de compra.");
                    break;
                case 3:
                    System.out.println("Mostrando historial de compras...");
                    break;
                case 4:
                    System.out.println("Regresando al menú principal...");
                    return;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 4);
    }
}
