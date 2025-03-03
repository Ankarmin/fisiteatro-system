package com.fisiteatro.fisiteatrosystem.service;

import com.fisiteatro.fisiteatrosystem.model.dao.AsientoDAO;
import com.fisiteatro.fisiteatrosystem.model.dto.AsientoDTO;

import java.io.IOException;
import java.util.List;

public class AsientoService {
    private final AsientoDAO asientoDAO;

    public AsientoService(int idEvento) {
        this.asientoDAO = new AsientoDAO(idEvento);
    }

    public void create(int capacidad, int idEvento) throws IOException {
        asientoDAO.create(capacidad, idEvento);
    }

    public List<AsientoDTO> readAll() {
        return asientoDAO.readAll();
    }

    public void updateOcupado(AsientoDTO asiento, int idEvento) throws IOException {
        asientoDAO.updateOcupado(asiento, idEvento);
    }

    public void deleteFile(int idEvento) throws IOException {
        asientoDAO.deleteFile(idEvento);
    }

    public AsientoDTO obtenerPrimerAsientoDisponible() {
        return asientoDAO.obtenerPrimerAsientoDisponible();
    }
}