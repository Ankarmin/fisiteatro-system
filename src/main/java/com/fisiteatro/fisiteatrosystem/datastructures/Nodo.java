package com.fisiteatro.fisiteatrosystem.datastructures;

public class Nodo<T> {
    T dato;
    Nodo<T> siguiente;
    Nodo<T> izquierdo;
    Nodo<T> derecho;

    public Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
        this.izquierdo = null;
        this.derecho = null;
    }
}