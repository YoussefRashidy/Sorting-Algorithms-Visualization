package org.example.sortingalgorithmsvisualization.Model.Observers;

public interface Observable {
    public void register(Observer observer) ;
    public void unRegister(Observer observer) ;
    public void swapNotification(int index1 , int index2) ;
    public void comparisonNotification(int index1 , int index2) ;
    public void divideNotification(int index , int index2 , int index3) ;
    public void setNotification(int index , int val) ;
    public void sortNotification() ;
    public void mergeNotification(int index1 , int index2);
    public void partitionNotification(int index1 , int left , int right) ;
}
