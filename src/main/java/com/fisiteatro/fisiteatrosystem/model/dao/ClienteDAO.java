package com.fisiteatro.fisiteatrosystem.model.dao;

import com.fasterxml.jackson.core.type.TypeReference;
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

        try {
            clientes.cargarDesdeJson(FILE_PATH, Cliente[].class);
            System.out.println("Clientes cargados correctamente desde JSON.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());
        }
    }

    public void create(Cliente cliente) throws IOException {
        clientes.offer(cliente);
        saveToFile();
    }

    public List<Cliente> readAll() {
        return clientes.toList();
    }

    public Cliente iniciarSesion(String dni, String contrasena) {
        for (Cliente cliente : clientes.toList()) {
            if (cliente.getDni().equals(dni) && cliente.getContrasena().equals(contrasena)) {
                return cliente;
            }
        }
        return null;
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
    //se puede mejorar
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
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), clientes.toList());
    }


    public Cliente obtenerPorDni(String dni) {
        for (Cliente cliente : clientes.toList()) {
            if (cliente.getDni().equals(dni)) {
                return cliente;
            }
        }
        return null;
    }

}