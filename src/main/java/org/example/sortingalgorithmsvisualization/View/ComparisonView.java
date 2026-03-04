package org.example.sortingalgorithmsvisualization.View;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import org.example.sortingalgorithmsvisualization.Controller.ComparisonArray;
import org.example.sortingalgorithmsvisualization.Model.ComparisonStat;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ComparisonView extends VBox {
    private LinkedHashMap<ComparisonArray, List<ComparisonStat>> statTable;
    private TableView<ComparisonStat> table;

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
                -fx-background-color: #0d1117;
                -fx-border-color: #21262d;
                -fx-border-width: 1;
                -fx-table-cell-border-color: #21262d;
                -fx-font-size: 13px;
                -fx-selection-bar: #1f6feb;
                -fx-text-fill: White;
                """);

        Platform.runLater(() -> {
            table.lookupAll(".column-header").forEach(node -> {
                node.setStyle("""
                            -fx-background-color: #161b22;
                            -fx-text-fill: #ffffff;
                            -fx-font-weight: bold;
                            -fx-font-size: 13px;
                            -fx-pref-height: 50px;
                            -fx-border-color: #313244;
                            -fx-border-width: 0 1 2 0;
                            -fx-alignment: CENTER-LEFT;
                            -fx-padding: 0 0 0 10;
                        """);
            });
        });
        // Define the columns
        TableColumn<ComparisonStat, String> sortingAlgorithm = new TableColumn<>("Algorithm");
        sortingAlgorithm.setCellValueFactory(new PropertyValueFactory<>("sortingAlgorithm"));
        table.setFixedCellSize(50);
        styleColumn(sortingAlgorithm, "#89b4fa", "CENTER-LEFT");

        TableColumn<ComparisonStat, String> arrayGenerationMode = new TableColumn<>("Generation Mode");
        arrayGenerationMode.setCellValueFactory(new PropertyValueFactory<>("generationMode"));
        styleColumn(arrayGenerationMode, "#a6e3a1", "CENTER");

        TableColumn<ComparisonStat, Integer> arraySize = new TableColumn<>("Array Size");
        arraySize.setCellValueFactory(new PropertyValueFactory<>("arraySize"));
        styleColumn(arraySize, "#f9e2af", "CENTER");

        TableColumn<ComparisonStat, Integer> numberOfRuns = new TableColumn<>("Runs");
        numberOfRuns.setCellValueFactory(new PropertyValueFactory<>("numberOfruns"));
        styleColumn(numberOfRuns, "#fab387", "CENTER");

        TableColumn<ComparisonStat, Double> averageTime = new TableColumn<>("Avg Time (ns)");
        averageTime.setCellValueFactory(new PropertyValueFactory<>("averageTime"));
        styleColumn(averageTime, "#eba0ac", "CENTER-RIGHT");

        TableColumn<ComparisonStat, Double> minTime = new TableColumn<>("Min Time (ns)");
        minTime.setCellValueFactory(new PropertyValueFactory<>("minTime"));
        styleColumn(minTime, "#94e2d5", "CENTER-RIGHT");

        TableColumn<ComparisonStat, Double> maxTime = new TableColumn<>("Max Time (ns)");
        maxTime.setCellValueFactory(new PropertyValueFactory<>("maxTime"));
        styleColumn(maxTime, "#f38ba8", "CENTER-RIGHT");

        TableColumn<ComparisonStat, Long> numOfComparisons = new TableColumn<>("Comparisons");
        numOfComparisons.setCellValueFactory(new PropertyValueFactory<>("numOfComparison"));
        styleColumn(numOfComparisons, "#b4befe", "CENTER-RIGHT");

        TableColumn<ComparisonStat, Long> numOfInterchanges = new TableColumn<>("Interchanges");
        numOfInterchanges.setCellValueFactory(new PropertyValueFactory<>("numOfInterchange"));
        styleColumn(numOfInterchanges, "#f5c2e7", "CENTER-RIGHT");

        table.getColumns().addAll(sortingAlgorithm, arrayGenerationMode, arraySize, averageTime, minTime, maxTime, numOfInterchanges, numOfComparisons, numberOfRuns);

        ObservableList<ComparisonStat> statsList = FXCollections.observableArrayList();
        for (Map.Entry<ComparisonArray, List<ComparisonStat>> entry : statTable.entrySet()) {
            statsList.addAll(entry.getValue());
        }

        table.setItems(statsList);

    }

    public void buildView() {
        this.getChildren().add(table);
    }

    private <T> void styleColumn(TableColumn<ComparisonStat, T> col, String color, String alignment) {
        col.setCellFactory(c -> new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color: transparent;");
                } else {
                    setText(item.toString());
                    String rowBg = getIndex() % 2 == 0 ? "#0d1117" : "#161b22";
                    setStyle(
                            "-fx-text-fill: " + color + ";" +
                            "-fx-alignment: " + alignment + ";" +
                            "-fx-font-size: 12px;" +
                            "-fx-padding: 0 12 0 12;" +
                            "-fx-background-color: " + rowBg + ";" +
                            "-fx-font-weight: bold;" +
                            "-fx-border-color: #21262d;" +
                            "-fx-border-width: 0 0 1 0;"
                    );
                }
            }
        });
    }


}
