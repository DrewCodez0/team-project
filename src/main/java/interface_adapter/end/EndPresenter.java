package interface_adapter.end;

import interface_adapter.ViewManagerModel;
import interface_adapter.game.GameViewModel;
import interface_adapter.start.StartViewModel;
import use_case.end.EndOutputBoundary;
import use_case.end.EndOutputData;

public class EndPresenter implements EndOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final EndViewModel endViewModel;
    private final GameViewModel gameViewModel;
    private final StartViewModel startViewModel;

    public EndPresenter(ViewManagerModel viewManagerModel, EndViewModel endViewModel,
                        GameViewModel gameViewModel, StartViewModel startViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.endViewModel = endViewModel;
        this.gameViewModel = gameViewModel;
        this.startViewModel = startViewModel;
    }

    @Override
    public void prepareSuccessView(EndOutputData endOutputData) {
        final EndState endState = endViewModel.getState();
        endState.setWon(true);
        prepareBaseView(endOutputData, endState);
    }

    @Override
    public void prepareFailView(EndOutputData endOutputData) {
        final EndState endState = endViewModel.getState();
        endState.setWon(false);
        prepareBaseView(endOutputData, endState);
    }

    private void prepareBaseView(EndOutputData endOutputData, EndState endState) {
        endState.setWord(endOutputData.getWord());
        endState.setGuessesUsed(endOutputData.getGuessesUsed());
        endState.setMaxGuesses(endOutputData.getMaxGuesses());
        endState.setMessage(endOutputData.getMessage());
        endState.setGuessHistory(endOutputData.getGuessHistory());

        endViewModel.setState(endState);
        endViewModel.firePropertyChange();

        viewManagerModel.setState(endViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareGameView() {
        gameViewModel.firePropertyChange(GameViewModel.NEW_GAME);

        viewManagerModel.setState(gameViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareStartView() {
        viewManagerModel.setState(startViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
