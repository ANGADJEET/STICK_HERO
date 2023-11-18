package com.example.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StickHero extends Application {
    private SoundManager soundManager = new SoundManager();
    private StartScreen startScreen = new StartScreen();
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML_FILES/StartScreen.fxml"));
        primaryStage.setTitle("Stick Hero");
        primaryStage.setScene(new Scene(root, 607, 302));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}