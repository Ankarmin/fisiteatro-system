package com.fisiteatro.fisiteatrosystem.model.dao;

import com.fisiteatro.fisiteatrosystem.model.dto.Administrador;

import java.io.IOException;
import java.util.List;

public interface IAdministradorDAO {
    void create(Administrador administrador) throws IOException;

    List<Administrador> readAll();

    void update(Administrador administrador) throws IOException;

    void delete(String dni) throws IOException;
}