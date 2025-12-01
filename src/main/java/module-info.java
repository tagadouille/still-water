module com.app.main {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;
    requires transitive javafx.graphics;

    requires java.desktop;
    requires javafx.swing;

    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;

    opens com.app.main to javafx.fxml;
    opens com.app.main.controller to javafx.fxml;
    opens com.app.main.controller.menu to javafx.fxml;

    exports com.app.main;
}
