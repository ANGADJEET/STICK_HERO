package com.example.project;

import java.io.*;

public class GameValues implements Serializable {
    private int score;
    private int cherryCount;

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCherryCount() {
        return this.cherryCount;
    }

    public void setCherryCount(int cherryCount) {
        this.cherryCount = cherryCount;
    }
}