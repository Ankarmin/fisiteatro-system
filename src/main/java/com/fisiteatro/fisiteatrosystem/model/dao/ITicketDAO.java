package com.fisiteatro.fisiteatrosystem.model.dao;

import com.fisiteatro.fisiteatrosystem.model.dto.Ticket;

import java.io.IOException;
import java.util.List;

public interface ITicketDAO {
    void create(Ticket ticket) throws IOException;

    List<Ticket> readAll();

    void update(Ticket ticket) throws IOException;

    void delete(String dni, String fila, int numero) throws IOException;
}