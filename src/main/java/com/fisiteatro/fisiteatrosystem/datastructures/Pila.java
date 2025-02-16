package com.fisiteatro.fisiteatrosystem.datastructures;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

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

    public List<T> toList() {
        List<T> list = new ArrayList<>();
        Nodo<T> actual = cima;
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
        for (int i = datos.length - 1; i >= 0; i--) {
            push(datos[i]);
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