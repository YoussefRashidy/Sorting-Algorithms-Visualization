package org.example.sortingalgorithmsvisualization.Model;

import org.example.sortingalgorithmsvisualization.Controller.InputData;
import org.example.sortingalgorithmsvisualization.Model.PureSorting.SortingStrategy;

public class ComparisonManager {
    private InputData data ;
    private SortingAlgorithmsFactory factory = new SortingAlgorithmsFactory() ;

    public InputData getData() {
        return data;
    }

    public void setData(InputData data) {
        this.data = data;
    }


}
