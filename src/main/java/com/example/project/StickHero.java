package com.example.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StickHero{
    private SoundManager soundManager = new SoundManager();
    private StartScreen startScreen = new StartScreen();
    static StickHero stickHero = null;
    private StickHero() throws IOException {}
    public static StickHero getInstance(Stage stage) throws IOException {
        if (stickHero == null){
            stickHero = new StickHero();
        }
        return stickHero;
    }
    public void startGame(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML_FILES/StartScreen.fxml"));
        primaryStage.setTitle("Stick Hero");
        primaryStage.setScene(new Scene(root, 607, 302));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}