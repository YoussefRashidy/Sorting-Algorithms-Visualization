package org.example.sortingalgorithmsvisualization.Model.Observers;

public interface Observer {
    public void swap(int index1 , int index2) ;
    public void compare(int index1 , int index2) ;
    public void set(int index , int val) ;
}
