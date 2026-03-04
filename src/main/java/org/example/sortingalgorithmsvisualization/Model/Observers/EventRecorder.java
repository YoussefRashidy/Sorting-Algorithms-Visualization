package org.example.sortingalgorithmsvisualization.Model.Observers;

import org.example.sortingalgorithmsvisualization.Model.Events.*;

import java.util.ArrayList;
import java.util.List;

public class EventRecorder implements Observer{
    private final List<SortingEvent> eventList = new ArrayList<>() ;
    @Override
    public void swap(int index1, int index2) {
        SortingEvent event = new SwapEvent(index1, index2) ;
        eventList.add(event) ;
    }

    @Override
    public void compare(int index1, int index2) {
        SortingEvent event = new ComparisonEvent(index1, index2) ;
        eventList.add(event) ;
    }

    @Override
    public void set(int index, int val) {
        SortingEvent event = new SetEvent(index, val) ;
        eventList.add(event) ;
    }

    @Override
    public void merge(int index1, int index2) {
        SortingEvent event = new MergeEvent(index1, index2) ;
        eventList.add(event) ;
    }

    @Override
    public void sortEvent() {
        SortingEvent event = new SortedEvent() ;
        eventList.add(event) ;
    }

    @Override
    public void divide(int index, int index2, int index3) {
        DivideEvent event = new DivideEvent(index ,index2,index3) ;
        eventList.add(event) ;
    }

    public List<SortingEvent> getEventList() {
        return eventList;
    }
}
