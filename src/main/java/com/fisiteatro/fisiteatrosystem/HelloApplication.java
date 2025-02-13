package com.fisiteatro.fisiteatrosystem;

import com.fisiteatro.fisiteatrosystem.datastructures.ArbolBinarioBusqueda;
import com.fisiteatro.fisiteatrosystem.datastructures.Cola;
import com.fisiteatro.fisiteatrosystem.datastructures.ListaEnlazada;
import com.fisiteatro.fisiteatrosystem.datastructures.Pila;
import com.fisiteatro.fisiteatrosystem.model.dao.*;
import com.fisiteatro.fisiteatrosystem.model.dto.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        comprobarCRUD();
    }

    public static void main(String[] args) {
        launch();
    }

    private void comprobarCRUD() throws IOException {
        // Administrador
//        Cola<Administrador> administradores = new Cola<>();
//        administradores.cargarDesdeJson("src/main/java/com/fisiteatro/fisiteatrosystem/data/administrador.json", Administrador[].class);
//        AdministradorDAO administradorDAO = new AdministradorDAO(administradores);
//        Administrador admin = new Administrador("DA", "Admin", UUID.randomUUID().toString(), "newpass");
//        administradorDAO.create(admin);
//        List<Administrador> adminList = administradorDAO.readAll();
//        System.out.println("Administradores después de crear:");
//        adminList.forEach(System.out::println);

        // Cliente
        Cola<Cliente> clientes = new Cola<>();
        clientes.cargarDesdeJson("src/main/java/com/fisiteatro/fisiteatrosystem/data/cliente.json", Cliente[].class);
        ClienteDAO clienteDAO = new ClienteDAO(clientes);
        Cliente cliente = new Cliente("DA", "Cliente", UUID.randomUUID().toString(), "newpass");
        clienteDAO.create(cliente);
        List<Cliente> clienteList = clienteDAO.readAll();
        System.out.println("Clientes después de crear:");
        clienteList.forEach(System.out::println);

        // Evento
//        ListaEnlazada<Evento> eventos = new ListaEnlazada<>();
//        eventos.cargarDesdeJson("src/main/java/com/fisiteatro/fisiteatrosystem/data/evento.json", Evento[].class);
//        EventoDAO eventoDAO = new EventoDAO(eventos);
//        Evento evento = new Evento("Nuevo DA " + UUID.randomUUID(), "2023-12-31", "23:59", 100.0f, 500);
//        eventoDAO.create(evento);
//        List<Evento> eventoList = eventoDAO.readAll();
//        System.out.println("Eventos después de crear:");
//        eventoList.forEach(System.out::println);

        // Ticket
//        Pila<Ticket> tickets = new Pila<>();
//        tickets.cargarDesdeJson("src/main/java/com/fisiteatro/fisiteatrosystem/data/ticketsComprados.json", Ticket[].class);
//        TicketDAO ticketDAO = new TicketDAO(tickets);
        //Ticket ticket = new Ticket(cliente, new Asiento("D", 3, true));
        //ticketDAO.create(ticket);
        //List<Ticket> ticketList = ticketDAO.readAll();
        //System.out.println("Tickets después de crear:");
        //ticketList.forEach(System.out::println);

        // Asiento
//        ArbolBinarioBusqueda<Asiento> asientos = new ArbolBinarioBusqueda<>();
//        asientos.cargarDesdeJson("src/main/java/com/fisiteatro/fisiteatrosystem/data/asiento.json", Asiento[].class);
//        AsientoDAO asientoDAO = new AsientoDAO(asientos);
//        //Asiento asiento = new Asiento("F", 4, true);
//        //asientoDAO.create(asiento);
//        List<Asiento> asientoList = asientoDAO.readAll();
//        System.out.println("Asientos después de crear:");
//        asientoList.forEach(System.out::println);
       }
}