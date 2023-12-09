package com.example.project;
import java.util.*;

public class ScoreSubject {
    private int score;
    private final List<ScoreObserver> observers = new ArrayList<>();

    public void addObserver(ScoreObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ScoreObserver observer) {
        observers.remove(observer);
    }

    public void setScore(int newScore) {
        this.score = newScore;
        notifyObservers();
    }

    private void notifyObservers() {
        for (ScoreObserver observer : observers) {
            observer.updateScore(score);
        }
    }
}
