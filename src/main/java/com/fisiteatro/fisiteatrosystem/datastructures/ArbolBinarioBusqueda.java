package com.fisiteatro.fisiteatrosystem.datastructures;

public class ArbolBinarioBusqueda<T extends Comparable<T>> {
    private Nodo<T> raiz;

    public ArbolBinarioBusqueda() {
        this.raiz = null;
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

    public boolean buscar(T dato) {
        return buscarRecursivo(raiz, dato);
    }

    private boolean buscarRecursivo(Nodo<T> nodo, T dato) {
        if (nodo == null) {
            return false;
        }
        if (dato.compareTo(nodo.dato) == 0) {
            return true;
        }
        return dato.compareTo(nodo.dato) < 0 ? buscarRecursivo(nodo.izquierdo, dato) : buscarRecursivo(nodo.derecho, dato);
    }

    public void eliminar(T dato) {
        raiz = eliminarRecursivo(raiz, dato);
    }

    private Nodo<T> eliminarRecursivo(Nodo<T> nodo, T dato) {
        if (nodo == null) {
            return null;
        }
        if (dato.compareTo(nodo.dato) < 0) {
            nodo.izquierdo = eliminarRecursivo(nodo.izquierdo, dato);
        } else if (dato.compareTo(nodo.dato) > 0) {
            nodo.derecho = eliminarRecursivo(nodo.derecho, dato);
        } else {
            if (nodo.izquierdo == null) {
                return nodo.derecho;
            } else if (nodo.derecho == null) {
                return nodo.izquierdo;
            }
            nodo.dato = encontrarMinimo(nodo.derecho).dato;
            nodo.derecho = eliminarRecursivo(nodo.derecho, nodo.dato);
        }
        return nodo;
    }

    private Nodo<T> encontrarMinimo(Nodo<T> nodo) {
        while (nodo.izquierdo != null) {
            nodo = nodo.izquierdo;
        }
        return nodo;
    }

    public void imprimirEnOrden() {
        imprimirEnOrdenRecursivo(raiz);
    }

    private void imprimirEnOrdenRecursivo(Nodo<T> nodo) {
        if (nodo != null) {
            imprimirEnOrdenRecursivo(nodo.izquierdo);
            System.out.println(nodo.dato);
            imprimirEnOrdenRecursivo(nodo.derecho);
        }
    }
}