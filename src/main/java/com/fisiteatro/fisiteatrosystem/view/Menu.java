package com.fisiteatro.fisiteatrosystem.view;
//para el catalogo
import com.fisiteatro.fisiteatrosystem.model.dao.EventoDAO;
import com.fisiteatro.fisiteatrosystem.datastructures.ListaEnlazada;
import com.fisiteatro.fisiteatrosystem.model.dto.Evento;
//para el cliente
import com.fisiteatro.fisiteatrosystem.datastructures.Cola;
import com.fisiteatro.fisiteatrosystem.model.dao.ClienteDAO;
import com.fisiteatro.fisiteatrosystem.model.dto.Cliente;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private ClienteDAO clienteDAO;
    private Scanner scanner;
    private EventoDAO eventoDAO;
    public Menu() {
        this.clienteDAO = new ClienteDAO(new Cola<>());
        this.eventoDAO = new EventoDAO(new ListaEnlazada<>());
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
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    iniciarSesion();
                    break;
                case 2:
                    registrarse();
                    break;
                case 3:
                    eventoDAO.verCatalogo();
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

        if (dni.equals("99999999") && contrasena.equals("admin")) {
            System.out.println("¡Inicio de sesión exitoso! Bienvenido Administrador.");
            MenuAdmin menuAdmin = new MenuAdmin();
            menuAdmin.mostrarMenu();
            return;
        }
        if (cliente != null) {
            System.out.println("¡Inicio de sesión exitoso! Bienvenido, " + cliente.getNombres());
            MenuCliente menuCliente = new MenuCliente(cliente.getDni());
            menuCliente.mostrarMenu();
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

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.mostrarMenu();
    }
}
