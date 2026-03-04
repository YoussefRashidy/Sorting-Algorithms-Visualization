package org.example.sortingalgorithmsvisualization.Model.SimulationSorting.Observer;

import org.example.sortingalgorithmsvisualization.Model.Observers.Observable;
import org.example.sortingalgorithmsvisualization.Model.Observers.Observer;
import org.example.sortingalgorithmsvisualization.Model.PureSorting.SortingStrategy;

import java.util.ArrayList;
import java.util.List;

abstract public class AbstractSimulationSorting implements Observable, SortingStrategy {
    private final List<Observer> observers = new ArrayList<>() ;

    @Override
    public void register(Observer observer) {
        observers.add(observer) ;
    }

    @Override
    public void unRegister(Observer observer) {
        observers.remove(observer) ;
    }

    @Override
    public void swapNotification(int index1, int index2) {
        for (Observer observer : observers) observer.swap(index1, index2);
    }

    @Override
    public void comparisonNotification(int index1, int index2) {
        for (Observer observer : observers) observer.compare(index1, index2);
    }

    @Override
    public void setNotification(int index , int val) {
        for (Observer observer : observers) observer.set(index, val);
    }

    @Override
    public void divideNotification(int index, int index2 , int index3) {
        //No-Op for now
        for (Observer observer : observers) observer.divide(index, index2 ,index3);
    }

    @Override
    public void mergeNotification(int index1 , int index2) {
        // No-Op fir now
        for (Observer observer : observers) observer.merge(index1,index2);

    }
    @Override
    public void sortNotification() {
        for (Observer observer : observers) observer.sortEvent();
    }
}
