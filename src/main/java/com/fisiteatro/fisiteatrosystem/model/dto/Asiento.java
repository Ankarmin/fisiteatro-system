package com.fisiteatro.fisiteatrosystem.model.dto;

public class Asiento {
    private String fila;
    private int numero;
    private boolean estado;

    public Asiento() {
    }

    public Asiento(String fila, int numero, boolean estado) {
        this.fila = fila;
        this.numero = numero;
        this.estado = estado;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
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

    @Override
    public String toString() {
        return "Asiento{" +
                "fila='" + fila + '\'' +
                ", numero=" + numero +
                ", estado=" + estado +
                '}';
    }
}