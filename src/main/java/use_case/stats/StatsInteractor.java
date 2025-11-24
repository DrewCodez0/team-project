package use_case.stats;

import entity.Theme;
import interface_adapter.stats.StatsPresenter;
import interface_adapter.stats.StatsState;


public class StatsInteractor implements StatsInputBoundary {
    private final StatsDataAccessInterface statsDataAccess;
    private final StatsOutputBoundary statsOutputBoundary;

    public StatsInteractor(StatsDataAccessInterface statsDataAccess, StatsOutputBoundary statsOutputBoundary) {
        this.statsDataAccess = statsDataAccess;
        this.statsOutputBoundary = statsOutputBoundary;
    }

    @Override
    public void execute(StatsInputData inputData) {
        final Theme theme = statsDataAccess.getDefaultTheme();
        statsOutputBoundary.prepareSuccessView(theme);
    }
}
