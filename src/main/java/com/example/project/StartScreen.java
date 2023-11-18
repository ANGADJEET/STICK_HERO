package com.example.project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartScreen {
    Stage primaryStage = new Stage();

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void displayMenu() {
        // Display the main menu options
    }

    public void startNewGame() {
        // Start a new game
        GameController gameController = new GameController();
        gameController.startGame();
        //load the fxml file which handles the starting of the game
        loadGameStartScreen();
    }

    private void loadGameStartScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StartScreen.fxml"));
            Parent root = loader.load();
            // Create a scene using the loaded FXML file
            Scene gameStartScene = new Scene(root, 608, 300);
            // Set the scene on the primary stage
            primaryStage.setScene(gameStartScene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadGame() {
        // Load a previously saved game
    }

    // Other menu-related methods
}