package com.fisiteatro.fisiteatrosystem.controller;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iniciarSesion_txtFieldContrasena.setVisible(false);
        registrar_txtField_contrasena.setVisible(false);
    }

    public void switchForm(ActionEvent event) {
        panelIniciarSesion.setVisible(event.getSource() == registrar_hyperlink_iniciarSesion);
        panelRegistrar.setVisible(event.getSource() == iniciarSesion_hyperlinkRegistrarse);
    }

    //para mostrar contraseña al presionar el check box
    @FXML
    private void mostrarContrasena() {
        boolean mostrarIniciarSesion = iniciarSesion_mostrarContrasena.isSelected();
        boolean mostrarRegistrar = registrar_mostrarContrasena.isSelected();
        registrar_txtField_contrasena.setVisible(mostrarRegistrar);
        registrar_password_contrasena.setVisible(!mostrarRegistrar);
        iniciarSesion_txtFieldContrasena.setVisible(mostrarIniciarSesion);
        iniciarSesion_passwordFieldContrasena.setVisible(!mostrarIniciarSesion);
        if (mostrarIniciarSesion) {
            iniciarSesion_txtFieldContrasena.setText(iniciarSesion_passwordFieldContrasena.getText());
        } else {
            iniciarSesion_passwordFieldContrasena.setText(iniciarSesion_txtFieldContrasena.getText());
        }
        if (mostrarRegistrar) {
            registrar_txtField_contrasena.setText(registrar_password_contrasena.getText());
        } else {
            registrar_password_contrasena.setText(registrar_txtField_contrasena.getText());
        }
    }
}
