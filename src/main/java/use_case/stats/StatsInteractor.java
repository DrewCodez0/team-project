package use_case.stats;

import entity.Theme;


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

    @Override
    public void prepareStartView() {
        statsOutputBoundary.prepareStartView(statsDataAccess.getDefaultTheme());
    }

}
