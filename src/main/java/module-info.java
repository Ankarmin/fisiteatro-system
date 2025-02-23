module com.fisiteatro.fisiteatrosystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.fasterxml.jackson.databind;

    opens com.fisiteatro.fisiteatrosystem to javafx.graphics;
    opens com.fisiteatro.fisiteatrosystem.view.fxml to javafx.fxml;
    opens com.fisiteatro.fisiteatrosystem.controller to javafx.fxml;
    opens com.fisiteatro.fisiteatrosystem.util to javafx.fxml;
    opens com.fisiteatro.fisiteatrosystem.model.dao to com.fasterxml.jackson.databind;
    opens com.fisiteatro.fisiteatrosystem.model.dto to javafx.base, com.fasterxml.jackson.databind;

    exports com.fisiteatro.fisiteatrosystem;
    exports com.fisiteatro.fisiteatrosystem.controller;
}