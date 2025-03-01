package com.fisiteatro.fisiteatrosystem.service;

import com.fisiteatro.fisiteatrosystem.datastructures.Cola;
import com.fisiteatro.fisiteatrosystem.datastructures.Pila;
import com.fisiteatro.fisiteatrosystem.model.dao.TicketDAO;
import com.fisiteatro.fisiteatrosystem.model.dto.TicketDTO;

import java.io.IOException;
import java.util.List;

public class TicketService {
    private final TicketDAO ticketDAO;

    public TicketService() {
        this.ticketDAO = new TicketDAO();
    }

    public void create(TicketDTO ticket) throws IOException {
        ticketDAO.create(ticket);
    }

    public List<TicketDTO> readAll() {
        return ticketDAO.readAll();
    }

    public void update(TicketDTO ticket) throws IOException {
        ticketDAO.update(ticket);
    }

    public Pila<TicketDTO> getTicketsPorDNI(String dni) {
        return ticketDAO.getTicketsPorDNI(dni);
    }

    public Cola<TicketDTO> getSolicitudesTickets() {
        return ticketDAO.getSolicitudesTickets();
    }

    public void delete(String dni, String fila, int numero) throws IOException {
        ticketDAO.delete(dni, fila, numero);
    }

    public void saveSolicitudesTicketsJSON(Cola<TicketDTO> solicitudesTickets) throws IOException {
        ticketDAO.saveSolicitudesTicketsJSON(solicitudesTickets);
    }

    public void aceptarSolicitud(TicketDTO ticket) throws IOException {
        ticketDAO.aceptarSolicitud(ticket);
    }

    public TicketDTO eliminarTicket() throws IOException {
        return ticketDAO.eliminarTicket();
    }
}