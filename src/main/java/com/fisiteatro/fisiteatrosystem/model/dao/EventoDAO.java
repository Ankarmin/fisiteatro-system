package com.fisiteatro.fisiteatrosystem.model.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fisiteatro.fisiteatrosystem.datastructures.ListaEnlazada;
import com.fisiteatro.fisiteatrosystem.model.dto.EventoDTO;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class EventoDAO implements IEventoDAO {
    private static final String FILE_PATH = "src/main/java/com/fisiteatro/fisiteatrosystem/data/evento.json";
    private ListaEnlazada<EventoDTO> eventos;

    public EventoDAO() {

        this.eventos = new ListaEnlazada<>();

        try {
            eventos.cargarDesdeJson(FILE_PATH, EventoDTO[].class); // Usa el método de ListaEnlazada
            System.out.println("Eventos cargados correctamente desde JSON.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());
        }

    }

    public void create(EventoDTO eventoDTO) throws IOException {
        eventos.add(eventoDTO);
        saveToFile();
    }

    public List<EventoDTO> readAll() {
        return eventos.toList();
    }

    public void update(EventoDTO eventoDTO) throws IOException {
        ListaEnlazada<EventoDTO> temp = new ListaEnlazada<>();
        for (EventoDTO current : eventos.toList()) {
            if (current.getNombre().equals(eventoDTO.getNombre())) {
                temp.add(eventoDTO);
            } else {
                temp.add(current);
            }
        }
        eventos = temp;
        saveToFile();
    }

//    public void update(int index, Evento evento) throws IOException{
//        if(index >= 0 && index < eventos.toList().size()){
//            eventos.set(index, evento);
//            saveToFile();
//        } else {
//            throw new IndexOutOfBoundsException("Índice fuera de rango: " + (index + 1));
//        }
//    }

    public boolean validarIndex(int index) {
        return eventos.validarIndex(index);
    }

    public int createId() {
        Random random = new Random();
        int id;
        do {
            id = random.nextInt(900) + 100;
        } while (validarId(id));
        return id;
    }

    public boolean validarId(int id) {
        for (EventoDTO eventoDTO : eventos.toList()) {
            if (id == eventoDTO.getId()) {
                return true;
            }
        }
        return false;
    }

    public void delete(String nombre) throws IOException {
        ListaEnlazada<EventoDTO> temp = new ListaEnlazada<>();
        for (EventoDTO current : eventos.toList()) {
            if (!current.getNombre().equals(nombre)) {
                temp.add(current);
            }
        }
        eventos = temp;
        saveToFile();
    }

    public void deleteById(int id) throws IOException {
        int posicion = 0, n = 0;
        for (EventoDTO current : eventos.toList()) {
            if (current.getId() == id) {
                posicion = n;
                break;
            }
            n++;
        }
        eventos.remove(posicion);
        saveToFile();
    }

    public EventoDTO getById(int id) {
        for (EventoDTO eventoDTO : eventos.toList()) {
            if (eventoDTO.getId() == id) {
                return eventoDTO;
            }
        }
        return null;
    }

    private void saveToFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), eventos.toList());
    }

    public void verCatalogo() {
        List<EventoDTO> eventosLista = this.eventos.toList();
        if (eventosLista.isEmpty()) {
            System.out.println("No hay eventos disponibles.");
            return;
        }

        System.out.println("\n\t\t--- CATALOGO DE EVENTOS ---\n");
        System.out.printf("%-5s %-20s %-12s %-8s %-10s %-10s%n", "ID", "Nombre", "Fecha", "Hora", "Precio", "Capacidad");
        System.out.println("---------------------------------------------------------------------");

        for (EventoDTO eventoDTO : eventosLista) {
            System.out.printf("%-5d %-20s %-12s %-8s %-10.2f %-10d%n",
                    eventoDTO.getId(), eventoDTO.getNombre(), eventoDTO.getFecha(), eventoDTO.getHora(), eventoDTO.getPrecio(), eventoDTO.getCapacidad());
        }
    }

    public void reducirCapacidad(EventoDTO eventoDTO) throws IOException {
        List<EventoDTO> eventoDTOS = readAll();
        for (EventoDTO e : eventoDTOS) {
            if (e.equals(eventoDTO)) {  // Comparar por atributos
                e.setCapacidad(e.getCapacidad() - 1);
                saveToFile();
                return;
            }
        }
    }

    public void aumentarCapacidad(EventoDTO eventoDTO) throws IOException {
        List<EventoDTO> eventoDTOS = readAll();
        for (EventoDTO e : eventoDTOS) {
            if (e.equals(eventoDTO)) {
                e.setCapacidad(e.getCapacidad() + 1);
                saveToFile();
                return;
            }
        }
    }

}