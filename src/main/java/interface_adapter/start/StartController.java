package interface_adapter.start;

import data_access.Language;
import data_access.SettingsStore;
import entity.Theme;
import interface_adapter.game.GameState;
import interface_adapter.options.OptionsState;
import use_case.game.GameDataAccessInterface;
import use_case.start.StartInputBoundary;
import interface_adapter.game.GameViewModel;

import javax.swing.*;


public class StartController {
    private final StartInputBoundary startInteractor;
    private GameViewModel gameViewModel = null;
    private final GameDataAccessInterface gameDataAccess;


    public StartController(StartInputBoundary startInputBoundary, GameViewModel gameViewModel, GameDataAccessInterface gameDataAccess) {
        this.startInteractor = startInputBoundary;
        this.gameViewModel = gameViewModel;
        this.gameDataAccess = gameDataAccess;
    }

    public void prepareStartView() {
        startInteractor.prepareStartView();
    }

    public void switchToGameView(OptionsState optionsState) {
        startInteractor.prepareGameView(optionsState);
    }

    public void switchToOptionsView() {
        startInteractor.prepareOptionsView();
    }

    public void switchToStatsView() {
        startInteractor.prepareStatsView();
    }

    public void setGameViewModel(GameViewModel gameViewModel) {
        this.gameViewModel = gameViewModel;
    }

    public void startNewGame() {
        if (gameViewModel == null) {
            return;
        }

        OptionsState s = SettingsStore.get();
        String newWord= gameDataAccess.getRandomWord(s.getLength(), s.getLanguage());

        GameState gs = new GameState();
        gs.setLength(s.getLength());
        gs.setMaxGuesses(s.getMaxGuesses());
        gs.setLanguage(s.getLanguage());
        gs.setTheme(s.getTheme());
        gs.setWordToGuess(newWord);
        gs.resetWords();

        gameViewModel.setState(gs);
        gameViewModel.firePropertyChange();

        switchToGameView(s);
    }
}
