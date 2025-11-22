package interface_adapter.start;

import interface_adapter.options.OptionsState;
import use_case.start.StartInputBoundary;

public class StartController {
    private final StartInputBoundary startInteractor;

    public StartController(StartInputBoundary startInputBoundary) {
        this.startInteractor = startInputBoundary;
    }

    public void prepareStartView() {
        startInteractor.prepareStartView();
    }

    public void switchToGameView(OptionsState optionsState) {
        startInteractor.prepareGameView(optionsState);
    }

    public void switchToOptionsView() {
        startInteractor.prepareOptionsView();
    }

    public void switchToStatsView() {
        startInteractor.prepareStatsView();
    }
}
