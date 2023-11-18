package TesTingThings;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class StickHeroGame extends Application {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    Pane root;
    Canvas canvas;
    GraphicsContext gc;
    StickHeroCharacter character;
    List<Platform> platforms;
    Cherry cherry;

    int score = 0;
    boolean isRevived = false;
    int cherriesForRevival = 5;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Stick Hero Game");
        root = new Pane();
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load game assets
        Image characterImage = new Image(getClass().getResourceAsStream("/hero.png"));
        Image platformImage = new Image(getClass().getResourceAsStream("/beta.png"));
        Image cherryImage = new Image(getClass().getResourceAsStream("/fruit.png"));

        // Initialize game objects
        character = new StickHeroCharacter(characterImage, WIDTH / 2, HEIGHT - 50);
        platforms = new ArrayList<>();
        platforms.add(new Platform(platformImage, 100, HEIGHT - 150));
        platforms.add(new Platform(platformImage, 300, HEIGHT - 200));
        platforms.add(new Platform(platformImage, 500, HEIGHT - 150));
        cherry = new Cherry(cherryImage, 400, HEIGHT - 220);

        // Game loop
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                gc.clearRect(0, 0, WIDTH, HEIGHT);
                character.renderStick(gc);
                character.update();
                character.render(gc);
                for (Platform platform : platforms) {
                    platform.render(gc);
                }
                cherry.render(gc);

                if (character.isExtending || character.isRetracting) {
                    character.renderStick(gc);
                }

                // Check for collision
                if (character.isOnPlatform(platforms)) {
                    character.landOnPlatform();
                } else {
                    character.fall();
                }

                // Check for cherry collection
                if (cherry.intersects(character)) {
                    score += 10;
                    cherriesForRevival++;
                    cherry.resetPosition();
                }

                // Check for revival
                if (!isRevived && cherriesForRevival >= 5) {
                    character.revive();
                    cherriesForRevival -= 5;
                    isRevived = true;
                }

                // Check for game over
//                if (!character.isAlive()) {
//                    System.out.println("Game Over! Your Score: " + score);
//                    stop();
//                }
            }
        }.start();

        scene.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (!character.isExtending && !character.isRetracting) {
                    character.extendStick();
                }
            }
        });

        scene.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (character.isExtending) {
                    character.retractStick();
                }
            }
        });
    }
}

class StickHeroCharacter {
    Image characterImage;
    boolean isRevived;
    double x;
    double y;
    double stickLength;
    double stickExtension;
    boolean isExtending;
    boolean isRetracting;

    public StickHeroCharacter(Image characterImage, double x, double y) {
        this.characterImage = characterImage;
        this.x = x;
        this.y = y;
        this.stickLength = 0;
        this.stickExtension = 0;
        this.isExtending = false;
        this.isRetracting = false;
    }

    public void update() {
        if (isExtending && stickLength < StickHeroGame.WIDTH) {
            stickLength += 5; // Adjust the speed of stick extension
        } else if (isRetracting && stickLength > 0) {
            stickLength -= 5; // Adjust the speed of stick retraction
        }

        if (stickLength <= 0) {
            // The stick has retracted completely, reset extension state
            isRetracting = false;
            isExtending = false;
        }

        x += 1; // Adjust the character's movement speed
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(characterImage, x, y);
    }

    public void renderStick(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        double stickTopX = x + characterImage.getWidth() / 2 - 1;
        double stickTopY = y + characterImage.getHeight();
        double stickBottomY = stickTopY + stickLength;
        gc.fillRect(stickTopX, stickTopY, 5, stickBottomY); // Draw the stick
    }

    public boolean isOnPlatform(List<Platform> platforms) {
        for (Platform platform : platforms) {
            if (y + characterImage.getHeight() >= platform.getY() &&
                    y + characterImage.getHeight() <= platform.getY() + platform.getHeight() &&
                    x + characterImage.getWidth() / 2 >= platform.getX() &&
                    x + characterImage.getWidth() / 2 <= platform.getX() + platform.getWidth()) {
                return true;
            }
        }
        return false;
    }

    public void landOnPlatform() {
        stickLength = stickExtension;
        isRetracting = false;
    }

    public void fall() {
        if (y + characterImage.getHeight() < StickHeroGame.HEIGHT) {
            y += 5; // Adjust the fall speed
        }
    }

//    public boolean isAlive() {
//        return y + characterImage.getHeight() < StickHeroGame.HEIGHT;
//    }

    public void extendStick() {
        isExtending = true;
        isRetracting = false;
    }

    public void retractStick() {
        isExtending = false;
        isRetracting = true;
    }

    public void resetPosition() {
        x = StickHeroGame.WIDTH / 2;
        y = StickHeroGame.HEIGHT - 50;
        stickLength = 0;
        stickExtension = 0;
        isExtending = false;
        isRetracting = false;
    }

    public void revive() {
        resetPosition();
        isRevived = true;
    }
}

class Platform {
    Image platformImage;
    double x;
    double y;
    double width;
    double height;

    public Platform(Image platformImage, double x, double y) {
        this.platformImage = platformImage;
        this.x = x;
        this.y = y;
        this.width = platformImage.getWidth();
        this.height = platformImage.getHeight();
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(platformImage, x, y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}

class Cherry {
    Image cherryImage;
    double x;
    double y;
    double width;
    double height;

    public Cherry(Image cherryImage, double x, double y) {
        this.cherryImage = cherryImage;
        this.x = x;
        this.y = y;
        this.width = cherryImage.getWidth();
        this.height = cherryImage.getHeight();
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(cherryImage, x, y);
    }

    public boolean intersects(StickHeroCharacter character) {
        double characterX = character.x + character.characterImage.getWidth() / 2;
        double characterY = character.y + character.characterImage.getHeight();

        return characterX >= x && characterX <= x + width &&
                characterY >= y && characterY <= y + height;
    }

    public void resetPosition() {
        x = Math.random() * (StickHeroGame.WIDTH - cherryImage.getWidth());
        y = Math.random() * (StickHeroGame.HEIGHT - cherryImage.getHeight());
    }
}
