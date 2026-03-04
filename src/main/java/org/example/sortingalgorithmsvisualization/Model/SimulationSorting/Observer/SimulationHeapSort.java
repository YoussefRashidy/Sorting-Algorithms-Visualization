package org.example.sortingalgorithmsvisualization.Model.SimulationSorting.Observer;

public class SimulationHeapSort extends AbstractSimulationSorting {
    public int[] sort(int[] array) {
        buildMaxHeap(array);
        for (int i = array.length - 1; i >= 1; i--) {
            swapNotification(i, 0);
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            heapSize--;
            maxHeapify(0, array);
        }
        sortNotification();
        return array;
    }

    private int heapSize;

    private void maxHeapify(int index, int[] array) {
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        int largest = index;
        if (left < heapSize) {
            comparisonNotification(left, largest);
            if ( array[largest] < array[left])
                largest = left;
        }

        if (right < heapSize) {
            comparisonNotification(right, largest);
            if ( array[largest] < array[right])
                largest = right;
        }

        comparisonNotification(index, largest);
        if (largest == index) return;

        int temp = array[index];
        array[index] = array[largest];
        array[largest] = temp;
        swapNotification(largest, index);
        maxHeapify(largest, array);

    }

    private void buildMaxHeap(int[] array) {
        int index = array.length / 2;
        heapSize = array.length;
        for (int i = index; i >= 0; i--) {
            maxHeapify(i, array);
        }
    }
}
