package com.fisiteatro.fisiteatrosystem.model.dao;

import com.fisiteatro.fisiteatrosystem.model.dto.Asiento;

import java.io.IOException;
import java.util.List;

public interface IAsientoDAO {
    void create(Asiento asiento) throws IOException;

    List<Asiento> readAll();

    void update(Asiento asiento) throws IOException;

    void delete(String fila, int numero) throws IOException;
}