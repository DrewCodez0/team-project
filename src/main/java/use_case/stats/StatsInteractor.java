package use_case.stats;

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
        try{
            StatsState stats = statsDataAccess.getStats();

            if(stats.getTotalGames() == 0){
                StatsOutputData outputData = new StatsOutputData(stats, false);
                ((StatsPresenter) statsOutputBoundary).setOutputData(outputData);
                statsOutputBoundary.prepareDefaultView();
            } else {
                StatsOutputData outputData = new StatsOutputData(stats, true);
                ((StatsPresenter) statsOutputBoundary).setOutputData(outputData);
                statsOutputBoundary.prepareDefaultView();
            }
        }catch (Exception e){
            StatsOutputData outputData = new StatsOutputData(new StatsState(), false);
            ((StatsPresenter) statsOutputBoundary).setOutputData(outputData);
            statsOutputBoundary.prepareFailView();
        }
    }
}
