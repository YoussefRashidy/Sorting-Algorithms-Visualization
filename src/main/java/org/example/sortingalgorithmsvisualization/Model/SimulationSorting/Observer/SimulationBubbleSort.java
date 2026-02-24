package org.example.sortingalgorithmsvisualization.Model.SimulationSorting.Observer;

import org.example.sortingalgorithmsvisualization.Model.PureSorting.SortingStrategy;

import java.util.ArrayList;
import java.util.List;

public class SimulationBubbleSort extends AbstractSimulationSorting {
    @Override
    public int[] sort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            boolean swap = false;
            for (int j = 0; j < array.length - 1 - i; j++) {
                // Record Comparison
                comparisonNotification(j , j+1);
                if (array[j + 1] < array[j]) {

                    swap = true;
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    // Record Swap
                    swapNotification(j , j+1);
                }
            }
            if (!swap) break;
        }
        return array;
    }
}
