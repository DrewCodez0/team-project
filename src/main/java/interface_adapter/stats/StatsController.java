package interface_adapter.stats;

import use_case.stats.StatsInputBoundary;
import use_case.stats.StatsInputData;

public class StatsController {
    private final StatsInputBoundary statsInteractor;

    public StatsController(StatsInputBoundary statsInteractor) {
        this.statsInteractor = statsInteractor;
    }

    public void execute() {
        statsInteractor.execute(new StatsInputData());
    }

    public void switchToStartView() {
        statsInteractor.prepareStartView();
    }
}