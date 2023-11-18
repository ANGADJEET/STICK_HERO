package TesTingThings;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class StickHeroExtension extends Application {

    private Rectangle stick;
    private boolean extending = false;
    private double extensionSpeed = 2.0; // Adjust the extension speed

    @Override
    public void start(Stage primaryStage) {
        // Create a root pane
        Pane root = new Pane();
        stick = new Rectangle(10, 30, Color.BROWN);
        stick.setTranslateX(100);
        stick.setTranslateY(150);

        // Set up an event handler for mouse press
        root.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                extending = true;
            }
        });

        // Set up an event handler for mouse release
        root.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                extending = false;
            }
        });

        // Add the rectangle to the root pane
        root.getChildren().add(stick);

        // Create a scene
        Scene scene = new Scene(root, 400, 300);

        // Set up the stage
        primaryStage.setTitle("Stick Hero Extension");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Set up AnimationTimer for extension animation
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (extending) {
                    extendStick();
                }
            }
        };
        animationTimer.start();
    }

    private void extendStick() {
        // Increase the height and adjust the position of the rectangle
        double currentHeight = stick.getHeight();
        double newHeight = currentHeight + extensionSpeed;

        // Limit the maximum height (adjust as needed)
        double maxHeight = 100.0;
        if (newHeight <= maxHeight) {
            stick.setHeight(newHeight);
            stick.setTranslateY(stick.getTranslateY() - extensionSpeed);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
