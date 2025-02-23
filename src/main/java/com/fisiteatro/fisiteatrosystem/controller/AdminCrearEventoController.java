package com.fisiteatro.fisiteatrosystem.controller;

import com.fisiteatro.fisiteatrosystem.model.dto.EventoDTO;
import com.fisiteatro.fisiteatrosystem.service.AsientoService;
import com.fisiteatro.fisiteatrosystem.service.EventoService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminCrearEventoController implements Initializable {

    @FXML
    private Button agregarEvento_bttnAgregar;

    @FXML
    private Button agregarEvento_bttnCancelar;

    @FXML
    private TextField agregarEvento_txtFieldCapacidad;

    @FXML
    private TextField agregarEvento_txtFieldFecha;

    @FXML
    private TextField agregarEvento_txtFieldHora;

    @FXML
    private TextField agregarEvento_txtFieldId;

    @FXML
    private TextField agregarEvento_txtFieldNombre;

    @FXML
    private TextField agregarEvento_txtFieldPrecio;

    private EventoService eventoService;
    private AsientoService asientoService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setEventoService(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @FXML
    private void crearEvento() throws IOException {
        String nombre = agregarEvento_txtFieldNombre.getText();
        String fecha = agregarEvento_txtFieldFecha.getText();
        String hora = agregarEvento_txtFieldHora.getText();
        int capacidad = Integer.parseInt(agregarEvento_txtFieldCapacidad.getText());
        float precio = Float.parseFloat(agregarEvento_txtFieldPrecio.getText());

        EventoDTO evento = new EventoDTO(0, nombre, fecha, hora, precio, capacidad);
        eventoService.create(evento);

        AsientoService asientoService = new AsientoService(evento.getId());
        asientoService.create(capacidad, evento.getId());

        Stage currentStage = (Stage) agregarEvento_bttnAgregar.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void cancelarCreacion() {
        Stage currentStage = (Stage) agregarEvento_bttnCancelar.getScene().getWindow();
        currentStage.close();
    }
}
