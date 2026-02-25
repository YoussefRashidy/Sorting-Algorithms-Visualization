package org.example.sortingalgorithmsvisualization.View;

import javafx.animation.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.util.Duration;
import org.example.sortingalgorithmsvisualization.Model.Events.*;

import java.util.Arrays;
import java.util.List;

public class VisualizationView extends Scene {
    private double[] normalizedNums ;
    private Rectangle[] bars ;
    private int gap = 4 ;
    private int padding = 20 ;
    private static final Color BAR_COLOR   = Color.web("#38bdf8");// soft cyan
    private static final Color comparisonColor = Color.RED ;
    private static final Color swapColor = Color.YELLOW ;
    private int animationDuration = 200 ;


    public VisualizationView(Parent parent) {
        super(parent);
    }

    public VisualizationView(Parent parent, double v, double v1) {
        super(parent, v, v1);
    }

    public VisualizationView(Parent parent, Paint paint) {
        super(parent, paint);
    }

    public VisualizationView(Parent parent, double v, double v1, Paint paint) {
        super(parent, v, v1, paint);
    }

    public VisualizationView(Parent parent, double v, double v1, boolean b) {
        super(parent, v, v1, b);
    }

    public VisualizationView(Parent parent, double v, double v1, boolean b, SceneAntialiasing sceneAntialiasing) {
        super(parent, v, v1, b, sceneAntialiasing);
    }


    public double[] getNormalizedNums() {
        return normalizedNums;
    }

    public void setNormalizedNums(int[] nums) {
        int max = Arrays.stream(nums).max().getAsInt() ;

        this.normalizedNums = Arrays.stream(nums.clone()).
                mapToDouble(p -> (double) p/max)
                .toArray() ;
    }

    public void initializeBars() {
        Pane root = new Pane() ;
        bars = new Rectangle[normalizedNums.length] ;
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double height = bounds.getHeight() ;
        double width = bounds.getWidth() ;
        double barWidth = (width/normalizedNums.length) - gap ;
        double heightFactor = height - 2*padding ;
        for (int i = 0 ; i < normalizedNums.length ; i++) {
            double barHeight = normalizedNums[i] * heightFactor ;
            Rectangle bar = new Rectangle() ;
            bar.setX(i*(barWidth+gap)+ gap/2.0);
            bar.setWidth(barWidth);
            bar.setY(height-barHeight-padding);
            bar.setHeight(barHeight);
            bar.setArcWidth(12);
            bar.setArcHeight(12);
            bar.setFill(BAR_COLOR);
            root.getChildren().add(bar) ;
            bars[i] = bar ;
        }
        this.setRoot(root);
    }

    public Timeline onComparison(ComparisonEvent event) {
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
        });

        return tl1;
    }
    public Timeline onInterchange(SwapEvent event) {
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
        });

        return tl1;
    }

    public Timeline onSet(SetEvent event) {
        int index = event.index();
        double val = (double) event.val();
        double max = Arrays.stream(normalizedNums).max().orElse(1);
        double normalizedVal = val / max;

        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double height = bounds.getHeight();
        double heightFactor = height - 2 * padding;
        double newBarHeight = normalizedVal * heightFactor;

        Timeline tl = new Timeline();
        KeyValue kvh = new KeyValue(bars[index].heightProperty(), newBarHeight, Interpolator.EASE_BOTH);
        KeyValue kvy = new KeyValue(bars[index].yProperty(), height - newBarHeight - padding, Interpolator.EASE_BOTH);
        KeyValue kvColor = new KeyValue(bars[index].fillProperty(), Color.LIME);
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(animationDuration), kvh, kvy, kvColor));
        tl.setOnFinished(e -> bars[index].setFill(BAR_COLOR));
        return tl;
    }

    /**
     * Plays a list of SortingEvents sequentially with proper animation timing.
     */
    public void playEvents(List<SortingEvent> events) {
        playEventAt(events, 0);
    }

    private void playEventAt(List<SortingEvent> events, int index) {
        if (index >= events.size()) return;

        SortingEvent event = events.get(index);
        Timeline tl = switch (event) {
            case ComparisonEvent ce -> onComparison(ce);
            case SwapEvent se -> onInterchange(se);
            case SetEvent se -> onSet(se);
            case DivideEvent de -> null;
        };
        if (tl != null) {
            var original = tl.getOnFinished();
            tl.setOnFinished(e -> {
                if (original != null) original.handle(e);
                playEventAt(events, index + 1);
            });
            tl.play();
        } else {
            playEventAt(events, index + 1);
        }
    }

    public int getAnimationDuration() {
        return animationDuration;
    }

    public void setAnimationDuration(int animationDuration) {
        this.animationDuration = animationDuration;
    }

}
