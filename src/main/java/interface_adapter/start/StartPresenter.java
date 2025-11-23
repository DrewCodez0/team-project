package interface_adapter.start;

import entity.Theme;
import interface_adapter.ViewManagerModel;
import interface_adapter.game.GameViewModel;
import interface_adapter.options.OptionsViewModel;
import interface_adapter.stats.StatsViewModel;
import use_case.start.StartOutputBoundary;

public class StartPresenter implements StartOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final StartViewModel startViewModel;
    private final GameViewModel gameViewModel;
    private final OptionsViewModel optionsViewModel;
    private final StatsViewModel statsViewModel;

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
    public void prepareStartView(Theme theme) {
        startViewModel.setState(theme);
        startViewModel.firePropertyChange();
    }

    @Override
    public void prepareGameView() {
        gameViewModel.firePropertyChange(GameViewModel.NEW_GAME);

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
