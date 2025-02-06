package com.fisiteatro.fisiteatrosystem;

import com.fisiteatro.fisiteatrosystem.datastructures.Cola;
import com.fisiteatro.fisiteatrosystem.datastructures.ListaEnlazada;
import com.fisiteatro.fisiteatrosystem.datastructures.Pila;
import com.fisiteatro.fisiteatrosystem.model.dto.Administrador;
import com.fisiteatro.fisiteatrosystem.model.dto.Cliente;
import com.fisiteatro.fisiteatrosystem.model.dto.Evento;
import com.fisiteatro.fisiteatrosystem.model.dto.Ticket;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        cargarDatos();
    }

    public static void main(String[] args) {
        launch();
    }

    private void cargarDatos() throws IOException {
        Cola<Cliente> colaClientes = new Cola<>();
        colaClientes.cargarDesdeJson("src/main/java/com/fisiteatro/fisiteatrosystem/data/cliente.json", Cliente[].class);
        System.out.println("Clientes en la cola:");
        colaClientes.imprimir();

        ListaEnlazada<Evento> listaEventos = new ListaEnlazada<>();
        listaEventos.cargarDesdeJson("src/main/java/com/fisiteatro/fisiteatrosystem/data/evento.json", Evento[].class);
        System.out.println("Eventos en la lista enlazada:");
        listaEventos.imprimir();

        Pila<Ticket> pilaTickets = new Pila<>();
        pilaTickets.cargarDesdeJson("src/main/java/com/fisiteatro/fisiteatrosystem/data/ticket.json", Ticket[].class);
        System.out.println("Tickets en la pila:");
        pilaTickets.imprimir();

        Cola<Administrador> colaAdministradores = new Cola<>();
        colaAdministradores.cargarDesdeJson("src/main/java/com/fisiteatro/fisiteatrosystem/data/administrador.json", Administrador[].class);
        System.out.println("Administradores en la cola:");
        colaAdministradores.imprimir();
    }
}