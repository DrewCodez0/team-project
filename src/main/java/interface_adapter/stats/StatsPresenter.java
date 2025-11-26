package interface_adapter.stats;

import interface_adapter.ViewManagerModel;
import interface_adapter.start.StartViewModel;
import use_case.stats.StatsOutputBoundary;
import use_case.stats.StatsOutputData;

public class StatsPresenter implements StatsOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final StatsViewModel statsViewModel;
    private final StartViewModel startViewModel;

    public StatsPresenter(ViewManagerModel viewManagerModel,
                          StatsViewModel statsViewModel,
                          StartViewModel startViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.statsViewModel = statsViewModel;
        this.startViewModel = startViewModel;
    }

    @Override
    public void prepareSuccessView(StatsOutputData outputData) {
        final StatsState statsState = new StatsState();
        statsState.setGamesPlayed(outputData.getStats().getGamesPlayed());
        statsState.setWinPercentage(outputData.getStats().getWinPercentage());
        statsState.setCurrentStreak(outputData.getStats().getCurrentStreak());
        statsState.setMaxStreak(outputData.getStats().getMaxStreak());
        statsViewModel.setState(statsState);
        statsViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String message) {
        final StatsState statsState = new StatsState();
        statsState.setError(message);
        statsViewModel.setState(statsState);
        statsViewModel.firePropertyChange();
    }

    @Override
    public void prepareStartView() {
        viewManagerModel.setState(startViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareExportSuccessView(String message) {
        final StatsState statsState = statsViewModel.getState();
        statsState.setExportMessage(message);
        statsViewModel.firePropertyChange();
    }

    @Override
    public void prepareExportFailView(String message) {
        final StatsState statsState = statsViewModel.getState();
        statsState.setExportMessage(message);
        statsViewModel.firePropertyChange();
    }
}
