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
                -fx-background-color: white;
                -fx-border-color: #d1d5db;
                -fx-border-width: 2;
                -fx-table-cell-border-color: #21262d;
                -fx-font-size: 13px;
                -fx-text-fill: White;
                """);

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
        });
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
        this.getChildren().add(table);
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


}
