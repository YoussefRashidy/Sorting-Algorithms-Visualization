package org.example.sortingalgorithmsvisualization.View;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.example.sortingalgorithmsvisualization.Controller.ComparisonArray;
import org.example.sortingalgorithmsvisualization.Model.ComparisonStat;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class ChartView {
    private LinkedHashMap<ComparisonArray, List<ComparisonStat>> statTable;
    LineChart<Number, Number> lineChart ;

    public void setStatTable(LinkedHashMap<ComparisonArray, List<ComparisonStat>> statTable) {
        this.statTable = statTable;
    }

    public LinkedHashMap<ComparisonArray, List<ComparisonStat>> getStatTable() {
        return statTable;
    }
    public void plotAlgorithms(String metricLabel,String generationMode){
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Array Size");
        yAxis.setLabel(metricLabel);
        lineChart = new LineChart<>(xAxis, yAxis);
        String[] algos = {"Merge Sort","Bubble Sort","Insertion Sort","Selection Sort","Heap Sort"} ;
        Arrays.stream(algos).forEach(algo -> plotMetric(algo, metricLabel, generationMode));
    }
    public void plotMetric(String Algorithm,String metricLabel,String generationMode) {


        lineChart.getData().add(extractSeries(Algorithm,metricLabel,generationMode)) ;
    }

    // take care of file loaded arrays since generation mode is shared among them
    private XYChart.Series<Number, Number> extractSeries(String Algorithm, String metric, String generationMode) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(Algorithm);
        for (var entry : statTable.sequencedEntrySet()) {
            if (!entry.getKey().generationMode().equalsIgnoreCase(generationMode)) continue;
            var statList = entry.getValue();
            for (var stat : statList) {
                if (stat.getSortingAlgorithm().equalsIgnoreCase(Algorithm)) {
                    Number x = stat.getArraySize();
                    Number y = switch (metric) {
                        case "minRunTime" -> stat.getMinTime();
                        case "maxRunTime" -> stat.getMaxTime();
                        case "averageRunTime" -> stat.getAverageTime();
                        case "numOfInterchanges" -> stat.getNumOfInterchange();
                        case "numOfComparison" -> stat.getNumOfComparison();
                        default -> throw new IllegalArgumentException("Unknown metric");
                    };
                    series.getData().add(new XYChart.Data<>(x, y));
                    break;
                }
            }
        }
        return series;
    }
}
