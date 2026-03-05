package org.example.sortingalgorithmsvisualization.View;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.util.Duration;
import org.example.sortingalgorithmsvisualization.Controller.CSVWriter;
import org.example.sortingalgorithmsvisualization.Controller.ComparisonArray;
import org.example.sortingalgorithmsvisualization.Model.ComparisonStat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//TODO implement return to main menu button
//TODO Style the tool box and button
public class ComparisonView extends VBox implements Animatable {
    private LinkedHashMap<ComparisonArray, List<ComparisonStat>> statTable;
    private TableView<ComparisonStat> table;
    private HBox toolBar = new HBox(10);
    private MainGUI mainView ;
    private ArrayList<Timeline> animationList = new ArrayList<>() ;

    public LinkedHashMap<ComparisonArray, List<ComparisonStat>> getStatTable() {
        return statTable;
    }

    public void setStatTable(LinkedHashMap<ComparisonArray, List<ComparisonStat>> statTable) {
        this.statTable = statTable;
    }

    public void buildTable() {
        // Create the table
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefHeight(bounds.getHeight());
        table.setPrefWidth(bounds.getWidth());
        table.setStyle("""
                -fx-background-color: white;
                -fx-border-color: #d1d5db;
                -fx-border-width: 2;
                -fx-table-cell-border-color: #21262d;
                -fx-font-size: 13px;
                -fx-text-fill: White;
                """);
        // Define the columns
        TableColumn<ComparisonStat, String> sortingAlgorithm = new TableColumn<>("Algorithm");
        sortingAlgorithm.setCellValueFactory(new PropertyValueFactory<>("sortingAlgorithm"));
        table.setFixedCellSize(50);
        styleColumn(sortingAlgorithm);

        TableColumn<ComparisonStat, String> arrayGenerationMode = new TableColumn<>("Generation Mode");
        arrayGenerationMode.setCellValueFactory(new PropertyValueFactory<>("generationMode"));
        styleColumn(arrayGenerationMode);

        TableColumn<ComparisonStat, Integer> arraySize = new TableColumn<>("Array Size");
        arraySize.setCellValueFactory(new PropertyValueFactory<>("arraySize"));
        styleColumn(arraySize);

        TableColumn<ComparisonStat, Integer> numberOfRuns = new TableColumn<>("Runs");
        numberOfRuns.setCellValueFactory(new PropertyValueFactory<>("numberOfruns"));
        styleColumn(numberOfRuns);

        TableColumn<ComparisonStat, Double> averageTime = new TableColumn<>("Avg Time (ns)");
        averageTime.setCellValueFactory(new PropertyValueFactory<>("averageTime"));
        styleColumn(averageTime);

        TableColumn<ComparisonStat, Double> minTime = new TableColumn<>("Min Time (ns)");
        minTime.setCellValueFactory(new PropertyValueFactory<>("minTime"));
        styleColumn(minTime);

        TableColumn<ComparisonStat, Double> maxTime = new TableColumn<>("Max Time (ns)");
        maxTime.setCellValueFactory(new PropertyValueFactory<>("maxTime"));
        styleColumn(maxTime);

        TableColumn<ComparisonStat, Long> numOfComparisons = new TableColumn<>("Comparisons");
        numOfComparisons.setCellValueFactory(new PropertyValueFactory<>("numOfComparison"));
        styleColumn(numOfComparisons);

        TableColumn<ComparisonStat, Long> numOfInterchanges = new TableColumn<>("Interchanges");
        numOfInterchanges.setCellValueFactory(new PropertyValueFactory<>("numOfInterchange"));
        styleColumn(numOfInterchanges);

        table.getColumns().addAll(sortingAlgorithm, arrayGenerationMode, arraySize, averageTime, minTime, maxTime, numOfInterchanges, numOfComparisons, numberOfRuns);

        ObservableList<ComparisonStat> statsList = FXCollections.observableArrayList();
        for (Map.Entry<ComparisonArray, List<ComparisonStat>> entry : statTable.entrySet()) {
            statsList.addAll(entry.getValue());
        }

        table.setItems(statsList);

    }

