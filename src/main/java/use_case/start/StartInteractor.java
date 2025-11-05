package use_case.start;

import entity.Theme;
import interface_adapter.options.OptionsState;

public class StartInteractor implements StartInputBoundary {
    private final StartDataAccessInterface startDataAccess;
    private final StartOutputBoundary startPresenter;

    public StartInteractor(StartDataAccessInterface startDataAccess,
                           StartOutputBoundary startOutputBoundary) {
        this.startDataAccess = startDataAccess;
        this.startPresenter = startOutputBoundary;
    }

    @Override
    public void execute(Theme theme) {
        /*
        should probably get the theme from startdataaccessobject and send it to presenter
         */
    }

    @Override
    public void prepareGameView(OptionsState optionsState) {
        startPresenter.prepareGameView(optionsState);
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
