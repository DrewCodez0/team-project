package interface_adapter.end;

import interface_adapter.ViewManagerModel;
import interface_adapter.game.GameViewModel;
import interface_adapter.options.OptionsState;
import interface_adapter.options.OptionsViewModel;
import interface_adapter.start.StartViewModel;
import use_case.end.EndOutputBoundary;
import use_case.end.EndOutputData;

public class EndPresenter implements EndOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final EndViewModel endViewModel;
    private final GameViewModel gameViewModel;
    private final StartViewModel startViewModel;
    //private final OptionsViewModel optionsViewModel;

    public EndPresenter(ViewManagerModel viewManagerModel, EndViewModel endViewModel,
                        GameViewModel gameViewModel, StartViewModel startViewModel,
                        OptionsViewModel optionsViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.endViewModel = endViewModel;
        this.gameViewModel = gameViewModel;
        this.startViewModel = startViewModel;
        //this.optionsViewModel = optionsViewModel;
    }

    @Override
    public void prepareSuccessView(EndOutputData endOutputData) {
        final EndState endState = endViewModel.getState();
        endState.setWord(endOutputData.getWord());
        endState.setWon(true);
        endState.setGuessesUsed(endOutputData.getGuessesUsed());
        endState.setMaxGuesses(endOutputData.getMaxGuesses());
        endState.setMessage(endOutputData.getMessage());

        endViewModel.setState(endState);
        endViewModel.firePropertyChange();

        viewManagerModel.setState(endViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(EndOutputData outputData) {
        final EndState endState = endViewModel.getState();
        endState.setWord(outputData.getWord());
        endState.setWon(false);
        endState.setGuessesUsed(outputData.getGuessesUsed());
        endState.setMaxGuesses(outputData.getMaxGuesses());
        endState.setMessage(outputData.getMessage());

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
