package org.example.sortingalgorithmsvisualization.Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    InputData data;
    ArrayFileReader fileReader = new ArrayFileReader();
    ArrayGenerator generator = new ArrayGenerator() ;
    ArrayList<int[]> arrays;

    public void setData(InputData data) {
        this.data = data;
    }

    //Data processing methods
    private void processData() {
        if (data.autoGeneration) {

        } else {
            // Read data using file reader
            String[] paths = data.files.stream().map(File::getPath).toArray(String[]::new);
            try {
                arrays = fileReader.readArrays(paths);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
