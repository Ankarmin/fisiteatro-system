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

    public Pila<TicketDTO> getHistorialTicketsEliminados() {
        return ticketDAO.getHistorialTicketsEliminados();
    }

    public Pila<TicketDTO> getHistorialEliminadosPorDNI(String dni) {
        return ticketDAO.getHistorialEliminadosPorDNI(dni);
    }

    public Cola<TicketDTO> getSolicitudesTickets() {
        return ticketDAO.getSolicitudesTickets();
    }

    public Pila<TicketDTO> getTicketsComprados() {
        return ticketDAO.getTicketsComprados();
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

    public void rechazarSolicitud(TicketDTO ticket) throws IOException {
        ticketDAO.rechazarSolicitud(ticket);
    }

    public void deleteById(int id) throws IOException {
        ticketDAO.deleteById(id);
    }

    public TicketDTO reemitirTicket(int idEvento) throws IOException {
        return ticketDAO.reemitirTicket(idEvento);
    }

    public int totalTicketsVendidos() {
        return ticketDAO.totalTicketsVendidos();
    }

    public int totalTicketsEliminados() {
        return ticketDAO.totalTicketsEliminados();
    }
}