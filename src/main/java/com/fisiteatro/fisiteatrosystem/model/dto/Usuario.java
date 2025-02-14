package com.fisiteatro.fisiteatrosystem.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class Usuario {
    protected String nombres;
    protected String apellidos;
    protected String dni;
    protected String contrasena;

    protected Usuario() {
    }

    protected Usuario(String nombres, String apellidos, String dni, String contrasena) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.contrasena = contrasena;
    }

    public String getNombres() {
        return nombres;
    }

    @JsonIgnore
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}