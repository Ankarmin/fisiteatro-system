package com.fisiteatro.fisiteatrosystem.view;

import com.fisiteatro.fisiteatrosystem.datastructures.Cola;
import com.fisiteatro.fisiteatrosystem.model.dao.AdministradorDAO;

import java.util.Scanner;

public class MenuAdmin {

    private AdministradorDAO adminDAO;
    private Scanner scanner;

    public MenuAdmin() {
        this.adminDAO = new AdministradorDAO(new Cola<>());
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        char opcion;
        do {
            System.out.println("\n\t---ADMINISTRADOR---\n");
            System.out.println("1. CRUD Eventos");
            System.out.println("2. Gestionar Tickets");
            System.out.println("3. Reportes");
            System.out.println("4. Cerrar Sesion");

            opcion = scanner.next().charAt(0);
            scanner.nextLine();

            switch(opcion){
                case '1':
                    // menu del crud
                    mostrarMenuEventos();
                    break;
                case '2':
                    // ver la cima d la pila de tickets y que le salga si aceptar o no
                    break;
                case '3':
                    // reportes si da tiempo
                    break;
                case '4':
                    System.out.println("Saliendo de la sesion...");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente nuevamente.");
            }
        } while(opcion != '4');
    }

    private void mostrarMenuEventos(){
        char opcion;
        do{
            System.out.println("\n\t---CRUD EVENTOS---\n");
            System.out.println("1. Agregar evento");
            System.out.println("2. Modificar evento");
            System.out.println("3. Eliminar evento");
            System.out.println("4. Regresar");

            opcion = scanner.next().charAt(0);
            scanner.nextLine();

            switch (opcion){
                case '1':
                    // agrgar
                    break;
                case '2':
                    // modificar
                    break;
                case '3':
                    // eliminar
                    break;
                case '4':
                    System.out.println("Regresando al menú principal...");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente nuevamente.");
            }
        } while (opcion != '4');
    }

    private void agregarEvento(){
        // datos: nombre, fecha, hora, precio, capacidad
        System.out.print("Ingrese nombre del evento: ");
        System.out.print("Ingrese fecha del evento (AAAA-MM-DD): ");
        System.out.print("Ingrese hora del evento (HH:MM): ");
        System.out.print("Ingrese el precio por entrada: ");
        System.out.print("Ingrese la capacidad del evento: ");
    }

    public static void main(String[] args) {

    }
}
