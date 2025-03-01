package com.fisiteatro.fisiteatrosystem.model.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fisiteatro.fisiteatrosystem.datastructures.Cola;
import com.fisiteatro.fisiteatrosystem.datastructures.Pila;
import com.fisiteatro.fisiteatrosystem.model.dto.TicketDTO;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class TicketDAO implements ITicketDAO {
    private static final String FILE_PATH = "src/main/java/com/fisiteatro/fisiteatrosystem/data/ticketsComprados.json";
    private static final String PATH_ELIMINADOS = "src/main/java/com/fisiteatro/fisiteatrosystem/data/ticketsEliminadosPorEvento/eliminados_";
    private final Pila<TicketDTO> tickets;

    public TicketDAO() {

        this.tickets = new Pila<>();
        try {
            tickets.cargarDesdeJson(FILE_PATH, TicketDTO[].class);
            System.out.println("Tickets cargados con exito");
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
        }
    }

    public void create(TicketDTO ticketDTO) throws IOException {
        ticketDTO.setId(createId());
        tickets.push(ticketDTO);
        saveToFile();
    }

    private boolean validarId(int id) {
        for (TicketDTO ticketDTO : tickets.toList()) {
            if (id == ticketDTO.getId()) {
                return true;
            }
        }
        return false;
    }

    private int createId() {
        Random rand = new Random();
        int id;
        do {
            id = rand.nextInt(9000) + 1000;
        } while (validarId(id));
        return id;
    }

    public List<TicketDTO> readAll() {
        return tickets.toList();
    }

    public void update(TicketDTO ticketDTO) throws IOException {
        Pila<TicketDTO> temp = new Pila<>();
        while (!tickets.isEmpty()) {
            TicketDTO current = tickets.pop();
            if (current.getCliente().getDni().equals(ticketDTO.getCliente().getDni()) &&
                    current.getAsiento().getFila().equals(ticketDTO.getAsiento().getFila()) &&
                    current.getAsiento().getNumero() == ticketDTO.getAsiento().getNumero()) {
                temp.push(ticketDTO);
            } else {
                temp.push(current);
            }
        }
        while (!temp.isEmpty()) {
            tickets.push(temp.pop());
        }
        saveToFile();
    }

    public Pila<TicketDTO> getTicketsPorDNI(String DNI) {
        Pila<TicketDTO> ticketsCliente = new Pila<>();
        for (TicketDTO ticketDTO : tickets.toList()) {
            if (ticketDTO.getCliente().getDni().equals(DNI)) {
                ticketsCliente.push(ticketDTO);
            }
        }
        return ticketsCliente;
    }

    public Cola<TicketDTO> getSolicitudesTickets() {
        String FILENAME = "src/main/java/com/fisiteatro/fisiteatrosystem/data/solicitudesTickets.json";
        Cola<TicketDTO> solicitudesTickets = new Cola<>();
        try {
            solicitudesTickets.cargarDesdeJson(FILENAME, TicketDTO[].class);
            System.out.println("Solicitudes tickets cargados con exito");
        } catch (IOException e) {
            System.out.println("Error al cargar solicitudes d tickets: " + e.getMessage());
        }
        return solicitudesTickets;
    }

    public void deleteSolicitud(Cola<TicketDTO> solicitudes) throws IOException {
        solicitudes.desencolar();
        saveSolicitudesTicketsJSON(solicitudes);
    }

    public void saveSolicitudesTicketsJSON(Cola<TicketDTO> solicitudesTickets) throws IOException {
        String FILENAME = "src/main/java/com/fisiteatro/fisiteatrosystem/data/solicitudesTickets.json";
        File file = new File(FILENAME);
        file.getParentFile().mkdirs();

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, solicitudesTickets.toList());
    }

    public Pila<TicketDTO> getTicketsEliminados(int idEvento) {
        String FILENAME = PATH_ELIMINADOS + idEvento + ".json";
        Pila<TicketDTO> ticketsEliminados = new Pila<>();
        try {
            ticketsEliminados.cargarDesdeJson(FILENAME, TicketDTO[].class);
            System.out.println("pila tickets eliminados cargados");
        } catch (IOException e) {
            System.out.println("Error al cargar la pila d tickets eliminados: " + e.getMessage());
        }
        return ticketsEliminados;
    }

    public void addTicketEliminado(TicketDTO ticketDTO, Pila<TicketDTO> pila) throws IOException {
        ticketDTO.getEvento().setCapacidad(ticketDTO.getEvento().getCapacidad() + 1);
        ticketDTO.getAsiento().setEstado(true);

        pila.push(ticketDTO);
        saveTicketsEliminadosJSON(pila, ticketDTO.getEvento().getId());
    }

    public void saveTicketsEliminadosJSON(Pila<TicketDTO> ticketsEliminados, int idEvento) throws IOException {
        String FILENAME = PATH_ELIMINADOS + idEvento + ".json";
        File file = new File(FILENAME);
        file.getParentFile().mkdirs();

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, ticketsEliminados.toList());
    }

    public void delete(String dni, String fila, int numero) throws IOException {
        Pila<TicketDTO> temp = new Pila<>();
        while (!tickets.isEmpty()) {
            TicketDTO current = tickets.pop();
            if (!(current.getCliente().getDni().equals(dni) &&
                    current.getAsiento().getFila().equals(fila) &&
                    current.getAsiento().getNumero() == numero)) {
                temp.push(current);
            }
        }
        while (!temp.isEmpty()) {
            tickets.push(temp.pop());
        }
        //tickets = temp;
        saveToFile();
    }

    private void saveToFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), tickets.toList());
    }

    public void aceptarSolicitud(TicketDTO ticket) throws IOException {
        Cola<TicketDTO> solicitudes = getSolicitudesTickets();
        deleteSolicitud(solicitudes);

        Pila<TicketDTO> ticketsEliminados = getTicketsEliminados(ticket.getEvento().getId());
        addTicketEliminado(ticket, ticketsEliminados);
    }

    public TicketDTO eliminarTicket() throws IOException {
        if (!tickets.isEmpty()) {
            TicketDTO ticketEliminado = tickets.pop(); // Elimina y retorna el primer elemento de la pila (LIFO)
            saveToFile();
            return ticketEliminado;
        } else {
            System.out.println("No hay tickets para eliminar.");
            return null;
        }
    }
}