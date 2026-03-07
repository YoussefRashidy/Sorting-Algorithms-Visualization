package org.example.sortingalgorithmsvisualization.Model;

import org.example.sortingalgorithmsvisualization.Model.PureSorting.*;
import org.example.sortingalgorithmsvisualization.Model.SimulationSorting.*;

public class SortingAlgorithmsFactory {
    public SortingStrategy createPureAlgorithm(String algo) {
        return switch (algo) {
            case "Merge Sort" -> new MergeSort() ;
            case "Bubble Sort" -> new BubbleSort() ;
            case "Insertion Sort" -> new InsertionSort() ;
            case "Selection Sort" -> new SelectionSort() ;
            case "Heap Sort" -> new HeapSort() ;
            case "Quick Sort" -> new QuickSort() ;
            default -> throw new IllegalArgumentException() ;
        } ;
    }

    public AbstractSimulationSorting createSimulationAlgorithm(String algo) {
        return switch (algo) {
            case "Merge Sort" -> new SimulationMergeSort() ;
            case "Bubble Sort" -> new SimulationBubbleSort() ;
            case "Insertion Sort" -> new SimulationInsertionSort() ;
            case "Selection Sort" -> new SimulationSelectionSort() ;
            case "Heap Sort" -> new SimulationHeapSort() ;
            case "Quick Sort" -> new SimulationQuickSort() ;
            default -> throw new IllegalArgumentException() ;
        } ;
    }
}
