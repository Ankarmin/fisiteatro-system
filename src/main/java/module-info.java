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

    opens com.fisiteatro.fisiteatrosystem to javafx.fxml;
    opens com.fisiteatro.fisiteatrosystem.model.dto to com.fasterxml.jackson.databind;

    exports com.fisiteatro.fisiteatrosystem;
}