package com.fisiteatro.fisiteatrosystem.model.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fisiteatro.fisiteatrosystem.datastructures.Cola;
import com.fisiteatro.fisiteatrosystem.datastructures.ListaEnlazada;
import com.fisiteatro.fisiteatrosystem.datastructures.Pila;
import com.fisiteatro.fisiteatrosystem.model.dto.Ticket;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class TicketDAO implements ITicketDAO {
    private static final String FILE_PATH = "src/main/java/com/fisiteatro/fisiteatrosystem/data/ticketsComprados.json";
    private Pila<Ticket> tickets;

    public TicketDAO() {

        this.tickets = new Pila<>();
        try {
            tickets.cargarDesdeJson(FILE_PATH, Ticket[].class);
            System.out.println("Tickets cargados con exito");
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
        }
    }

    public void create(Ticket ticket) throws IOException {
        tickets.push(ticket);
        saveToFile();
    }

    private boolean validarId(int id) {
        for (Ticket ticket : tickets.toList()) {
            if (id == ticket.getId()) {
                return true;
            }
        }
        return false;
    }

    public int createId() {
        Random rand = new Random();
        int id;
        do {
            id = rand.nextInt(9000) + 1000;
        } while (validarId(id));
        return id;
    }

    public List<Ticket> readAll() {
        return tickets.toList();
    }

    // solo se podria actulizar el cliente del ticket (despues d cancelarlo)
    public void update(Ticket ticket) throws IOException {
        Pila<Ticket> temp = new Pila<>();
        while (!tickets.isEmpty()) {
            Ticket current = tickets.pop();
            if (current.getCliente().getDni().equals(ticket.getCliente().getDni()) &&
                    current.getAsiento().getFila().equals(ticket.getAsiento().getFila()) &&
                    current.getAsiento().getNumero() == ticket.getAsiento().getNumero()) {
                temp.push(ticket);
            } else {
                temp.push(current);
            }
        }
        tickets = temp;
        saveToFile();
    }

    public ListaEnlazada<Ticket> getTicketsPorCliente(String dni) {
        ListaEnlazada<Ticket> ticketsCliente = new ListaEnlazada<>();
        for (Ticket ticket : tickets.toList()) {
            if (ticket.getCliente().getDni().equals(dni)) {
                ticketsCliente.add(ticket);
            }
        }
        return ticketsCliente;

    }

    public Ticket getTicketById(int id, ListaEnlazada<Ticket> ticketsCliente) {
        for (Ticket ticket : ticketsCliente.toList()) {
            if (ticket.getId() == id) {
                return ticket;
            }
        }
        return null;
    }

    public Cola<Ticket> getSolicitudesTickets() {
        String FILENAME = "src/main/java/com/fisiteatro/fisiteatrosystem/data/solicitudesTickets.json";
        Cola<Ticket> solicitudesTickets = new Cola<>();
        try {
            solicitudesTickets.cargarDesdeJson(FILENAME, Ticket[].class);
            System.out.println("Solicitudes tickets cargados con exito");
        } catch (IOException e) {
            System.out.println("Error al cargar solicitudes d tickets: " + e.getMessage());
        }
        return solicitudesTickets;
    }

    public void saveSolicitudesTicketsJSON(Cola<Ticket> solicitudesTickets) throws IOException {
        String FILENAME = "src/main/java/com/fisiteatro/fisiteatrosystem/data/solicitudesTickets.json";

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILENAME), solicitudesTickets.toList());
    }


    public void delete(String dni, String fila, int numero) throws IOException {
        Pila<Ticket> temp = new Pila<>();
        while (!tickets.isEmpty()) {
            Ticket current = tickets.pop();
            if (!(current.getCliente().getDni().equals(dni) &&
                    current.getAsiento().getFila().equals(fila) &&
                    current.getAsiento().getNumero() == numero)) {
                temp.push(current);
            }
        }
        tickets = temp;
        saveToFile();
    }

    private void loadFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try {
                List<Ticket> ticketList = mapper.readValue(file,
                        mapper.getTypeFactory().constructCollectionType(List.class, Ticket.class));
                // Insertar en orden inverso para mantener la pila correctamente
                for (int i = ticketList.size() - 1; i >= 0; i--) {
                    tickets.push(ticketList.get(i));
                }
            } catch (IOException e) {
                System.out.println("Error al cargar los tickets desde el archivo: " + e.getMessage());
            }
        }
    }

    private void saveToFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), tickets.toList());
    }
}