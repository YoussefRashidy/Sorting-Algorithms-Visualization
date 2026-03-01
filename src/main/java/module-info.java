module org.example.sortingalgorithmsvisualization {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.sortingalgorithmsvisualization to javafx.fxml;
    exports org.example.sortingalgorithmsvisualization;
    exports org.example.sortingalgorithmsvisualization.View;
    exports org.example.sortingalgorithmsvisualization.Model.Events;
    exports org.example.sortingalgorithmsvisualization.Model.SimulationSorting.Observer;
    exports org.example.sortingalgorithmsvisualization.Model.PureSorting;
    exports org.example.sortingalgorithmsvisualization.Controller;
    exports org.example.sortingalgorithmsvisualization.Model;

}