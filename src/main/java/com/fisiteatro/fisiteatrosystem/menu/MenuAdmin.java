package com.fisiteatro.fisiteatrosystem.menu;

import com.fisiteatro.fisiteatrosystem.datastructures.Cola;
import com.fisiteatro.fisiteatrosystem.datastructures.Nodo;
import com.fisiteatro.fisiteatrosystem.datastructures.Pila;
import com.fisiteatro.fisiteatrosystem.model.dao.AdministradorDAO;
import com.fisiteatro.fisiteatrosystem.model.dao.AsientoDAO;
import com.fisiteatro.fisiteatrosystem.model.dao.EventoDAO;
import com.fisiteatro.fisiteatrosystem.model.dao.TicketDAO;
import com.fisiteatro.fisiteatrosystem.model.dto.Administrador;
import com.fisiteatro.fisiteatrosystem.model.dto.Asiento;
import com.fisiteatro.fisiteatrosystem.model.dto.Evento;
import com.fisiteatro.fisiteatrosystem.model.dto.Ticket;

import java.io.IOException;
import java.util.Scanner;

public class MenuAdmin {

    private final AdministradorDAO adminDAO;
    private final EventoDAO eventoDAO;
    private final Scanner scanner;
    private final TicketDAO ticketDAO;

    public MenuAdmin() {
        this.adminDAO = new AdministradorDAO();
        this.eventoDAO = new EventoDAO();
        this.scanner = new Scanner(System.in);
        this.ticketDAO = new TicketDAO();
    }

