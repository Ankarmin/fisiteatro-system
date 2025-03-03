package com.fisiteatro.fisiteatrosystem.model.dao;

import com.fisiteatro.fisiteatrosystem.model.dto.AsientoDTO;

import java.io.IOException;
import java.util.List;

public interface IAsientoDAO {
    void create(int capacidad, int idEvento) throws IOException;

    List<AsientoDTO> readAll();

    //void update(Asiento asiento, int id) throws IOException;

    //void delete(String eventoNombre,String fila, int numero) throws IOException;
}