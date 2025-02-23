package com.fisiteatro.fisiteatrosystem.controller;

import com.fisiteatro.fisiteatrosystem.model.dto.ClienteDTO;
import com.fisiteatro.fisiteatrosystem.service.AdministradorService;
import com.fisiteatro.fisiteatrosystem.service.ClienteService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
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

        configurarEventosTeclado();
    }

    public void switchForm(ActionEvent event) {
        panelIniciarSesion.setVisible(event.getSource() == registrar_hyperlink_iniciarSesion);
        panelRegistrar.setVisible(event.getSource() == iniciarSesion_hyperlinkRegistrarse);
    }

    private void configurarServices() {
        clienteService = new ClienteService();
        administradorService = new AdministradorService();
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

    @FXML
    private void iniciarSesion() {
        String nombreUsuario = iniciarSesion_txtFieldNombreUsuario.getText();
        String contrasena = iniciarSesion_mostrarContrasena.isSelected() ? iniciarSesion_txtFieldContrasena.getText() : iniciarSesion_passwordFieldContrasena.getText();

        if (nombreUsuario.isEmpty() || contrasena.isEmpty()) {
            System.out.println("Todos los campos deben estar llenos.");
            return;
        }

        String role = administradorService.iniciarSesion(nombreUsuario, contrasena) ? "admin" : clienteService.iniciarSesion(nombreUsuario, contrasena) ? "user" : null;

        if (role == null) {
            System.out.println("Usuario o contraseña incorrectos.");
            return;
        }

        abrirPanelSegunRol(role);
    }

    private void abrirPanelSegunRol(String role) {

        switch (role) {
            case "admin":
                abrirVentana("Admin", "/com/fisiteatro/fisiteatrosystem/view/fxml/Admin.fxml");
                break;
            case "user":
                abrirVentana("Cliente", "/com/fisiteatro/fisiteatrosystem/view/fxml/User.fxml");
                break;
            default:
                System.out.println("Rol de usuario no reconocido.");
        }
    }

    private void abrirVentana(String titulo, String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = fxmlLoader.load();

            if (Objects.equals(titulo, "Cliente")) {
                ClienteDTO clienteDTO = clienteService.obtenerPorDni(iniciarSesion_txtFieldNombreUsuario.getText());
                UserController userController = fxmlLoader.getController();
                userController.setClienteDTO(clienteDTO);
            }

            Stage stage = (Stage) iniciarSesion_bttnIngresar.getScene().getWindow();
            stage.close();

            Stage nuevaVentana = new Stage();
            nuevaVentana.setTitle(titulo);
            nuevaVentana.setScene(new Scene(root));

            nuevaVentana.setOnCloseRequest(event -> {
                Platform.exit();
                System.exit(0);
            });

            nuevaVentana.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void registrarCliente() throws IOException {
        String nombres = registrar_nombres.getText();
        String apellidos = registrar_apellidos.getText();
        String dni = registrar_dni.getText();
        String contrasena = registrar_mostrarContrasena.isSelected() ? registrar_txtField_contrasena.getText() : registrar_password_contrasena.getText();

        ClienteDTO cliente = new ClienteDTO(nombres, apellidos, dni, contrasena);
        clienteService.create(cliente);
        System.out.println("Registrar cliente");
        mostrarInicioSesion();
    }

    private void configurarEventosTeclado() {
        iniciarSesion_txtFieldNombreUsuario.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                iniciarSesion();
            }
        });

        iniciarSesion_passwordFieldContrasena.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                iniciarSesion();
            }
        });

        iniciarSesion_txtFieldContrasena.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                iniciarSesion();
            }
        });

        registrar_nombres.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    registrarCliente();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        registrar_apellidos.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    registrarCliente();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        registrar_dni.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    registrarCliente();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        registrar_password_contrasena.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    registrarCliente();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void mostrarInicioSesion() {
        panelIniciarSesion.setVisible(true);
        panelRegistrar.setVisible(false);
    }
}
