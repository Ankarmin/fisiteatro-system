package com.fisiteatro.fisiteatrosystem.datastructures;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public List<T> toList() {
        List<T> list = new ArrayList<>();
        Nodo<T> actual = cabeza;
        while (actual != null) {
            list.add(actual.dato);
            actual = actual.siguiente;
        }
        return list;
    }

    public void cargarDesdeJson(String filePath, Class<T[]> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(filePath);
        if (file.length() == 0) {
            System.out.println("Archivo " + filePath + " vacío. Inicialización con valores por defecto.");
            return;
        }
        T[] datos = mapper.readValue(file, clazz);
        for (T dato : datos) {
            add(dato);
        }
    }

    public void remove(int posicion) throws IOException {
        if (posicion < 0) {
            throw new IndexOutOfBoundsException("Posición inválida");
        }

        // eliminar primer nodo
        if (posicion == 0) {
            if (cabeza == null) {
                throw new IndexOutOfBoundsException("La lista está vacía");
            }
            cabeza = cabeza.siguiente;
            return;
        }

        Nodo<T> actual = cabeza;
        for (int i = 0; i < posicion - 1; i++) {
            if (actual == null || actual.siguiente == null) {
                throw new IndexOutOfBoundsException("Posición inválida");
            }
            actual = actual.siguiente;
        }

        if (actual.siguiente == null) {
            throw new IndexOutOfBoundsException("Posición inválida");
        }
        actual.siguiente = actual.siguiente.siguiente;
    }
}