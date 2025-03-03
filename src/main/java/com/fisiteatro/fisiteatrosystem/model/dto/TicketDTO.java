package com.fisiteatro.fisiteatrosystem.model.dto;

public class TicketDTO {
    private int id;
    private ClienteDTO clienteDTO;
    private AsientoDTO asientoDTO;
    private EventoDTO eventoDTO;

    public TicketDTO(int id, ClienteDTO clienteDTO, AsientoDTO asientoDTO, EventoDTO eventoDTO) {
        this.id = id;
        this.clienteDTO = clienteDTO;
        this.asientoDTO = asientoDTO;
        this.eventoDTO = eventoDTO;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public ClienteDTO getCliente() {
        return clienteDTO;
    }

    public void setCliente(ClienteDTO clienteDTO) {
        this.clienteDTO = clienteDTO;
    }

    public AsientoDTO getAsiento() {
        return asientoDTO;
    }

    public void setAsiento(AsientoDTO asientoDTO) {
        this.asientoDTO = asientoDTO;
    }

    public EventoDTO getEvento() {
        return eventoDTO;
    }

    public void setEvento(EventoDTO eventoDTO) {
        this.eventoDTO = eventoDTO;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                "cliente=" + clienteDTO +
                ", asiento=" + asientoDTO +
                ", evento=" + eventoDTO +
                '}';
    }
}
