package TesTingThings;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class RectangleAnimationApp extends Application {

    private Rectangle rectangle;
    private double targetX = 200; // Destination X position
    private double targetY = 200; // Destination Y position

    @Override
    public void start(Stage primaryStage) {
        // Create a root pane
        Pane root = new Pane();

        // Create a rectangle
        rectangle = new Rectangle(50, 30, Color.BLUE);
        rectangle.setTranslateX(100); // Set initial X position
        rectangle.setTranslateY(100); // Set initial Y position

        // Set up an AnimationTimer for the animation
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Update the position of the rectangle in each frame
                updatePosition();

                // Check if the rectangle has reached the destination
                if (rectangle.getTranslateX() >= targetX && rectangle.getTranslateY() >= targetY) {
                    stop(); // Stop the animation when the destination is reached
                }
            }
        };

        // Set up an event handler for the rectangle
        rectangle.setOnMouseClicked(event -> {
            // Start the AnimationTimer when the rectangle is clicked
            animationTimer.start();
        });

        // Add the rectangle to the root pane
        root.getChildren().add(rectangle);

        // Create a scene
        Scene scene = new Scene(root, 400, 300);

        // Set up the stage
        primaryStage.setTitle("Rectangle Animation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updatePosition() {
        // Update the position of the rectangle based on the elapsed time
        double speed = 2.0; // Adjust the speed of the animation
        double deltaX = targetX - rectangle.getTranslateX();
        double deltaY = targetY - rectangle.getTranslateY();
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distance > 0) {
            double directionX = deltaX / distance;
            double directionY = deltaY / distance;

            // Move the rectangle towards the destination
            rectangle.setTranslateX(rectangle.getTranslateX() + directionX * speed);
            rectangle.setTranslateY(rectangle.getTranslateY() + directionY * speed);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
