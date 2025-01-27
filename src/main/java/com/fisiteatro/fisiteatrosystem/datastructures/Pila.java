package com.fisiteatro.fisiteatrosystem.datastructures;

import java.util.EmptyStackException;

public class Pila<T> {
    private Nodo<T> cima;

    public Pila() {
        this.cima = null;
    }

    public void push(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        nuevoNodo.siguiente = cima;
        cima = nuevoNodo;
    }

    public T pop() {
        if (cima == null) {
            throw new EmptyStackException();
        }
        T dato = cima.dato;
        cima = cima.siguiente;
        return dato;
    }

    public T peek() {
        if (cima == null) {
            throw new EmptyStackException();
        }
        return cima.dato;
    }

    public boolean isEmpty() {
        return cima == null;
    }
}