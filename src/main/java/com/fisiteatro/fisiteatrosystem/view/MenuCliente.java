package com.fisiteatro.fisiteatrosystem.view;

import com.fisiteatro.fisiteatrosystem.model.dao.*;
import com.fisiteatro.fisiteatrosystem.datastructures.*;
import com.fisiteatro.fisiteatrosystem.model.dto.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MenuCliente {
    private Scanner scanner;
    private EventoDAO eventoDAO;
    private TicketDAO ticketDAO;
    private String clienteDni;

    public MenuCliente(String clienteDni) {
        this.scanner = new Scanner(System.in);
        this.eventoDAO = new EventoDAO(new ListaEnlazada<>());
        this.ticketDAO = new TicketDAO();
        this.clienteDni = clienteDni;
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ CLIENTE ---");
            System.out.println("1. Ver eventos");
            System.out.println("2. Comprar ticket");
            System.out.println("2. Eliminar ticket");
            System.out.println("4. Historial de compra");
            System.out.println("5. Cerrar sesion");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("Mostrando eventos disponibles...");
                    eventoDAO.verCatalogo();
                    break;
                case 2:
                    System.out.println("Función para comprando tickets.");
                    generarTicket();
                    break;
                case 3:
                    System.out.println("Función para anulación de compra.");

                    break;
                case 4:
                    System.out.println("Mostrando historial de compras...");
                    mostrarHistorialCompras();
                    break;
                case 5:
                    System.out.println("Regresando al menú principal...");
                    return;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 5);
    }

    private void generarTicket() {
        if (clienteDni == null || clienteDni.isEmpty()) {
            System.out.println("Error: No se encontró el DNI del cliente.");
            return;
        }

        List<Evento> eventos = eventoDAO.readAll();
        if (eventos.isEmpty()) {
            System.out.println("No hay eventos disponibles para comprar boletos.");
            return;
        }

        eventoDAO.verCatalogo();
        System.out.print("Seleccione el ID del evento: ");
        int seleccion = scanner.nextInt();
        scanner.nextLine();

        if (!eventoDAO.validarId(seleccion)) {
            System.out.println("Selección inválida.");
            return;
        }

        Evento eventoSeleccionado = eventoDAO.getById(seleccion);
        if (eventoSeleccionado.getCapacidad() <= 0) {
            System.out.println("El evento está agotado.");
            return;
        }

        // se le asigna un numero d asiento nodo
        AsientoDAO asientoDAO = new AsientoDAO(eventoSeleccionado.getId());
        Asiento asientoSeleccionado = asientoDAO.obtenerPrimerAsientoDisponible();

        Cliente cliente = new ClienteDAO(new Cola<>()).obtenerPorDni(clienteDni);
        if (cliente == null) {
            System.out.println("Error: Cliente no encontrado.");
            return;
        }

        // aca se gener id y s mete en el constructor
        int idTicket = ticketDAO.createId();

        // cambiar el estado del asiento
        try {
            asientoDAO.update(asientoSeleccionado, eventoSeleccionado.getId());
            System.out.println("asiento en false");
        } catch (IOException e) {
            System.out.println("Error al cambiar el estado del asiento: " + e.getMessage());
        }

        Ticket ticket = new Ticket(idTicket, cliente, asientoSeleccionado, eventoSeleccionado);

        try {
            eventoDAO.reducirCapacidad(eventoSeleccionado);
            ticketDAO.create(ticket); // se guardan todos en una pila
            System.out.println("Ticket generado con éxito. Número de asiento: " + asientoSeleccionado.getNumero() + " - Fila: " + asientoSeleccionado.getFila());

            // Mostrar los datos en forma de tabla
            System.out.println("\n--------------------- DETALLE DEL TICKET GENERADO -------------------");
            System.out.printf("%-15s %-12s %-20s %-12s %-8s %-10s %-15s %-15s%n",
                    "N° Ticket", "DNI Cliente", "Evento", "Fecha", "Hora", "Precio", "N° Asiento", "Fila Asiento");
            System.out.println("------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-15s %-12s %-20s %-12s %-8s %-10.2f %-15d %-15s%n",
                    ticket.getId(), cliente.getDni(), eventoSeleccionado.getNombre(),
                    eventoSeleccionado.getFecha(), eventoSeleccionado.getHora(), eventoSeleccionado.getPrecio(),
                    asientoSeleccionado.getNumero(), asientoSeleccionado.getFila());

        } catch (IOException e) {
            System.out.println("Error al guardar la reserva.");
        }

    }

    private void mostrarHistorialCompras() {
        List<Ticket> tickets = ticketDAO.readAll();
        if (tickets.isEmpty()) {
            System.out.println("No hay tickets comprados.");
            return;
        }

        // Filtrar los tickets del cliente actual
        List<Ticket> ticketsCliente = tickets.stream()
                .filter(ticket -> ticket.getCliente().getDni().equals(clienteDni))
                .toList();

        if (ticketsCliente.isEmpty()) {
            System.out.println("No tienes tickets en tu historial de compras.");
            return;
        }

        System.out.println("\n--- HISTORIAL DE COMPRAS ---");
        System.out.printf("%-15s %-12s %-20s %-12s %-8s %-10s %-15s %-15s%n",
                "N° Ticket", "DNI Cliente", "Evento", "Fecha", "Hora", "Precio", "N° Asiento", "Fila Asiento");
        System.out.println("------------------------------------------------------------------------------------------------------------");

        for (Ticket ticket : ticketsCliente) {
            System.out.printf("%-15s %-12s %-20s %-12s %-8s %-10.2f %-15d %-15s%n",
                    ticket.getId(), ticket.getCliente().getDni(), ticket.getEvento().getNombre(),
                    ticket.getEvento().getFecha(), ticket.getEvento().getHora(), ticket.getEvento().getPrecio(),
                    ticket.getAsiento().getNumero(), ticket.getAsiento().getFila());
        }
    }
}
