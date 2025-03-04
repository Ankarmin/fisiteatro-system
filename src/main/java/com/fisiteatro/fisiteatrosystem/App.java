package com.fisiteatro.fisiteatrosystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fisiteatro/fisiteatrosystem/view/fxml/Login.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Fisiteatro");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
