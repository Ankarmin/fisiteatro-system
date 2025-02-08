package com.fisiteatro.fisiteatrosystem.datastructures;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    public List<T> toList() {
        List<T> list = new ArrayList<>();
        Nodo<T> actual = frente;
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