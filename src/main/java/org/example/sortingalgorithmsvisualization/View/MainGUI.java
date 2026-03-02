package org.example.sortingalgorithmsvisualization.View;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class MainGUI extends Application {
    private static final Color BG_TOP      = Color.web("#0f172a");  // deep navy
    private static final Color BG_BOTTOM   = Color.web("#1e293b");  // slightly lighter

    private static final Color BAR_COLOR   = Color.web("#38bdf8");  // soft cyan
    private static final Color BAR_GLOW    = Color.web("#0ea5e9");

    private static final Color ACCENT_VIS  = Color.web("#22d3ee");  // bright cyan
    private static final Color ACCENT_CMP  = Color.web("#f472b6");  // neon pink

    private static final Color TEXT_MAIN   = Color.web("#f1f5f9");  // near white
    private static final Color TEXT_SUB    = Color.web("#94a3b8");  // cool gray

    private static final Color CARD_BG     = Color.web("#1e293b");
    private static final Color CARD_HOVER  = Color.web("#334155");

    // Cache scenes for later use
    InputScene inputScene = new InputScene("Visualization") ;

    // Array to hold animations
    ArrayList<Timeline> animations = new ArrayList<>() ;

    @Override
    public void start(Stage stage) throws Exception {
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        StackPane root = new StackPane();
        Pane bars = animatedBars(bounds.getWidth(), bounds.getHeight());
        VBox box = buildMainUI();
        root.getChildren().addAll(bars, box);
        root.setBackground(new Background(new BackgroundFill(BG_TOP,null,null)));
        Scene scene = new Scene(root, Color.web("#121212"));
        scene.getStylesheets().add(getClass().getResource("/main-scene.css").toExternalForm()) ;
        stage.setScene(scene);
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());

        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private Pane animatedBars(double width, double height) {
        Pane pane = new Pane();
        int numBars = 50;
        double gap = 4;
        double barW = width / numBars - gap;
        Random rng = new Random();
        for (int i = 0; i < numBars; i++) {
            Rectangle bar = new Rectangle();
            double x = (barW + gap) * i + gap / 2;
            bar.setX(x);
            bar.setWidth(barW);

            double startHeight = 50 + rng.nextDouble() * 80;
            double maxHeight = rng.nextDouble() * height;

            bar.setHeight(startHeight);
            bar.setY(height - startHeight);

            bar.setArcWidth(12);
            bar.setArcHeight(12);

            bar.setFill(BAR_COLOR);

            // Animation for bars
            Timeline tl = new Timeline();
            double dur = 1.5 + rng.nextDouble() * 2.5;
            KeyValue kvh1 = new KeyValue(bar.heightProperty(), startHeight, Interpolator.EASE_BOTH);
            KeyValue kvh2 = new KeyValue(bar.heightProperty(), maxHeight, Interpolator.EASE_BOTH);
            KeyValue kvy1 = new KeyValue(bar.yProperty(), height - startHeight, Interpolator.EASE_BOTH);
            KeyValue kvy2 = new KeyValue(bar.yProperty(), height - maxHeight, Interpolator.EASE_BOTH);

            KeyFrame frame1 = new KeyFrame(Duration.ZERO, kvh1, kvy1);
            KeyFrame frame2 = new KeyFrame(Duration.seconds(dur), kvh2, kvy2);
            tl.getKeyFrames().addAll(frame1, frame2);

            tl.setAutoReverse(true);
            tl.setCycleCount(Animation.INDEFINITE);
            tl.setDelay(Duration.seconds(3 * rng.nextDouble()));
            tl.play();
            animations.add(tl) ;
            pane.getChildren().add(bar);

        }
        return pane;
    }

    private VBox buildMainUI() {
        VBox box = new VBox(20);

        Label title = new Label("Sorting Visualizer");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 42));
        title.setTextFill(TEXT_MAIN);

        Label subTitle = new Label("Choose a mode to begin");
        subTitle.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
        subTitle.setTextFill(TEXT_SUB);

        StackPane visualizationCard = buildOptionCard(
                "Visualization Mode",
                "Watch the sorting algorithm animate step by step.",
                "Start Visualization",
                ACCENT_VIS
        );

        StackPane comparisonCard = buildOptionCard(
                "Comparison Mode",
                "Compare two algorithms side by side and see operations.",
                "Start Comparison",
                ACCENT_CMP
        );

        HBox hBox = new HBox() ;
        hBox.getChildren().addAll(visualizationCard,comparisonCard) ;
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(40);
        hBox.setPadding(new Insets(20, 0, 0, 0));

        box.getChildren().addAll(title, subTitle,hBox);
        box.setAlignment(Pos.CENTER);


        return box;
    }

    private StackPane buildOptionCard(String modeText, String desc, String buttonText, Color accent) {

        Rectangle bg = new Rectangle(280, 220);
        bg.setFill(CARD_BG);
        bg.setArcWidth(12);
        bg.setArcHeight(12);
        // Thin colored top border via a second small rectangle
        Rectangle topBorder = new Rectangle(280, 4);
        topBorder.setFill(accent);
        topBorder.setArcWidth(12);
        topBorder.setArcHeight(12);

        // ── Text content ────────────────────────────────────────────────────
        Label nameLabel = new Label(modeText);
        nameLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        nameLabel.setTextFill(accent);

        Label descLabel = new Label(desc);
        descLabel.setFont(Font.font("Courier New", 12));
        descLabel.setTextFill(TEXT_SUB);
        descLabel.setLineSpacing(4);

        // Spacer to push CTA to bottom
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Label cta = new Label(buttonText);
        cta.setFont(Font.font("Courier New", FontWeight.BOLD, 13));
        cta.setTextFill(TEXT_MAIN);
        cta.setPadding(new Insets(6, 14, 6, 14));
        cta.setBackground(new Background(new BackgroundFill(
                accent.deriveColor(0, 1, 1, 0.2),
                new CornerRadii(6), Insets.EMPTY)));

        VBox content = new VBox(14, nameLabel, descLabel, spacer, cta);
        content.setPadding(new Insets(24, 24, 20, 24));
        content.setPrefSize(280, 220);

        StackPane card = new StackPane();
        card.getChildren().addAll(bg, content);

        // Put the colored border on top-center
        StackPane.setAlignment(topBorder, Pos.TOP_CENTER);
        card.getChildren().add(topBorder);

        // ── Hover & click interactions ──────────────────────────────────────
        card.setOnMouseEntered(e -> {
            bg.setFill(CARD_HOVER);
            card.setCursor(javafx.scene.Cursor.HAND);
            // Subtle scale up
            ScaleTransition st = new ScaleTransition(Duration.millis(120), card);
            st.setToX(1.03);
            st.setToY(1.03);
            st.play();
        });

        card.setOnMouseExited(e -> {
            bg.setFill(CARD_BG);
            ScaleTransition st = new ScaleTransition(Duration.millis(120), card);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        card.setOnMousePressed(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(80), card);
            st.setToX(0.97);
            st.setToY(0.97);
            st.play();
        });

        card.setOnMouseReleased(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(80), card);
            st.setToX(1.03);
            st.setToY(1.03);
            st.play();
        });

        card.setOnMousePressed(e -> {
            Stage primaryStage = (Stage) card.getScene().getWindow();

            // pause animations
            pause();
            // Fade out current scene
            FadeTransition fadeOut = new FadeTransition(Duration.millis(400), card.getScene().getRoot());
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(event -> {
                primaryStage.getScene().setRoot(inputScene);
                // Fade in new scene
                FadeTransition fadeIn = new FadeTransition(Duration.millis(400), inputScene);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });
            fadeOut.play();
        });


        return card;
    }
    private void transitionToScene(Stage stage, Scene nextScene) {
        Parent currentRoot = stage.getScene().getRoot();

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), currentRoot);
        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(300), currentRoot);

        fadeOut.setToValue(0.0);
        scaleOut.setToX(0.95);
        scaleOut.setToY(0.95);

        ParallelTransition exit = new ParallelTransition(fadeOut, scaleOut);
        exit.setOnFinished(e -> {
            stage.setScene(nextScene);
            stage.setFullScreen(true);

            Parent nextRoot = nextScene.getRoot();
            nextRoot.setOpacity(0);
            nextRoot.setScaleX(0.95);
            nextRoot.setScaleY(0.95);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), nextRoot);
            ScaleTransition scaleIn = new ScaleTransition(Duration.millis(300), nextRoot);
            fadeIn.setToValue(1.0);
            scaleIn.setToX(1.0);
            scaleIn.setToY(1.0);

            new ParallelTransition(fadeIn, scaleIn).play();
        });
        exit.play();
    }


    public void play() {
        animations.forEach(Timeline::play);
    }


    public void pause() {
        animations.forEach(Timeline::pause);
    }


}
