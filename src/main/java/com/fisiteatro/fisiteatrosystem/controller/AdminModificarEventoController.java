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

public class AdminModificarEventoController implements Initializable {

    @FXML
    private Button modificarEvento_bttnAgregar;

    @FXML
    private Button modificarEvento_bttnCancelar;

    @FXML
    private TextField modificarEvento_txtFieldCapacidad;

    @FXML
    private TextField modificarEvento_txtFieldFecha;

    @FXML
    private TextField modificarEvento_txtFieldHora;

    @FXML
    private TextField modificarEvento_txtFieldNombre;

    @FXML
    private TextField modificarEvento_txtFieldPrecio;

    private EventoService eventoService;

    private int id;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setEventoService(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    public void setEventoDTO(EventoDTO evento) {
        id = evento.getId();
        modificarEvento_txtFieldNombre.setText(evento.getNombre());
        modificarEvento_txtFieldFecha.setText(evento.getFecha());
        modificarEvento_txtFieldHora.setText(evento.getHora());
        modificarEvento_txtFieldPrecio.setText(String.valueOf(evento.getPrecio()));
        modificarEvento_txtFieldCapacidad.setText(String.valueOf(evento.getCapacidad()));
    }

    @FXML
    private void modificarEvento() throws IOException {
        String nombre = modificarEvento_txtFieldNombre.getText();
        String fecha = modificarEvento_txtFieldFecha.getText();
        String hora = modificarEvento_txtFieldHora.getText();
        int nuevaCapacidad = Integer.parseInt(modificarEvento_txtFieldCapacidad.getText());
        float precio = Float.parseFloat(modificarEvento_txtFieldPrecio.getText());

        EventoDTO evento = new EventoDTO(id, nombre, fecha, hora, precio, nuevaCapacidad);
        EventoDTO eventoActual = eventoService.getById(id);

        if (eventoActual != null) {
            eventoActual.setCapacidad(nuevaCapacidad);
            eventoService.update(evento);

            AsientoService asientoService = new AsientoService(evento.getId());
            asientoService.create(nuevaCapacidad, evento.getId());
        }

        Stage currentStage = (Stage) modificarEvento_bttnAgregar.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void cancelarModificacion() {
        Stage currentStage = (Stage) modificarEvento_bttnCancelar.getScene().getWindow();
        currentStage.close();
    }
}
