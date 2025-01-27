package com.fisiteatro.fisiteatrosystem.datastructures;

public class ListaEnlazada<T> {
    private Nodo<T> cabeza;

    public ListaEnlazada() {
        this.cabeza = null;
    }

    public void add(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = nuevoNodo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevoNodo;
        }
    }

    public void set(int posicion, T nuevoDato) {
        if (posicion < 0) {
            throw new IndexOutOfBoundsException("Posición inválida");
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < posicion; i++) {
            if (actual == null) {
                throw new IndexOutOfBoundsException("Posición inválida");
            }
            actual = actual.siguiente;
        }
        if (actual == null) {
            throw new IndexOutOfBoundsException("Posición inválida");
        }
        actual.dato = nuevoDato;
    }
}