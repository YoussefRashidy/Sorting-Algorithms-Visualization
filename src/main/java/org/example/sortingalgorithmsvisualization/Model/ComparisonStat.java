package org.example.sortingalgorithmsvisualization.Model;

public class ComparisonStat {
    String sortingAlgorithm ;
    int arraySize ;
    String generationMode ; // this will be generation mode or file name
    int numberOfruns ;
    double averageTime ;
    double minTime ;
    double maxTime ;
    long numOfComparison ;
    long numOfInterchange;

    public String getSortingAlgorithm() {
        return sortingAlgorithm;
    }

    public void setSortingAlgorithm(String sortingAlgorithm) {
        this.sortingAlgorithm = sortingAlgorithm;
    }

    public int getArraySize() {
        return arraySize;
    }

    public void setArraySize(int arraySize) {
        this.arraySize = arraySize;
    }

    public String getGenerationMode() {
        return generationMode;
    }

    public void setGenerationMode(String generationMode) {
        this.generationMode = generationMode;
    }

    public int getNumberOfruns() {
        return numberOfruns;
    }

    public void setNumberOfruns(int numberOfruns) {
        this.numberOfruns = numberOfruns;
    }

    public double getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(double averageTime) {
        this.averageTime = averageTime;
    }

    public double getMinTime() {
        return minTime;
    }

    public void setMinTime(double minTime) {
        this.minTime = minTime;
    }

    public double getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(double maxTime) {
        this.maxTime = maxTime;
    }

    public long getNumOfComparison() {
        return numOfComparison;
    }

    public void setNumOfComparison(long numOfComparison) {
        this.numOfComparison = numOfComparison;
    }

    public long getNumOfInterchange() {
        return numOfInterchange;
    }

    public void setNumOfInterchange(long numOfInterchange) {
        this.numOfInterchange = numOfInterchange;
    }
}
