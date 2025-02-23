package com.fisiteatro.fisiteatrosystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private Button administrarEventos_bttnBuscar;

    @FXML
    private Button administrarEventos_bttnCrearEvento;

    @FXML
    private Button administrarEventos_bttnEliminarEvento;

    @FXML
    private Button administrarEventos_bttnModificarEvento;

    @FXML
    private TableColumn<?, ?> administrarEventos_columna_capacidad;

    @FXML
    private TableColumn<?, ?> administrarEventos_columna_fecha;

    @FXML
    private TableColumn<?, ?> administrarEventos_columna_hora;

    @FXML
    private TableColumn<?, ?> administrarEventos_columna_id;

    @FXML
    private TableColumn<?, ?> administrarEventos_columna_nombre;

    @FXML
    private TableColumn<?, ?> administrarEventos_columna_precio;

    @FXML
    private TableView<?> administrarEventos_tableViewEventos;

    @FXML
    private TextField administrarEventos_textFieldBuscar;

    @FXML
    private Button bttnAdministrarEventos;

    @FXML
    private Button bttnCerrarSesion;

    @FXML
    private Button bttnEventos;

    @FXML
    private Button bttnGestionarTickets;

    @FXML
    private Button bttnInventarios;

    @FXML
    private Button eventos_bttnBuscar;

    @FXML
    private TableColumn<?, ?> eventos_columna_capacidad;

    @FXML
    private TableColumn<?, ?> eventos_columna_fecha;

    @FXML
    private TableColumn<?, ?> eventos_columna_hora;

    @FXML
    private TableColumn<?, ?> eventos_columna_id;

    @FXML
    private TableColumn<?, ?> eventos_columna_nombre;

    @FXML
    private TableColumn<?, ?> eventos_columna_precio;

    @FXML
    private TableView<?> eventos_tableViewEventos;

    @FXML
    private TextField eventos_txtFieldBuscar;

    @FXML
    private Button gestionarTickets_bttnAceptarEliminacion;

    @FXML
    private Button gestionarTickets_bttnRechazarEliminacion;

    @FXML
    private TableColumn<?, ?> gestionarTickets_columna_cliente;

    @FXML
    private TableColumn<?, ?> gestionarTickets_columna_evento;

    @FXML
    private TableColumn<?, ?> gestionarTickets_columna_fecha;

    @FXML
    private TableColumn<?, ?> gestionarTickets_columna_hora;

    @FXML
    private TableColumn<?, ?> gestionarTickets_columna_nroTicket;

    @FXML
    private TableView<?> gestionarTickets_tableViewSolicitudes;

    @FXML
    private TableColumn<?, ?> inventarios_columna_cantidadAumentada;

    @FXML
    private TableColumn<?, ?> inventarios_columna_categoriaProducto;

    @FXML
    private TableColumn<?, ?> inventarios_columna_fecha;

    @FXML
    private TableColumn<?, ?> inventarios_columna_idProducto;

    @FXML
    private TableColumn<?, ?> inventarios_columna_nombreProducto;

    @FXML
    private TableColumn<?, ?> inventarios_columna_sede;

    @FXML
    private TableView<?> inventarios_tableView;

    @FXML
    private Label lblNombreCuenta;

    @FXML
    private Label lblPath;

    @FXML
    private AnchorPane panelAdministrarEventos;

    @FXML
    private AnchorPane panelEventos;

    @FXML
    private AnchorPane panelGestionarTickets;

    @FXML
    private AnchorPane pnlInventarios;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void switchForm(ActionEvent event) {
        panelEventos.setVisible(event.getSource() == bttnEventos);
        panelAdministrarEventos.setVisible(event.getSource() == bttnAdministrarEventos);
        panelGestionarTickets.setVisible(event.getSource() == bttnGestionarTickets);
    }
}
