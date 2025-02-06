package com.fisiteatro.fisiteatrosystem.datastructures;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

public class Cola<T> {
    private Nodo<T> frente;
    private Nodo<T> fondo;

    public Cola() {
        this.frente = null;
        this.fondo = null;
    }

    public void offer(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (fondo != null) {
            fondo.siguiente = nuevoNodo;
        }
        fondo = nuevoNodo;
        if (frente == null) {
            frente = nuevoNodo;
        }
    }

    public T poll() {
        if (frente == null) {
            throw new NoSuchElementException();
        }
        T dato = frente.dato;
        frente = frente.siguiente;
        if (frente == null) {
            fondo = null;
        }
        return dato;
    }

    public T peek() {
        if (frente == null) {
            throw new NoSuchElementException();
        }
        return frente.dato;
    }

    public boolean isEmpty() {
        return frente == null;
    }

    public void cargarDesdeJson(String filePath, Class<T[]> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        T[] datos = mapper.readValue(new File(filePath), clazz);
        for (T dato : datos) {
            offer(dato);
        }
    }

    public void imprimir() {
        Nodo<T> actual = frente;
        while (actual != null) {
            System.out.println(actual.dato);
            actual = actual.siguiente;
        }
    }
}