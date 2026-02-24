package org.example.sortingalgorithmsvisualization.Model.Events;

public sealed interface SortingEvent permits ComparisonEvent , SwapEvent ,DivideEvent,SetEvent {
}
