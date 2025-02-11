package com.fisiteatro.fisiteatrosystem.model.dto;

public class Asiento implements Comparable<Asiento> {
    private String eventoNombre;
    private String fila;
    private int numero;
    private boolean estado;

    public Asiento() {
    }

    public Asiento(String eventoNombre,String fila, int numero, boolean estado) {
        this.eventoNombre = eventoNombre;
        this.fila = fila;
        this.numero = numero;
        this.estado = estado;
    }

    public String getEventoNombre() {
        return eventoNombre;
    }
    public void setEventoNombre(String eventoNombre) {
        this.eventoNombre = eventoNombre;
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

    @Override
    public int compareTo(Asiento o) {
        int eventoComparison = this.eventoNombre.compareTo(o.eventoNombre);
        if (eventoComparison != 0) {
            return eventoComparison;
        }
        int filaComparison = this.fila.compareTo(o.fila);
        if (filaComparison != 0) {
            return filaComparison;
        }
        return Integer.compare(this.numero, o.numero);
    }
}