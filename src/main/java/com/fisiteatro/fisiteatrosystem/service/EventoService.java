package com.fisiteatro.fisiteatrosystem.service;

import com.fisiteatro.fisiteatrosystem.model.dao.EventoDAO;
import com.fisiteatro.fisiteatrosystem.model.dto.EventoDTO;

import java.io.IOException;
import java.util.List;

public class EventoService {
    private final EventoDAO eventoDAO;

    public EventoService() {
        this.eventoDAO = new EventoDAO();
    }

    public void create(EventoDTO evento) throws IOException {
        eventoDAO.create(evento);
    }

    public List<EventoDTO> readAll() {
        return eventoDAO.readAll();
    }

    public void update(EventoDTO evento) throws IOException {
        eventoDAO.update(evento);
    }

    public void delete(String nombre) throws IOException {
        eventoDAO.delete(nombre);
    }

    public void deleteById(int id) throws IOException {
        eventoDAO.deleteById(id);
    }

    public EventoDTO getById(int id) {
        return eventoDAO.getById(id);
    }

    public void verCatalogo() {
        eventoDAO.verCatalogo();
    }

    public void reducirCapacidad(EventoDTO evento) throws IOException {
        eventoDAO.reducirCapacidad(evento);
    }

    public void aumentarCapacidad(EventoDTO evento) throws IOException {
        eventoDAO.aumentarCapacidad(evento);
    }
}