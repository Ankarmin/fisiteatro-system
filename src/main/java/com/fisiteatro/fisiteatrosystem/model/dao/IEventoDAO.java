package com.fisiteatro.fisiteatrosystem.model.dao;

import com.fisiteatro.fisiteatrosystem.model.dto.Evento;

import java.io.IOException;
import java.util.List;

public interface IEventoDAO {
    void create(Evento evento) throws IOException;

    List<Evento> readAll();

    void update(Evento evento) throws IOException;

    void delete(String nombre) throws IOException;
}