package use_case.start;

import entity.Theme;

public class StartInteractor implements StartInputBoundary {
    private final StartDataAccessInterface startDataAccess;
    private final StartOutputBoundary startPresenter;

    public StartInteractor(StartDataAccessInterface startDataAccess,
                           StartOutputBoundary startOutputBoundary) {
        this.startDataAccess = startDataAccess;
        this.startPresenter = startOutputBoundary;
    }

    @Override
    public void prepareStartView() {
        final Theme theme = startDataAccess.getDefaultTheme();
        startPresenter.prepareStartView(theme);
    }

    @Override
    public void prepareGameView() {
        startPresenter.prepareGameView();
    }

    @Override
    public void prepareOptionsView() {
        startPresenter.prepareOptionsView();
    }

    @Override
    public void prepareStatsView() {
        startPresenter.prepareStatsView();
    }
}