    public void mostrarMenu() {
        char opcion;
        do {
            System.out.println("\n\t---ADMINISTRADOR---\n");
            System.out.println("0. Ver eventos");
            System.out.println("1. CRUD Eventos");
            System.out.println("2. Gestionar Tickets");
            System.out.println("3. Reportes");
            System.out.println("4. Cambiar contraseña");
            System.out.println("5. Cerrar Sesión");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.next().charAt(0);
            scanner.nextLine();

            switch (opcion) {
                case '0':
                    eventoDAO.verCatalogo();
                    break;
                case '1':
                    // menu del crud
                    mostrarMenuEventos();
                    break;
                case '2':
                    // ver la cima d la pila de tickets y que le salga si aceptar o no
                    gestionarTickets();
                    break;
                case '3':
                    // reportes si da tiempo
                    break;
                case '4':
                    // cambiar contra4
                    cambiarContrasenia();
                    break;
                case '5':
                    System.out.println("Saliendo de la sesión...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != '5');
    }

    private void agregarEvento() {
        // datos: nombre, fecha, hora, precio, capacidad
        System.out.print("\nIngrese nombre del evento: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese fecha del evento (AAAA-MM-DD): ");
        String fecha = scanner.nextLine();

        System.out.print("Ingrese hora del evento (HH:MM): ");
        String hora = scanner.nextLine();

        System.out.print("Ingrese el precio de entrada: ");
        float precio = scanner.nextFloat();
        scanner.nextLine();

        System.out.print("Ingrese la capacidad del evento: ");
        int capacidad = scanner.nextInt();
        scanner.nextLine();

        int id = eventoDAO.createId();

        Evento nuevoEvento = new Evento(id, nombre, fecha, hora, precio, capacidad);
        try {
            eventoDAO.create(nuevoEvento);
            // crear los asientos
            AsientoDAO asientoDAO = new AsientoDAO(nuevoEvento.getId()); // aca d por si se crea el file
            asientoDAO.create(nuevoEvento.getCapacidad(), nuevoEvento.getId()); // aca escribe en el file
            System.out.println("\nEvento creado con éxito.");
        } catch (IOException e) {
            System.out.println("Error al crear evento: " + e.getMessage());
        }
    }

    private void modificarEvento() {
        // primero mostrar la lista de eventos con indice y q el admin elija
        // evento modificar segun el indice
        eventoDAO.verCatalogo();

        System.out.print("Ingrese el ID del evento a modificar: ");
        int indice = scanner.nextInt();
        scanner.nextLine();

        if (!eventoDAO.validarId(indice)) {
            System.out.println("ID no válido.");
            return;
        }

        // el evento del indice se guarde en una variable y elegir q se quiere editar para no tener q llenar todos los campos
        Evento evento = eventoDAO.getById(indice);

        if (evento == null) {
            System.out.println("ID incorrecto.");
            return;
        }

        // menu q hace los cambios
        opcionesModificarEvento(evento);

        try {
            eventoDAO.update(evento);
            System.out.println("\n\tEvento actualizado con éxito\n");
            eventoDAO.verCatalogo();
        } catch (IOException e) {
            System.out.println("Error al actualizar evento: " + e.getMessage());
        }
    }

    private void eliminarEvento() {
        // primero mostrar la lista de eventos con indice y q el admin elija
        // evento eliminar segun el indice
        eventoDAO.verCatalogo();

        System.out.print("Ingrese el ID del evento a eliminar: ");
        int indice = scanner.nextInt();
        scanner.nextLine();

        if (!eventoDAO.validarId(indice)) {
            System.out.println("ID no válido.");
            return;
        }

        try {
            Evento evento = eventoDAO.getById(indice);
            // borrar el archivo
            AsientoDAO asientoDAO = new AsientoDAO(evento.getId());
            asientoDAO.deleteFile(evento.getId());

            eventoDAO.deleteById(indice);
            System.out.println("Evento eliminado correctamente.\n");
            eventoDAO.verCatalogo();
        } catch (IOException e) {
            System.out.println("Error al eliminar evento: " + e.getMessage());
        }
    }

    private void gestionarTickets() {
        // k muestre la primera solicitud y si hay mas k debajo saalgaa "mas solicitudes..."
        // y k ingresanado x sale d la gstion
        Cola<Ticket> solicitudesTickets = ticketDAO.getSolicitudesTickets();

        if (solicitudesTickets.isEmpty()) {
            System.out.println("No hay solicitudes de cancelación por revisar.");
            return;
        }

        Nodo<Ticket> ticket = solicitudesTickets.getFrente();
        do {
            Ticket ticketActual = solicitudesTickets.getDato(ticket);

            // carga la pilaa d tickets eliminados segun el evento (se guardaa en diferentes arhivos)
            Pila<Ticket> ticketsEliminados = ticketDAO.getTicketsEliminados(ticketActual.getEvento().getId());

            System.out.println("\n\t\t\t\t--- SOLICITUDES DE CANCELACIÓN ---\n");
            System.out.printf("%-5s %-20s %-12s %-8s %-10s%n", "N° Ticket", "Evento", "Fecha", "Hora", "Cliente");
            System.out.println("----------------------------------------------------------------------------------");
            System.out.printf("%-5d %-20s %-12s %-8s %-10s%n",
                    ticketActual.getId(), ticketActual.getEvento().getNombre(), ticketActual.getEvento().getFecha(),
                    ticketActual.getEvento().getHora(), ticketActual.getCliente().getNombreCompleto());
            System.out.println("----------------------------------------------------------------------------------");
            if (ticket == solicitudesTickets.getFondo()) {
                System.out.println("No se encontraron más solicitudes en cola");
            } else {
                System.out.println("Más solicitudes en cola...");
            }

            //logica d aceptar o no
            System.out.println("\n¿Desea aceptar la cancelación de este ticket? (S/N)\n" +
                    "Si desea salir de la gestión de tickets, ingrese 'X': ");
            String response = scanner.nextLine().toUpperCase();

            switch (response) {
                case "S":
                    try {
                        // aumentaaa n uno la capacidad del evento y cambia el estado del asiento a true(disp)
                        Evento eventoActual = eventoDAO.getById(ticketActual.getEvento().getId());
                        AsientoDAO asientoDAO = new AsientoDAO(eventoActual.getId());
                        Asiento asientoActual = ticketDAO.getAsiento(ticketActual.getAsiento().getNumero(), eventoActual);

                        eventoDAO.aumentarCapacidad(eventoActual);
                        // ETE NO CAMBIA EL ESTADO DEL ASIENTO -> NO LO PASA A TRUE
                        asientoDAO.updateDesocupado(asientoActual, eventoActual.getId());

                        // agrega ticket a la pila d eliminados correspondiente
                        // creaa json segun el evento y sobreescribe
                        ticketDAO.addTicketEliminado(ticketActual, ticketsEliminados);

                        // k se borre d las solicitudes
                        ticketDAO.deleteSolicitud(solicitudesTickets);

                        System.out.println("Ticket eliminado correctamente.");
                    } catch (IOException e) {
                        System.out.println("Error al eliminar el ticket: " + e.getMessage());
                    }
                    break;
                case "N":

                    try {
                        // si no se acepta la cancelacion k regrese a la pilaa d tickets comprados
                        ticketDAO.create(ticketActual);

                        // k se borre d las solicitudes
                        ticketDAO.deleteSolicitud(solicitudesTickets);

                        System.out.println("Cancelación negada correctamente.");
                    } catch (IOException e) {
                        System.out.println("Error al negar cancelación: " + e.getMessage());
                    }
                    break;
                case "X":
                    return;
                default:
                    System.out.println("Respuesta no válida. Vuelva a intentar");
            }

            // pasa al siguiente
            ticket = solicitudesTickets.pasarSiguiente(ticket);
        } while (ticket != null);


        // q se muestre el frente de la cola y se vea si cola sig != null
        // q se muestre algo como "mas tickets en la cola"
        // opcions d si/no para la cancelacion, luego d esto el ticket
        // se va a una pila
    }

    private void cambiarContrasenia() {
        System.out.print("\nIngrese contraseña actual: ");
        String contraseniaActual = scanner.nextLine();

        if (!adminDAO.verificarContrasenia(contraseniaActual)) {
            System.out.println("Contraseña incorrecta.");
            return;
        }

        System.out.print("Ingrese la nueva contraseña: ");
        String contraseniaNueva = scanner.nextLine();

        Administrador administrador = adminDAO.cambiarContrasenia(contraseniaNueva);
        try {
            adminDAO.update(administrador);
            System.out.println("\nContraseña actualizada con exito.");
        } catch (IOException e) {
            System.out.println("Error al intentar cambiar la contraseña: " + e.getMessage());
        }
    }

    private void mostrarMenuEventos() {
        char opcion;
        do {
            System.out.println("\n\t---CRUD EVENTOS---\n");
            System.out.println("1. Agregar evento");
            System.out.println("2. Modificar evento");
            System.out.println("3. Eliminar evento");
            System.out.println("4. Regresar");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.next().charAt(0);
            scanner.nextLine();

            switch (opcion) {
                case '1':
                    // agrgar
                    agregarEvento();
                    break;
                case '2':
                    // modificar
                    modificarEvento();
                    break;
                case '3':
                    // eliminar
                    eliminarEvento();
                    break;
                case '4':
                    System.out.println("Regresando al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != '4');
    }

    public void opcionesModificarEvento(Evento evento) {
        char opcion;
        System.out.println("\n\t---MODIFICAR EVENTO---\n");
        System.out.println("1. Modificar fecha");
        System.out.println("2. Modificar hora");
        System.out.println("3. Modificar precio");
        System.out.println("4. Modificar capacidad");
        System.out.println("5. Regresar");
        System.out.print("Ingrese opción: ");

        opcion = scanner.next().charAt(0);
        scanner.nextLine();

        switch (opcion) {
            case '1':
                System.out.print("\nIngrese nueva fecha (AAAA-MM-DD): ");
                String nuevaFecha = scanner.nextLine();
                evento.setFecha(nuevaFecha);
                break;
            case '2':
                System.out.print("\nIngrese nueva hora (HH:MM): ");
                String nuevaHora = scanner.nextLine();
                evento.setHora(nuevaHora);
                break;
            case '3':
                System.out.print("\nIngrese nuevo precio: ");
                float nuevoPrecio = scanner.nextFloat();
                evento.setPrecio(nuevoPrecio);
                break;
            case '4':
                System.out.print("\nIngrese nueva capacidad: ");
                int nuevaCapacidad = scanner.nextInt();
                evento.setCapacidad(nuevaCapacidad);
                // volver a crear con cierta capacidad(sobreescribe)
                AsientoDAO asientoDAO = new AsientoDAO(evento.getId());
                try {
                    asientoDAO.create(nuevaCapacidad, evento.getId());
                    System.out.println("Capacidad actualizada con exito.");
                } catch (IOException e) {
                    System.out.println("Error al intentar cambiar la capacidad: " + e.getMessage());
                }
                break;
            case '5':
                System.out.println("Regresando...");
                break;
            default:
                System.out.println("Opción no válida. Intente nuevamente.");
        }
    }

    public static void main(String[] args) {
        MenuAdmin menu = new MenuAdmin();
        menu.mostrarMenu();
    }
}
