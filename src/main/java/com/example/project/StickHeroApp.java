package com.example.project;

import javafx.application.Application;
import javafx.stage.Stage;

public class StickHeroApp extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        StickHero stickHero = StickHero.getInstance(stage);
        stickHero.startGame(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
