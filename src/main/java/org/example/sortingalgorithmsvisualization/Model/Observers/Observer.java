package org.example.sortingalgorithmsvisualization.Model.Observers;

public interface Observer {
    public void swap(int index1 , int index2) ;
    public void compare(int index1 , int index2) ;
    public void set(int index , int val) ;
    public void merge(int index1 ,int index2) ;
    public void sortEvent();
    public void divide(int index,int index2 , int index3) ;
    public void partition(int index , int left , int right) ;
    public void mergeComparison(int val1 , int val2 ,int index1 ,int index2 , int setIndex) ;

}
