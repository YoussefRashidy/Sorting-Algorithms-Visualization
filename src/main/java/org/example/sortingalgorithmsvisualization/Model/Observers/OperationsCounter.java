package org.example.sortingalgorithmsvisualization.Model.Observers;

public class OperationsCounter implements Observer {
    private int swap;
    private int comparison;
    private int set;

    @Override
    public void swap(int index1, int index2) {
        swap += 1;
    }

    @Override
    public void compare(int index1, int index2) {
        comparison += 1;
    }

    @Override
    public void set(int index, int val) {
        // Here sets are counted as interchanges for simplicity
//        set+=1;
        swap++;
    }

    @Override
    public void merge(int index1, int index2) {
        // NO-OP
    }

    @Override
    public void sortEvent() {
        //NO-OP
    }

    @Override
    public void divide(int index, int index2, int index3) {
        // NO-OP
    }

    @Override
    public void partition(int index, int left, int right) {
        // NO_OP
    }

    @Override
    public void mergeComparison(int val1, int val2, int index1, int index2, int setIndex) {
        comparison+=1;
    }


    public int getSwap() {
        return swap;
    }

    public int getComparison() {
        return comparison;
    }
}
