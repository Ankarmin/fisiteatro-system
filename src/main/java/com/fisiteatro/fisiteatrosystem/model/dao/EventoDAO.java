package com.fisiteatro.fisiteatrosystem.model.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fisiteatro.fisiteatrosystem.datastructures.ListaEnlazada;
import com.fisiteatro.fisiteatrosystem.model.dto.Evento;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class EventoDAO implements IEventoDAO {
    private static final String FILE_PATH = "src/main/java/com/fisiteatro/fisiteatrosystem/data/evento.json";
    private ListaEnlazada<Evento> eventos;

    public EventoDAO(ListaEnlazada<Evento> eventos) {

        this.eventos = eventos;
        cargarEventosDesdeArchivo();
    }
    private void cargarEventosDesdeArchivo() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("Archivo de eventos no encontrado. Se inicializa vacío.");
            return;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Evento> listaEventos = mapper.readValue(file, new TypeReference<List<Evento>>() {});
            for (Evento evento : listaEventos) {
                eventos.add(evento);
            }
            System.out.println("Eventos cargados correctamente desde JSON.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());
        }
    }

    public void create(Evento evento) throws IOException {
        eventos.add(evento);
        saveToFile();
    }

    public List<Evento> readAll() {
        return eventos.toList();
    }

    public void update(Evento evento) throws IOException {
        ListaEnlazada<Evento> temp = new ListaEnlazada<>();
        for (Evento current : eventos.toList()) {
            if (current.getNombre().equals(evento.getNombre())) {
                temp.add(evento);
            } else {
                temp.add(current);
            }
        }
        eventos = temp;
        saveToFile();
    }

    public void delete(String nombre) throws IOException {
        ListaEnlazada<Evento> temp = new ListaEnlazada<>();
        for (Evento current : eventos.toList()) {
            if (!current.getNombre().equals(nombre)) {
                temp.add(current);
            }
        }
        eventos = temp;
        saveToFile();
    }

    private void saveToFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), eventos.toList());
    }
}