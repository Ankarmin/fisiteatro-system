package com.fisiteatro.fisiteatrosystem.model.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fisiteatro.fisiteatrosystem.datastructures.Cola;
import com.fisiteatro.fisiteatrosystem.model.dto.AdministradorDTO;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AdministradorDAO implements IAdministradorDAO {
    private static final String FILE_PATH = "src/main/java/com/fisiteatro/fisiteatrosystem/data/administrador.json";
    private Cola<AdministradorDTO> administradores;

//    public AdministradorDAO(Cola<Administrador> administradores) {
//        this.administradores = administradores;
//    }

    public AdministradorDAO() {
        this.administradores = new Cola<>();
        try {
            administradores.cargarDesdeJson(FILE_PATH, AdministradorDTO[].class);
            System.out.println("Administradores cargados correctamente desde JSON.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());
        }
    }

    public void create(AdministradorDTO administradorDTO) throws IOException {
        administradores.offer(administradorDTO);
        saveToFile();
    }

    public List<AdministradorDTO> readAll() {
        return administradores.toList();
    }

    public void update(AdministradorDTO administradorDTO) throws IOException {
        Cola<AdministradorDTO> temp = new Cola<>();
        while (!administradores.isEmpty()) {
            AdministradorDTO current = administradores.poll();
            if (current.getDni().equals(administradorDTO.getDni())) {
                temp.offer(administradorDTO);
            } else {
                temp.offer(current);
            }
        }
        administradores = temp;
        saveToFile();
    }

    public void delete(String dni) throws IOException {
        Cola<AdministradorDTO> temp = new Cola<>();
        while (!administradores.isEmpty()) {
            AdministradorDTO current = administradores.poll();
            if (!current.getDni().equals(dni)) {
                temp.offer(current);
            }
        }
        administradores = temp;
        saveToFile();
    }

    public boolean verificarContrasenia(String contrasenia) {
        if (!administradores.isEmpty()) {
            AdministradorDTO administradorDTO = administradores.peek();
            return administradorDTO.getContrasena().equals(contrasenia);
        }
        return false;
    }

    public AdministradorDTO cambiarContrasenia(String contrasenia) {
        AdministradorDTO administradorDTO = administradores.peek();
        administradorDTO.setContrasena(contrasenia);
        return administradorDTO;
    }

    private void saveToFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), administradores.toList());
    }
}