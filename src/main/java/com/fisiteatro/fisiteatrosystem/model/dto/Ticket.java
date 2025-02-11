package com.fisiteatro.fisiteatrosystem.model.dto;

import java.util.Objects;
import java.util.Random;

public class Ticket {
    private int id;
    private Cliente cliente;
    private Asiento asiento;
    private Evento evento;

    public Ticket() {
        this.id = generarId();
    }

    public Ticket(Cliente cliente, Asiento asiento, Evento evento) {
        this.id= generarId();
        this.cliente = cliente;
        this.asiento = asiento;
        this.evento = evento;
    }
    private int generarId() {
        Random rand = new Random();
        return rand.nextInt(9000) + 1000;
    }
    public int getId() {
        return id;
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

    public Evento getEvento() {
        return evento;
    }
    public void setEvento(Evento evento) {
        this.evento = evento;
    }
    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                "cliente=" + cliente +
                ", asiento=" + asiento +
                ", evento=" + evento +
                '}';
    }
}
