module org.example.sortingalgorithmsvisualization {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.sortingalgorithmsvisualization to javafx.fxml;
    opens org.example.sortingalgorithmsvisualization.View to javafx.fxml, javafx.base;
    opens org.example.sortingalgorithmsvisualization.Model to javafx.base;
    exports org.example.sortingalgorithmsvisualization;
    exports org.example.sortingalgorithmsvisualization.View;
    exports org.example.sortingalgorithmsvisualization.Model.Events;
    exports org.example.sortingalgorithmsvisualization.Model.SimulationSorting;
    exports org.example.sortingalgorithmsvisualization.Model.PureSorting;
    exports org.example.sortingalgorithmsvisualization.Controller;
    exports org.example.sortingalgorithmsvisualization.Model;
    exports org.example.sortingalgorithmsvisualization.Model.Observers;

}