package interface_adapter.stats;

import interface_adapter.ViewManagerModel;
import interface_adapter.start.StartViewModel;
import use_case.stats.StatsOutputBoundary;
import use_case.stats.StatsOutputData;

public class StatsPresenter implements StatsOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final StatsViewModel statsViewModel;
    private final StartViewModel startViewModel;
    private StatsOutputData outputData;

    public StatsPresenter(ViewManagerModel viewManagerModel,
                          StatsViewModel statsViewModel,
                          StartViewModel startViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.statsViewModel = statsViewModel;
        this.startViewModel = startViewModel;
    }

    public void setOutputData(StatsOutputData outputData) {
        this.outputData = outputData;
    }

    @Override
    public void prepareSuccessView() {
        if (outputData != null && outputData.hasStats()) {
            statsViewModel.setState(outputData.getStatsState());
            statsViewModel.firePropertyChange();
        }
    }

    @Override
    public void prepareFailView() {
        prepareDefaultView();
    }

    @Override
    public void prepareDefaultView() {
        if (outputData != null) {
            statsViewModel.setState(outputData.getStatsState());
        } else {
            statsViewModel.setState(new StatsState());
        }
        statsViewModel.firePropertyChange();
    }

    @Override
    public void prepareStartView() {
        viewManagerModel.setState(startViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}