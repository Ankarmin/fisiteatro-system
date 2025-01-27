package com.fisiteatro.fisiteatrosystem.model.dto;

public class Asiento {
    private int numero;
    private boolean estado;

    public Asiento() {
    }

    public Asiento(int numero, boolean estado) {
        this.numero = numero;
        this.estado = estado;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
