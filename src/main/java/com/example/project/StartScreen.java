package com.example.project;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class StartScreen {

    private Stage stage;
    private GraphicsContext gc;
    private Canvas canvas;
    private Rectangle stick;
    private Rotate stickRotate;
    private double extensionSpeed = 5;
    private double rotationSpeed = 2;
    private boolean isExtending = false;
    private boolean isRotating = false;
    private boolean isGenerated = false;
    private boolean isAtEnd = false;
    private boolean isAlive = true;
    private int heroX = 115;

    private int xposSeconD = 500;
    private int yposSeconD = 260;
    private boolean reacheSecPlatform = false;

    @FXML
    public void start_new_game(MouseEvent mouseEvent) throws IOException {
        initializeGame();
        setupMouseEvents();
        setupAnimationTimer();
    }

    private void initializeGame() throws IOException {
        Pane gamePage = FXMLLoader.load(getClass().getResource("/FXML_FILES/GamePlayWinter.fxml"));
        initializeStick();
        initializeCanvas();
        gamePage.getChildren().addAll(canvas, stick);

        stage = new Stage();
        stage.setTitle("Stick Hero Game");
        stage.setScene(new Scene(gamePage));
        stage.show();
    }

    private void initializeStick() {
        stick = new Rectangle(5, 0, Color.BLACK);
        stickRotate = new Rotate(0, 0, 260);
    }

    private void initializeCanvas() {
        stick.setLayoutX(148);
        stick.setLayoutY(260);
        canvas = new Canvas(768, 483);
        gc = canvas.getGraphicsContext2D();
    }

    private void setupMouseEvents() {
        Pane gamePage = (Pane) stage.getScene().getRoot();
        gamePage.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                isExtending = true;
                isRotating = false;
                extendStick();
            }
        });

        gamePage.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                isExtending = false;
                isRotating = true;
            }
        });
    }

    private void setupAnimationTimer() {
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                updateGame();
            }
        }.start();
    }

    private void updateGame() {
        clearCanvas();
        if(reacheSecPlatform) {
            renderAgain();
            reacheSecPlatform = false;
        }
        else {
            renderPlatform();
            renderPlayer();
            handleStickAnimation();
        }
    }
    private void renderFall(){
        if(!isAlive){
            System.out.println("render fall");
            Player player = new Player();
            player.renderHero(gc, heroX, false, stick.getHeight());
        }
    }
    private void renderAgain(){
        System.out.println("render again");
        String[] imageNames = {"alpha.png", "beta.png", "gamma.png"};

        Random random = new Random();
        int randomIndex = random.nextInt(imageNames.length);

        // Get the randomly selected image name
        String randomImageName = imageNames[randomIndex];
        // Generate a random index to select a random image name

        // Create the Platform object with the randomly selected image
        Platform platform = new Platform(new Image(getClass().getResourceAsStream("/" + randomImageName)), 0, 0);
        platform.render(gc);
        Player player = new Player();
        reacheSecPlatform = player.renderHero(gc, 115, true, 260);
    }
    private void clearCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
    private void renderPlatform() {
        Platform platform = new Platform(new Image(getClass().getResourceAsStream("/beta.png")), 0, 0);
        platform.render(gc);
    }
    private void renderPlayer() {
        Player player = new Player();
        if (isGenerated && heroX < stick.getHeight() + 120) {
            heroX += 1;
            player.renderHero(gc, heroX, true, stick.getHeight());
        } else {
            isAtEnd = true;
            player.renderHero(gc, heroX, false, stick.getHeight());
        }
    }

    private void handleStickAnimation() {
        if (isExtending) {
            extendStick();
        }
        if (isRotating) {
            rotate();
        }
        renderStick();
    }

    private void extendStick() {
        if (!isGenerated) {
            double currentHeight = stick.getHeight();
            double newHeight = currentHeight + extensionSpeed;
            double maxHeight = 500;
            stick.setHeight(Math.min(newHeight, maxHeight));
        }
    }

    private void rotate() {
        double currentAngle = stickRotate.getAngle();
        double newAngle = currentAngle + rotationSpeed;
        double maxAngle = 90;
        if (newAngle <= maxAngle) {
            stickRotate.setAngle(newAngle);
            isGenerated = true;
        } else {
            isRotating = false;
        }
    }

    private void renderStick() {
        gc.setFill(Color.BLACK);
        gc.save();
        gc.translate(148, 260);
        gc.rotate(stickRotate.getAngle());
        gc.fillRect(-5, -stick.getHeight(), 5, stick.getHeight());
        gc.restore();
    }
}
