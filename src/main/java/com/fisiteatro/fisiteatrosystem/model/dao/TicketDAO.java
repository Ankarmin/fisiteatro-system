package com.fisiteatro.fisiteatrosystem.model.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fisiteatro.fisiteatrosystem.datastructures.Cola;
import com.fisiteatro.fisiteatrosystem.datastructures.ListaEnlazada;
import com.fisiteatro.fisiteatrosystem.datastructures.Pila;
import com.fisiteatro.fisiteatrosystem.model.dto.AsientoDTO;
import com.fisiteatro.fisiteatrosystem.model.dto.EventoDTO;
import com.fisiteatro.fisiteatrosystem.model.dto.TicketDTO;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class TicketDAO implements ITicketDAO {
    private static final String FILE_PATH = "src/main/java/com/fisiteatro/fisiteatrosystem/data/ticketsComprados.json";
    private static final String PATH_ELIMINADOS = "src/main/java/com/fisiteatro/fisiteatrosystem/data/ticketsEliminadosPorEvento/eliminados_";
    private final Pila<TicketDTO> tickets;

    public TicketDAO() {

        this.tickets = new Pila<>();
        try {
            tickets.cargarDesdeJson(FILE_PATH, TicketDTO[].class);
            System.out.println("Tickets cargados con exito");
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
        }
    }

    public void create(TicketDTO ticketDTO) throws IOException {
        tickets.push(ticketDTO);
        saveToFile();
    }

    private boolean validarId(int id) {
        for (TicketDTO ticketDTO : tickets.toList()) {
            if (id == ticketDTO.getId()) {
                return true;
            }
        }
        return false;
    }

    public int createId() {
        Random rand = new Random();
        int id;
        do {
            id = rand.nextInt(9000) + 1000;
        } while (validarId(id));
        return id;
    }

    public List<TicketDTO> readAll() {
        return tickets.toList();
    }

    // solo se podria actulizar el cliente del ticket (despues d cancelarlo)
    public void update(TicketDTO ticketDTO) throws IOException {
        Pila<TicketDTO> temp = new Pila<>();
        while (!tickets.isEmpty()) {
            TicketDTO current = tickets.pop();
            if (current.getCliente().getDni().equals(ticketDTO.getCliente().getDni()) &&
                    current.getAsiento().getFila().equals(ticketDTO.getAsiento().getFila()) &&
                    current.getAsiento().getNumero() == ticketDTO.getAsiento().getNumero()) {
                temp.push(ticketDTO);
            } else {
                temp.push(current);
            }
        }
        while (!temp.isEmpty()) {
            tickets.push(temp.pop());
        }
        //tickets = temp;
        saveToFile();
    }

    public ListaEnlazada<TicketDTO> getTicketsPorCliente(String dni) {
        ListaEnlazada<TicketDTO> ticketsCliente = new ListaEnlazada<>();
        for (TicketDTO ticketDTO : tickets.toList()) {
            if (ticketDTO.getCliente().getDni().equals(dni)) {
                ticketsCliente.add(ticketDTO);
            }
        }
        return ticketsCliente;

    }

    public TicketDTO getTicketById(int id, ListaEnlazada<TicketDTO> ticketsCliente) {
        for (TicketDTO ticketDTO : ticketsCliente.toList()) {
            if (ticketDTO.getId() == id) {
                return ticketDTO;
            }
        }
        return null;
    }

    public Cola<TicketDTO> getSolicitudesTickets() {
        String FILENAME = "src/main/java/com/fisiteatro/fisiteatrosystem/data/solicitudesTickets.json";
        Cola<TicketDTO> solicitudesTickets = new Cola<>();
        try {
            solicitudesTickets.cargarDesdeJson(FILENAME, TicketDTO[].class);
            System.out.println("Solicitudes tickets cargados con exito");
        } catch (IOException e) {
            System.out.println("Error al cargar solicitudes d tickets: " + e.getMessage());
        }
        return solicitudesTickets;
    }

    public void deleteSolicitud(Cola<TicketDTO> solicitudes) throws IOException {
        solicitudes.desencolar();
        saveSolicitudesTicketsJSON(solicitudes);
    }

    public void saveSolicitudesTicketsJSON(Cola<TicketDTO> solicitudesTickets) throws IOException {
        String FILENAME = "src/main/java/com/fisiteatro/fisiteatrosystem/data/solicitudesTickets.json";

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILENAME), solicitudesTickets.toList());
    }

    public Pila<TicketDTO> getTicketsEliminados(int idEvento) {
        String FILENAME = PATH_ELIMINADOS + idEvento + ".json";
        Pila<TicketDTO> ticketsEliminados = new Pila<>();
        try {
            ticketsEliminados.cargarDesdeJson(FILENAME, TicketDTO[].class);
            System.out.println("pila tickets eliminados cargados");
        } catch (IOException e) {
            System.out.println("Error al cargar la pila d tickets eliminados: " + e.getMessage());
        }
        return ticketsEliminados;
    }

    public void addTicketEliminado(TicketDTO ticketDTO, Pila<TicketDTO> pila) throws IOException {
        // mismos cambios pero en el ticket para agregarlo a la pila actualizao
        ticketDTO.getEvento().setCapacidad(ticketDTO.getEvento().getCapacidad() + 1);
        ticketDTO.getAsiento().setEstado(true);

        pila.push(ticketDTO);
        saveTicketsEliminadosJSON(pila, ticketDTO.getEvento().getId());
    }

    public AsientoDTO getAsiento(int numAsiento, EventoDTO eventoDTO) {
        AsientoDAO asientoDAO = new AsientoDAO(eventoDTO.getId());
        for (AsientoDTO asientoDTO : asientoDAO.readAll()) {
            if (asientoDTO.getNumero() == numAsiento) {
                return asientoDTO;
            }
        }
        return null;
    }

    public void saveTicketsEliminadosJSON(Pila<TicketDTO> ticketsEliminados, int idEvento) throws IOException {
        String FILENAME = PATH_ELIMINADOS + idEvento + ".json";

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILENAME), ticketsEliminados.toList());
    }

    public void delete(String dni, String fila, int numero) throws IOException {
        Pila<TicketDTO> temp = new Pila<>();
        while (!tickets.isEmpty()) {
            TicketDTO current = tickets.pop();
            if (!(current.getCliente().getDni().equals(dni) &&
                    current.getAsiento().getFila().equals(fila) &&
                    current.getAsiento().getNumero() == numero)) {
                temp.push(current);
            }
        }
        while (!temp.isEmpty()) {
            tickets.push(temp.pop());
        }
        //tickets = temp;
        saveToFile();
    }

    private void loadFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try {
                List<TicketDTO> ticketDTOList = mapper.readValue(file,
                        mapper.getTypeFactory().constructCollectionType(List.class, TicketDTO.class));
                // Insertar en orden inverso para mantener la pila correctamente
                for (int i = ticketDTOList.size() - 1; i >= 0; i--) {
                    tickets.push(ticketDTOList.get(i));
                }
            } catch (IOException e) {
                System.out.println("Error al cargar los tickets desde el archivo: " + e.getMessage());
            }
        }
    }

    private void saveToFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), tickets.toList());
    }

}