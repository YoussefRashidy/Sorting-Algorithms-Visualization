package org.example.sortingalgorithmsvisualization.Model.SimulationSorting.Observer;

public class OperationsCounter implements Observer{
    private int swap ;
    private int comparison ;
    private int set ;
    @Override
    public void swap(int index1, int index2) {
        swap+= swap ;
    }

    @Override
    public void compare(int index1, int index2) {
        comparison+=comparison ;
    }

    @Override
    public void set(int index, int val) {
        set+=1;
    }

    public int getSwap() {
        return swap;
    }

    public int getComparison() {
        return comparison;
    }
}
