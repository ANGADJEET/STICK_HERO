package com.example.project;

import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
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
import javafx.scene.transform.Rotate; // Import Rotate
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class StartScreen {

    private Stage stage;
    private boolean is_at_end = false;
    private GraphicsContext gc;
    private Canvas canvas;
    private Rectangle stick = new Rectangle(5, 0, Color.BLACK);
    private Rotate stickRotate = new Rotate(0,0,260);
    private boolean isExtending = false;
    private boolean isRotating = false;
    private double extensionSpeed = 2.0;
    private double rotationSpeed = 2.0;
    private boolean is_generated = false;
    public boolean isAlive = true;
    private int heroX = 115;

    @FXML
    public void start_new_game(MouseEvent mouseEvent) throws IOException {
        Pane gamePage = FXMLLoader.load(getClass().getResource("/FXML_FILES/GamePlayWinter.fxml"));
        stick.setLayoutX(148);
        stick.setLayoutY(260);
        canvas = new Canvas(768, 483);
        gc = canvas.getGraphicsContext2D();
        //stick.getTransforms().add(stickRotate);
        gamePage.getChildren().addAll(canvas, stick);

        // Set the pivot at the center of the stick's base
//        stickRotate.setPivotX(0);
//        stickRotate.setPivotY(260);

        stage = new Stage();
        stage.setTitle("Stick Hero Game");
        stage.setScene(new Scene(gamePage));
        stage.show();

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
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                Platform platform = new Platform(new Image(getClass().getResourceAsStream("/beta.png")), 0, 0);
                platform.render(gc);
                Player player = new Player();
                if(is_generated && heroX < stick.getHeight()+120){
                    heroX += 1;
                    player.renderHero(gc, heroX,true,stick.getHeight());
                }
                else{
//                  is_generated = true;
                    is_at_end = true;
                    player.renderHero(gc, heroX,false,stick.getHeight());
                }
                if (isExtending) {
                    extendStick();
                }
                if (isRotating) {
                    rotate();
                }
                gc.setFill(Color.BLACK);
                gc.save();
                gc.translate(148, 260);
                gc.rotate(stickRotate.getAngle());
                gc.fillRect(-5, -stick.getHeight(), 5, stick.getHeight());
                gc.restore();
            }
        }.start();
    }
    private void extendStick() {
        if(!is_generated) {
            double currentHeight = stick.getHeight();
            double newHeight = currentHeight + extensionSpeed;
            double maxHeight = 200;
            stick.setHeight(newHeight);
        }
    }
        public void rotate(){
            double currentAngle = stickRotate.getAngle();
            double newAngle = currentAngle + rotationSpeed;
            double maxAngle = 90;
            if (newAngle <= maxAngle) {
                stickRotate.setAngle(newAngle);
                is_generated = true;
            }
            else {
                isRotating = false;
            }
        }
}
