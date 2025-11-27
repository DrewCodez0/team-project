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

    /**
     * Executes the primary action to load start view of the game.
     */
    public void prepareStartView() {
        startInteractor.prepareStartView();
    }

    /**
     * Initiates the process of switching to the game view.
     */
    public void switchToGameView() {
        startInteractor.prepareGameView();
    }

    /**
     * Initiates the process of switching to the options view.
     */
    public void switchToOptionsView() {
        startInteractor.prepareOptionsView();
    }

    /**
     * Initiates the process of switching to the stats view.
     */
    public void switchToStatsView() {
        statsController.execute();
        startInteractor.prepareStatsView();
    }
}
