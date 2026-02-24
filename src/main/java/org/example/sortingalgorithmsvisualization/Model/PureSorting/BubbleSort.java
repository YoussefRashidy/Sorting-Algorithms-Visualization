package org.example.sortingalgorithmsvisualization.Model.PureSorting;

public class BubbleSort implements SortingStrategy {
    @Override
    public int[] sort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            boolean swap = false;
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j + 1] < array[j]) {
                    swap = true;
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
            if (!swap) break;
        }
        return array;
    }
}
