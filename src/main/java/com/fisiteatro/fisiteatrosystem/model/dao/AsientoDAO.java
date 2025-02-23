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
        // se cargan los asientos d json a arbol
        this.asientos = new ArbolBinarioBusqueda<>();
        String FILENAME = PATH + idEvento + ".json";

        File file = new File(FILENAME);

        // si no existe, q se cree
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
                AsientoDTO nuevoAsiento = new AsientoDTO(idEvento, fila, nuevoNumeroAsiento, true);
                asientos.insertar(nuevoAsiento);
            }
            saveToFile(idEvento);
        }
    }

    private void insertarEnOrdenBalanceado(ArbolBinarioBusqueda<AsientoDTO> arbol, int min, int max, int idEvento) {
        if (min > max) return;

        int mitad = (min + max) / 2;

        String fila = determinarFila(mitad, max);

        AsientoDTO asientoDTO = new AsientoDTO(idEvento, fila, mitad, true);
        arbol.insertar(asientoDTO);

        insertarEnOrdenBalanceado(arbol, min, mitad - 1, idEvento);
        insertarEnOrdenBalanceado(arbol, mitad + 1, max, idEvento);
    }

    private String determinarFila(int numeroAsiento, int capacidad) {
        // cantidad total de niveles
        int niveles = (int) (Math.log(capacidad) / Math.log(2)) + 1;

        // Determinar en qué nivel del árbol está el asiento
        int nivelActual = obtenerNivelAsiento(numeroAsiento, 1, capacidad, 1, niveles);

        // Si por alguna razón no se encuentra el nivel, devolver última fila
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

    public void updateDesocupado(AsientoDTO asientoDTO, int idEvento) throws IOException {
        System.out.println("Dirección de memoria de asientoEnLista: " + System.identityHashCode(asientoDTO));
        asientoDTO.setEstado(true);
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

    private void loadFromFile(int idEvento) {
        ObjectMapper mapper = new ObjectMapper();
        String FILENAME = PATH + idEvento + ".json";
        try {
            File file = new File(FILENAME);
            if (file.exists()) {
                AsientoDTO[] asientosArray = mapper.readValue(file, AsientoDTO[].class);
                for (AsientoDTO asientoDTO : asientosArray) {
                    asientos.insertar(asientoDTO);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int generarNumeroAsiento() {
        int nuevoNumero;
        do {
            nuevoNumero = random.nextInt(100) + 1;
        } while (existeAsiento(nuevoNumero));
        return nuevoNumero;
    }

    private boolean existeAsiento(int numero) {
        List<AsientoDTO> listaAsientoDTOS = readAll();
        return listaAsientoDTOS.stream().anyMatch(a -> a.getNumero() == numero);
    }

    private String obtenerNivelABB(int numero) {
        return obtenerNivelRec(asientos.getRaiz(), numero, 0);
    }

    private String obtenerNivelRec(Nodo<AsientoDTO> nodo, int numero, int nivel) {
        if (nodo == null) {
            return String.valueOf((char) ('A' + nivel));
        }

        int numeroNodo = asientos.getDato(nodo).getNumero();

        if (numero < numeroNodo) {
            return obtenerNivelRec(asientos.getIzquierdo(nodo), numero, nivel + 1);
        } else if (numero > numeroNodo) {
            return obtenerNivelRec(asientos.getDerecho(nodo), numero, nivel + 1);
        } else {
            return String.valueOf((char) ('A' + nivel));
        }
    }

    public int generarNuevoAsiento(String eventoNombre) {
        return generarNumeroAsiento();
    }

    public String obtenerNivelAsiento(int numero) {
        return obtenerNivelABB(numero);
    }

    public AsientoDTO obtenerPrimerAsientoDisponible() {
        return obtenerPrimerAsientoDisponibleRec(asientos.getRaiz());
    }

    private AsientoDTO obtenerPrimerAsientoDisponibleRec(Nodo<AsientoDTO> nodo) {
        if (nodo == null) {
            return null;
        }

        // hacia la izquierda para encontrar el menor número de asiento
        AsientoDTO izquierdoDisponible = obtenerPrimerAsientoDisponibleRec(asientos.getIzquierdo(nodo));
        if (izquierdoDisponible != null) {
            return izquierdoDisponible;
        }

        // Si el asiento actual está disponible, lo retornamos
        AsientoDTO asientoDTOActual = asientos.getDato(nodo);
        if (asientoDTOActual.getEstado()) {
            return asientoDTOActual;
        }

        return obtenerPrimerAsientoDisponibleRec(asientos.getDerecho(nodo));
    }
}