package com.example.project;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;

public class Player {
    private static Hero hero;

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
    private double xpos;
    public Player(){
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
    public void generate_stick(){
    }
    public boolean checkIsAlive() {
        if(xpos>stickEndpoint){
            System.out.println("player is dead");
            return false;
        }
        return true;
    }
    public void renderHero(GraphicsContext gc, double xPos, boolean isAlive, double stickEndpoint){
        if(isAlive) {
            isAlive = checkIsAlive();
            System.out.println(stickEndpoint);
            System.out.println(xPos);
            stickEndpoint = stickEndpoint;
            gc.drawImage(hero.getImage(), xPos, 232);
        }
        else{
            yPos += 1;
            System.out.println("xpos in" + xPos);
            gc.drawImage(hero.getImage(), xPos, yPos);
        }
    }
}
