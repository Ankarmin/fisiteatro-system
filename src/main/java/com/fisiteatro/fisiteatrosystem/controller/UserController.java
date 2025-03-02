package com.fisiteatro.fisiteatrosystem.controller;

import com.fisiteatro.fisiteatrosystem.datastructures.Cola;
import com.fisiteatro.fisiteatrosystem.model.dto.AsientoDTO;
import com.fisiteatro.fisiteatrosystem.model.dto.ClienteDTO;
import com.fisiteatro.fisiteatrosystem.model.dto.EventoDTO;
import com.fisiteatro.fisiteatrosystem.model.dto.TicketDTO;
import com.fisiteatro.fisiteatrosystem.service.AsientoService;
import com.fisiteatro.fisiteatrosystem.service.EventoService;
import com.fisiteatro.fisiteatrosystem.service.TicketService;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
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
    }

    public void setClienteDTO(ClienteDTO clienteDTO) {
        this.clienteDTO = clienteDTO;
        cargarCompras();
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
        compras_columna_dniCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getDni()));

        compras_columna_evento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEvento().getNombre()));

        compras_columna_fecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEvento().getFecha()));

        compras_columna_hora.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEvento().getHora()));

        compras_columna_precio.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getEvento().getPrecio()));

        compras_columna_nroAsiento.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAsiento().getNumero()).asObject());

        compras_columna_filaAsiento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAsiento().getFila()));

    }

    private void cargarCompras() {
        ObservableList<TicketDTO> ticketList = FXCollections.observableArrayList(ticketService.getTicketsPorDNI(clienteDTO.getDni()).toList());
        compras_tableViewCompras.setItems(ticketList);
        compras_tableViewCompras.refresh();
    }

    private void cargarEventos() {
        ObservableList<EventoDTO> eventoList = FXCollections.observableArrayList(eventoService.readAll());
        eventos_tableViewEventos.setItems(eventoList);
        eventos_tableViewEventos.refresh();
    }

    private void configurarService() {
        ticketService = new TicketService();
        eventoService = new EventoService();
    }

    @FXML
    private void comprarTicket() {
        EventoDTO eventoSeleccionadoComprar = eventos_tableViewEventos.getSelectionModel().getSelectedItem();

        if (eventoSeleccionadoComprar != null) {
            System.out.println("Comprando ticket para: " + eventoSeleccionadoComprar.getNombre());

            if (eventoSeleccionadoComprar.getCapacidad() <= 0) {
                System.out.println("No hay asientos disponibles");
                return;
            }

            AsientoDTO asientoDisponible = null;
            AsientoService asiento = new AsientoService(eventoSeleccionadoComprar.getId());

            int idEventoSeleccionado = eventoSeleccionadoComprar.getId();
            String PATH = "src/main/java/com/fisiteatro/fisiteatrosystem/data/ticketsEliminadosPorEvento/eliminados_" + idEventoSeleccionado + ".json";
            File archivo = new File(PATH);

            if (archivo.exists() && archivo.length() > 3) {
                TicketDTO ticketPorReemitir = null;
                try {
                    ticketPorReemitir = ticketService.reemitirTicket(idEventoSeleccionado);
                } catch (IOException e) {
                    System.err.println("Error al obtener ticket por reemitir: " + e.getMessage());
                }

                asientoDisponible = ticketPorReemitir.getAsiento();

                try {
                    asiento.updateOcupado(asientoDisponible, idEventoSeleccionado);
                } catch (IOException e) {
                    System.err.println("Error al intentar cambiar el estado del asiento: " + e.getMessage());
                }
            } else {
                asientoDisponible = asiento.obtenerPrimerAsientoDisponible();

                if (asientoDisponible == null) {
                    System.out.println("No hay asientos disponibles.");
                    return;
                }

                try {
                    asiento.updateOcupado(asientoDisponible, eventoSeleccionadoComprar.getId());
                } catch (IOException e) {
                    System.err.println("Error al intentar cambiar el estado del asiento: " + e.getMessage());
                }
            }

            TicketDTO nuevoTicket = new TicketDTO(0, clienteDTO, asientoDisponible, eventoSeleccionadoComprar);

            try {
                eventoService.disminuirEnUno(eventoSeleccionadoComprar.getId());

                ticketService.create(nuevoTicket);

                System.out.println("Compra realizada con éxito. Asiento asignado: " + asientoDisponible);
                cargarCompras();
                cargarEventos();
            } catch (IOException e) {
                System.err.println("Error al registrar la compra: " + e.getMessage());
            }
        } else {
            System.out.println("Seleccione un evento antes de comprar.");
        }
    }

    @FXML
    private void eliminarTicket() {
        try {
            TicketDTO ticketEliminado = compras_tableViewCompras.getSelectionModel().getSelectedItem();

            Cola<TicketDTO> solicitudesTickets = ticketService.getSolicitudesTickets();

            if (ticketEliminado != null) {
                solicitudesTickets.offer(ticketEliminado);
                ticketService.saveSolicitudesTicketsJSON(solicitudesTickets);
                ticketService.deleteById(ticketEliminado.getId());
            } else {
                System.out.println("Seleccione ticket para solicitar su eliminación.");
            }

            cargarCompras();
            cargarEventos();
        } catch (IOException e) {
            System.err.println("Error al eliminar el ticket: " + e.getMessage());
        }
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
    private void buscarEvento() {
        String busqueda = eventos_txtFieldBuscar.getText();
        ObservableList<EventoDTO> eventoList = FXCollections.observableArrayList(eventoService.buscarEvento(busqueda));
        eventos_tableViewEventos.setItems(eventoList);
        eventos_tableViewEventos.refresh();
    }
}
