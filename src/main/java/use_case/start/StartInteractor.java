package use_case.start;

import entity.Theme;

public class StartInteractor implements StartInputBoundary {
    private final StartDataAccessInterface startDataAccessObject;
    private final StartOutputBoundary startPresenter;

    public StartInteractor(StartDataAccessInterface startDataAccessInterface,
                           StartOutputBoundary startOutputBoundary) {
        this.startDataAccessObject = startDataAccessInterface;
        this.startPresenter = startOutputBoundary;
    }

    @Override
    public void execute(Theme theme) {
        /*
        should probably get the theme from startdataaccessobject and send it to presenter
         */
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
