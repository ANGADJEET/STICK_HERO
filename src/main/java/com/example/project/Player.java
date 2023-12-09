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

    public void generate_stick() {
        // Implementation for generating stick
    }

    public boolean checkIsAlive(double xpos) {
        if (xpos > stickEndpoint && xpos < 480) {
            System.out.println("player is dead");
            return false;
        }
        if (xpos < stickEndpoint && xpos > 480) {
            System.out.println("player is dead");
            return false;
        }
        return true;
    }

    public boolean renderHero(GraphicsContext gc, double xPos, boolean isAlive, double stickEndpoint) {
        if (isAlive) {
            isAlive = checkIsAlive(xPos);
            System.out.println(this.stickEndpoint);  // Use 'this' to refer to the class variable
            System.out.println(xPos);
            this.stickEndpoint = stickEndpoint;  // Use 'this' to refer to the class variable
            gc.drawImage(hero.getImage(), xPos, 232);
        } else {
            yPos += 1;
            System.out.println("xpos in" + xPos);
            gc.drawImage(hero.getImage(), xPos, yPos);
            if (xPos > 480 && xPos < 520) {
                System.out.println("reached second");
                return true;  // Corrected return value
            } else if (xPos > 520) {
                System.out.println("reached end");
                return false;
            }
        }
        return false;
    }
}
