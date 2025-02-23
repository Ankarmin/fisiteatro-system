package com.fisiteatro.fisiteatrosystem.controller;

import com.fisiteatro.fisiteatrosystem.model.dao.EventoDAO;
import com.fisiteatro.fisiteatrosystem.model.dao.TicketDAO;
import com.fisiteatro.fisiteatrosystem.model.dto.AsientoDTO;
import com.fisiteatro.fisiteatrosystem.model.dto.ClienteDTO;
import com.fisiteatro.fisiteatrosystem.model.dto.EventoDTO;
import com.fisiteatro.fisiteatrosystem.model.dto.TicketDTO;
import com.fisiteatro.fisiteatrosystem.service.EventoService;
import com.fisiteatro.fisiteatrosystem.service.TicketService;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
    private TableColumn<TicketDTO, String> compras_columna_dniCliente;

    @FXML
    private TableColumn<TicketDTO, String> compras_columna_evento;

    @FXML
    private TableColumn<TicketDTO, String> compras_columna_fecha;

    @FXML
    private TableColumn<TicketDTO, String> compras_columna_filaAsiento;

    @FXML
    private TableColumn<TicketDTO, String> compras_columna_filaAsiento1;

    @FXML
    private TableColumn<TicketDTO, String> compras_columna_hora;

    @FXML
    private TableColumn<TicketDTO, Integer> compras_columna_nroAsiento;

    @FXML
    private TableColumn<TicketDTO, Integer> compras_columna_nroTicket;

    @FXML
    private TableColumn<TicketDTO, Float> compras_columna_precio;

    @FXML
    private TableView<TicketDTO> compras_tableViewCompras;

    @FXML
    private TextField compras_txtFieldBuscar;

    @FXML
    private Button eventos_bttnBuscar;

    @FXML
    private Button eventos_bttnComprar;

    @FXML
    private TableColumn<EventoDTO, Integer> eventos_columna_capacidad;

    @FXML
    private TableColumn<EventoDTO, String> eventos_columna_fecha;

    @FXML
    private TableColumn<EventoDTO, String> eventos_columna_hora;

    @FXML
    private TableColumn<EventoDTO, Integer> eventos_columna_id;

    @FXML
    private TableColumn<EventoDTO, String> eventos_columna_nombre;

    @FXML
    private TableColumn<EventoDTO, Float> eventos_columna_precio;

    @FXML
    private TableView<EventoDTO> eventos_tableViewEventos;

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

    private TicketService ticketService;
    private EventoService eventoService;

    private ClienteDTO clienteDTO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarService();
        configurarColumnaEventos();
        configurarColumnaCompras();

        cargarEventos();
        cargarCompras();
    }

    public void setClienteDTO(ClienteDTO clienteDTO) {
        this.clienteDTO = clienteDTO;
    }

    public void switchForm(ActionEvent event) {
        panelEventos.setVisible(event.getSource() == bttnEventos);
        panelCompras.setVisible(event.getSource() == bttnCompras);
        panelHistorial.setVisible(event.getSource() == bttnHistorial);
    }

    private void configurarColumnaEventos() {
        eventos_columna_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        eventos_columna_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        eventos_columna_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        eventos_columna_hora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        eventos_columna_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        eventos_columna_capacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
    }

    private void configurarColumnaCompras() {
        compras_columna_nroTicket.setCellValueFactory(new PropertyValueFactory<>("id"));
        compras_columna_dniCliente.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCliente().getDni()));

        compras_columna_evento.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEvento().getNombre()));

        compras_columna_fecha.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEvento().getFecha()));

        compras_columna_hora.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEvento().getHora()));

        compras_columna_precio.setCellValueFactory(cellData ->
                new SimpleObjectProperty(cellData.getValue().getEvento().getPrecio()));

        compras_columna_nroAsiento.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getAsiento().getNumero()).asObject());

        compras_columna_filaAsiento.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAsiento().getFila()));

    }

    private void cargarCompras() {
        ObservableList<TicketDTO> ticketList = FXCollections.observableList(ticketService.readAll());
        compras_tableViewCompras.setItems(ticketList);
        compras_tableViewCompras.refresh();
    }

    private void cargarEventos() {
        ObservableList<EventoDTO> eventoList = FXCollections.observableList(eventoService.readAll());

        eventos_tableViewEventos.setItems(eventoList);
        eventos_tableViewEventos.refresh();
    }

    private void configurarService() {
        ticketService = new TicketService();
        eventoService = new EventoService();
    }

}
