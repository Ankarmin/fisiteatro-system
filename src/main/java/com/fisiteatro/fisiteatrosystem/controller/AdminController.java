package com.fisiteatro.fisiteatrosystem.controller;

import com.fisiteatro.fisiteatrosystem.model.dao.EventoDAO;
import com.fisiteatro.fisiteatrosystem.model.dto.Evento;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
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
    private TableColumn<Evento, Integer> administrarEventos_columna_capacidad;

    @FXML
    private TableColumn<Evento, String> administrarEventos_columna_fecha;

    @FXML
    private TableColumn<Evento, String> administrarEventos_columna_hora;

    @FXML
    private TableColumn<Evento, Integer> administrarEventos_columna_id;

    @FXML
    private TableColumn<Evento, String> administrarEventos_columna_nombre;

    @FXML
    private TableColumn<Evento, Float> administrarEventos_columna_precio;

    @FXML
    private TableView<Evento> administrarEventos_tableViewEventos;

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
    private TableColumn<Evento, Integer> eventos_columna_capacidad;

    @FXML
    private TableColumn<Evento, String> eventos_columna_fecha;

    @FXML
    private TableColumn<Evento, String> eventos_columna_hora;

    @FXML
    private TableColumn<Evento, Integer> eventos_columna_id;

    @FXML
    private TableColumn<Evento, String> eventos_columna_nombre;

    @FXML
    private TableColumn<Evento, Float> eventos_columna_precio;

    @FXML
    private TableView<Evento> eventos_tableViewEventos;

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
        configurarColumnasEventos();
        configurarColumnasAdministrarEventos();

        cargarEventos();
        cargarAdministrarEventos();
    }

    public void switchForm(ActionEvent event) {
        panelEventos.setVisible(event.getSource() == bttnEventos);
        panelAdministrarEventos.setVisible(event.getSource() == bttnAdministrarEventos);
        panelGestionarTickets.setVisible(event.getSource() == bttnGestionarTickets);
    }

    private void configurarColumnasEventos() {
        eventos_columna_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        eventos_columna_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        eventos_columna_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        eventos_columna_hora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        eventos_columna_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        eventos_columna_capacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
    }

    private void configurarColumnasAdministrarEventos() {
        administrarEventos_columna_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        administrarEventos_columna_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        administrarEventos_columna_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        administrarEventos_columna_hora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        administrarEventos_columna_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        administrarEventos_columna_capacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
    }

    private void cargarEventos() {
        EventoDAO eventoDAO = new EventoDAO();
        ObservableList<Evento> eventosList = FXCollections.observableList(eventoDAO.readAll());

        eventos_tableViewEventos.setItems(eventosList);
        eventos_tableViewEventos.refresh();
    }

    private void cargarAdministrarEventos() {
        EventoDAO eventoDAO = new EventoDAO();
        ObservableList<Evento> eventosList = FXCollections.observableList(eventoDAO.readAll());

        administrarEventos_tableViewEventos.setItems(eventosList);
        administrarEventos_tableViewEventos.refresh();
    }

    @FXML
    private void cerrarSesion() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fisiteatro/fisiteatrosystem/view/fxml/Login.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Fisiteatro");

            Stage currentStage = (Stage) bttnCerrarSesion.getScene().getWindow();
            currentStage.close();

            stage.setOnCloseRequest(event -> {
                Platform.exit();
                System.exit(0);
            });

            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