    public void buildView() {
        this.getChildren().clear();
        toolBar.getChildren().clear();
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        // TODO fix placeholder issue
        if (statTable == null || statTable.isEmpty()) {
            table = new TableView<>();
            table.setPrefWidth(bounds.getWidth());
            table.setPrefHeight(bounds.getHeight());
            Label placeHolder = new Label("No results have arrived yet Loading");
            ProgressIndicator indicator = new ProgressIndicator();
            indicator.setPrefSize(60, 60);
            VBox loadingBox = new VBox(12, placeHolder, indicator);
            loadingBox.setAlignment(Pos.CENTER);
            // A simple animation for loading
            KeyValue v1 = new KeyValue(placeHolder.textProperty(), "No results have arrived yet Loading");
            KeyValue v2 = new KeyValue(placeHolder.textProperty(), "No results have arrived yet Loading.");
            KeyValue v3 = new KeyValue(placeHolder.textProperty(), "No results have arrived yet Loading..");
            KeyValue v4 = new KeyValue(placeHolder.textProperty(), "No results have arrived yet Loading...");
            KeyFrame frame1 = new KeyFrame(Duration.ZERO, v1);
            KeyFrame frame2 = new KeyFrame(Duration.seconds(0.5), v2);
            KeyFrame frame3 = new KeyFrame(Duration.seconds(1), v3);
            KeyFrame frame4 = new KeyFrame(Duration.seconds(1.5), v4);
            Timeline tl = new Timeline(frame1, frame2, frame3, frame4);
            tl.setCycleCount(Animation.INDEFINITE);
            tl.play();
            placeHolder.setStyle("""
                    -fx-text-fill: #0ea5e9;
                    -fx-font-family: 'Segoe UI';
                    -fx-font-size: 30px;
                    -fx-text-alignment: Center;
                    """);
            // FIX ANIMATION LEAKING
            animationList.add(tl) ;
            table.setPlaceholder(loadingBox);
            this.getChildren().addAll(table);
            this.setAlignment(Pos.CENTER);
            table.refresh();
        } else {
            buildTable();
            this.stop();
            Button exportButton = new Button("⬇ Export CSV");
            exportButton.setOnAction(e -> {
                saveCSV();
            });
            Button toMianMenuButton = new Button("To Main Menu") ;
            toMianMenuButton.setOnAction(e->{
                this.getChildren().clear();
                mainView.loadMainView();
            });
            toolBar.getChildren().addAll(exportButton,toMianMenuButton);
            toolBar.setAlignment(Pos.CENTER_LEFT);
            toolBar.setPadding(new Insets(10, 20, 10, 20));
            toolBar.setStyle("""
                        -fx-background-color: #1e293b;
                        -fx-border-color: #334155;
                        -fx-border-width: 0 0 2 0;
                        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0.2, 0, 3);
                    """);
            exportButton.setStyle("""
                        -fx-background-color: #38bdf8;
                        -fx-text-fill: #0f172a;
                        -fx-font-size: 13px;
                        -fx-font-weight: bold;
                        -fx-padding: 8 18 8 18;
                        -fx-background-radius: 8px;
                        -fx-cursor: hand;
                        -fx-effect: dropshadow(gaussian, rgba(56,189,248,0.4), 8, 0.3, 0, 2);
                    """);
            toMianMenuButton.setStyle(exportButton.getStyle());
            this.getChildren().addAll(toolBar, table);
            this.setSpacing(20);
            Platform.runLater(() -> {
                table.lookupAll(".column-header").forEach(node -> {
                    node.setStyle("""
                                -fx-background-color: #f3f4f6;
                                -fx-text-fill: #111827;
                                -fx-font-weight: bold;
                                -fx-font-size: 13px;
                                -fx-pref-height: 50px;
                                -fx-border-color: #d1d5db;
                                -fx-border-width: 1 1 2 1;
                                -fx-alignment: CENTER-LEFT;
                                -fx-padding: 5 5 5 5;
                                -fx-border-radius: 3px;
                                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 3, 0.3, 0, 2);
                            """);
                });
                table.refresh();
            });
        }
    }

    private <T> void styleColumn(TableColumn<ComparisonStat, T> col) {
        col.setCellFactory(c -> new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.toString());
                    setStyle(
                            "-fx-text-fill: " + "#111827" + ";" +
                                    "-fx-alignment: " + "Center" + ";" +
                                    "-fx-font-size: 12px;" +
                                    "-fx-padding: 0 12 0 12;" +
                                    "-fx-font-weight: bold;" +
                                    "-fx-border-color: #d1d5db;" +
                                    "-fx-border-width: 1 1 1 1;" +
                                    "-fx-border-radius: 3px;"
                    );
                }
            }
        });
    }

    private void saveCSV() {
        CSVWriter writer = new CSVWriter();
        String csv = writer.writeCSV(statTable);
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save csv");
        chooser.setInitialFileName("results.csv");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        File file = chooser.showSaveDialog(this.getScene().getWindow());
        if (file != null) {
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(csv);
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public MainGUI getMainView() {
        return mainView;
    }

    public void setMainView(MainGUI mainView) {
        this.mainView = mainView;
    }

    @Override
    public void play() {
        animationList.forEach(tl -> tl.play());
    }

    @Override
    public void pause() {
        animationList.forEach(tl -> tl.pause());

    }

    @Override
    public void stop() {
        animationList.forEach(tl -> tl.stop());
        animationList.clear();
    }
}
