package interface_adapter.stats;

import entity.Theme;
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
    public void prepareSuccessView(Theme theme) {
        statsViewModel.setState(theme);
        statsViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView() {
        prepareDefaultView();
    }

    @Override
    public void prepareDefaultView() {
        statsViewModel.setState(null);
        statsViewModel.firePropertyChange();
    }

    @Override
    public void prepareStartView() {
        viewManagerModel.setState(startViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}