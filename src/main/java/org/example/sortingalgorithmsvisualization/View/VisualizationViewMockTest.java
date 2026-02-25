package org.example.sortingalgorithmsvisualization.View;

import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.sortingalgorithmsvisualization.Model.Events.SortingEvent;
import org.example.sortingalgorithmsvisualization.Model.SimulationSorting.Observer.EventRecorder;
import org.example.sortingalgorithmsvisualization.Model.SimulationSorting.Observer.SimulationBubbleSort;

import java.util.List;

/**
 * A mock launcher to visually test that VisualizationView correctly
 * animates sorting events (comparisons, swaps, sets).
 *
 * Run this class to open a full-screen window that:
 *   1. Generates a random unsorted array of bars.
 *   2. Sorts it with SimulationBubbleSort, recording all events.
 *   3. Plays back each event as an animation so you can verify
 *      the visualization behaves correctly.
 */
public class VisualizationViewMockTest extends Application {

    private static final int ARRAY_SIZE = 30;
    private static final int ANIMATION_SPEED_MS = 150;

    @Override
    public void start(Stage stage) {

        // ── 1. Build a random unsorted array ────────────────────────────────
        int[] array = generateShuffledArray(ARRAY_SIZE);
        System.out.println("Unsorted array:");
        printArray(array);

        // ── 2. Sort with SimulationBubbleSort & record every event ──────────
        SimulationBubbleSort sorter = new SimulationBubbleSort();
        EventRecorder recorder = new EventRecorder();
        sorter.register(recorder);
        int[] sorted = sorter.sort(array.clone());

        List<SortingEvent> events = recorder.getEventList();
        System.out.println("Total events recorded: " + events.size());
        System.out.println("Sorted array:");
        printArray(sorted);

        // ── 3. Build the VisualizationView with the original array ──────────
        VisualizationView view = new VisualizationView(new Pane());


        // ── 4. Show the stage, then start playing events ────────────────────
        stage.setTitle("Visualization Mock Test - Bubble Sort");
        stage.setScene(view);
        stage.setMaximized(true);
        stage.setOnShown(e -> {
            view.setNormalizedNums(array);
            view.initializeBars();
            view.setAnimationDuration(ANIMATION_SPEED_MS);

            // Small delay so the window renders before animations start
            javafx.animation.PauseTransition pause =
                    new javafx.animation.PauseTransition(javafx.util.Duration.millis(250));
            pause.setOnFinished(evt -> view.playEvents(events));
            pause.play();
        });
        stage.show();
    }

    // ── Helper methods ──────────────────────────────────────────────────────

    /**
     * Creates an array [1..size] and shuffles it (Fisher-Yates).
     */
    private int[] generateShuffledArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) arr[i] = i + 1;
        java.util.Random rng = new java.util.Random();
        for (int i = size - 1; i > 0; i--) {
            int j = rng.nextInt(i + 1);
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        return arr;
    }

    private void printArray(int[] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(", ");
        }
        sb.append("]");
        System.out.println(sb);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
