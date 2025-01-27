package com.fisiteatro.fisiteatrosystem.model.dto;

public class Ticket {
    private Cliente cliente;
    private Asiento asiento;

    public Ticket() {
    }

    public Ticket(Cliente cliente, Asiento asiento) {
        this.cliente = cliente;
        this.asiento = asiento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }
}
