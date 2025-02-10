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

        try {
            eventos.cargarDesdeJson(FILE_PATH, Evento[].class); // Usa el método de ListaEnlazada
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

    public void update(int index, Evento evento) throws IOException{
        if(index >= 0 && index < eventos.toList().size()){
            eventos.set(index, evento);
            saveToFile();
        } else {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + (index + 1));
        }
    }

    public boolean validarIndex(int index) {
        return eventos.validarIndex(index);
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

    public void deleteByIndex(int index) throws IOException {
//        if (index < 0 || index >= eventos.toList().size()) {
//            throw new IndexOutOfBoundsException("Índice fuera de rango.");
//        }
//
//        ListaEnlazada<Evento> temp = new ListaEnlazada<>();
//        int currentIndex = 0;
//
//        for (Evento current : eventos.toList()) {
//            if (currentIndex != index) {
//                temp.add(current);
//            }
//            currentIndex++;
//        }
//
//        eventos = temp;
//        saveToFile();

        eventos.remove(index);
        saveToFile();
    }

    public Evento get (int index){
        return eventos.get(index);
    }

    private void saveToFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), eventos.toList());
    }

    public void verCatalogo() {
        List<Evento> eventosLista = this.eventos.toList();
        if (eventosLista.isEmpty()) {
            System.out.println("No hay eventos disponibles.");
            return;
        }

        System.out.println("\n\t\t--- CATALOGO DE EVENTOS ---\n");
        System.out.printf("%-5s %-20s %-12s %-8s %-10s %-10s%n", "ID", "Nombre", "Fecha", "Hora", "Precio", "Capacidad");
        System.out.println("---------------------------------------------------------------------");

        int index = 1;
        for (Evento evento : eventosLista) {
            System.out.printf("%-5d %-20s %-12s %-8s %-10.2f %-10d%n",
                    index++, evento.getNombre(), evento.getFecha(), evento.getHora(), evento.getPrecio(), evento.getCapacidad());
        }
    }
}