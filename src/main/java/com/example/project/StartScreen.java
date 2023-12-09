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
    private int cherryCount = 0;
    private boolean isExtending = false;
    private boolean isRotating = false;
    private boolean isTaken = false;
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
                    System.out.println("Flipped");
                }
            });

            gamePage.setOnMouseReleased(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    isFlipped = false;
                    System.out.println("Not Flipped");
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
        setupMouseEvents();
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
            movePlayerAndCheckCollision(player);
        } else {
            handleEndConditions(player);
        }
    }

    private void movePlayerAndCheckCollision(Player player) {
        heroX += 1;

        double cherryXpos = ;
        renderCherry(gc, cherryXpos);

        if (isFlipped && !isTaken) {
            renderFlippedPlayerAndCheckCherry(player, cherryXpos);
        } else if (isFlipped) {
            renderFlippedPlayerAndCheckPlatformCollision(player);
        } else {
            player.renderHero(gc, heroX, isFlipped ? heroY + 20 : heroY, true, stick.getHeight());
        }
    }

    private void renderFlippedPlayerAndCheckCherry(Player player, double cherryXpos) {
        player.renderHero(gc, heroX, heroY + 20, true, stick.getHeight());

        if (heroX > cherryXpos - 20 && heroX < cherryXpos + 20) {
            handleCherryTaken();
        }
    }

    private void renderFlippedPlayerAndCheckPlatformCollision(Player player) {
        player.renderHero(gc, heroX, heroY + 20, true, stick.getHeight());

        if (heroX <= 125 && heroX >= 400) {
            handleGameOverDueToPlatform();
        }
    }

    private void handleCherryTaken() {
        cherryCount++;
        isTaken = true;
        System.out.println("Cherry - " + cherryCount);
    }

    private void handleGameOverDueToPlatform() {
        System.out.println("Game Over, due to Platform");
        System.out.println(score);
        timer.stop();
    }

    private void handleEndConditions(Player player) throws IOException {
        if (heroX != 115) {
            handleEndGame(player);
        } else {
            player.renderHero(gc, heroX, heroY, true, stick.getHeight());
        }
    }

    private void handleEndGame(Player player) throws IOException {
        isAtEnd = true;
        System.out.println(player.checkIsAlive(heroX));
        player.renderHero(gc, heroX, heroY, false, stick.getHeight());

        if (player.checkIsAlive(heroX) && !isFlipped) {
            handleNextLevel();
        } else if (isFlipped) {
            handleGameOverDueToFlippedPlatform();
        } else {
            handleGameOver();
        }
    }

    private void handleNextLevel() throws IOException {
        heroX = 115;
        isAtEnd = false;
        isGenerated = false;
        isRotating = false;
        isExtending = false;
        isTaken = false;
        isFlipped = false;
        isAlive = true;
        score++;
        System.out.println(score);
        updateScore();
        spawnGame(gc);
    }

    private void handleGameOverDueToFlippedPlatform() {
        System.out.println("Game Over due to flipped Platform");
        System.out.println(score);
        timer.stop();
    }

    private void handleGameOver() {
        System.out.println("Game Over");
        System.out.println(score);
        timer.stop();
    }

    private void updateScore() {
        String scoreString = Integer.toString(score);
        scoreText.setText(scoreString);
        CherryCount.setText(scoreString);
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
