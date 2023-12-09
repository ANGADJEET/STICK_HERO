package com.example.project;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;


public class Platform implements Rectangle, Collider {
    private double xPos;
    private double width;
    private double height;
    private Image platformImage;

    public Platform(Image platformImage, double x, double y) {
        this.platformImage = platformImage;
        this.width = x;
        this.height = y;
    }
    public void render(GraphicsContext gc) {
        double x = 100;
        double y = 260;
        gc.drawImage(platformImage, x, y);
        gc.drawImage(new Image(getClass().getResourceAsStream("/gamma.png")), x + 400, y);
    }
    @Override
    public double getWidth() {
        return width;
    }
    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public boolean checkCollision(Collider other) {
        // Collision logic with another collider (e.g., hero or stick)
        return false;
    }
}