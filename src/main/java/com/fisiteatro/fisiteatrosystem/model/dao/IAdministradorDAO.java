package com.fisiteatro.fisiteatrosystem.model.dao;

import com.fisiteatro.fisiteatrosystem.model.dto.AdministradorDTO;

import java.io.IOException;
import java.util.List;

public interface IAdministradorDAO {
    void create(AdministradorDTO administradorDTO) throws IOException;

    List<AdministradorDTO> readAll();

    void update(AdministradorDTO administradorDTO) throws IOException;

    void delete(String dni) throws IOException;
}