# Sorting Algorithms Visualization

A JavaFX desktop application that provides an interactive, animated visualization and benchmarking tool for six classic sorting algorithms.

---

## Table of Contents

- [Features](#features)
- [Supported Algorithms](#supported-algorithms)
- [Project Structure](#project-structure)
- [Architecture & Design Patterns](#architecture--design-patterns)
- [Prerequisites](#prerequisites)
- [Building & Running](#building--running)
- [Usage](#usage)
- [Exporting Results](#exporting-results)
- [Data Analysis](#data-analysis)
- [Report](#report)

---

## Features

| Feature | Description |
|---|---|
| **Visualization Mode** | Watch a sorting algorithm animate step-by-step on a bar chart with color-coded events (comparisons, swaps, merges, partitions, etc.) |
| **Comparison Mode** | Benchmark multiple algorithms against multiple arrays simultaneously and view a statistics table |
| **Auto-Generation** | Instantly generate arrays of any size in four array types: Random, Sorted, Inversely Sorted, Nearly Sorted |
| **File Loading** | Load custom integer arrays from files instead of auto-generating |
| **Export to CSV** | Save the comparison statistics table to a `.csv` file |
| **Animated Landing Page** | Animated bar background on the main menu for a polished UI |

---

## Supported Algorithms

- Bubble Sort
- Insertion Sort
- Selection Sort
- Merge Sort
- Quick Sort
- Heap Sort

---

## Project Structure

```
Sorting Algorithms Visualization/
│
├── docs/
│   └── report.pdf                  # Project report
│
├── analysis/
│   ├── sample_data.csv             # Sample benchmark CSV used in analysis
│   └── DataAnalysis.ipynb          # Jupyter notebook – statistical analysis & plots
│
└── src/main/java/org/example/sortingalgorithmsvisualization/
│
├── HelloApplication.java           # JavaFX entry point
│
├── Controller/                     # Application controller layer
│   ├── Controller.java             # Main controller – orchestrates modes & data flow
│   ├── InputData.java              # Data transfer object for user input
│   ├── ArrayGenerator.java         # Generates arrays (random, sorted, inversely sorted, nearly sorted)
│   ├── ArrayFileReader.java        # Reads integer arrays from files
│   ├── ArrayType.java              # Enum: RANDOM | SORTED | INVERSELY_SORTED | NEARLY_SORTED
│   ├── ComparisonArray.java        # Record wrapping an array + its generation metadata
│   ├── CSVWriter.java              # Serialises comparison results to CSV
│   └── Adaptor/
│       ├── ArrayAdaptor.java       # Converts raw data into ComparisonArray lists
│       └── ComparisonArrayAdaptor.java  # Interface for the adaptor
│
├── Model/                          # Domain / business logic layer
│   ├── VisualizationManager.java   # Drives the step-by-step animation loop
│   ├── ComparisonManager.java      # Runs benchmarks and collects ComparisonStat results
│   ├── SortingAlgorithmsFactory.java  # Factory – creates pure or simulation algorithm instances
│   ├── AnimationCallback.java      # Functional interface fired after each animation frame
│   ├── SessionCallback.java        # Functional interface fired when a full sort session ends
│   ├── ComparisonStat.java         # Holds benchmark results for one algorithm + array pair
│   │
│   ├── PureSorting/                # Plain sorting implementations (used for timing)
│   │   ├── SortingStrategy.java    # Strategy interface
│   │   ├── BubbleSort.java
│   │   ├── InsertionSort.java
│   │   ├── SelectionSort.java
│   │   ├── MergeSort.java
│   │   ├── QuickSort.java
│   │   └── HeapSort.java
│   │
│   ├── SimulationSorting/          # Event-emitting sorting implementations (used for visualization)
│   │   ├── AbstractSimulationSorting.java  # Base class – holds observer list & emits events
│   │   ├── SimulationBubbleSort.java
│   │   ├── SimulationInsertionSort.java
│   │   ├── SimulationSelectionSort.java
│   │   ├── SimulationMergeSort.java
│   │   ├── SimulationQuickSort.java
│   │   └── SimulationHeapSort.java
│   │
│   ├── Events/                     # Sorting event types (Observer pattern payloads)
│   │   ├── SortingEvent.java       # Sealed base interface
│   │   ├── ComparisonEvent.java
│   │   ├── SwapEvent.java
│   │   ├── SetEvent.java
│   │   ├── SortedEvent.java
│   │   ├── MergeEvent.java
│   │   ├── MergeComparisonEvent.java
│   │   ├── DivideEvent.java
│   │   └── PartitionEvent.java
│   │
│   └── Observers/                  # Observers attached to simulation sorts
│       ├── Observable.java         # Observable interface
│       ├── Observer.java           # Observer interface
│       ├── EventRecorder.java      # Records the full event list for replay
│       └── OperationsCounter.java  # Counts comparisons and swaps
│
└── View/                           # JavaFX UI layer
    ├── MainGUI.java                # Application window – landing page & scene switching
    ├── InputScene.java             # Input form (algorithm selection, array config, mode choice)
    ├── VisualizationView.java      # Animated bar-chart visualization scene
    ├── ComparisonView.java         # Statistics table scene
    ├── ChartView.java              # Reusable chart component
    └── Animatable.java             # Interface for scenes that support animation
```

---

## Architecture & Design Patterns

| Pattern | Where it is used |
|---|---|
| **Observer** | `AbstractSimulationSorting` (Observable) notifies `EventRecorder` and `OperationsCounter` (Observers) of every sorting event |
| **Strategy** | `SortingStrategy` interface allows swapping pure sorting implementations at runtime |
| **Factory** | `SortingAlgorithmsFactory` centralises creation of both pure and simulation algorithm objects |
| **Adaptor** | `ArrayAdaptor` / `ComparisonArrayAdaptor` convert heterogeneous array sources (auto-generated or file-loaded) into a uniform `ComparisonArray` format |
| **Callback / Functional Interface** | `AnimationCallback` and `SessionCallback` decouple the animation loop from the controller |
| **MVC** | `View` package handles rendering, `Controller` package handles user input and data preparation, `Model` package encapsulates all algorithmic logic |

---

## Prerequisites

| Requirement | Version |
|---|---|
| Java JDK | 24 |
| JavaFX | 17.0.6 (pulled automatically by Maven) |
| Maven | 3.x |

---

## Building & Running

### Using the Maven Wrapper (recommended)

```bash
# Windows
mvnw.cmd clean javafx:run

# macOS / Linux
./mvnw clean javafx:run
```

### Using a local Maven installation

```bash
mvn clean javafx:run
```

---

## Usage

1. **Launch** the application. The animated landing page presents two mode cards.
2. **Choose a mode:**
   - **Visualization Mode** – watch a single algorithm sort a single array frame by frame.
   - **Comparison Mode** – benchmark any combination of algorithms and array configurations.
3. **Configure the input:**
   - Toggle between *Auto Generated Array* and *Load arrays from file*.
   - For auto-generation, specify the **array size**, **max value**, and one or more **array types** (Random, Sorted, Reversed, Nearly Sorted).
   - Select one or more **algorithms** from the checklist.
4. **Start** – the application transitions to the appropriate view and begins sorting/benchmarking.
5. In **Visualization Mode**, use the on-screen menu (top-right corner, hover to reveal) to control playback speed or navigate between sessions.
6. In **Comparison Mode**, wait for the background thread to finish; the statistics table will populate automatically.

---

## Exporting Results

In **Comparison Mode**, once the table has loaded:

1. Click the **Export CSV** button in the toolbar.
2. Choose a save location in the file dialog.
3. The file is written with the following columns:

| Column | Description |
|---|---|
| Sorting Algorithm | Algorithm name |
| Array Size | Number of elements |
| Generation Mode | How the array was created |
| Number of Runs | Timing runs performed per configuration |
| Average Time | Mean execution time (nanoseconds) |
| Minimum Time | Fastest run (nanoseconds) |
| Maximum Time | Slowest run (nanoseconds) |
| Number of Comparisons | Total element comparisons |
| Number of Interchanges | Total element swaps / assignments |

---

## Data Analysis

The `analysis/` directory contains a self-contained analysis of the benchmark results:

| File | Description |
|---|---|
| `analysis/sample_data.csv` | Sample CSV exported from the application's Comparison Mode |
| `analysis/DataAnalysis.ipynb` | Jupyter notebook that loads the CSV, produces descriptive statistics, and plots performance comparisons across algorithms and array types |

### Running the notebook

```bash
# Install dependencies (first time only)
pip install pandas matplotlib notebook

# Launch Jupyter
jupyter notebook analysis/DataAnalysis.ipynb
```

The notebook expects `sample_data.csv` to be present in the same `analysis/` folder.

---

## Report

A full written report covering the design decisions, algorithm analysis, and benchmarking methodology is available in:

```
docs/report.pdf
```

