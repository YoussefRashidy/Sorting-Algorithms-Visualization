module org.example.sortingalgorithmsvisualization {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.sortingalgorithmsvisualization to javafx.fxml;
    exports org.example.sortingalgorithmsvisualization;
    exports org.example.sortingalgorithmsvisualization.View;

}