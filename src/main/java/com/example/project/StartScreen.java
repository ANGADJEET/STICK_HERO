package com.example.project;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
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

import java.io.*;
import java.util.Random;

public class StartScreen implements ScoreObserver {
    @FXML
    private Text scoreText = new Text("0"); // Initialize with "0" or any default value
    @FXML
    private Text CherryCount = new Text();
    AnimationTimer timer;
    private Stage stage;
    private ScoreSubject scoreSubject = new ScoreSubject();
    private GraphicsContext gc;
    private Canvas canvas;
    private Rectangle stick;
    private Rotate stickRotate;
    private Pane gamePage;
    private static int score = 0;
    private static int cherryCount = 0;
    private boolean isExtending = false;
    private boolean isRotating = false;
    boolean isTaken = false;
    private boolean isGenerated = false;
    private boolean isAtEnd = false;
    private boolean isAlive = true;
    private boolean isFlipped = false;
    private double extensionSpeed = 5;
    private double rotationSpeed = 10;
    private int heroX = 115;
    private HighScoreCalculator highScoreCalculator = new HighScoreCalculator();
    private int highScore = highScoreCalculator.calculateHighScore();
    private int index = 0;
    private int heroY = 232;

    private Pane endPage;

    RandomCherryGenerator gen = RandomCherryGenerator.getInstance();
    private double cherryPos = gen.generateCherry();

    static GameValues gameValues = new GameValues();
    public StartScreen() throws IOException {
        super();
        scoreSubject.addObserver(this);
    }

    public void serialize() throws IOException {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream("./src/StoredGame.txt"));
            gameValues.setScore(score);
            gameValues.setCherryCount(cherryCount);
            out.writeObject(gameValues);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        } finally {
            out.close();
        }
    }

    public static void deserialize() throws IOException, ClassNotFoundException {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream (new FileInputStream("./src/StoredGame.txt"));
            gameValues = (GameValues) in.readObject();
            score = gameValues.getScore();
            System.out.println(score);
            cherryCount = gameValues.getCherryCount();
            System.out.println(cherryCount);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            in.close();
        }
    }


    @FXML
    public void start_new_game(MouseEvent mouseEvent) throws IOException {
        initializeGame();
        setupMouseEvents();
        scoreSubject.setScore(0);
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
        if (timer != null) {
            timer.stop();
        }

        clearCanvas();
        gamePage = FXMLLoader.load(getClass().getResource("/FXML_FILES/GamePlayWinter.fxml"));
        initializeStick();
        initializeCanvas();
        gamePage.getChildren().addAll(canvas, stick);
        stage.setScene(new Scene(gamePage));
        stage.show();
        setupMouseEvents();
        setupAnimationTimer();
    }

    public void loadSavedGame(MouseEvent mouseEvent)throws IOException, ClassNotFoundException{
        deserialize();
        initializeGame();
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
        gamePage.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.S) {
                try {
                    System.out.println("clicked on save");
                    serialize();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        if (!isGenerated) {
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
        } else {
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
        renderPillar();
        renderPlayer();
        handleStickAnimation();
        setupMouseEvents();
        // Render the score in each frame
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 75, 70);
        gc.fillText("Cherry: " + cherryCount, 255, 70);
        gc.fillText("High: " + highScore, 450, 70);
    }

    private void clearCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void renderPillar() {
        Pillar platform = new Pillar(new Image(getClass().getResourceAsStream("/beta.png")), 0, 0);
        platform.render(gc, index);
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

    private void movePlayerAndCheckCollision(Player player) throws IOException {
        heroX += 3;
        double cherryXpos = cherryPos;
        renderCherry(gc, cherryXpos);
        if (isFlipped && !isTaken) {
            renderFlippedPlayerAndCheckCherry(player, cherryXpos);
        } else if (isFlipped) {
            renderFlippedPlayerAndCheckPillarCollision(player);
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

    private void renderFlippedPlayerAndCheckPillarCollision(Player player) throws IOException {
        player.renderHero(gc, heroX, heroY + 20, true, stick.getHeight());

        if (heroX <= 125 && heroX >= 400) {
            handleGameOverDueToPillar();
        }
    }

    void handleCherryTaken() {
        cherryCount++;
        isTaken = true;
        System.out.println("Cherry - " + cherryCount);
    }
    void handleGameOverDueToPillar() throws IOException {
        System.out.println("Game Over, due to Pillar");
        highScoreCalculator.addScore(score);
        System.out.println(score);
        loadEndPage();
        //timer.stop();
    }
    private void handleEndConditions(Player player) throws IOException {
        if (heroX != 115) {
            handleEndGame(player);
        } else {
            player.renderHero(gc, heroX, heroY, true, stick.getHeight());
        }
    }
    public void HandleRevive(MouseEvent mouseEvent) throws IOException {
        if (allowRevive()) {
            cherryCount -= 2;
            initializeGame();
            setupMouseEvents();
            setupAnimationTimer();
        }
        else{
            System.out.println("Not enough Cherry");
            System.exit(0);
            System.out.println("Exit");
        }
    }

    public void HandleExit(MouseEvent mouseEvent) throws IOException {
        //do a system exit
        System.exit(0);
        System.out.println("Exit");
    }

    private void handleEndGame(Player player) throws IOException {
        isAtEnd = true;
        System.out.println(player.checkIsAlive(heroX));
        player.renderHero(gc, heroX, heroY, false, stick.getHeight());
        if (isFlipped) {
            handleGameOverDueToFlippedPillar();
        } else if (player.checkIsAlive(heroX) && !isFlipped) {
            handleNextLevel();
        } else {
            handleGameOver();
        }
    }

    public void loadEndPage() throws IOException {
        endPage = FXMLLoader.load(getClass().getResource("/FXML_FILES/GameOverWinter.fxml"));
        Stage endStage = new Stage();
        endStage.setTitle("Game Over");
        endStage.setScene(new Scene(endPage));
        endStage.show();
    }

    void handleNextLevel() throws IOException {
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
        Random random = new Random();
        index = random.nextInt(4);
        System.out.println("Random number between 0 and 3: " + index);
        updateScore();
        spawnGame(gc);
    }

    public boolean allowRevive() {
        return cherryCount >= 0;
    }


    void handleGameOverDueToFlippedPillar() throws IOException {
        System.out.println("Game Over due to flipped Pillar");
        loadEndPage();
        highScoreCalculator.addScore(score);
        System.out.println(score);
        timer.stop();
    }

    void handleGameOver() throws IOException {
        System.out.println("Game Over");
        loadEndPage();
        highScoreCalculator.addScore(score);
        System.out.println(score);
        timer.stop();
    }

    void updateScore() {
        String scoreString = Integer.toString(score);
        scoreText.setText(scoreString);
        CherryCount.setText(scoreString);
    }

    void handleStickAnimation() {
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
        double newAngle = currentAngle + (rotationSpeed);
        double maxAngle = 90;
        if (newAngle <= maxAngle) {
            stickRotate.setAngle(newAngle);
            isGenerated = true;
        } else {
            isRotating = false;
        }
    }

    void renderStick() {
        gc.setFill(Color.BLACK);
        gc.save();
        gc.translate(148, 260);
        gc.rotate(stickRotate.getAngle());
        gc.fillRect(-5, -stick.getHeight(), 5, stick.getHeight());
        gc.restore();
    }

    @Override
    public void updateScore(int newScore) {

    }
}
