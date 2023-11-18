module com.example.project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires javafx.media;

    opens com.example.project to javafx.fxml;
    exports TesTingThings;
    exports com.example.project;
}