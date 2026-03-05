package org.example.sortingalgorithmsvisualization.Controller;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public record ComparisonArray(int[] array, String generationMode, int id) {

    // this id is required to identify different arrays
    // Also not Linked Hash table end up being useless
    // so in future update replace it with a list of entries
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    public ComparisonArray(int[] array, String generationMode) {
        this(array, generationMode, COUNTER.getAndIncrement());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object arr) {
        if (this == arr) return true;
        if (!(arr instanceof ComparisonArray other)) return false;
        return this.id == other.id;
    }

}
