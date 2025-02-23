package com.fisiteatro.fisiteatrosystem.controller;

import com.fisiteatro.fisiteatrosystem.model.dao.EventoDAO;
import com.fisiteatro.fisiteatrosystem.model.dto.Evento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private Button bttnCerrarSesion;

    @FXML
    private Button bttnCompras;

    @FXML
    private Button bttnEventos;

    @FXML
    private Button bttnHistorial;

    @FXML
    private Button compras_bttnBuscar;

    @FXML
    private Button compras_bttnEliminar;

    @FXML
    private TableColumn<?, ?> compras_columna_dniCliente;

    @FXML
    private TableColumn<?, ?> compras_columna_evento;

    @FXML
    private TableColumn<?, ?> compras_columna_fecha;

    @FXML
    private TableColumn<?, ?> compras_columna_filaAsiento;

    @FXML
    private TableColumn<?, ?> compras_columna_filaAsiento1;

    @FXML
    private TableColumn<?, ?> compras_columna_hora;

    @FXML
    private TableColumn<?, ?> compras_columna_nroAsiento;

    @FXML
    private TableColumn<?, ?> compras_columna_nroTicket;

    @FXML
    private TableColumn<?, ?> compras_columna_precio;

    @FXML
    private TableView<?> compras_tableViewCompras;

    @FXML
    private TextField compras_txtFieldBuscar;

    @FXML
    private Button eventos_bttnBuscar;

    @FXML
    private Button eventos_bttnComprar;

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
    private TableColumn<?, ?> historialCompras_columna_dniCliente;

    @FXML
    private TableColumn<?, ?> historialCompras_columna_evento;

    @FXML
    private TableColumn<?, ?> historialCompras_columna_fecha;

    @FXML
    private TableColumn<?, ?> historialCompras_columna_hora;

    @FXML
    private TableColumn<?, ?> historialCompras_columna_nroAsiento;

    @FXML
    private TableColumn<?, ?> historialCompras_columna_nroTicket;

    @FXML
    private TableColumn<?, ?> historialCompras_columna_precio;

    @FXML
    private TableView<?> historialCompras_tablevVewCompras;

    @FXML
    private TableColumn<?, ?> historialEliminados_columna_dniCliente;

    @FXML
    private TableColumn<?, ?> historialEliminados_columna_evento;

    @FXML
    private TableColumn<?, ?> historialEliminados_columna_fecha;

    @FXML
    private TableColumn<?, ?> historialEliminados_columna_filaAsiento;

    @FXML
    private TableColumn<?, ?> historialEliminados_columna_hora;

    @FXML
    private TableColumn<?, ?> historialEliminados_columna_nroAsiento;

    @FXML
    private TableColumn<?, ?> historialEliminados_columna_nroTicket;

    @FXML
    private TableColumn<?, ?> historialEliminados_columna_precio;

    @FXML
    private TableView<?> historialEliminados_tableViewEliminados;

    @FXML
    private Label lblNombreCuenta;

    @FXML
    private Label lblPath;

    @FXML
    private AnchorPane panelCompras;

    @FXML
    private AnchorPane panelEventos;

    @FXML
    private AnchorPane panelHistorial;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColumnaEventos();

        cargarEventos();
    }

    public void switchForm(ActionEvent event) {
        panelEventos.setVisible(event.getSource() == bttnEventos);
        panelCompras.setVisible(event.getSource() == bttnCompras);
        panelHistorial.setVisible(event.getSource() == bttnHistorial);
    }

    private void configurarColumnaEventos(){
        eventos_columna_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        eventos_columna_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        eventos_columna_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        eventos_columna_hora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        eventos_columna_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        eventos_columna_capacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
    }

    private void configurarColumnaCompras(){
        compras_columna_nroTicket.setCellValueFactory(new PropertyValueFactory<>("id"));
        compras_columna_dniCliente.setCellValueFactory(new PropertyValueFactory<>("dni"));
        compras_columna_evento.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        compras_columna_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        compras_columna_hora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        compras_columna_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        compras_columna_nroAsiento.setCellValueFactory(new PropertyValueFactory<>("numero"));
        compras_columna_filaAsiento.setCellValueFactory(new PropertyValueFactory<>("fila"));
    }

    private void cargarCompras(){

    }

    private void cargarEventos(){
        EventoDAO eventoDAO = new EventoDAO();
        ObservableList<Evento> eventosList = FXCollections.observableList(eventoDAO.readAll());

        eventos_tableViewEventos.setItems(eventosList);
        eventos_tableViewEventos.refresh();
    }
}
