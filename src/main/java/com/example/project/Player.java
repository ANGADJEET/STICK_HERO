package com.example.project;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;

public class Player {
    private Hero hero;  // Non-static instance of Hero
    private Rectangle stick;
    private int score;
    private int cherries;
    private boolean isFlipped;
    private boolean isDead;
    private boolean isExtending;
    private boolean isRetracting;
    private boolean isWalking;
    private double yPos = 232;
    private double stickEndpoint;

    public Player() {
        this.hero = new Hero(115);
        this.stick = new Rectangle();
        this.score = 0;
        this.cherries = 0;
        this.isFlipped = false;
        this.isDead = false;
        this.isExtending = false;
        this.isRetracting = false;
        this.isWalking = false;
    }


    public boolean checkIsAlive(double xpos) {
        if (xpos > 540 || xpos < 460) {
            System.out.println("Player is dead");
            return false;
        }
        else{
            System.out.println("Player is alive");
            return true;
        }
    }

    public boolean renderHero(GraphicsContext gc, double xPos, double yPosn, boolean isAlive, double stickEndpoint) {
        gc.drawImage(hero.getImage(), xPos, yPosn);
        return false;
    }

}
