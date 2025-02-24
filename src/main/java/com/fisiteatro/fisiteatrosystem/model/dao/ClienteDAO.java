package com.fisiteatro.fisiteatrosystem.model.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fisiteatro.fisiteatrosystem.datastructures.Cola;
import com.fisiteatro.fisiteatrosystem.model.dto.ClienteDTO;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ClienteDAO implements IClienteDAO {
    private static final String FILE_PATH = "src/main/java/com/fisiteatro/fisiteatrosystem/data/cliente.json";
    private Cola<ClienteDTO> clientes;

    public ClienteDAO(Cola<ClienteDTO> clientes) {

        this.clientes = clientes;

        try {
            clientes.cargarDesdeJson(FILE_PATH, ClienteDTO[].class);
            System.out.println("Clientes cargados correctamente desde JSON.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());
        }
    }

    public void create(ClienteDTO clienteDTO) throws IOException {
        clientes.offer(clienteDTO);
        saveToFile();
    }

    public List<ClienteDTO> readAll() {
        return clientes.toList();
    }

    public boolean iniciarSesion(String dni, String contrasena) {
        for (ClienteDTO clienteDTO : clientes.toList()) {
            if (clienteDTO.getDni().equals(dni) && clienteDTO.getContrasena().equals(contrasena)) {
                return true;
            }
        }
        return false;
    }

    public void update(ClienteDTO clienteDTO) throws IOException {
        Cola<ClienteDTO> temp = new Cola<>();
        while (!clientes.isEmpty()) {
            ClienteDTO current = clientes.poll();
            if (current.getDni().equals(clienteDTO.getDni())) {
                temp.offer(clienteDTO);
            } else {
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

    public ClienteDTO obtenerPorDni(String dni) {
        for (ClienteDTO clienteDTO : clientes.toList()) {
            if (clienteDTO.getDni().equals(dni)) {
                return clienteDTO;
            }
        }
        return null;
    }
}