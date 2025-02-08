package com.fisiteatro.fisiteatrosystem.model.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fisiteatro.fisiteatrosystem.datastructures.Cola;
import com.fisiteatro.fisiteatrosystem.model.dto.Cliente;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ClienteDAO implements IClienteDAO {
    private static final String FILE_PATH = "src/main/java/com/fisiteatro/fisiteatrosystem/data/cliente.json";
    private Cola<Cliente> clientes;

    public ClienteDAO(Cola<Cliente> clientes) {
        this.clientes = clientes;
    }

    public void create(Cliente cliente) throws IOException {
        clientes.offer(cliente);
        saveToFile();
    }

    public List<Cliente> readAll() {
        return clientes.toList();
    }

    public void update(Cliente cliente) throws IOException {
        Cola<Cliente> temp = new Cola<>();
        while (!clientes.isEmpty()) {
            Cliente current = clientes.poll();
            if (current.getDni().equals(cliente.getDni())) {
                temp.offer(cliente);
            } else {
                temp.offer(current);
            }
        }
        clientes = temp;
        saveToFile();
    }

    public void delete(String dni) throws IOException {
        Cola<Cliente> temp = new Cola<>();
        while (!clientes.isEmpty()) {
            Cliente current = clientes.poll();
            if (!current.getDni().equals(dni)) {
                temp.offer(current);
            }
        }
        clientes = temp;
        saveToFile();
    }

    private void saveToFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(FILE_PATH), clientes.toList());
    }
}