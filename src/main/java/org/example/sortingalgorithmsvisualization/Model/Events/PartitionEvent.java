package org.example.sortingalgorithmsvisualization.Model.Events;

public record PartitionEvent(int index ,int left , int right) implements SortingEvent {
}
