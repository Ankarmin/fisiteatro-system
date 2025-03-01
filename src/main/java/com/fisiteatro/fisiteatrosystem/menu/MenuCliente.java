package com.fisiteatro.fisiteatrosystem.menu;

import com.fisiteatro.fisiteatrosystem.datastructures.Cola;
import com.fisiteatro.fisiteatrosystem.datastructures.ListaEnlazada;
import com.fisiteatro.fisiteatrosystem.datastructures.Pila;
import com.fisiteatro.fisiteatrosystem.model.dao.AsientoDAO;
import com.fisiteatro.fisiteatrosystem.model.dao.ClienteDAO;
import com.fisiteatro.fisiteatrosystem.model.dao.EventoDAO;
import com.fisiteatro.fisiteatrosystem.model.dao.TicketDAO;
import com.fisiteatro.fisiteatrosystem.model.dto.Asiento;
import com.fisiteatro.fisiteatrosystem.model.dto.Cliente;
import com.fisiteatro.fisiteatrosystem.model.dto.Evento;
import com.fisiteatro.fisiteatrosystem.model.dto.Ticket;

import java.io.File;
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

        // Declaración de variables para su uso fuera del if-else
        int idTicket;
        Asiento asientoSeleccionado;

        // PRIMERO REVISAR SI HAY TICKETS ELIMINADOS PARA EL EVENTO
        // revisar si existe el archivo, q exista y q tenga lineas escritas IF ELSE
        int idEventoSeleccionado = eventoSeleccionado.getId();
        String PATH = "src/main/java/com/fisiteatro/fisiteatrosystem/data/ticketsEliminadosPorEvento/eliminados_";
        File archivo = new  File(PATH + idEventoSeleccionado + ".json");

        if (archivo.exists() && archivo.length() > 0) {
            // en el arbol ya sale como false, en la pila solo se hace pop y se guarda, y se crea en ticketscomprados
            // parecido a lo de cola a pila en solicitudes por parte del admin

            // acceder a la pila con tickets eliminados del evento
            Pila<Ticket> pilaEliminados = ticketDAO.getTicketsEliminados(idEventoSeleccionado);

            // obtener el ticket para reusar los datos
            Ticket ticketReemitido = pilaEliminados.peek();

            // sacamos los datos del ticket para pasarlo al constructor
            idTicket = ticketReemitido.getId();
            asientoSeleccionado = ticketReemitido.getAsiento();
            asientoSeleccionado.setEstado(false);

            // se elimina de la pila
            try {
                ticketDAO.deleteTicketEliminado(ticketReemitido, pilaEliminados);
                System.out.println("se borro de la pila eliminados");
            } catch (IOException e) {
                System.out.println("no se borro de la pila eliminados. " + e.getMessage());
            }

        } else {
            // SI NO HAY TICKETS ELIMINADOS PARA EL EVENTO, QUE SE ASIGNE DESDE EL ÁRBOL

            // se le asigna un numero d asiento nodo
            AsientoDAO asientoDAO = new AsientoDAO(eventoSeleccionado.getId());
            asientoSeleccionado = asientoDAO.obtenerPrimerAsientoDisponible();

            // aca se gener id y s mete en el constructor
            idTicket = ticketDAO.createId();

            // cambiar el estado del asiento en el arbol
            try {
                asientoDAO.updateOcupado(asientoSeleccionado, eventoSeleccionado.getId());
                System.out.println("asiento en false");
            } catch (IOException e) {
                System.out.println("Error al cambiar el estado del asiento: " + e.getMessage());
            }
        }

        // se mantiene fuera para reemplazar los datos del cliente en el ticket
        Cliente cliente = new ClienteDAO(new Cola<>()).obtenerPorDni(clienteDni);
        if (cliente == null) {
            System.out.println("Error: Cliente no encontrado.");
            return;
        }

        // se queda
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

    private void eliminarTicket() {
        List<Ticket> tickets = ticketDAO.readAll(); //tickets comprados d todos los clientes

        if (tickets.isEmpty()) {
            System.out.println("No hay tickets comprados.");
            return;
        }

        // Filtrar los tickets del cliente actual
        ListaEnlazada<Ticket> ticketsCliente = ticketDAO.getTicketsPorCliente(clienteDni);

        if (ticketsCliente.toList().isEmpty()) {
            System.out.println("No tienes tickets para eliminar.");
            return;
        }

        // Mostrar los tickets disponibles para eliminar
        System.out.println("\n--- TICKETS DISPONIBLES PARA ELIMINAR ---");
        System.out.printf("%-15s %-12s %-20s %-12s %-8s %-10s %-15s %-15s%n",
                "N° Ticket", "DNI Cliente", "Evento", "Fecha", "Hora", "Precio", "N° Asiento", "Fila Asiento");
        System.out.println("------------------------------------------------------------------------------------------------------------");

        for (Ticket ticket : ticketsCliente.toList()) {
            System.out.printf("%-15s %-12s %-20s %-12s %-8s %-10.2f %-15d %-15s%n",
                    ticket.getId(), ticket.getCliente().getDni(), ticket.getEvento().getNombre(),
                    ticket.getEvento().getFecha(), ticket.getEvento().getHora(), ticket.getEvento().getPrecio(),
                    ticket.getAsiento().getNumero(), ticket.getAsiento().getFila());
        }

        System.out.print("Ingrese el ID del ticket que desea eliminar: ");
        int ticketId = scanner.nextInt();
        scanner.nextLine();

        // Buscar el ticket en la lista
        Ticket ticketAEliminar = ticketDAO.getTicketById(ticketId, ticketsCliente);

        if (ticketAEliminar == null) {
            System.out.println("Error: No se encontró un ticket con el ID ingresado.");
            return;
        }

        // ticket se guarda en una cola, luego la cola se sube a solicitudes.json
        Cola<Ticket> colaTicketsEliminados = ticketDAO.getSolicitudesTickets();

        try {
            // Agregar el ticket eliminado a la cola
            colaTicketsEliminados.offer(ticketAEliminar);

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
            ticketDAO.delete(ticketAEliminar.getCliente().getDni(),
                    ticketAEliminar.getAsiento().getFila(),
                    ticketAEliminar.getAsiento().getNumero());
            System.out.println("El ticket ha sido eliminado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al eliminar el ticket: " + e.getMessage());
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
