package interface_adapter.stats;

import interface_adapter.ViewManagerModel;
import interface_adapter.start.StartViewModel;
import use_case.stats.StatsOutputBoundary;

public class StatsPresenter implements StatsOutputBoundary {

    public StatsPresenter(ViewManagerModel viewManagerModel,
                          StatsViewModel statsViewModel,
                          StartViewModel startViewModel) {

    }

    @Override
    public void prepareSuccessView() {}

    @Override
    public void prepareFailView() {}

    @Override
    public void prepareDefaultView() {}

    @Override
    public void prepareStartView() {}
}
