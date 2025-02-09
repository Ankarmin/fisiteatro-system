package com.fisiteatro.fisiteatrosystem.view;

import com.fisiteatro.fisiteatrosystem.model.dao.EventoDAO;
import com.fisiteatro.fisiteatrosystem.datastructures.ListaEnlazada;
import com.fisiteatro.fisiteatrosystem.model.dto.Evento;

import java.util.List;
import java.util.Scanner;

public class MenuCliente {
    private Scanner scanner;
    private EventoDAO eventoDAO;

    public MenuCliente() {
        this.scanner = new Scanner(System.in);
        this.eventoDAO = new EventoDAO(new ListaEnlazada<>());
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ CLIENTE ---");
            System.out.println("1. Ver eventos");
            System.out.println("2. Anulación de compra");
            System.out.println("3. Historial de compra");
            System.out.println("4. Cerrar sesion");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("Mostrando eventos disponibles...");
                    listarEventos();
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

    private void listarEventos() {
        List<Evento> eventos = eventoDAO.readAll();
        if (eventos.isEmpty()) {
            System.out.println("No hay eventos disponibles.");
            return;
        }

        System.out.println("\n--- CATÁLOGO DE EVENTOS ---");
        System.out.printf("%-5s %-20s %-12s %-8s %-10s %-10s%n", "ID", "Nombre", "Fecha", "Hora", "Precio", "Capacidad");
        System.out.println("--------------------------------------------------------------");

        int index = 1;
        for (Evento evento : eventos) {
            System.out.printf("%-5d %-20s %-12s %-8s %-10.2f %-10d%n",
                    index++, evento.getNombre(), evento.getFecha(), evento.getHora(), evento.getPrecio(), evento.getCapacidad());
        }
    }
}
