package com.fisiteatro.fisiteatrosystem.view;

import com.fisiteatro.fisiteatrosystem.datastructures.Cola;
import com.fisiteatro.fisiteatrosystem.model.dao.ClienteDAO;
import com.fisiteatro.fisiteatrosystem.model.dto.Cliente;

import java.io.IOException;
import java.util.Scanner;

public class Menu {

    private ClienteDAO clienteDAO;
    private Scanner scanner;

    public Menu() {
        this.clienteDAO = new ClienteDAO(new Cola<>());
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrarse");
            System.out.println("3. Ver catálogo de eventos");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea

            switch (opcion) {
                case 1:
                    iniciarSesion();
                    break;
                case 2:
                    registrarse();
                    break;
                case 3:
                    verCatalogo();
                    break;
                case 4:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 4);
    }

    private void iniciarSesion() {
        System.out.print("Ingrese su DNI: ");
        String dni = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String contrasena = scanner.nextLine();

        Cliente cliente = clienteDAO.iniciarSesion(dni, contrasena);
        if (cliente != null) {
            System.out.println("¡Inicio de sesión exitoso! Bienvenido, " + cliente.getNombres());
        } else {
            System.out.println("DNI o contraseña incorrectos. Intente nuevamente.");
        }
    }

    private void registrarse() {
        System.out.print("Ingrese su nombre: ");
        String nombres = scanner.nextLine();

        System.out.print("Ingrese su apellido: ");
        String apellidos = scanner.nextLine();

        System.out.print("Ingrese su DNI: ");
        String dni = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String contrasena = scanner.nextLine();

        if (clienteDAO.obtenerPorDni(dni) != null) {
            System.out.println("\n Error: Ya existe un cliente con este DNI.");
            return;
        }

        Cliente nuevoCliente = new Cliente(nombres, apellidos, dni, contrasena);
        try {
            clienteDAO.create(nuevoCliente);
            System.out.println("Registro exitoso. ¡Ahora puede iniciar sesión!");
        } catch (IOException e) {
            System.out.println("Error al registrar el cliente.");
        }
    }

    private void verCatalogo() {
        System.out.println("Mostrando el catálogo de eventos...");
        // Aquí puedes agregar la funcionalidad para mostrar eventos
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.mostrarMenu();
    }
}
