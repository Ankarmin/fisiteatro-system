package com.fisiteatro.fisiteatrosystem.model.dao;

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
        mapper.writeValue(new File(FILE_PATH), eventos.toList());
    }
}