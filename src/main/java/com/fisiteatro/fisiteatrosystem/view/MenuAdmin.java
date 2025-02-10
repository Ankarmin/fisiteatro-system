package com.fisiteatro.fisiteatrosystem.view;

import com.fisiteatro.fisiteatrosystem.datastructures.Cola;
import com.fisiteatro.fisiteatrosystem.datastructures.ListaEnlazada;
import com.fisiteatro.fisiteatrosystem.model.dao.AdministradorDAO;
import com.fisiteatro.fisiteatrosystem.model.dao.EventoDAO;
import com.fisiteatro.fisiteatrosystem.model.dto.Administrador;
import com.fisiteatro.fisiteatrosystem.model.dto.Evento;

import java.io.IOException;
import java.util.Scanner;

public class MenuAdmin {

    private AdministradorDAO adminDAO;
    private EventoDAO eventoDAO;
    private Scanner scanner;

    public MenuAdmin() {
        this.adminDAO = new AdministradorDAO();
        this.eventoDAO = new EventoDAO(new ListaEnlazada<>());
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        char opcion;
        do {
            System.out.println("\n\t---ADMINISTRADOR---\n");
            System.out.println("1. CRUD Eventos");
            System.out.println("2. Gestionar Tickets");
            System.out.println("3. Reportes");
            System.out.println("4. Cambiar contraseña");
            System.out.println("5. Cerrar Sesión");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.next().charAt(0);
            scanner.nextLine();

            switch(opcion){
                case '1':
                    // menu del crud
                    mostrarMenuEventos();
                    break;
                case '2':
                    // ver la cima d la pila de tickets y que le salga si aceptar o no
                    gestionarTickets();
                    break;
                case '3':
                    // reportes si da tiempo
                    break;
                case '4':
                    // cambiar contra
                    cambiarContrasenia();
                    break;
                case '5':
                    System.out.println("Saliendo de la sesión...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while(opcion != '5');
    }

    private void agregarEvento(){
        // datos: nombre, fecha, hora, precio, capacidad
        System.out.print("\nIngrese nombre del evento: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese fecha del evento (AAAA-MM-DD): ");
        String fecha = scanner.nextLine();

        System.out.print("Ingrese hora del evento (HH:MM): ");
        String hora = scanner.nextLine();

        System.out.print("Ingrese el precio de entrada: ");
        float precio = scanner.nextFloat();
        scanner.nextLine();

        System.out.print("Ingrese la capacidad del evento: ");
        int capacidad = scanner.nextInt();
        scanner.nextLine();

        Evento nuevoEvento = new Evento(nombre, fecha, hora, precio, capacidad);
        try{
            eventoDAO.create(nuevoEvento);
            System.out.println("\nEvento creado con éxito.");
        }catch(IOException e){
            System.out.println("Error al crear evento: " + e.getMessage());
        }
    }

    private void modificarEvento(){
        // primero mostrar la lista de eventos con indice y q el admin elija
        // evento modificar segun el indice
        eventoDAO.verCatalogo();

        System.out.print("Ingrese el ID del evento a modificar: ");
        int indice = scanner.nextInt();
        scanner.nextLine();
        indice -= 1;

        if(!eventoDAO.validarIndex(indice)){
            System.out.println("ID no válido.");
            return;
        }

        // el evento del indice se guarde en una variable y elegir q se quiere editar para no tener q llenar todos los campos
        Evento evento = eventoDAO.get(indice);
        // menu q hace los cambios
        opcionesModificarEvento(evento);

        try {
            eventoDAO.update(indice, evento);
            System.out.println("\n\tEvento actualizado con éxito\n");
            eventoDAO.verCatalogo();
        } catch (IOException e) {
            System.out.println("Error al actualizar evento: " + e.getMessage());
        }
    }

    private void eliminarEvento(){
        // primero mostrar la lista de eventos con indice y q el admin elija
        // evento eliminar segun el indice
        eventoDAO.verCatalogo();

        System.out.print("Ingrese el ID del evento a eliminar: ");
        int indice = scanner.nextInt();
        scanner.nextLine();
        indice -= 1;

        if(!eventoDAO.validarIndex(indice)){
            System.out.println("ID no válido.");
            return;
        }

        try {
            eventoDAO.deleteByIndex(indice);
            System.out.println("Evento eliminado correctamente.\n");
            eventoDAO.verCatalogo();
        } catch (IOException e) {
            System.out.println("Error al eliminar evento: " + e.getMessage());
        }
    }

    private void gestionarTickets(){
        // q se muestre el frente de la cola y se vea si cola sig != null
        // q se muestre algo como "mas tickets en la cola"
        // opcions d si/no para la cancelacion, luego d esto el ticket
        // se va a una pila
    }

    private void cambiarContrasenia(){
        System.out.print("\nIngrese contraseña actual: ");
        String contraseniaActual = scanner.nextLine();

        if(!adminDAO.verificarContrasenia(contraseniaActual)){
            System.out.println("Contraseña incorrecta.");
            return;
        }

        System.out.print("Ingrese la nueva contraseña: ");
        String contraseniaNueva = scanner.nextLine();

        Administrador administrador = adminDAO.cambiarContrasenia(contraseniaNueva);
        try {
            adminDAO.update(administrador);
            System.out.println("\nContraseña actualizada con exito.");
        } catch (IOException e) {
            System.out.println("Error al intentar cambiar la contraseña: " + e.getMessage());
        }
    }

    private void mostrarMenuEventos(){
        char opcion;
        do{
            System.out.println("\n\t---CRUD EVENTOS---\n");
            System.out.println("1. Agregar evento");
            System.out.println("2. Modificar evento");
            System.out.println("3. Eliminar evento");
            System.out.println("4. Regresar");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.next().charAt(0);
            scanner.nextLine();

            switch (opcion){
                case '1':
                    // agrgar
                    agregarEvento();
                    break;
                case '2':
                    // modificar
                    modificarEvento();
                    break;
                case '3':
                    // eliminar
                    eliminarEvento();
                    break;
                case '4':
                    System.out.println("Regresando al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != '4');
    }

    public void opcionesModificarEvento(Evento evento){
        char opcion;
        System.out.println("\n\t---MODIFICAR EVENTO---\n");
        System.out.println("1. Modificar nombre");
        System.out.println("2. Modificar fecha");
        System.out.println("3. Modificar hora");
        System.out.println("4. Modificar precio");
        System.out.println("5. Modificar capacidad");
        System.out.println("6. Regresar");
        System.out.print("Ingrese opción: ");

        opcion = scanner.next().charAt(0);
        scanner.nextLine();

        switch(opcion){
            case '1':
                System.out.print("\nIngrese nuevo nombre: ");
                String nuevoNombre = scanner.nextLine();
                evento.setNombre(nuevoNombre);
                break;
            case '2':
                System.out.print("\nIngrese nueva fecha (AAAA-MM-DD): ");
                String nuevaFecha = scanner.nextLine();
                evento.setFecha(nuevaFecha);
                break;
            case '3':
                System.out.print("\nIngrese nueva hora (HH:MM): ");
                String nuevaHora = scanner.nextLine();
                evento.setHora(nuevaHora);
                break;
            case '4':
                System.out.print("\nIngrese nuevo precio: ");
                float nuevoPrecio = scanner.nextFloat();
                evento.setPrecio(nuevoPrecio);
                break;
            case '5':
                System.out.print("\nIngrese nueva capacidad: ");
                int nuevaCapacidad = scanner.nextInt();
                evento.setCapacidad(nuevaCapacidad);
                break;
            case '6':
                System.out.println("Regresando...");
                break;
            default:
                System.out.println("Opción no válida. Intente nuevamente.");
        }
    }

    public static void main(String[] args) {
        MenuAdmin menu = new MenuAdmin();
        menu.mostrarMenu();
    }
}
