package com.fisiteatro.fisiteatrosystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
