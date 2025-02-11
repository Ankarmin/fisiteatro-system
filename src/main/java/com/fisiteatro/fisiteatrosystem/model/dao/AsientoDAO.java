package com.fisiteatro.fisiteatrosystem.model.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fisiteatro.fisiteatrosystem.datastructures.Nodo;
import com.fisiteatro.fisiteatrosystem.datastructures.ArbolBinarioBusqueda;
import com.fisiteatro.fisiteatrosystem.model.dto.Asiento;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AsientoDAO implements IAsientoDAO {
    private static final String FILE_PATH = "src/main/java/com/fisiteatro/fisiteatrosystem/data/asiento.json";
    private final ArbolBinarioBusqueda<Asiento> asientos;
    private final Random random = new Random();

    public AsientoDAO(ArbolBinarioBusqueda<Asiento> asientos) {

        this.asientos = asientos;
        loadFromFile();
    }

    public void create(Asiento asiento) throws IOException {
        int nuevoNumero = generarNumeroAsiento();
        String nivel = obtenerNivelABB(nuevoNumero);
        asiento.setNumero(nuevoNumero);
        asiento.setFila(nivel);
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

    public void delete(String eventoNombre,String fila, int numero) throws IOException {
        Asiento asiento = new Asiento(eventoNombre,fila, numero, false);
        asientos.eliminar(asiento);
        saveToFile();
    }

    private void saveToFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Asiento> list = readAll();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), list);
    }
    private void loadFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File(FILE_PATH);
            if (file.exists()) {
                Asiento[] asientosArray = mapper.readValue(file, Asiento[].class);
                for (Asiento asiento : asientosArray) {
                    asientos.insertar(asiento);
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
        List<Asiento> listaAsientos = readAll();
        return listaAsientos.stream().anyMatch(a -> a.getNumero() == numero);
    }

    private String obtenerNivelABB(int numero) {
        return obtenerNivelRec(asientos.getRaiz(), numero, 0);
    }

    private String obtenerNivelRec(Nodo<Asiento> nodo, int numero, int nivel) {
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
}