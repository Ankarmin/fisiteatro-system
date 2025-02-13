package com.fisiteatro.fisiteatrosystem.model.dto;

public class Cliente extends Usuario {

    public Cliente() {
    }

    public Cliente(String nombres, String apellidos, String dni, String contrasena) {
        super(nombres, apellidos, dni, contrasena);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", dni='" + dni + '\'' +
                ", contrasena='" + contrasena + '\'' +
                '}';
    }
}