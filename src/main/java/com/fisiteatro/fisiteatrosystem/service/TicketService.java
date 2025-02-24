package com.fisiteatro.fisiteatrosystem.service;

import com.fisiteatro.fisiteatrosystem.datastructures.Cola;
import com.fisiteatro.fisiteatrosystem.datastructures.ListaEnlazada;
import com.fisiteatro.fisiteatrosystem.datastructures.Pila;
import com.fisiteatro.fisiteatrosystem.model.dao.TicketDAO;
import com.fisiteatro.fisiteatrosystem.model.dto.AsientoDTO;
import com.fisiteatro.fisiteatrosystem.model.dto.EventoDTO;
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

    public TicketDTO getTicketById(int id, ListaEnlazada<TicketDTO> ticketsCliente) {
        return ticketDAO.getTicketById(id, ticketsCliente);
    }

    public Cola<TicketDTO> getSolicitudesTickets() {
        return ticketDAO.getSolicitudesTickets();
    }

    public void deleteSolicitud(Cola<TicketDTO> solicitudes) throws IOException {
        ticketDAO.deleteSolicitud(solicitudes);
    }

    public Pila<TicketDTO> getTicketsEliminados(int idEvento) {
        return ticketDAO.getTicketsEliminados(idEvento);
    }

    public void addTicketEliminado(TicketDTO ticket, Pila<TicketDTO> pila) throws IOException {
        ticketDAO.addTicketEliminado(ticket, pila);
    }

    public AsientoDTO getAsiento(int numAsiento, EventoDTO evento) {
        return ticketDAO.getAsiento(numAsiento, evento);
    }

    public void delete(String dni, String fila, int numero) throws IOException {
        ticketDAO.delete(dni, fila, numero);
    }

    public void saveTicketsEliminadosJSON(Pila<TicketDTO> ticketsEliminados, int idEvento) throws IOException {
        ticketDAO.saveTicketsEliminadosJSON(ticketsEliminados, idEvento);
    }

    public void saveSolicitudesTicketsJSON(Cola<TicketDTO> solicitudesTickets) throws IOException {
        ticketDAO.saveSolicitudesTicketsJSON(solicitudesTickets);
    }
}