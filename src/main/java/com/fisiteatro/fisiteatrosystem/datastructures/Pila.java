package com.fisiteatro.fisiteatrosystem.datastructures;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
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

    public void cargarDesdeJson(String filePath, Class<T[]> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        T[] datos = mapper.readValue(new File(filePath), clazz);
        for (T dato : datos) {
            push(dato);
        }
    }

    public void imprimir() {
        Nodo<T> actual = cima;
        while (actual != null) {
            System.out.println(actual.dato);
            actual = actual.siguiente;
        }
    }
}