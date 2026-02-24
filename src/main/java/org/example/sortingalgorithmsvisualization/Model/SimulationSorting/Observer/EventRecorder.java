package org.example.sortingalgorithmsvisualization.Model.SimulationSorting.Observer;

import org.example.sortingalgorithmsvisualization.Model.Events.ComparisonEvent;
import org.example.sortingalgorithmsvisualization.Model.Events.SetEvent;
import org.example.sortingalgorithmsvisualization.Model.Events.SortingEvent;
import org.example.sortingalgorithmsvisualization.Model.Events.SwapEvent;

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

    public List<SortingEvent> getEventList() {
        return eventList;
    }
}
