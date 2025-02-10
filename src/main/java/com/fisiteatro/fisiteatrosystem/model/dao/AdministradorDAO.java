package com.fisiteatro.fisiteatrosystem.model.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fisiteatro.fisiteatrosystem.datastructures.Cola;
import com.fisiteatro.fisiteatrosystem.model.dto.Administrador;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AdministradorDAO implements IAdministradorDAO {
    private static final String FILE_PATH = "src/main/java/com/fisiteatro/fisiteatrosystem/data/administrador.json";
    private Cola<Administrador> administradores;

//    public AdministradorDAO(Cola<Administrador> administradores) {
//        this.administradores = administradores;
//    }

    public AdministradorDAO() {
        this.administradores = new Cola<>();
        try {
            administradores.cargarDesdeJson(FILE_PATH, Administrador[].class);
            System.out.println("Administradores cargados correctamente desde JSON.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());
        }
    }

    public void create(Administrador administrador) throws IOException {
        administradores.offer(administrador);
        saveToFile();
    }

    public List<Administrador> readAll() {
        return administradores.toList();
    }

    public void update(Administrador administrador) throws IOException {
        Cola<Administrador> temp = new Cola<>();
        while (!administradores.isEmpty()) {
            Administrador current = administradores.poll();
            if (current.getDni().equals(administrador.getDni())) {
                temp.offer(administrador);
            } else {
                temp.offer(current);
            }
        }
        administradores = temp;
        saveToFile();
    }

    public void delete(String dni) throws IOException {
        Cola<Administrador> temp = new Cola<>();
        while (!administradores.isEmpty()) {
            Administrador current = administradores.poll();
            if (!current.getDni().equals(dni)) {
                temp.offer(current);
            }
        }
        administradores = temp;
        saveToFile();
    }

    public boolean verificarContrasenia (String contrasenia){
        if(!administradores.isEmpty()){
            Administrador administrador = administradores.peek();
            return administrador.getContrasena().equals(contrasenia);
        }
        return false;
    }

    public Administrador cambiarContrasenia(String contrasenia){
        Administrador administrador = administradores.peek();
        administrador.setContrasena(contrasenia);
        return administrador;
    }

    private void saveToFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), administradores.toList());
    }
}