package org.example.sortingalgorithmsvisualization.Model.Events;

public sealed interface SortingEvent permits ComparisonEvent, DivideEvent, MergeEvent, PartitionEvent, SetEvent, SortedEvent, SwapEvent {
}
