package org.example.sortingalgorithmsvisualization.Model;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import org.example.sortingalgorithmsvisualization.Model.Events.*;
import org.example.sortingalgorithmsvisualization.Model.SimulationSorting.Observer.AbstractSimulationSorting;
import org.example.sortingalgorithmsvisualization.Model.Observers.EventRecorder;
import org.example.sortingalgorithmsvisualization.Model.Observers.OperationsCounter;
import org.example.sortingalgorithmsvisualization.View.VisualizationView;

import java.util.ArrayList;

public class VisualizationManager {
    int[] nums ;
    AbstractSimulationSorting sort ;
    EventRecorder recorder  ;
    OperationsCounter counter ;
    VisualizationView visualizationView ;
    ArrayList<SortingEvent> events ;
    int currentEventIndex = 0 ;
    SessionCallback onSessionFinish ;

    public int[] getNums() {
        return nums;
    }

    public void setNums(int[] nums) {
        this.nums = nums;
    }

    public AbstractSimulationSorting getSort() {
        return sort;
    }

    public void setSort(AbstractSimulationSorting sort) {
        this.sort = sort;
    }

    // Methods to play the visualization

    private void attachObservers() {
        recorder = new EventRecorder() ;
        counter = new OperationsCounter() ;
        sort.register(recorder);
        sort.register(counter);
    }

    public void startVisualization(SessionCallback onSessionFinish) {
        this.onSessionFinish = onSessionFinish ;
        attachObservers();
        sort.sort(nums.clone()) ;
        events = (ArrayList<SortingEvent>) recorder.getEventList();
        currentEventIndex = 0 ;
        visualizationView.setNormalizedNums(nums);
//        visualizationView.setManager(this);
        visualizationView.initializeView();

        // a small delay such that the view fully loads before starting animation
        PauseTransition beforePlayPause = new PauseTransition(Duration.millis(1000)) ;
        beforePlayPause.setOnFinished(e-> playNext());
        beforePlayPause.play();
    }

    public void playNext() {

        if (currentEventIndex < events.size()) {
            AnimationCallback onFinish = () -> {
                currentEventIndex++ ;
                playNext();
            } ;
            switch (events.get(currentEventIndex)) {
                case ComparisonEvent cp -> visualizationView.onComparison(cp,onFinish);
                case SetEvent cp -> visualizationView.onSet(cp,onFinish);
                case SwapEvent cp -> visualizationView.onInterchange(cp,onFinish);
                case MergeEvent me -> visualizationView.onMerge(me,onFinish);
                case DivideEvent dv -> visualizationView.onDivide(dv, onFinish);
                case SortedEvent so -> visualizationView.onSort(so, onFinish);
                case PartitionEvent pa -> visualizationView.onPartition(pa, onFinish);
//                case ComparisonEvent cp -> visualizationView.onComparison(cp);
                default -> throw new IllegalArgumentException("Unsupported event") ;
            }
        }
        else {
            //TODO instruct the view to pop up windows showing num of comparisons
            visualizationView.finishPopUp("Coming soon", counter.getComparison(), counter.getSwap(),this.onSessionFinish);
        }
    }

    public VisualizationView getVisualizationView() {
        return visualizationView;
    }

    public void setVisualizationView(VisualizationView visualizationView) {
        this.visualizationView = visualizationView;
    }
}
