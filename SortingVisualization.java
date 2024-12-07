import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class SortingVisualization extends Application {

    private static final int NUM_BARS = 50;
    private static final int MAX_HEIGHT = 400;
    private static final int BAR_WIDTH = 10;
    private static final int[] array = new int[NUM_BARS];
    private static final Rectangle[] bars = new Rectangle[NUM_BARS];

    public static void main(String[] args) {
        generateRandomArray();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        for (int i = 0; i < NUM_BARS; i++) {
            bars[i] = new Rectangle(i * BAR_WIDTH, MAX_HEIGHT - array[i], BAR_WIDTH - 1, array[i]);
            bars[i].setFill(Color.BLUE);
            root.getChildren().add(bars[i]);
        }

        Scene scene = new Scene(root, NUM_BARS * BAR_WIDTH, MAX_HEIGHT);
        primaryStage.setTitle("Sorting Visualization");
        primaryStage.setScene(scene);
        primaryStage.show();

        bubbleSortWithVisualization();
    }

    private static void generateRandomArray() {
        Random random = new Random();
        for (int i = 0; i < NUM_BARS; i++) {
            array[i] = random.nextInt(MAX_HEIGHT);
        }
    }

    private void bubbleSortWithVisualization() {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        int n = array.length;
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < n - 1; i++) {
                if (array[i] > array[i + 1]) {
                    swapped = true;
                    swapBars(i, i + 1, timeline);
                }
            }
            n--;
        } while (swapped);

        timeline.play();
    }

    private void swapBars(int i, int j, Timeline timeline) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;

        KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.1),
                new KeyValue(bars[i].heightProperty(), array[i]),
                new KeyValue(bars[i].yProperty(), MAX_HEIGHT - array[i]),
                new KeyValue(bars[j].heightProperty(), array[j]),
                new KeyValue(bars[j].yProperty(), MAX_HEIGHT - array[j]));
        timeline.getKeyFrames().add(keyFrame1);

        Rectangle tempBar = bars[i];
        bars[i] = bars[j];
        bars[j] = tempBar;
    }
}
