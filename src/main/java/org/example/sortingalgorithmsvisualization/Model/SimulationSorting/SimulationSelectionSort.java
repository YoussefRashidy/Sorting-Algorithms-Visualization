package org.example.sortingalgorithmsvisualization.Model.SimulationSorting;

public class SimulationSelectionSort extends AbstractSimulationSorting{
    @Override
    public int[] sort(int[] array) {
        for (int i = 0 ; i < array.length ; i++) {
            int minIndex = i ;
            for (int j = i+1 ; j < array.length ; j++) {
                comparisonNotification(j,minIndex);
                if (array[j] < array[minIndex]) minIndex = j ;
            }
            swapNotification(i,minIndex);
            int temp = array[i] ;
            array[i] = array[minIndex] ;
            array[minIndex] = temp;
        }
        sortNotification();
        return array;
    }

    @Override
    public String getName() {
        return "Selection Sort";
    }
}
