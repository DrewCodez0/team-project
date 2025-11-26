package interface_adapter.start;

import interface_adapter.stats.StatsController;
import use_case.start.StartInputBoundary;

public class StartController {
    private final StartInputBoundary startInteractor;
    private final StatsController statsController;

    public StartController(StartInputBoundary startInteractor, StatsController statsController) {
        this.startInteractor = startInteractor;
        this.statsController = statsController;
    }

    public void prepareStartView() {
        startInteractor.prepareStartView();
    }

    public void switchToGameView() {
        startInteractor.prepareGameView();
    }

    public void switchToOptionsView() {
        startInteractor.prepareOptionsView();
    }

    public void switchToStatsView() {
        statsController.execute();
        startInteractor.prepareStatsView();
    }
}
