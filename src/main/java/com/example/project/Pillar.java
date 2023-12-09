package com.example.project;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class Pillar implements Rectangle {
    private double xPos;
    private double width;
    private double height;
    private Image platformImage;
    private String[] pillars = {"/beta.png","/gamma.png","/alpha.png","/beta.png"};
    public Pillar(Image platformImage, double x, double y) {
        this.platformImage = platformImage;
        this.width = x;
        this.height = y;
    }
    public void render(GraphicsContext gc, int index) {
        double x = 100;
        double y = 260;
        gc.drawImage(platformImage, x, y);
        gc.drawImage(new Image(getClass().getResourceAsStream(pillars[index])), x + 400, y);
    }
    @Override
    public double getWidth() {
        return width;
    }
    @Override
    public double getHeight() {
        return height;
    }
}