package org.example.sortingalgorithmsvisualization.Model.Events;

public sealed interface SortingEvent permits ComparisonEvent, DivideEvent, MergeComparisonEvent, MergeEvent, PartitionEvent, SetEvent, SortedEvent, SwapEvent {
}
