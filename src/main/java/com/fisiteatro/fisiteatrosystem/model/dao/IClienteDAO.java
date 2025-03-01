package com.fisiteatro.fisiteatrosystem.model.dao;

import com.fisiteatro.fisiteatrosystem.model.dto.ClienteDTO;

import java.io.IOException;
import java.util.List;

public interface IClienteDAO {
    void create(ClienteDTO clienteDTO) throws IOException;

    List<ClienteDTO> readAll();

    void update(ClienteDTO clienteDTO) throws IOException;


}