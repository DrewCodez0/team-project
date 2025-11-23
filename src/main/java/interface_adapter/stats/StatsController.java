package interface_adapter.stats;

import use_case.stats.StatsInputBoundary;
import use_case.stats.StatsInputData;
import use_case.stats.StatsOutputBoundary;

public class StatsController {
    private final StatsInputBoundary statsInteractor;
    private final StatsOutputBoundary statsOutputBoundary; // For view navigation

    public StatsController(StatsInputBoundary statsInteractor, StatsOutputBoundary statsOutputBoundary) {
        this.statsInteractor = statsInteractor;
        this.statsOutputBoundary = statsOutputBoundary;
    }

    public void execute() {
        statsInteractor.execute(new StatsInputData());
    }

    public void switchToStartView() {
        statsOutputBoundary.prepareStartView();
    }
}