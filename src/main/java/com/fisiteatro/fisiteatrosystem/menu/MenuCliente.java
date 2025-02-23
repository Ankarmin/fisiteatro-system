package com.fisiteatro.fisiteatrosystem.menu;

import com.fisiteatro.fisiteatrosystem.datastructures.Cola;
import com.fisiteatro.fisiteatrosystem.model.dao.AsientoDAO;
import com.fisiteatro.fisiteatrosystem.model.dao.ClienteDAO;
import com.fisiteatro.fisiteatrosystem.model.dao.EventoDAO;
import com.fisiteatro.fisiteatrosystem.model.dao.TicketDAO;
import com.fisiteatro.fisiteatrosystem.model.dto.AsientoDTO;
import com.fisiteatro.fisiteatrosystem.model.dto.ClienteDTO;
import com.fisiteatro.fisiteatrosystem.model.dto.EventoDTO;
import com.fisiteatro.fisiteatrosystem.model.dto.TicketDTO;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MenuCliente {
    private final Scanner scanner;
    private final EventoDAO eventoDAO;
    private final TicketDAO ticketDAO;
    private final String clienteDni;

    public MenuCliente(String clienteDni) {
        this.scanner = new Scanner(System.in);
        this.eventoDAO = new EventoDAO();
        this.ticketDAO = new TicketDAO();
        this.clienteDni = clienteDni;
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ CLIENTE ---");
            System.out.println("1. Ver eventos");
            System.out.println("2. Comprar ticket");
            System.out.println("3. Eliminar ticket");
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
                    eliminarTicket();
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

        List<EventoDTO> eventoDTOS = eventoDAO.readAll();
        if (eventoDTOS.isEmpty()) {
            System.out.println("No hay eventos disponibles para comprar boletos.");
            return;
        }

        eventoDAO.verCatalogo();
        System.out.print("Seleccione el ID del evento: ");
        int seleccion = scanner.nextInt();
        scanner.nextLine();

        EventoDTO eventoDTOSeleccionado = eventoDAO.getById(seleccion);
        if (eventoDTOSeleccionado.getCapacidad() <= 0) {
            System.out.println("El evento está agotado.");
            return;
        }

        // se le asigna un numero d asiento nodo
        AsientoDAO asientoDAO = new AsientoDAO(eventoDTOSeleccionado.getId());
        AsientoDTO asientoDTOSeleccionado = asientoDAO.obtenerPrimerAsientoDisponible();

        ClienteDTO clienteDTO = new ClienteDAO(new Cola<>()).obtenerPorDni(clienteDni);
        if (clienteDTO == null) {
            System.out.println("Error: Cliente no encontrado.");
            return;
        }

        // aca se gener id y s mete en el constructor
        int idTicket = ticketDAO.createId();

        // cambiar el estado del asiento
        try {
            asientoDAO.updateOcupado(asientoDTOSeleccionado, eventoDTOSeleccionado.getId());
            System.out.println("asiento en false");
        } catch (IOException e) {
            System.out.println("Error al cambiar el estado del asiento: " + e.getMessage());
        }

        TicketDTO ticketDTO = new TicketDTO(idTicket, clienteDTO, asientoDTOSeleccionado, eventoDTOSeleccionado);

        try {
            // eventoDAO.reducirCapacidad(eventoDTOSeleccionado);
            ticketDAO.create(ticketDTO); // se guardan todos en una pila
            System.out.println("Ticket generado con éxito. Número de asiento: " + asientoDTOSeleccionado.getNumero() + " - Fila: " + asientoDTOSeleccionado.getFila());

            // Mostrar los datos en forma de tabla
            System.out.println("\n--------------------- DETALLE DEL TICKET GENERADO -------------------");
            System.out.printf("%-15s %-12s %-20s %-12s %-8s %-10s %-15s %-15s%n",
                    "N° Ticket", "DNI Cliente", "Evento", "Fecha", "Hora", "Precio", "N° Asiento", "Fila Asiento");
            System.out.println("------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-15s %-12s %-20s %-12s %-8s %-10.2f %-15d %-15s%n",
                    ticketDTO.getId(), clienteDTO.getDni(), eventoDTOSeleccionado.getNombre(),
                    eventoDTOSeleccionado.getFecha(), eventoDTOSeleccionado.getHora(), eventoDTOSeleccionado.getPrecio(),
                    asientoDTOSeleccionado.getNumero(), asientoDTOSeleccionado.getFila());

        } catch (IOException e) {
            System.out.println("Error al guardar la reserva.");
        }

    }

    private void eliminarTicket() {
        /* List<TicketDTO> ticketDTOS = ticketDAO.readAll(); //tickets comprados d todos los clientes

        if (ticketDTOS.isEmpty()) {
            System.out.println("No hay tickets comprados.");
            return;
        }

        // Filtrar los tickets del cliente actual
        ListaEnlazada<TicketDTO> ticketsCliente = ticketDAO.getTicketsPorCliente(clienteDni);

        if (ticketsCliente.toList().isEmpty()) {
            System.out.println("No tienes tickets para eliminar.");
            return;
        }

        // Mostrar los tickets disponibles para eliminar
        System.out.println("\n--- TICKETS DISPONIBLES PARA ELIMINAR ---");
        System.out.printf("%-15s %-12s %-20s %-12s %-8s %-10s %-15s %-15s%n",
                "N° Ticket", "DNI Cliente", "Evento", "Fecha", "Hora", "Precio", "N° Asiento", "Fila Asiento");
        System.out.println("------------------------------------------------------------------------------------------------------------");

        for (TicketDTO ticketDTO : ticketsCliente.toList()) {
            System.out.printf("%-15s %-12s %-20s %-12s %-8s %-10.2f %-15d %-15s%n",
                    ticketDTO.getId(), ticketDTO.getCliente().getDni(), ticketDTO.getEvento().getNombre(),
                    ticketDTO.getEvento().getFecha(), ticketDTO.getEvento().getHora(), ticketDTO.getEvento().getPrecio(),
                    ticketDTO.getAsiento().getNumero(), ticketDTO.getAsiento().getFila());
        }

        System.out.print("Ingrese el ID del ticket que desea eliminar: ");
        int ticketId = scanner.nextInt();
        scanner.nextLine();

        // Buscar el ticket en la lista
        TicketDTO ticketDTOAEliminar = ticketDAO.getTicketById(ticketId, ticketsCliente);

        if (ticketDTOAEliminar == null) {
            System.out.println("Error: No se encontró un ticket con el ID ingresado.");
            return;
        }

        // ticket se guarda en una cola, luego la cola se sube a solicitudes.json
        Cola<TicketDTO> colaTicketsEliminados = ticketDAO.getSolicitudesTickets();

        try {
            // Agregar el ticket eliminado a la cola
            colaTicketsEliminados.offer(ticketDTOAEliminar);

            // Guardar la cola actualizada en el JSON
            ticketDAO.saveSolicitudesTicketsJSON(colaTicketsEliminados);

            // Aumenta en 1 la capacidad -> TODAVÌA, K SE AUMENTE CUANDO SE ACEPTA LA SOLICITUD
            //eventoDAO.aumentarCapacidad(ticketAEliminar.getEvento());

        } catch (IOException e) {
            System.out.println("Error al guardar el ticket eliminado en el archivo: " + e.getMessage());
            return;
        }

        // Eliminar el ticket del DAO
        try {
            ticketDAO.delete(ticketDTOAEliminar.getCliente().getDni(),
                    ticketDTOAEliminar.getAsiento().getFila(),
                    ticketDTOAEliminar.getAsiento().getNumero());
            System.out.println("El ticket ha sido eliminado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al eliminar el ticket: " + e.getMessage());
        } */
    }

    private void mostrarHistorialCompras() {
        List<TicketDTO> ticketDTOS = ticketDAO.readAll();
        if (ticketDTOS.isEmpty()) {
            System.out.println("No hay tickets comprados.");
            return;
        }

        // Filtrar los tickets del cliente actual
        List<TicketDTO> ticketsCliente = ticketDTOS.stream()
                .filter(ticketDTO -> ticketDTO.getCliente().getDni().equals(clienteDni))
                .toList();

        if (ticketsCliente.isEmpty()) {
            System.out.println("No tienes tickets en tu historial de compras.");
            return;
        }

        System.out.println("\n--- HISTORIAL DE COMPRAS ---");
        System.out.printf("%-15s %-12s %-20s %-12s %-8s %-10s %-15s %-15s%n",
                "N° Ticket", "DNI Cliente", "Evento", "Fecha", "Hora", "Precio", "N° Asiento", "Fila Asiento");
        System.out.println("------------------------------------------------------------------------------------------------------------");

        for (TicketDTO ticketDTO : ticketsCliente) {
            System.out.printf("%-15s %-12s %-20s %-12s %-8s %-10.2f %-15d %-15s%n",
                    ticketDTO.getId(), ticketDTO.getCliente().getDni(), ticketDTO.getEvento().getNombre(),
                    ticketDTO.getEvento().getFecha(), ticketDTO.getEvento().getHora(), ticketDTO.getEvento().getPrecio(),
                    ticketDTO.getAsiento().getNumero(), ticketDTO.getAsiento().getFila());
        }
    }
}
