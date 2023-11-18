package com.example.project;
import javafx.application.Application;
import javafx.stage.Stage;

public class StickHero extends Application {
    private StartScreen startMenu = new StartScreen();
    private AnimationManager animationManager = new AnimationManager();
    @Override
    public void start(Stage stage) {
        //I have to load the fxml file which handles the UI
        startMenu.startNewGame();
    }

    public static void main(String[] args) {
        launch();
    }
}
