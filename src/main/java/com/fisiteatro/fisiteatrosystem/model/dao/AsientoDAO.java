package com.fisiteatro.fisiteatrosystem.model.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fisiteatro.fisiteatrosystem.datastructures.ArbolBinarioBusqueda;
import com.fisiteatro.fisiteatrosystem.model.dto.Asiento;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AsientoDAO implements IAsientoDAO {
    private static final String FILE_PATH = "src/main/java/com/fisiteatro/fisiteatrosystem/data/asiento.json";
    private final ArbolBinarioBusqueda<Asiento> asientos;

    public AsientoDAO(ArbolBinarioBusqueda<Asiento> asientos) {
        this.asientos = asientos;
    }

    public void create(Asiento asiento) throws IOException {
        asientos.insertar(asiento);
        saveToFile();
    }

    public List<Asiento> readAll() {
        List<Asiento> list = new ArrayList<>();
        asientos.imprimirEnOrden(asiento -> list.add(asiento));
        return list;
    }

    public void update(Asiento asiento) throws IOException {
        asientos.eliminar(asiento);
        asientos.insertar(asiento);
        saveToFile();
    }

    public void delete(String fila, int numero) throws IOException {
        Asiento asiento = new Asiento(fila, numero, false);
        asientos.eliminar(asiento);
        saveToFile();
    }

    private void saveToFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Asiento> list = readAll();
        mapper.writeValue(new File(FILE_PATH), list);
    }
}