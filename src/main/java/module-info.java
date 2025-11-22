module com.app.main {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;
    requires javafx.graphics;

    opens com.app.main to javafx.fxml;

    exports com.app.main;
}
