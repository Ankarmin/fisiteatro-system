package com.fisiteatro.fisiteatrosystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
    private TextField modificarEvento_txtFieldId;

    @FXML
    private TextField modificarEvento_txtFieldNombre;

    @FXML
    private TextField modificarEvento_txtFieldPrecio;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
