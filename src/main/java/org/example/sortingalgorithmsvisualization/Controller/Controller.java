package org.example.sortingalgorithmsvisualization.Controller;

import org.example.sortingalgorithmsvisualization.Model.PureSorting.InsertionSort;
import org.example.sortingalgorithmsvisualization.Model.SimulationSorting.Observer.*;
import org.example.sortingalgorithmsvisualization.Model.VisualizationManager;
import org.example.sortingalgorithmsvisualization.View.MainGUI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    InputData data;
    ArrayFileReader fileReader = new ArrayFileReader();
    ArrayGenerator generator = new ArrayGenerator() ;
    ArrayList<int[]> arrays;
    List<AbstractSimulationSorting> simulationSortings ;
    String mode ;
    int currentAlgo ;
    int currentArray;
    VisualizationManager manager = new VisualizationManager() ;
    MainGUI mainGUI ;

    public void setData(InputData data) {
        this.data = data;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    //Data processing methods
    public void processData() {
        if (data.autoGeneration) {
            arrays = generator.generateBatch(data.arraySizes, data.generationMods,data.maxValue) ;
        } else {
            // Read data using file reader
            String[] paths = data.files.stream().map(File::getPath).toArray(String[]::new);
            try {
                arrays = fileReader.readArrays(paths);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        simulationSortings = getAlgorithms(data.algorithms) ;
        // now based on mode go to visualization manager or comparison manager
        if (mode.equalsIgnoreCase("visualization")) {
            // Instruct the main scene to load the visualization view
            manager.setVisualizationView(mainGUI.getView()) ;
            mainGUI.loadVisualization();
            manager.setNums(arrays.get(currentArray));
            manager.setSort(simulationSortings.get(currentAlgo));
            // Add call back such that once the manager finishes it gets the next array and algo
            manager.startVisualization(()->{
                this.loadNext();
            });
        }
    }

    public void loadNext() {
        currentAlgo++;
        if (currentAlgo >= simulationSortings.size()) {
            currentAlgo = 0 ;
            currentArray++ ;
            if (currentArray >= arrays.size()) {
                // here we have finished all arrays and algorithms so we will return to main scene
                // insrtuct the veiw to load tha main root
                mainGUI.loadMainView();
                currentAlgo = currentArray = 0 ;
                return;
            }
        }
        manager.setNums(arrays.get(currentArray));
        manager.setSort(simulationSortings.get(currentAlgo));
        manager.startVisualization(()->{
            this.loadNext();
        });
    }


    private List<AbstractSimulationSorting> getAlgorithms(ArrayList<String> algorithms) {
        List<AbstractSimulationSorting> simulationAlgorithms = new ArrayList<>() ;
        for (String algo : algorithms) {
            simulationAlgorithms.add(switch (algo) {
                case "Merge Sort" -> new SimulationMergeSort() ;
                case "Bubble Sort" -> new SimulationBubbleSort() ;
                case "Insertion Sort" -> new SimulationInsertionSort() ;
                case "Selection Sort" -> new SimulationSelectionSort() ;
                case "Heap Sort" -> new SimulationHeapSort() ;
                default -> throw new IllegalArgumentException("Invalid sorting algorithm") ;
            }) ;
        }
        return simulationAlgorithms ;
    }

    public MainGUI getMainGUI() {
        return mainGUI;
    }

    public void setMainGUI(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
