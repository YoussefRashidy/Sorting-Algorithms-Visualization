package org.example.sortingalgorithmsvisualization.Model.SimulationSorting.Observer;

import java.util.Arrays;

public class SimulationMergeSort extends AbstractSimulationSorting{
    @Override
    public int[] sort(int[] array) {
        mergeSort(array, 0, array.length-1);
        sortNotification();
        return array;
    }

    private void mergeSort(int[] array , int left , int right) {
        if (left >= right) return ;
        int q = (left+right) / 2 ;
        // Record divide
        divideNotification(q,left,right);
        mergeSort(array, left, q);
        mergeSort(array, q+1, right);
        merge(array, left, q, right);
    }

    private void merge(int[] array , int left , int q , int right) {
        int[] leftArray = Arrays.copyOfRange(array, left, q+1) ;
        int[] rightArray= Arrays.copyOfRange(array, q+1, right+1) ;
        int i , j ;
        i = j = 0 ;
        while (i < leftArray.length && j < rightArray.length) {
            // Record Comparison (solve comparison problem)
            comparisonNotification(left+i , q+j+1);
            if (leftArray[i] <= rightArray[j]) {
                //Record set (here it is considered swap)
                setNotification(left+i+j, leftArray[i]);
                array[left+ i + j] = leftArray[i] ;
                i++ ;
            }
            else {
                //Record set (here it is considered swap)
                setNotification(left+i+j, rightArray[j]);
                array[left+i+j] = rightArray[j] ;
                j++;
            }
        }
        while (i < leftArray.length) {
            //Record set (here it is considered swap)
            setNotification(left+i+j, leftArray[i]);
            array[left+i+j] = leftArray[i] ;
            i++ ;
        }
        while (j < rightArray.length) {
            //Record set (here it is considered swap)
            setNotification(left+i+j, rightArray[j]);
            array[left+i+j] = rightArray[j] ;
            j++ ;
        }
        mergeNotification(left, right);
    }

}
