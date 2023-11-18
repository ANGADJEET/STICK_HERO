package com.example.project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameController {
    private Player player;
    private Terrain terrain;

    private int revivalCost; // Cherries required for revival

    public GameController() {
        // Initialization, e.g., create player, hero, and terrain instances
        Player player = new Player();
        Terrain terrain = new Terrain();

    }

    public void startGame(Stage primaryStage) throws IOException {
        // Load the FXML file for the game screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_FILES/StartScreen.fxml"));

        // Load the root layout from the FXML file
        Parent gamePage = loader.load();

        // Set the controller for the FXML file
        GameController gameScreenController = loader.getController();
        // You might have a method to initialize the game screen in your controller.
//        gameScreenController.initializeGame();
        // Create a new scene with the loaded FXML content
        Scene scene = new Scene(gamePage);

        // Set the scene for the primary stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Stick Hero - Game");

        // Show the stage
        primaryStage.show();
    }

    public void restartLevel() {
        // Restart the current level
    }

    public void saveGame() {
        // Save the current game state
    }

    public void loadGame() {
        // Load a previously saved game state
    }

    public void setPrimaryStage(Stage primaryStage) {

    }

    // Methods for game control (start, restart, save, load, etc.)
}
