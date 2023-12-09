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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.IOException;

public class StartScreen {
    @FXML
    private Text scoreText = new Text("0"); // Initialize with "0" or any default value
    @FXML
    private Text CherryCount = new Text();
    AnimationTimer timer;
    private Stage stage;
    private GraphicsContext gc;
    private Canvas canvas;
    private Rectangle stick;
    private Rotate stickRotate;
    private Pane gamePage;
    private int score = 0;
    private boolean isExtending = false;
    private boolean isRotating = false;
    private boolean isGenerated = false;
    private boolean isAtEnd = false;
    private boolean isAlive = true;
    private boolean isFlipped = false;
    private double extensionSpeed = 3;
    private double rotationSpeed = 1;
    private int heroX = 115;
    private int heroY = 232;

    @FXML
    public void start_new_game(MouseEvent mouseEvent) throws IOException {
        initializeGame();
        setupMouseEvents();
        setupAnimationTimer();
    }

    private void initializeGame() throws IOException {
        gamePage = FXMLLoader.load(getClass().getResource("/FXML_FILES/GamePlayWinter.fxml"));
        initializeStick();
        initializeCanvas();
        gamePage.getChildren().addAll(canvas, stick);
        stage = new Stage();
        stage.setTitle("Stick Hero Game");
        stage.setScene(new Scene(gamePage));
        stage.show();
    }

    private void spawnGame(GraphicsContext gc) throws IOException {
        timer.stop();
        clearCanvas();
        gamePage = FXMLLoader.load(getClass().getResource("/FXML_FILES/GamePlayWinter.fxml"));
        initializeStick();
        initializeCanvas();
        gamePage.getChildren().addAll(canvas, stick);
        // Set the CherryCount text
        stage.setScene(new Scene(gamePage));
        stage.show();
        setupMouseEvents();
        setupAnimationTimer();
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
        if(!isGenerated) {
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
        else{
            gamePage.setOnMousePressed(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    isFlipped = true;
                    heroY = 245;
                }
            });

            gamePage.setOnMouseReleased(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    isFlipped = false;
                    heroY = 232;
                }
            });
        }

        }

    private void setupAnimationTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                try {
                    updateGame();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        timer.start();
    }

    private void updateGame() throws IOException {
        clearCanvas();
        renderPlatform();
        renderPlayer();
        handleStickAnimation();
        // Render the score in each frame
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 50, 50);

        gc.strokeText("Cherry" + score, 292, 56);
    }

    private void clearCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void renderPlatform() {
        Platform platform = new Platform(new Image(getClass().getResourceAsStream("/beta.png")), 0, 0);
        platform.render(gc);
    }

    public void renderCherry(GraphicsContext gc, double xPos) {
        gc.drawImage(new Image(getClass().getResourceAsStream("/fruit.png")), xPos, 270);
    }
    private void renderPlayer() throws IOException {
        Player player = new Player();
        if (isGenerated && heroX < stick.getHeight() + 120) {
            heroX += 1;
            double cherryXpos = 115 + 250;
            renderCherry(gc, cherryXpos);
            player.renderHero(gc, heroX, heroY,true, stick.getHeight());
        } else {
            if(heroX!=115) {
                isAtEnd = true;
                System.out.println(player.checkIsAlive(heroX));
                player.renderHero(gc, heroX, heroY, false, stick.getHeight());
                if(player.checkIsAlive(heroX)){
                    heroX = 115;
                    isAtEnd = false;
                    isGenerated = false;
                    isRotating = false;
                    isExtending = false;
                    isAlive = true;
                    score++;
                    System.out.println(score);
                    String scoreString = Integer.toString(score);
                    scoreText.setText(scoreString);
                    CherryCount.setText(scoreString);
                    spawnGame(gc);
                    return;
                }
                else{
                    System.out.println("Game Over");
                    System.out.println(score);
                    timer.stop();
                    return;
                }
            }
            if(heroX==115){
                player.renderHero(gc, heroX, heroY, true, stick.getHeight());
            }
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
