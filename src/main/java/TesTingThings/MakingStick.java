package TesTingThings;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MakingStick extends Application {
    GraphicsContext gc;

    int initialX = 50;
    int initialY = 50;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Stick Hero Game");
        Parent root = new Pane();
        Canvas canvas = new Canvas(600, 300);
        gc = canvas.getGraphicsContext2D();
        ((Pane) root).getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                interpolate(20);
            }
            protected void interpolate(double frac) {

            }
        }.start();
    }
}
