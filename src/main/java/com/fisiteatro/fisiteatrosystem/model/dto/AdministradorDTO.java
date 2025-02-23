package com.fisiteatro.fisiteatrosystem.model.dto;

public class AdministradorDTO extends UsuarioDTO {
    public AdministradorDTO() {
    }

    public AdministradorDTO(String nombres, String apellidos, String dni, String contrasena) {
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