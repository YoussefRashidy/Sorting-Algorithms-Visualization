package org.example.sortingalgorithmsvisualization.Model.Events;

public sealed interface SortingEvent permits ComparisonEvent, DivideEvent, MergeEvent, SetEvent, SortedEvent, SwapEvent {
}
