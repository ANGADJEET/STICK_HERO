package com.example.project;

import javafx.scene.image.Image;

public class Hero {
    private Image image;
    private double xPos;
    private Stick stick;

    public void setImage(final Image image) {
        this.image = image;
    }

    public double getxPos() {
        return this.xPos;
    }

    public void setxPos(final double xPos) {
        this.xPos = xPos;
    }

    public Stick getStick() {
        return this.stick;
    }

    public void setStick(final Stick stick) {
        this.stick = stick;
    }

    public Hero(double initialXPos) {
        this.xPos = initialXPos;
    }

    public void moveForward() {
        this.xPos += 1;
    }

    public Image getImage() {
        return new Image(getClass().getResourceAsStream("/hero.png"));
    }
    // Other hero-related attributes and methods
}