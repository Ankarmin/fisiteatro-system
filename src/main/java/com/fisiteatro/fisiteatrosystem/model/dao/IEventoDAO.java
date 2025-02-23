package com.fisiteatro.fisiteatrosystem.model.dao;

import com.fisiteatro.fisiteatrosystem.model.dto.EventoDTO;

import java.io.IOException;
import java.util.List;

public interface IEventoDAO {
    void create(EventoDTO eventoDTO) throws IOException;

    List<EventoDTO> readAll();

    void update(EventoDTO eventoDTO) throws IOException;

    void delete(String nombre) throws IOException;
}