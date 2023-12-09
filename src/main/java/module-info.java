module com.example.project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires javafx.media;
    requires java.desktop;
    requires org.junit.jupiter.api;
    opens com.example.project to javafx.fxml;
    exports com.example.project;
}