package interface_adapter.game;

import interface_adapter.ViewManagerModel;
import interface_adapter.end.EndViewModel;
import interface_adapter.start.StartViewModel;
import use_case.game.GameOutputBoundary;

public class GamePresenter implements GameOutputBoundary {
    private ViewManagerModel viewManagerModel;
    private GameViewModel gameViewModel;
    private StartViewModel startViewModel;
    private EndViewModel endViewModel;

    public GamePresenter(ViewManagerModel viewManagerModel, GameViewModel gameViewModel,
                         StartViewModel startViewModel, EndViewModel endViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.gameViewModel = gameViewModel;
        this.startViewModel = startViewModel;
        this.endViewModel = endViewModel;
    }

    @Override
    public void prepareGameView(GameState gameState) {
        /*
         * this might not be needed
         */
    }

    @Override
    public void prepareStartView() {
        viewManagerModel.setState(startViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareEndView(GameState gameState) { // this probably doesnt need gamestate
        final GameState endState = gameViewModel.getState();
        endViewModel.setState(endState);
        endViewModel.firePropertyChange();

        viewManagerModel.setState(endViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
