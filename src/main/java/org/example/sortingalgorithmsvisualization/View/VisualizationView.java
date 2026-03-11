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
import javafx.stage.StageStyle;
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
    private static final Color BAR_COLOR = Color.web("#38bdf8");
    private static final Color COMPARISON_COLOR = Color.web("#ef4444");
    private static final Color SWAP_COLOR = Color.web("#facc15");
    private static final Color SET_COLOR = Color.web("#84cc16");
    private static final Color RANGE_COLOR = Color.web("#9B59B6");
    private static final Color DIVIDE_COLOR = Color.web("#22c55e");
    private static final Color SORTED_COLOR = Color.web("#22c55e");
    private double animationDuration = 512;
    private double pauseDuration = 256 ;
    private int arrayMax;
    private Timeline currentAnimation;
    private VBox menu;
    private Pane overlayPane = new Pane();
    private Label statusLabel;
    private VBox legendBox;
    private String algorithm ;


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
        initializeStatusAndLegend();
        this.getChildren().add(menu);
        this.getChildren().add(overlayPane);
        this.getChildren().add(legendBox);
        this.getChildren().add(statusLabel);
        overlayPane.setMouseTransparent(true);
        // Align the menu to the top right corner
        StackPane.setAlignment(menu, Pos.TOP_RIGHT);
        StackPane.setAlignment(legendBox, Pos.TOP_LEFT);
        StackPane.setMargin(legendBox, new Insets(10, 10, 10, 10));
        StackPane.setAlignment(statusLabel, Pos.TOP_CENTER);
        StackPane.setMargin(statusLabel, new Insets(10, 10, 10, 10));
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
            animationDuration = 512;
        }

    }

    // this menu will allow user to change speed / pause or (reset- future feature) visualization
    private void menuBox() {
        menu = new VBox(10);
        Label algorithmName = new Label();
        algorithmName.setText(algorithm);
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
            if (animationDuration > 5) {
                animationDuration /= 2;
                speedLabel.setText("Speed: " + formatSpeedMultiplier() + "x");
            }
        });
        speedDown.setOnAction(e -> {
            if (animationDuration < 2000) {
                animationDuration *= 2;
                speedLabel.setText("Speed: " + formatSpeedMultiplier() + "x");
            }
        });

        pauseStart.setOnAction(e -> {
            if (currentAnimation == null) {
                return;
            }
            if (currentAnimation.getStatus() == Animation.Status.RUNNING) {
                pause();
                pauseStart.setText("▶");
            } else {
                play();
                pauseStart.setText("⏸");
            }
        });
    }

    private void initializeStatusAndLegend() {
        statusLabel = new Label("Ready to visualize");
        statusLabel.setStyle("""
                -fx-font-family: 'Consolas';
                -fx-font-size: 13px;
                -fx-text-fill: #e2e8f0;
                -fx-background-color: rgba(15,23,42,0.85);
                -fx-background-radius: 8px;
                -fx-padding: 6 10 6 10;
                """);

        legendBox = new VBox(6,
                legendItem(BAR_COLOR, "Default"),
                legendItem(COMPARISON_COLOR, "Compare"),
                legendItem(SWAP_COLOR, "Swap"),
                legendItem(DIVIDE_COLOR, "Divide/Partition"),
                legendItem(SORTED_COLOR, "Sorted"),
                legendItem(RANGE_COLOR, "Merge")
        );
        legendBox.setStyle("""
                -fx-background-color: rgba(15,23,42,0.85);
                -fx-background-radius: 10px;
                -fx-padding: 8 10 8 10;
                """);
        legendBox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        legendBox.setMouseTransparent(true);
    }

    private HBox legendItem(Color color, String text) {
        Region swatch = new Region();
        swatch.setPrefSize(12, 12);
        swatch.setBackground(new Background(
                new BackgroundFill(color, new CornerRadii(6), Insets.EMPTY)
        ));
        Label label = new Label(text);
        label.setStyle("-fx-text-fill: #cbd5e1; -fx-font-size: 12px;");
        HBox row = new HBox(8, swatch, label);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private String formatSpeedMultiplier() {
        double multiplier = 512.0 / animationDuration;
        if (Math.abs(multiplier - Math.rint(multiplier)) < 1e-9) {
            return String.valueOf((int) Math.rint(multiplier));
        }
        return String.format("%.2f", multiplier);
    }

    private void setStatus(String text) {
        if (statusLabel != null) {
            statusLabel.setText(text);
        }
    }


    public void onComparison(ComparisonEvent event, AnimationCallback onFinish) {
        int index1 = event.index1();
        int index2 = event.index2();
        setStatus("Comparing index " + index1 + " and " + index2);

        Timeline tl = new Timeline();
        KeyValue kv1 = new KeyValue(bars[index1].fillProperty(), COMPARISON_COLOR);
        KeyValue kv2 = new KeyValue(bars[index2].fillProperty(), COMPARISON_COLOR);
        tl.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(animationDuration), kv1),
                new KeyFrame(Duration.millis(animationDuration), kv2)
        );

        tl.setOnFinished(e -> {
            PauseTransition pauseTransition = new PauseTransition(Duration.millis(3*animationDuration)) ;
            pauseTransition.play();
            bars[index1].setFill(BAR_COLOR);
            bars[index2].setFill(BAR_COLOR);
            onFinish.run();
        });

        currentAnimation = tl;
        tl.play();
    }


    public void onInterchange(SwapEvent event, AnimationCallback onFinish) {
        int index1 = event.index1();
        int index2 = event.index2();
        setStatus("Swapping index " + index1 + " and " + index2);

        Timeline tl1 = new Timeline();
        KeyValue kv1 = new KeyValue(bars[index1].fillProperty(), SWAP_COLOR);
        KeyValue kv2 = new KeyValue(bars[index2].fillProperty(), SWAP_COLOR);
        KeyValue kvx1 = new KeyValue(bars[index1].xProperty(), bars[index2].getX(), Interpolator.EASE_BOTH);
        KeyValue kvx2 = new KeyValue(bars[index2].xProperty(), bars[index1].getX(), Interpolator.EASE_BOTH);

        tl1.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(animationDuration), kv1, kvx1),
                new KeyFrame(Duration.millis(animationDuration), kv2, kvx2)
        );
        tl1.setOnFinished(e -> {
            PauseTransition pauseTransition = new PauseTransition(Duration.millis(3*animationDuration)) ;
            pauseTransition.play();
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
        setStatus("Setting index " + index + " to value " + (int) val);
        double normalizedVal = val / arrayMax;

        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double height = bounds.getHeight();
        double heightFactor = height - 2 * padding;
        double newBarHeight = normalizedVal * heightFactor;

        Timeline tl = new Timeline();
        KeyValue kvh = new KeyValue(bars[index].heightProperty(), newBarHeight, Interpolator.EASE_BOTH);
        KeyValue kvy = new KeyValue(bars[index].yProperty(), height - newBarHeight, Interpolator.EASE_BOTH);
        KeyValue kvColor = new KeyValue(bars[index].fillProperty(), SET_COLOR);
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(animationDuration), kvh, kvy, kvColor));
        tl.setOnFinished(e -> {
            PauseTransition pauseTransition = new PauseTransition(Duration.millis(3*animationDuration)) ;
            pauseTransition.play();
            bars[index].setFill(BAR_COLOR);
            onFinish.run();
        });
        currentAnimation = tl;
        tl.play();
    }

    public void onDivide(DivideEvent event, AnimationCallback onFinish) {
        setStatus("Dividing around index " + event.index());
        int left = event.index2();
        int right = event.index3();
        Timeline tl = new Timeline();
        int timeShift = 0;
        for (int i = left; i < event.index(); i++) {
            KeyValue kv = new KeyValue(bars[i].fillProperty(), RANGE_COLOR);
            double delay = animationDuration + animationDuration * Math.log1p(timeShift++)+0.05*animationDuration*timeShift;
            KeyFrame frame = new KeyFrame(Duration.millis(delay), kv);
            tl.getKeyFrames().add(frame);
        }
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(animationDuration), new KeyValue(bars[event.index()].fillProperty(), DIVIDE_COLOR)));
        timeShift = 0 ;
        for (int i = event.index() + 1; i <= right; i++) {
            KeyValue kv = new KeyValue(bars[i].fillProperty(), RANGE_COLOR);
            double delay = animationDuration + animationDuration * Math.log1p(timeShift++)+0.05*animationDuration*timeShift;
            KeyFrame frame = new KeyFrame(Duration.millis(delay), kv);
            tl.getKeyFrames().add(frame);
        }
        tl.setOnFinished(e -> {
            PauseTransition pauseTransition = new PauseTransition(Duration.millis(3*animationDuration)) ;
            pauseTransition.play();
            for (Rectangle bar : bars) bar.setFill(BAR_COLOR);
            onFinish.run();
        });

        currentAnimation = tl;
        tl.play();
    }

    public void onMerge(MergeEvent event, AnimationCallback onFinish) {
        setStatus("Merging range [" + event.index1() + ", " + event.index2() + "]");
        int index1 = event.index1();
        int index2 = event.index2();
        int timeShift = 0;
        Timeline tl = new Timeline();
        for (int i = index1; i <= index2; i++) {
            KeyValue kv = new KeyValue(bars[i].fillProperty(), RANGE_COLOR);
            double delay = animationDuration + animationDuration * Math.log1p(timeShift++)+0.15*animationDuration*timeShift;
            KeyFrame frame = new KeyFrame(Duration.millis(delay), kv);
            tl.getKeyFrames().add(frame);

        }

        tl.setOnFinished(e -> {
            PauseTransition pauseTransition = new PauseTransition(Duration.millis(3*animationDuration)) ;
            pauseTransition.play();
            for (Rectangle bar : bars) bar.setFill(BAR_COLOR);
            onFinish.run();
        });

        currentAnimation = tl;
        tl.play();

    }

    private static final Color MERGE_BAR_COLOR    = Color.web("#f59e0b");
    private static final Color MERGE_WINNER_COLOR = Color.web("#34d399");
    private static final Color MERGE_LOSER_COLOR  = Color.web("#f87171");

    public void onMergeComparison(MergeComparisonEvent event, AnimationCallback onFinish) {
        setStatus("Merge compare values " + event.val1() + " and " + event.val2());
        // Currently not implemented
        Timeline tl = new Timeline();
        // Phase 1 show the new bars that one of which will be set
        int val1 = event.val1();
        int val2 = event.val2();
        int index1 = event.index1() ;
        int index2 = event.index2() ;
        // Create the two bars
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double height = bounds.getHeight();
        double width = bounds.getWidth();
        double initialHeight = 50 ;
        gap = Math.min(200.0 / normalizedNums.length, 4);
        double barWidth = (width / normalizedNums.length) - gap;
        double heightFactor = height - 2 * padding;
        double barHeight1 = (double) val1/arrayMax * heightFactor;

        Rectangle bar1 = new Rectangle();
        bar1.setX( index1* (barWidth + gap) + gap / 2.0);
        bar1.setWidth(barWidth);
        bar1.setY(height - initialHeight);
        bar1.setHeight(initialHeight);
        bar1.setArcWidth(12);
        bar1.setArcHeight(12);
        bar1.setFill(MERGE_BAR_COLOR);
        bar1.setOpacity(0.6);

        Rectangle bar2 = new Rectangle();
        double barHeight2 = (double) val2/arrayMax * heightFactor;
        bar2.setX( index2* (barWidth + gap) + gap / 2.0);
        bar2.setWidth(barWidth);
        bar2.setY(height - initialHeight);
        bar2.setHeight(initialHeight);
        bar2.setArcWidth(12);
        bar2.setArcHeight(12);
        bar2.setFill(MERGE_BAR_COLOR);
        bar2.setOpacity(0.5);

        KeyValue kvyb1 = new KeyValue(bar1.yProperty(), height-initialHeight) ;
        KeyValue kvyb2 = new KeyValue(bar2.yProperty(), height-initialHeight) ;
        KeyValue kvyb3 = new KeyValue(bar1.yProperty(), height-barHeight1) ;
        KeyValue kvyb4 = new KeyValue(bar2.yProperty(), height-barHeight2) ;
        KeyValue kvhb1 = new KeyValue(bar1.heightProperty(), initialHeight) ;
        KeyValue kvhb2 = new KeyValue(bar2.heightProperty(), initialHeight) ;
        KeyValue kvhb3 = new KeyValue(bar1.heightProperty(), barHeight1) ;
        KeyValue kvhb4 = new KeyValue(bar2.heightProperty(), barHeight2) ;

        KeyFrame frame1 = new KeyFrame(Duration.ZERO , kvhb1,kvhb2,kvyb1,kvyb2) ;
        KeyFrame frame2 = new KeyFrame(Duration.millis(animationDuration*3) , kvhb3,kvhb4,kvyb3,kvyb4) ;

        tl.getKeyFrames().addAll(frame2,frame1) ;
        // Phase 2 compare the two bars

        Timeline tl1 = new Timeline();
        KeyValue kv1 = new KeyValue(bar1.fillProperty(), COMPARISON_COLOR);
        KeyValue kv2 = new KeyValue(bar2.fillProperty(), COMPARISON_COLOR);
        tl1.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(animationDuration*2), kv1),
                new KeyFrame(Duration.millis(animationDuration*2), kv2)
        );

        tl1.setOnFinished(e -> {
            PauseTransition pauseTransition = new PauseTransition(Duration.millis(3.5*animationDuration)) ;
            overlayPane.getChildren().clear();
            onFinish.run();
        });

        overlayPane.getChildren().addAll(bar1,bar2) ;

        currentAnimation = tl ;
        tl.setOnFinished(e -> {
            currentAnimation = tl1 ;
            tl1.play();
        });
        tl.play();
        // Phase 3 for-future update
        // Show the smaller bar as a green bar
        // also color the index that will be set differently for a better visualization
    }


    public void onPartition(PartitionEvent event, AnimationCallback onFinish) {
        setStatus("Partition: pivot index " + event.index());
        int left = event.left();
        int right = event.right();
        Timeline tl = new Timeline();
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(animationDuration), new KeyValue(bars[event.index()].fillProperty(), DIVIDE_COLOR)));
        int timeShift = 0;
        for (int i = left; i < event.index(); i++) {
            KeyValue kv = new KeyValue(bars[i].fillProperty(), RANGE_COLOR);
            double delay = animationDuration + animationDuration * Math.log1p(timeShift++)+0.05*animationDuration*timeShift;
            KeyFrame frame = new KeyFrame(Duration.millis(delay), kv);
            tl.getKeyFrames().add(frame);
        }
        timeShift = 0 ;
        for (int i = event.index() + 1; i <= right; i++) {
            KeyValue kv = new KeyValue(bars[i].fillProperty(), RANGE_COLOR);
            double delay = animationDuration + animationDuration * Math.log1p(timeShift++)+0.05*animationDuration*timeShift;
            KeyFrame frame = new KeyFrame(Duration.millis(delay), kv);
            tl.getKeyFrames().add(frame);
        }
        tl.setOnFinished(e -> {
            PauseTransition pauseTransition = new PauseTransition(Duration.millis(3.5*animationDuration)) ;
            pauseTransition.play();
            for (Rectangle bar : bars) bar.setFill(BAR_COLOR);
            onFinish.run();
        });

        currentAnimation = tl;
        tl.play();
    }

    public void onSort(SortedEvent event, AnimationCallback onFinish) {
        setStatus("Array sorted successfully");
        int timeShift = 0;
        Timeline tl = new Timeline();
        for (Rectangle bar : bars) {
            KeyValue kv = new KeyValue(bar.fillProperty(), SORTED_COLOR);
            double delay = animationDuration + animationDuration * Math.log1p(timeShift++)+0.15*animationDuration*timeShift;
            KeyFrame frame = new KeyFrame(Duration.millis(delay), kv);
            tl.getKeyFrames().add(frame);
        }

        tl.setOnFinished(e -> {
            finishWithPause(onFinish);
        });

        currentAnimation = tl;
        tl.play();
    }


    public double getAnimationDuration() {
        return animationDuration;
    }

    public void setAnimationDuration(double animationDuration) {
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
        popup.initStyle(StageStyle.UNDECORATED);

        Label header = new Label("Visualization Completed");
        header.setStyle("""
                -fx-font-size: 20px;
                -fx-font-weight: bold;
                -fx-text-fill: #e2e8f0;
                """);

        Label content = new Label("Algorithm Name : " + algoName + "\nComparison Count : " + ComparisonCount + "\nInterchange Count : " + interchangeCount);
        content.setStyle("""
                -fx-font-size: 14px;
                -fx-text-fill: #cbd5e1;
                -fx-line-spacing: 4px;
                """);

        Button closePopUp = new Button("Next");
        closePopUp.setDefaultButton(true);
        String btnBase = """
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                -fx-background-color: #0ea5e9;
                -fx-text-fill: white;
                -fx-background-radius: 10px;
                -fx-padding: 8 20 8 20;
                -fx-cursor: hand;
                """;
        String btnHover = """
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                -fx-background-color: #0284c7;
                -fx-text-fill: white;
                -fx-background-radius: 10px;
                -fx-padding: 8 20 8 20;
                -fx-cursor: hand;
                """;
        closePopUp.setStyle(btnBase);
        closePopUp.setOnMouseEntered(e -> closePopUp.setStyle(btnHover));
        closePopUp.setOnMouseExited(e -> closePopUp.setStyle(btnBase));
        closePopUp.setOnAction(e -> popup.close());

        VBox layoutBox = new VBox(16, header, content, closePopUp);
        layoutBox.setAlignment(Pos.CENTER);
        layoutBox.setPadding(new Insets(24));
        layoutBox.setStyle("""
                -fx-background-color: linear-gradient(to bottom right, #0f172a, #1e293b);
                -fx-background-radius: 14px;
                -fx-border-color: #334155;
                -fx-border-width: 1.2px;
                -fx-border-radius: 14px;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.45), 20, 0.2, 0, 6);
                """);

        Scene scene = new Scene(layoutBox, 460, 240);

        popup.setOnHidden(e -> onSessionFinish.onFinish());
        popup.setScene(scene);
        popup.centerOnScreen();
        popup.show();

    }

    @Override
    public void play() {
        if (currentAnimation != null) {
            currentAnimation.play();
        }
    }

    @Override
    public void pause() {
        if (currentAnimation != null) {
            currentAnimation.pause();
        }
    }

    @Override
    public void stop() {
        if (currentAnimation != null) {
            currentAnimation.stop();
        }
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    private void finishWithPause(AnimationCallback onFinish) {
        PauseTransition pause = new PauseTransition(Duration.millis(animationDuration)) ;
        pause.setOnFinished(e-> onFinish.run());
        pause.play();
    }
}
