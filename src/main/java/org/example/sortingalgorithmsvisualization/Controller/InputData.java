package org.example.sortingalgorithmsvisualization.Controller;

import java.io.File;
import java.util.ArrayList;

public class InputData {
    public boolean autoGeneration ;
    public ArrayList<Integer> arraySizes = new ArrayList<>();
    public int maxValue ;
    public ArrayList<File> files ;
    public ArrayList<String> algorithms = new ArrayList<>();
    public ArrayType[] generationMods;
    public int numberOfRuns;
}
