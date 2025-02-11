package com.fisiteatro.fisiteatrosystem.model.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fisiteatro.fisiteatrosystem.datastructures.Pila;
import com.fisiteatro.fisiteatrosystem.model.dto.Ticket;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TicketDAO implements ITicketDAO {
    private static final String FILE_PATH = "src/main/java/com/fisiteatro/fisiteatrosystem/data/ticket.json";
    private Pila<Ticket> tickets;

    public TicketDAO(Pila<Ticket> tickets) {

        this.tickets = tickets;
        loadFromFile();
    }

    public void create(Ticket ticket) throws IOException {
        tickets.push(ticket);
        saveToFile();
    }

    public List<Ticket> readAll() {
        return tickets.toList();
    }

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