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
import org.example.sortingalgorithmsvisualization.Controller.ComparisonArray;
import org.example.sortingalgorithmsvisualization.Controller.Controller;
import org.example.sortingalgorithmsvisualization.Model.ComparisonStat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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

    // save stage for later use
    Stage primaryStage ;

    // Cache scenes for later use
    InputScene inputScene = new InputScene("Visualization Mode") ;
    VisualizationView view = new VisualizationView() ;
    ComparisonView comparisonView = new ComparisonView() ;
    StackPane mainView  ;

    // Ref to controller
    Controller controller = new Controller();

    // Array to hold animations
    ArrayList<Timeline> animations = new ArrayList<>() ;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage ;
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        mainView = new StackPane();
        Pane bars = animatedBars(bounds.getWidth(), bounds.getHeight());
        VBox box = buildMainUI();
        mainView.getChildren().addAll(bars, box);
        mainView.setBackground(new Background(new BackgroundFill(BG_TOP,null,null)));
        Scene scene = new Scene(mainView, Color.web("#121212"));
        scene.getStylesheets().add(getClass().getResource("/main-scene.css").toExternalForm()) ;
        stage.setScene(scene);
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        controller.setMainGUI(this);
        this.inputScene.controller = controller ;
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


        Label nameLabel = new Label(modeText);
        nameLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        nameLabel.setTextFill(accent);

        Label descLabel = new Label(desc);
        descLabel.setFont(Font.font("Courier New", 12));
        descLabel.setTextFill(TEXT_SUB);
        descLabel.setLineSpacing(4);
        descLabel.setWrapText(true);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Label buttonLike = new Label(buttonText);
        buttonLike.setFont(Font.font("Courier New", FontWeight.BOLD, 13));
        buttonLike.setTextFill(TEXT_MAIN);
        buttonLike.setPadding(new Insets(6, 14, 6, 14));
        buttonLike.setBackground(new Background(new BackgroundFill(
                accent.deriveColor(0, 1, 1, 0.2),
                new CornerRadii(6), Insets.EMPTY)));

        VBox content = new VBox(14, nameLabel, descLabel, spacer, buttonLike);
        content.setBackground(new Background(new BackgroundFill(BG_BOTTOM,CornerRadii.EMPTY,Insets.EMPTY)));
        content.setBorder(new Border(new BorderStroke(accent,BorderStrokeStyle.SOLID,new CornerRadii(5),new BorderWidths(4, 0, 0, 0))));
        content.setPadding(new Insets(24, 24, 20, 24));
        content.setPrefSize(280, 220);

        StackPane card = new StackPane();
        card.getChildren().addAll( content);


        card.setOnMouseEntered(e -> {
            content.setBackground(new Background(new BackgroundFill(CARD_HOVER,new CornerRadii(5),Insets.EMPTY)));
            card.setCursor(javafx.scene.Cursor.HAND);
            ScaleTransition st = new ScaleTransition(Duration.millis(120), card);
            st.setToX(1.03);
            st.setToY(1.03);
            st.play();
        });

        card.setOnMouseExited(e -> {
            content.setBackground(new Background(new BackgroundFill(CARD_BG,new CornerRadii(5),Insets.EMPTY)));
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
                inputScene.setMode(modeText);
                primaryStage.getScene().setRoot(inputScene);
                inputScene.play();
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


    public void play() {
        animations.forEach(Timeline::play);
    }


    public void pause() {
        animations.forEach(Timeline::pause);
    }

    public void loadVisualization() {
        primaryStage.getScene().setRoot(view);
    }

    public void loadMainView(){
        mainView.setOpacity(1);
        primaryStage.getScene().setRoot(mainView);
        play();
    }

    public void loadComparisonView(){
        inputScene.pause();
        comparisonView.setMainView(this);
        // null state table to avoid keeping old results
        comparisonView.setStatTable(null);
        comparisonView.buildView();
        primaryStage.getScene().setRoot(comparisonView);
    }


    public VisualizationView getView() {
        return view;
    }

    public void setView(VisualizationView view) {
        this.view = view;
    }

    public void loadStatTable(LinkedHashMap<ComparisonArray, List<ComparisonStat>> statTable) {
        comparisonView.setStatTable(statTable);
        comparisonView.buildView();
    }
}
