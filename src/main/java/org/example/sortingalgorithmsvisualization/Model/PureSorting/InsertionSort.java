package org.example.sortingalgorithmsvisualization.Model.PureSorting;

public class InsertionSort implements SortingStrategy{
    @Override
    public int[] sort(int[] array) {
        for (int i = 1 ; i < array.length ; i++) {
            int j = i - 1 ;
            int key = array[i] ;
            while ( j >= 0 && key < array[j]){
                array[j+1] = array[j] ;
                j-- ;
            }
            array[j+1] = key ;
        }
        return array;
    }
}
