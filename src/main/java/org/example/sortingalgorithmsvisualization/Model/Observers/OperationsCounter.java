package org.example.sortingalgorithmsvisualization.Model.Observers;

public class OperationsCounter implements Observer{
    private int swap ;
    private int comparison ;
    private int set ;
    @Override
    public void swap(int index1, int index2) {
        swap+= 1 ;
    }

    @Override
    public void compare(int index1, int index2) {
        comparison+=1 ;
    }

    @Override
    public void set(int index, int val) {
//        set+=1;
        swap++;
    }

    public int getSwap() {
        return swap;
    }

    public int getComparison() {
        return comparison;
    }
}
