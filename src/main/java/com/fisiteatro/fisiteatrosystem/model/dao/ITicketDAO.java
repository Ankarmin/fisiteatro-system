package com.fisiteatro.fisiteatrosystem.model.dao;

import com.fisiteatro.fisiteatrosystem.model.dto.TicketDTO;

import java.io.IOException;
import java.util.List;

public interface ITicketDAO {
    void create(TicketDTO ticketDTO) throws IOException;

    List<TicketDTO> readAll();

    void update(TicketDTO ticketDTO) throws IOException;

    void delete(String dni, String fila, int numero) throws IOException;
}