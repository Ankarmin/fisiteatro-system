package com.fisiteatro.fisiteatrosystem.model.dao;

import com.fisiteatro.fisiteatrosystem.model.dto.Cliente;

import java.io.IOException;
import java.util.List;

public interface IClienteDAO {
    void create(Cliente cliente) throws IOException;

    List<Cliente> readAll();

    void update(Cliente cliente) throws IOException;

    void delete(String dni) throws IOException;
}