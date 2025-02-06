package com.fisiteatro.fisiteatrosystem.model.dto;

public class Administrador extends Usuario {
    public Administrador() {
    }

    public Administrador(String nombres, String apellidos, String dni, String contrasena) {
        super(nombres, apellidos, dni, contrasena);
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", dni='" + dni + '\'' +
                ", contrasena='" + contrasena + '\'' +
                '}';
    }
}