package com.example.project;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class StartScreenTest {

    private StartScreen startScreen;

    @Test
    void handleCherryTaken() {
        startScreen.handleCherryTaken();
        assertTrue(startScreen.isTaken);
    }

    @Test
    void handleGameOverDueToPlatform() {
        assertDoesNotThrow(() -> startScreen.handleGameOverDueToPlatform());
    }

    @Test
    void handleNextLevel() {
        assertDoesNotThrow(() -> startScreen.handleNextLevel());
    }

    @Test
    void handleGameOverDueToFlippedPlatform() {
        assertDoesNotThrow(() -> startScreen.handleGameOverDueToFlippedPlatform());
    }

    @Test
    void handleGameOver() {
        assertDoesNotThrow(() -> startScreen.handleGameOver());
    }

    @Test
    void updateScore() {
        assertDoesNotThrow(() -> startScreen.updateScore());
    }

    @Test
    void handleStickAnimation() {
        assertDoesNotThrow(() -> startScreen.handleStickAnimation());
    }

    @Test
    void renderStick() {
        assertDoesNotThrow(() -> startScreen.renderStick());
    }
}