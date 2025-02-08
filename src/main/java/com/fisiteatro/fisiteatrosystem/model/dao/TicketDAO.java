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

    private void saveToFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(FILE_PATH), tickets.toList());
    }
}