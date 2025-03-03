package com.fisiteatro.fisiteatrosystem.model.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fisiteatro.fisiteatrosystem.datastructures.ArbolBinarioBusqueda;
import com.fisiteatro.fisiteatrosystem.datastructures.Nodo;
import com.fisiteatro.fisiteatrosystem.model.dto.AsientoDTO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AsientoDAO implements IAsientoDAO {
    private static final String PATH = "src/main/java/com/fisiteatro/fisiteatrosystem/data/asientosPorEvento/asientos_";
    private final ArbolBinarioBusqueda<AsientoDTO> asientos;
    private final Random random = new Random();

    public AsientoDAO(int idEvento) {
        this.asientos = new ArbolBinarioBusqueda<>();
        String FILENAME = PATH + idEvento + ".json";

        File file = new File(FILENAME);

        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        try {
            asientos.cargarDesdeJson(FILENAME, AsientoDTO[].class);
            System.out.println("Asientos cargados con exito");
        } catch (IOException e) {
            System.out.println("Error al cargar la JSON de Asientos: " + e.getMessage());
        }

    }

    public void create(int nuevaCapacidad, int idEvento) throws IOException {
        List<AsientoDTO> asientosExistentes = readAll();
        int capacidadActual = asientosExistentes.size();

        int asientosPorAgregar = nuevaCapacidad - capacidadActual;

        if (asientosPorAgregar > 0) {
            for (int i = 1; i <= asientosPorAgregar; i++) {
                int nuevoNumeroAsiento = capacidadActual + i;
                String fila = determinarFila(nuevoNumeroAsiento, nuevaCapacidad);
                AsientoDTO nuevoAsiento = new AsientoDTO(fila, nuevoNumeroAsiento, true);
                asientos.insertar(nuevoAsiento);
            }
            saveToFile(idEvento);
        }
    }

    private String determinarFila(int numeroAsiento, int capacidad) {
        int niveles = (int) (Math.log(capacidad) / Math.log(2)) + 1;

        int nivelActual = obtenerNivelAsiento(numeroAsiento, 1, capacidad, 1, niveles);

        if (nivelActual == -1) return String.valueOf((char) ('A' + niveles - 1));

        return String.valueOf((char) ('A' + (nivelActual - 1)));
    }

    private int obtenerNivelAsiento(int numeroAsiento, int min, int max, int nivelActual, int nivelesMaximos) {
        if (min > max || nivelActual > nivelesMaximos) return -1;  // No encontrado o fuera del límite

        int mitad = (min + max) / 2;

        if (numeroAsiento == mitad) return nivelActual;

        if (numeroAsiento < mitad) {
            return obtenerNivelAsiento(numeroAsiento, min, mitad - 1, nivelActual + 1, nivelesMaximos);
        } else {
            return obtenerNivelAsiento(numeroAsiento, mitad + 1, max, nivelActual + 1, nivelesMaximos);
        }
    }

    public List<AsientoDTO> readAll() {
        List<AsientoDTO> list = new ArrayList<>();
        asientos.imprimirEnOrden(asiento -> list.add(asiento));
        return list;
    }

    public void updateOcupado(AsientoDTO asientoDTO, int idEvento) throws IOException {
        asientoDTO.setEstado(false);
        saveToFile(idEvento);
    }

    public void deleteFile(int idEvento) throws IOException {
        String FILENAME = PATH + idEvento + ".json";
        File file = new File(FILENAME);

        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Archivo " + FILENAME + " eliminado correctamente.");
            } else {
                System.out.println("No se pudo eliminar el archivo " + FILENAME);
            }
        } else {
            System.out.println("El archivo " + FILENAME + " no existe.");
        }
    }

    private void saveToFile(int idEvento) throws IOException {
        String FILE_PATH = PATH + idEvento + ".json";

        ObjectMapper mapper = new ObjectMapper();
        List<AsientoDTO> list = readAll();

        if (list.isEmpty()) {
            System.out.println("No hay asientos para guardar.");
            return;
        }

        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), list);
        System.out.println("Asientos guardados correctamente en " + new File(FILE_PATH).getAbsolutePath());
    }

    public AsientoDTO obtenerPrimerAsientoDisponible() {
        return obtenerPrimerAsientoDisponibleRec(asientos.getRaiz());
    }

    private AsientoDTO obtenerPrimerAsientoDisponibleRec(Nodo<AsientoDTO> nodo) {
        if (nodo == null) {
            return null;
        }

        AsientoDTO izquierdoDisponible = obtenerPrimerAsientoDisponibleRec(asientos.getIzquierdo(nodo));
        if (izquierdoDisponible != null) {
            return izquierdoDisponible;
        }

        AsientoDTO asientoDTOActual = asientos.getDato(nodo);
        if (asientoDTOActual.getEstado()) {
            return asientoDTOActual;
        }

        return obtenerPrimerAsientoDisponibleRec(asientos.getDerecho(nodo));
    }
}