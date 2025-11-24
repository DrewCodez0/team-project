package interface_adapter.stats;

import use_case.stats.StatsInputBoundary;
import use_case.stats.StatsInputData;

public class StatsController {
    private final StatsInputBoundary statsInteractor;

    public StatsController(StatsInputBoundary statsInteractor) {
        this.statsInteractor = statsInteractor;
    }

    /**
     * Executes the primary action to load and display statistics.
     */
    public void execute() {
        statsInteractor.execute(new StatsInputData());
    }

    /**
     * Initiates the process of switching back to the start menu view.
     */
    public void switchToStartView() {
        statsInteractor.prepareStartView();
    }
}
