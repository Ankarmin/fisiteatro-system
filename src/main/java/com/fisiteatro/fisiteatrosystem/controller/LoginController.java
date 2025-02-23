package com.fisiteatro.fisiteatrosystem.controller;

import com.fisiteatro.fisiteatrosystem.service.AdministradorService;
import com.fisiteatro.fisiteatrosystem.service.ClienteService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button iniciarSesion_bttnIngresar;

    @FXML
    private Hyperlink iniciarSesion_hyperlinkRegistrarse;

    @FXML
    private CheckBox iniciarSesion_mostrarContrasena;

    @FXML
    private PasswordField iniciarSesion_passwordFieldContrasena;

    @FXML
    private TextField iniciarSesion_txtFieldContrasena;

    @FXML
    private TextField iniciarSesion_txtFieldNombreUsuario;

    @FXML
    private AnchorPane panelIniciarSesion;

    @FXML
    private AnchorPane panelRegistrar;

    @FXML
    private TextField registrar_apellidos;

    @FXML
    private Button registrar_bttnRegistrar;

    @FXML
    private TextField registrar_dni;

    @FXML
    private Hyperlink registrar_hyperlink_iniciarSesion;

    @FXML
    private CheckBox registrar_mostrarContrasena;

    @FXML
    private TextField registrar_nombres;

    @FXML
    private PasswordField registrar_password_contrasena;

    @FXML
    private TextField registrar_txtField_contrasena;

    private ClienteService clienteService;
    private AdministradorService administradorService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarServices();

        iniciarSesion_txtFieldContrasena.setVisible(false);
        registrar_txtField_contrasena.setVisible(false);
    }

    public void switchForm(ActionEvent event) {
        panelIniciarSesion.setVisible(event.getSource() == registrar_hyperlink_iniciarSesion);
        panelRegistrar.setVisible(event.getSource() == iniciarSesion_hyperlinkRegistrarse);
    }

    @FXML
    private void mostrarContrasena() {
        togglePasswordVisibility(iniciarSesion_mostrarContrasena, iniciarSesion_txtFieldContrasena, iniciarSesion_passwordFieldContrasena);
        togglePasswordVisibility(registrar_mostrarContrasena, registrar_txtField_contrasena, registrar_password_contrasena);
    }

    private void togglePasswordVisibility(CheckBox checkBox, TextField textField, PasswordField passwordField) {
        boolean isVisible = checkBox.isSelected();
        textField.setVisible(isVisible);
        passwordField.setVisible(!isVisible);
        if (isVisible) {
            textField.setText(passwordField.getText());
        } else {
            passwordField.setText(textField.getText());
        }
    }

    private void iniciarSesion() {
        String nombreUsuario = iniciarSesion_txtFieldNombreUsuario.getText();
        String contrasena = iniciarSesion_mostrarContrasena.isSelected() ? iniciarSesion_txtFieldContrasena.getText() : iniciarSesion_passwordFieldContrasena.getText();

        clienteService.iniciarSesion(nombreUsuario, contrasena);
        System.out.println("Iniciar sesion");
    }

    private void configurarServices() {
        clienteService = new ClienteService();
        administradorService = new AdministradorService();
    }
}
