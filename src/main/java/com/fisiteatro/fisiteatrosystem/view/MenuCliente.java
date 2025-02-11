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
    private AsientoDAO asientoDAO;
    private String clienteDni;

    public MenuCliente(String clienteDni) {
        this.scanner = new Scanner(System.in);
        this.eventoDAO = new EventoDAO(new ListaEnlazada<>());
        this.ticketDAO = new TicketDAO(new Pila<>());
        this.asientoDAO = new AsientoDAO(new ArbolBinarioBusqueda<>());
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
                    listarEventos();
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
                    return;
                case 5:
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
        System.out.printf("%-5s %-20s %-12s %-8s %-10s %-10s%n", "N°", "Nombre", "Fecha", "Hora", "Precio", "Capacidad");
        System.out.println("--------------------------------------------------------------");

        int index = 1;
        for (Evento evento : eventos) {
            System.out.printf("%-5d %-20s %-12s %-8s %-10.2f %-10d%n",
                    index++, evento.getNombre(), evento.getFecha(), evento.getHora(), evento.getPrecio(), evento.getCapacidad());
        }

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

        listarEventos();
        System.out.print("Seleccione el número del evento: ");
        int seleccion = scanner.nextInt() - 1;
        scanner.nextLine();

        if (seleccion < 0 || seleccion >= eventos.size()) {
            System.out.println("Selección inválida.");
            return;
        }

        Evento eventoSeleccionado = eventos.get(seleccion);
        if (eventoSeleccionado.getCapacidad() <= 0) {
            System.out.println("El evento está agotado.");
            return;
        }

        int nuevoAsiento = asientoDAO.generarNuevoAsiento(eventoSeleccionado.getNombre());
        String nivelAsiento = asientoDAO.obtenerNivelAsiento(nuevoAsiento);
        char filaAsignada = nivelAsiento.charAt(0);

        Asiento asiento = new Asiento(eventoSeleccionado.getNombre(), String.valueOf(filaAsignada), nuevoAsiento, true);

        Cliente cliente = new ClienteDAO(new Cola<>()).obtenerPorDni(clienteDni);
        if (cliente == null) {
            System.out.println("Error: Cliente no encontrado.");
            return;
        }

        Ticket ticket = new Ticket(cliente, asiento, eventoSeleccionado);

        try {
            eventoDAO.reducirCapacidad(eventoSeleccionado);
            ticketDAO.create(ticket);
            asientoDAO.create(asiento);
            System.out.println("Ticket generado con éxito. Número de asiento: " + nuevoAsiento + " - Fila: " + filaAsignada);

            // Mostrar los datos en forma de tabla
            System.out.println("\n--------------------- DETALLE DEL TICKET GENERADO -------------------");
            System.out.printf("%-15s %-12s %-20s %-12s %-8s %-10s %-15s %-15s%n",
                    "N° Ticket", "DNI Cliente", "Evento", "Fecha", "Hora", "Precio", "N° Asiento", "Fila Asiento");
            System.out.println("------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-15s %-12s %-20s %-12s %-8s %-10.2f %-15d %-15s%n",
                    ticket.getId(), cliente.getDni(), eventoSeleccionado.getNombre(),
                    eventoSeleccionado.getFecha(), eventoSeleccionado.getHora(), eventoSeleccionado.getPrecio(),
                    nuevoAsiento, filaAsignada);

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
