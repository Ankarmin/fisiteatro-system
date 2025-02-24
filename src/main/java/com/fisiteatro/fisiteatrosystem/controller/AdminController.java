package com.fisiteatro.fisiteatrosystem.controller;

import com.fisiteatro.fisiteatrosystem.model.dto.EventoDTO;
import com.fisiteatro.fisiteatrosystem.model.dto.TicketDTO;
import com.fisiteatro.fisiteatrosystem.service.AsientoService;
import com.fisiteatro.fisiteatrosystem.service.EventoService;
import com.fisiteatro.fisiteatrosystem.service.TicketService;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.stage.Modality;
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
    private TableColumn<EventoDTO, Integer> administrarEventos_columna_capacidad;

    @FXML
    private TableColumn<EventoDTO, String> administrarEventos_columna_fecha;

    @FXML
    private TableColumn<EventoDTO, String> administrarEventos_columna_hora;

    @FXML
    private TableColumn<EventoDTO, Integer> administrarEventos_columna_id;

    @FXML
    private TableColumn<EventoDTO, String> administrarEventos_columna_nombre;

    @FXML
    private TableColumn<EventoDTO, Float> administrarEventos_columna_precio;

    @FXML
    private TableView<EventoDTO> administrarEventos_tableViewEventos;

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
    private Button gestionarTickets_bttnAceptarEliminacion;

    @FXML
    private Button gestionarTickets_bttnRechazarEliminacion;

    @FXML
    private TableColumn<TicketDTO, String> gestionarTickets_columna_cliente;

    @FXML
    private TableColumn<TicketDTO, String> gestionarTickets_columna_evento;

    @FXML
    private TableColumn<TicketDTO, String> gestionarTickets_columna_fecha;

    @FXML
    private TableColumn<TicketDTO, String> gestionarTickets_columna_hora;

    @FXML
    private TableColumn<TicketDTO, Integer> gestionarTickets_columna_nroTicket;

    @FXML
    private TableView<TicketDTO> gestionarTickets_tableViewSolicitudes;

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

    private EventoService eventoService;
    private TicketService ticketService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarServices();

        configurarColumnasEventos();
        configurarColumnasGestionarTickets();

        cargarEventos();
        cargarSolicitudes();
    }

    @FXML
    private void switchForm(ActionEvent event) {
        panelEventos.setVisible(event.getSource() == bttnEventos);
        panelAdministrarEventos.setVisible(event.getSource() == bttnAdministrarEventos);
        panelGestionarTickets.setVisible(event.getSource() == bttnGestionarTickets);
    }

    private void configurarServices() {
        eventoService = new EventoService();
        ticketService = new TicketService();
    }

    private void configurarColumnasEventos() {
        administrarEventos_columna_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        administrarEventos_columna_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        administrarEventos_columna_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        administrarEventos_columna_hora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        administrarEventos_columna_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        administrarEventos_columna_capacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
    }

    private void configurarColumnasGestionarTickets() {
        gestionarTickets_columna_nroTicket.setCellValueFactory(new PropertyValueFactory<>("id"));
        gestionarTickets_columna_evento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEvento().getNombre()));
        gestionarTickets_columna_fecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEvento().getFecha()));
        gestionarTickets_columna_hora.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEvento().getHora()));
        gestionarTickets_columna_cliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getNombreCompleto()));
    }

    private void cargarEventos() {
        ObservableList<EventoDTO> eventosList = FXCollections.observableArrayList(eventoService.readAll());
        administrarEventos_tableViewEventos.setItems(eventosList);
        administrarEventos_tableViewEventos.refresh();
    }

    private void cargarSolicitudes() {
        ObservableList<TicketDTO> ticketList = FXCollections.observableArrayList(ticketService.getSolicitudesTickets().toList());
        gestionarTickets_tableViewSolicitudes.setItems(ticketList);
        gestionarTickets_tableViewSolicitudes.refresh();
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

    @FXML
    private void crearEvento() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fisiteatro/fisiteatrosystem/view/fxml/AdminCrearEvento.fxml"));
            Parent root = fxmlLoader.load();

            AdminCrearEventoController adminCrearEventoController = fxmlLoader.getController();
            adminCrearEventoController.setEventoService(eventoService);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Crear Evento");

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();

            cargarEventos();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void modificarEvento() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fisiteatro/fisiteatrosystem/view/fxml/AdminModificarEvento.fxml"));
            Parent root = fxmlLoader.load();

            AdminModificarEventoController adminModificarEventoController = fxmlLoader.getController();
            adminModificarEventoController.setEventoService(eventoService);
            EventoDTO evento = administrarEventos_tableViewEventos.getSelectionModel().getSelectedItem();
            adminModificarEventoController.setEventoDTO(evento);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modificar Evento");

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();

            cargarEventos();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void eliminarEvento() {
        EventoDTO evento = administrarEventos_tableViewEventos.getSelectionModel().getSelectedItem();
        try {
            eventoService.deleteById(evento.getId());

            AsientoService asientoService = new AsientoService(evento.getId());
            asientoService.deleteFile(evento.getId());

            cargarEventos();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
