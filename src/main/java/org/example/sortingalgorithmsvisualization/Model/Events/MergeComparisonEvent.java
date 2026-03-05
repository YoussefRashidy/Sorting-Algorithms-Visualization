package org.example.sortingalgorithmsvisualization.Model.Events;

// this will serve as an event for showing two floating bars being compared
public record MergeComparisonEvent(int val1 , int val2 ,int index1 ,int index2 , int setIndex) implements SortingEvent {
}
