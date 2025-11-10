package interface_adapter.start;

import interface_adapter.ViewManagerModel;
import interface_adapter.game.GameState;
import interface_adapter.game.GameViewModel;
import interface_adapter.options.OptionsState;
import interface_adapter.options.OptionsViewModel;
import interface_adapter.stats.StatsViewModel;
import use_case.start.StartOutputBoundary;

public class StartPresenter implements StartOutputBoundary {
    private ViewManagerModel viewManagerModel;
    private StartViewModel startViewModel;
    private GameViewModel gameViewModel;
    private OptionsViewModel optionsViewModel;
    private StatsViewModel statsViewModel;

    public StartPresenter(ViewManagerModel viewManagerModel,
                          StartViewModel startViewModel,
                          GameViewModel gameViewModel,
                          OptionsViewModel optionsViewModel,
                          StatsViewModel statsViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.startViewModel = startViewModel;
        this.gameViewModel = gameViewModel;
        this.optionsViewModel = optionsViewModel;
        this.statsViewModel = statsViewModel;
    }

    @Override
    public void prepareGameView(OptionsState optionsState) {
        gameViewModel.setState(new GameState());
        gameViewModel.firePropertyChange();

        viewManagerModel.setState(gameViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareOptionsView() {
        viewManagerModel.setState(optionsViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareStatsView() {
        viewManagerModel.setState(statsViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
