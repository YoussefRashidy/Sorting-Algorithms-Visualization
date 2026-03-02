package org.example.sortingalgorithmsvisualization.Model.SimulationSorting.Observer;

public class SimulationInsertionSort extends AbstractSimulationSorting{
    @Override
    public int[] sort(int[] array) {
        for (int i = 1 ; i < array.length ; i++) {
            int j = i - 1 ;
            int key = array[i] ;
            while ( j >= 0 ){
                // Record Comparison
                comparisonNotification(j,j+1);
                if (key >= array[j]) break;

                array[j+1] = array[j] ;
                // Record Swap
                swapNotification(j, j+1);
                j-- ;
            }
            // Record Setting
            array[j+1] = key ;
//            setNotification(j+1, key);
        }
        return array;
    }
}
