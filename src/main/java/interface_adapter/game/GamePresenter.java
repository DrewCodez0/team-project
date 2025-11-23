package interface_adapter.game;

import interface_adapter.ViewManagerModel;
import interface_adapter.end.EndViewModel;
import interface_adapter.start.StartViewModel;
import use_case.game.GameOutputBoundary;

public class GamePresenter implements GameOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final GameViewModel gameViewModel;
    private final StartViewModel startViewModel;
    private final EndViewModel endViewModel;

    public GamePresenter(ViewManagerModel viewManagerModel, GameViewModel gameViewModel,
                         StartViewModel startViewModel, EndViewModel endViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.gameViewModel = gameViewModel;
        this.startViewModel = startViewModel;
        this.endViewModel = endViewModel;
    }

    @Override
    public void updateGameView(GameState gameState) {
        gameViewModel.setState(gameState); // this is entirely unnecessary
        gameViewModel.firePropertyChange();
    }

    @Override
    public void shakeWord(GameState gameState) {
        gameViewModel.firePropertyChange(GameViewModel.SHAKE);
    }

    @Override
    public void prepareStartView() {
        viewManagerModel.setState(startViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareEndView(GameState gameState) {
        viewManagerModel.setState(endViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
