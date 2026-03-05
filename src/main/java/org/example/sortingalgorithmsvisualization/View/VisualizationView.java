package org.example.sortingalgorithmsvisualization.View;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.sortingalgorithmsvisualization.Model.AnimationCallback;
import org.example.sortingalgorithmsvisualization.Model.Events.*;
import org.example.sortingalgorithmsvisualization.Model.SessionCallback;
import javafx.scene.control.Button;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class VisualizationView extends StackPane implements Animatable {
    private double[] normalizedNums;
    private Rectangle[] bars;
    private double gap = 4;
    private int padding = 20;
    private static final Color BAR_COLOR = Color.web("#38bdf8");// soft cyan
    private static final Color comparisonColor = Color.RED;
    private static final Color swapColor = Color.YELLOW;
    private int animationDuration = 200;
    private int arrayMax;
    private Timeline currentAnimation;
    private VBox menu;


    public double[] getNormalizedNums() {
        return normalizedNums;
    }

    public void setNormalizedNums(int[] nums) {
        arrayMax = Arrays.stream(nums).max().getAsInt();

        this.normalizedNums = Arrays.stream(nums.clone()).
                mapToDouble(p -> (double) p / arrayMax)
                .toArray();
    }

    public void initializeView() {
        initializeBars();
        menuBox();
        this.getChildren().add(menu);
        // Align the menu to the top right corner
        StackPane.setAlignment(menu, Pos.TOP_RIGHT);
        // set the size to preferred size otherwise stack pane will stretch it
        menu.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        StackPane.setMargin(menu, new Insets(15, 15, 0, 0));
        menu.setOpacity(0.10);
        menu.setPadding(new Insets(20, 20, 20, 20));
        menu.setOnMouseEntered(e -> {
            menu.setOpacity(1);
        });
        menu.setOnMouseExited(e -> {
            menu.setOpacity(0.1);
        });

        // style the menu
        menu.setStyle("""
                -fx-background-color: rgba(15,23,42,0.9);
                -fx-background-radius: 12px;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 20, 0.3, 0, 4)
                """);


    }

    public void initializeBars() {
        Pane root = new Pane();
        resetView();
        bars = new Rectangle[normalizedNums.length];
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double height = bounds.getHeight();
        double width = bounds.getWidth();
        gap = Math.min(200.0 / normalizedNums.length, 4);
        double barWidth = (width / normalizedNums.length) - gap;
        double heightFactor = height - 2 * padding;
        for (int i = 0; i < normalizedNums.length; i++) {
            double barHeight = normalizedNums[i] * heightFactor;
            Rectangle bar = new Rectangle();
            bar.setX(i * (barWidth + gap) + gap / 2.0);
            bar.setWidth(barWidth);
            bar.setY(height - barHeight);
            bar.setHeight(barHeight);
            bar.setArcWidth(12);
            bar.setArcHeight(12);
            bar.setFill(BAR_COLOR);
            root.getChildren().add(bar);
            bars[i] = bar;
        }
        this.getChildren().add(root);
    }

    public void resetView() {
        if (bars != null) {
            this.getChildren().clear();
            animationDuration = 200;
        }

    }

    // this menu will allow user to change speed / pause or (reset- future feature) visualization
    private void menuBox() {
        menu = new VBox(10);
        Label algorithmName = new Label();
        algorithmName.setText("Coming soon");
        Label controls = new Label("⚙ Controls");
        HBox buttons = new HBox(12);
        Button speedUp = new Button();
        speedUp.setText("⏩");
        Button speedDown = new Button();
        speedDown.setText("⏪");
        Button pauseStart = new Button();
        pauseStart.setText("⏸");
        Label speedLabel = new Label("Speed: 1x");
        buttons.getChildren().addAll(speedDown, speedUp, pauseStart);
        menu.getChildren().addAll(algorithmName, controls, buttons, speedLabel);
        // Style label
        algorithmName.setStyle("""
                    -fx-font-size: 16px;
                    -fx-text-fill: #38bdf8;
                    -fx-padding: 6 12 6 12;
                    -fx-cursor: hand;
                    -fx-border-radius: 8px;
                    -fx-border-width: 1;
                """);
        controls.setStyle("""
                    -fx-font-size: 16px;
                    -fx-text-fill: #38bdf8;
                    -fx-padding: 6 12 6 12;
                    -fx-cursor: hand;
                    -fx-border-radius: 8px;
                    -fx-border-width: 1;
                """);
        // Style buttons
        speedUp.setStyle("""
                    -fx-font-size: 16px;
                    -fx-background-color: #1e3a5f;
                    -fx-text-fill: #38bdf8;
                    -fx-background-radius: 8px;
                    -fx-padding: 6 12 6 12;
                    -fx-cursor: hand;
                    -fx-border-color: #334155;
                    -fx-border-radius: 8px;
                    -fx-border-width: 1;
                """);
        speedDown.setStyle("""
                    -fx-font-size: 16px;
                    -fx-background-color: #1e3a5f;
                    -fx-text-fill: #38bdf8;
                    -fx-background-radius: 8px;
                    -fx-padding: 6 12 6 12;
                    -fx-cursor: hand;
                    -fx-border-color: #334155;
                    -fx-border-radius: 8px;
                    -fx-border-width: 1;
                """);
        pauseStart.setStyle("""
                    -fx-font-size: 16px;
                    -fx-background-color: #1e3a5f;
                    -fx-text-fill: #38bdf8;
                    -fx-background-radius: 8px;
                    -fx-padding: 6 12 6 12;
                    -fx-cursor: hand;
                    -fx-border-color: #334155;
                    -fx-border-radius: 8px;
                    -fx-border-width: 1;
                """);
        speedLabel.setStyle("""
                    -fx-font-family: 'Courier New';
                    -fx-font-size: 12px;
                    -fx-text-fill: #64748b;
                """);

        speedUp.setOnAction(e -> {
            if (animationDuration > 10) {
                animationDuration /= 2;
                speedLabel.setText("Speed: " + (200 / animationDuration) + "x");
            }
        });
        speedDown.setOnAction(e -> {
            if (animationDuration < 1000) {
                switch (animationDuration) {
                    case 200 -> animationDuration = 400;
                    case 400 -> animationDuration = 800;
                    case 800 -> animationDuration = 1000;
                }
                speedLabel.setText("Speed: 0." + (200.0 / animationDuration * 10) + "x");
            }
        });

        pauseStart.setOnAction(e -> {
            if (currentAnimation.getStatus() == Animation.Status.RUNNING) {
                pause();
                pauseStart.setText("▶");
            } else {
                play();
                pauseStart.setText("⏸");
            }
        });
    }


    public void onComparison(ComparisonEvent event, AnimationCallback onFinish) {
        int index1 = event.index1();
        int index2 = event.index2();

        Timeline tl1 = new Timeline();
        KeyValue kv1 = new KeyValue(bars[index1].fillProperty(), comparisonColor);
        KeyValue kv2 = new KeyValue(bars[index2].fillProperty(), comparisonColor);
        tl1.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(animationDuration), kv1),
                new KeyFrame(Duration.millis(animationDuration), kv2)
        );
        tl1.setOnFinished(e -> {
            bars[index1].setFill(BAR_COLOR);
            bars[index2].setFill(BAR_COLOR);
            onFinish.run();
        });
        currentAnimation = tl1;

        tl1.play();
    }

    public void onInterchange(SwapEvent event, AnimationCallback onFinish) {
        int index1 = event.index1();
        int index2 = event.index2();

        Timeline tl1 = new Timeline();
        KeyValue kv1 = new KeyValue(bars[index1].fillProperty(), swapColor);
        KeyValue kv2 = new KeyValue(bars[index2].fillProperty(), swapColor);
        KeyValue kvx1 = new KeyValue(bars[index1].xProperty(), bars[index2].getX(), Interpolator.EASE_BOTH);
        KeyValue kvx2 = new KeyValue(bars[index2].xProperty(), bars[index1].getX(), Interpolator.EASE_BOTH);

        tl1.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(animationDuration), kv1, kvx1),
                new KeyFrame(Duration.millis(animationDuration), kv2, kvx2)
        );
        tl1.setOnFinished(e -> {
            bars[index1].setFill(BAR_COLOR);
            bars[index2].setFill(BAR_COLOR);
            Rectangle temp = bars[index1];
            bars[index1] = bars[index2];
            bars[index2] = temp;
            onFinish.run();
        });
        currentAnimation = tl1;

        tl1.play();
    }

    public void onSet(SetEvent event, AnimationCallback onFinish) {
        int index = event.index();
        double val = event.val();
        double normalizedVal = val / arrayMax;

        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double height = bounds.getHeight();
        double heightFactor = height - 2 * padding;
        double newBarHeight = normalizedVal * heightFactor;

        Timeline tl = new Timeline();
        KeyValue kvh = new KeyValue(bars[index].heightProperty(), newBarHeight, Interpolator.EASE_BOTH);
        KeyValue kvy = new KeyValue(bars[index].yProperty(), height - newBarHeight, Interpolator.EASE_BOTH);
        KeyValue kvColor = new KeyValue(bars[index].fillProperty(), Color.LIME);
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(animationDuration), kvh, kvy, kvColor));
        tl.setOnFinished(e -> {
            bars[index].setFill(BAR_COLOR);
            onFinish.run();
        });
        currentAnimation = tl;
        tl.play();
    }

    public void onDivide(DivideEvent event, AnimationCallback onFinish) {
        int left = event.index2();
        int right = event.index3();
        Timeline tl = new Timeline();
        int timeShift = 0;
        for (int i = left; i < event.index(); i++) {
            KeyValue kv = new KeyValue(bars[i].fillProperty(), Color.web("#9B59B6"));
            double delay = animationDuration + animationDuration * Math.log1p(timeShift++);
            KeyFrame frame = new KeyFrame(Duration.millis(delay), kv);
            tl.getKeyFrames().add(frame);
        }
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(animationDuration), new KeyValue(bars[event.index()].fillProperty(), Color.GOLD)));
        for (int i = event.index() + 1; i <= right; i++) {
            KeyValue kv = new KeyValue(bars[i].fillProperty(), Color.web("#9B59B6"));
            double delay = animationDuration + animationDuration * Math.log1p(timeShift++);
            KeyFrame frame = new KeyFrame(Duration.millis(delay), kv);
            tl.getKeyFrames().add(frame);
        }
        tl.setOnFinished(e -> {
            for (Rectangle bar : bars) bar.setFill(BAR_COLOR);
            onFinish.run();
        });

        currentAnimation = tl;
        tl.play();
    }

    public void onMerge(MergeEvent event, AnimationCallback onFinish) {
        int index1 = event.index1();
        int index2 = event.index2();
        int timeShift = 0;
        Timeline tl = new Timeline();
        for (int i = index1; i <= index2; i++) {
            KeyValue kv = new KeyValue(bars[i].fillProperty(), Color.web("#9B59B6"));
            double delay = animationDuration + animationDuration * Math.log1p(timeShift++);
            KeyFrame frame = new KeyFrame(Duration.millis(delay), kv);
            tl.getKeyFrames().add(frame);

        }

        tl.setOnFinished(e -> {
            for (Rectangle bar : bars) bar.setFill(BAR_COLOR);
            onFinish.run();
        });

        currentAnimation = tl;
        tl.play();

    }

    public void onMergeComparison(MergeComparisonEvent event, AnimationCallback onFinish) {
        // Currently not implemented
        Timeline tl = new Timeline() ;
        tl.setOnFinished(e -> onFinish.run());
        tl.play();
    }


    public void onPartition(PartitionEvent event, AnimationCallback onFinish) {
        int left = event.left();
        int right = event.right();
        Timeline tl = new Timeline();
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(animationDuration), new KeyValue(bars[event.index()].fillProperty(), Color.GOLD)));
        int timeShift = 0;
        for (int i = left; i < event.index(); i++) {
            KeyValue kv = new KeyValue(bars[i].fillProperty(), Color.web("#9B59B6"));
            double delay = animationDuration + animationDuration * Math.log1p(timeShift++);
            KeyFrame frame = new KeyFrame(Duration.millis(delay), kv);
            tl.getKeyFrames().add(frame);
        }
        for (int i = event.index() + 1; i <= right; i++) {
            KeyValue kv = new KeyValue(bars[i].fillProperty(), Color.web("#9B59B6"));
            double delay = animationDuration + animationDuration * Math.log1p(timeShift++);
            KeyFrame frame = new KeyFrame(Duration.millis(delay), kv);
            tl.getKeyFrames().add(frame);
        }
        tl.setOnFinished(e -> {
            for (Rectangle bar : bars) bar.setFill(BAR_COLOR);
            onFinish.run();
        });

        currentAnimation = tl;
        tl.play();
    }

    public void onSort(SortedEvent event, AnimationCallback onFinish) {
        int timeShift = 0;
        Timeline tl = new Timeline();
        for (Rectangle bar : bars) {
            KeyValue kv = new KeyValue(bar.fillProperty(), Color.GREEN);
            double delay = animationDuration + animationDuration * Math.log1p(timeShift++);
            KeyFrame frame = new KeyFrame(Duration.millis(delay), kv);
            tl.getKeyFrames().add(frame);
        }

        tl.setOnFinished(e -> {
            onFinish.run();
        });

        currentAnimation = tl;
        tl.play();
    }


    public int getAnimationDuration() {
        return animationDuration;
    }

    public void setAnimationDuration(int animationDuration) {
        this.animationDuration = animationDuration;
    }

    // Create simple alert for visualization info
    public void finishAlert(String algoName, int interchangeCount, int ComparisonCount, SessionCallback onSessionFinish) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Visualization Statistics");
        alert.setHeaderText("Sorting completed");
        alert.setContentText("Algorithm Name : " + algoName + "\nComparison Count : " + ComparisonCount + "\nInterchange Count : " + interchangeCount);
        alert.setOnHidden(e -> {
            // call manager to proceed with other arrays,algorithms,etc
            onSessionFinish.onFinish();
        });
        alert.show();

    }

    //TODO style the pop-up
    public void finishPopUp(String algoName, int interchangeCount, int ComparisonCount, SessionCallback onSessionFinish) {
        Stage popup = new Stage();
        popup.setTitle("Visualization Statistics");
        popup.initOwner(this.getScene().getWindow());
        popup.initModality(Modality.APPLICATION_MODAL);
        Label header = new Label("Visualization Completed");
        Label Content = new Label("Algorithm Name : " + algoName + "\nComparison Count : " + ComparisonCount + "\nInterchange Count : " + interchangeCount);
        Button closePopUp = new Button("Next");
        closePopUp.setOnAction(e -> {
            popup.close();
        });
        VBox layoutBox = new VBox(10, header, Content, closePopUp);
        layoutBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layoutBox, 400, 200);
        popup.setOnHidden(e -> {
            onSessionFinish.onFinish();
        });
        popup.setScene(scene);
        popup.show();

    }

    @Override
    public void play() {
        currentAnimation.play();
    }

    @Override
    public void pause() {
        currentAnimation.pause();
    }

    @Override
    public void stop() {
        currentAnimation.stop();
    }
}