package org.example.sortingalgorithmsvisualization.Model.SimulationSorting;

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
        sortNotification();
        return array;
    }

    @Override
    public String getName() {
        return "Bubble Sort";
    }
}
