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
        loadFromFile();
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
                return cliente; // Retorna el cliente si las credenciales son correctas
            }
        }
        return null; // Retorna null si no se encuentra el cliente
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
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), clientes.toList());
    }

    private void loadFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try {
                List<Cliente> listaClientes = mapper.readValue(file, new TypeReference<List<Cliente>>() {});
                clientes = new Cola<>();
                for (Cliente cliente : listaClientes) {
                    clientes.offer(cliente);
                }
            } catch (IOException e) {
                System.err.println("Error al cargar clientes desde el archivo JSON: " + e.getMessage());
            }
        }
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