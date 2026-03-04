package org.example.sortingalgorithmsvisualization.Controller;

import org.example.sortingalgorithmsvisualization.Model.ComparisonStat;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CSVWriter {
    public String writeCSV(LinkedHashMap<ComparisonArray , List<ComparisonStat>> statTable){
        StringBuilder csvBuilder = new StringBuilder() ;
        csvBuilder.append("Sorting Algorithm,") ;
        csvBuilder.append("Array Size,") ;
        csvBuilder.append("Generation Mode,") ;
        csvBuilder.append("Number of Runs,") ;
        csvBuilder.append("Average Time,") ;
        csvBuilder.append("Minimum Time,") ;
        csvBuilder.append("Maximum Time,") ;
        csvBuilder.append("Number of Comparisons,") ;
        csvBuilder.append("Number of Interchanges\n") ;
        for (Map.Entry<ComparisonArray , List<ComparisonStat>> entry : statTable.sequencedEntrySet()) {
            for (ComparisonStat stat : entry.getValue()) {
                csvBuilder.append(stat.toString()) ;
            }
        }
        return csvBuilder.toString() ;
    }

}
