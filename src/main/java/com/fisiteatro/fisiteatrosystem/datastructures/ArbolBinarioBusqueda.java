package com.fisiteatro.fisiteatrosystem.datastructures;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class ArbolBinarioBusqueda<T extends Comparable<T>> {
    private Nodo<T> raiz;

    public ArbolBinarioBusqueda() {
        this.raiz = null;
    }

    public Nodo<T> getRaiz() {
        return raiz;
    }

    public void insertar(T dato) {
        raiz = insertarRecursivo(raiz, dato);
    }

    private Nodo<T> insertarRecursivo(Nodo<T> nodo, T dato) {
        if (nodo == null) {
            return new Nodo<>(dato);
        }
        if (dato.compareTo(nodo.dato) < 0) {
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, dato);
        } else if (dato.compareTo(nodo.dato) > 0) {
            nodo.derecho = insertarRecursivo(nodo.derecho, dato);
        }
        return nodo;
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
            insertar(dato);
        }
    }

    public void imprimirEnOrden(Consumer<T> consumer) {
        imprimirEnOrdenRecursivo(raiz, consumer);
    }

    private void imprimirEnOrdenRecursivo(Nodo<T> nodo, Consumer<T> consumer) {
        if (nodo != null) {
            imprimirEnOrdenRecursivo(nodo.izquierdo, consumer);
            consumer.accept(nodo.dato);
            imprimirEnOrdenRecursivo(nodo.derecho, consumer);
        }
    }

    public T getDato(Nodo<T> nodo) {
        return (nodo != null) ? nodo.dato : null;
    }

    public Nodo<T> getIzquierdo(Nodo<T> nodo) {
        return (nodo != null) ? nodo.izquierdo : null;
    }

    public Nodo<T> getDerecho(Nodo<T> nodo) {
        return (nodo != null) ? nodo.derecho : null;
    }
}