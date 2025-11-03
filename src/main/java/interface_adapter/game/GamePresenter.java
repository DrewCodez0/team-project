package interface_adapter.game;

import interface_adapter.ViewManagerModel;
import interface_adapter.end.EndViewModel;
import use_case.game.GameOutputBoundary;

public class GamePresenter implements GameOutputBoundary {
    private ViewManagerModel viewManagerModel;
    private GameViewModel gameViewModel;
    private EndViewModel endViewModel;

    public GamePresenter(ViewManagerModel viewManagerModel,
                         GameViewModel gameViewModel,
                         EndViewModel  endViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.gameViewModel = gameViewModel;
        this.endViewModel = endViewModel;
    }

    public void prepareGameView(GameState gameState) {
        /*
         * this might not be needed
         */
    }

    public void prepareEndView(GameState gameState) { // this probably doesnt need gamestate
        final GameState endState = gameViewModel.getState();
        endViewModel.setState(endState);
        endViewModel.firePropertyChange();

        viewManagerModel.setState(endViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
