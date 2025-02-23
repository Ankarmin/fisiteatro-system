package com.fisiteatro.fisiteatrosystem.model.dto;

public class AsientoDTO implements Comparable<AsientoDTO> {
    private int idEvento;
    private String fila;
    private int numero;
    private boolean estado;

    public AsientoDTO() {
    }

    public AsientoDTO(int idEvento, String fila, int numero, boolean estado) {
        this.idEvento = idEvento;
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

    public boolean getEstado() {
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

    @Override
    public int compareTo(AsientoDTO o) {
        return Integer.compare(this.numero, o.numero);
    }
}