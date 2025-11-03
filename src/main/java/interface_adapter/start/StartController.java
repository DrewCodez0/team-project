package interface_adapter.start;

import entity.Theme;
import use_case.start.StartInputBoundary;

public class StartController {
    private final StartInputBoundary startInteractor;

    public StartController(StartInputBoundary startInputBoundary) {
        this.startInteractor = startInputBoundary;
    }

    public void execute(Theme theme) {
        startInteractor.execute(theme);
    }

    public void switchToGameView() {
        startInteractor.prepareGameView();
    }

    public void switchToOptionsView() {
        startInteractor.prepareOptionsView();
    }

    public void switchToStatsView() {
        startInteractor.prepareStatsView();
    }
}
