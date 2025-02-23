package com.fisiteatro.fisiteatrosystem.service;

import com.fisiteatro.fisiteatrosystem.model.dao.AdministradorDAO;
import com.fisiteatro.fisiteatrosystem.model.dto.AdministradorDTO;

import java.io.IOException;
import java.util.List;

public class AdministradorService {
    private final AdministradorDAO administradorDAO;

    public AdministradorService() {
        this.administradorDAO = new AdministradorDAO();
    }

    public void create(AdministradorDTO administradorDTO) throws IOException {
        administradorDAO.create(administradorDTO);
    }

    public List<AdministradorDTO> readAll() {
        return administradorDAO.readAll();
    }

    public void update(AdministradorDTO administradorDTO) throws IOException {
        administradorDAO.update(administradorDTO);
    }

    public void delete(String dni) throws IOException {
        administradorDAO.delete(dni);
    }

    public boolean verificarContrasenia(String contrasenia) {
        return administradorDAO.verificarContrasenia(contrasenia);
    }

    public AdministradorDTO cambiarContrasenia(String contrasenia) {
        return administradorDAO.cambiarContrasenia(contrasenia);
    }
}