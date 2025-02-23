package com.fisiteatro.fisiteatrosystem.service;

import com.fisiteatro.fisiteatrosystem.datastructures.Cola;
import com.fisiteatro.fisiteatrosystem.model.dao.ClienteDAO;
import com.fisiteatro.fisiteatrosystem.model.dto.ClienteDTO;

import java.io.IOException;
import java.util.List;

public class ClienteService {
    private final ClienteDAO clienteDAO;

    public ClienteService() {
        this.clienteDAO = new ClienteDAO(new Cola<>());
    }

    public void create(ClienteDTO cliente) throws IOException {
        clienteDAO.create(cliente);
    }

    public List<ClienteDTO> readAll() {
        return clienteDAO.readAll();
    }

    public boolean iniciarSesion(String dni, String contrasena) {
        return clienteDAO.iniciarSesion(dni, contrasena);
    }

    public void update(ClienteDTO cliente) throws IOException {
        clienteDAO.update(cliente);
    }

    public void delete(String dni) throws IOException {
        clienteDAO.delete(dni);
    }

    public ClienteDTO obtenerPorDni(String dni) {
        return clienteDAO.obtenerPorDni(dni);
    }
}