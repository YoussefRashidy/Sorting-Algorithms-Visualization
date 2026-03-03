package org.example.sortingalgorithmsvisualization.Controller;

import java.util.Objects;

public record ComparisonArray(int[] array , String generationMode) {
    @Override
    public int hashCode(){
        return Objects.hash(generationMode,array.length) ;
    }
    @Override
    public boolean equals(Object arr){
        if (this == arr) return  true ;
        if (!(arr instanceof ComparisonArray)) return false ;
        // This works because of the way arrays are generated then compared
        // the same array is compared against multiple algorithms by making a copy of its content
        return this.array.length == ((ComparisonArray) arr).array.length &&
                this.generationMode.equals(((ComparisonArray)arr).generationMode) ;
    }

}
