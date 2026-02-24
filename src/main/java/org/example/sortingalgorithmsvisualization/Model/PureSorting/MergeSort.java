package org.example.sortingalgorithmsvisualization.Model.PureSorting;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MergeSort implements SortingStrategy{
    @Override
    public int[] sort(int[] array) {
        mergeSort(array, 0, array.length-1);
        return array;
    }

    private void mergeSort(int[] array , int left , int right) {
        if (left >= right) return ;
        int q = (left+right) / 2 ;
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
            if (leftArray[i] <= rightArray[j]) {
                array[left+ i + j] = leftArray[i] ;
                i++ ;
            }
            else {
                array[left+i+j] = rightArray[j] ;
                j++;
            }
        }
        while (i < leftArray.length) {
            array[left+i+j] = leftArray[i] ;
            i++ ;
        }
        while (j < rightArray.length) {
            array[left+i+j] = rightArray[j] ;
            j++ ;
        }

    }
}
