package org.example.sortingalgorithmsvisualization.Controller;


import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class ArrayFileReader {

    public int[] readArray(String pathString) throws IOException {
        Path path = Path.of(pathString) ;
        Scanner scanner = new Scanner(path) ;
        scanner.useDelimiter("\\s*,\\s*") ;
        ArrayList<Integer> nums = new ArrayList<>() ;
        while (scanner.hasNextInt()) {
            nums.add(scanner.nextInt()) ;
        }
        scanner.close();
        return nums.stream().mapToInt(Integer::intValue).toArray() ;
    }

    public ArrayList<int[]> readArrays(String[] paths) throws IOException {
        ArrayList<int[]> arrays = new ArrayList<>() ;
        for (String path : paths) {
            int[] arr = readArray(path) ;
            arrays.add(arr) ;
        }
        return arrays ;
    }
}
