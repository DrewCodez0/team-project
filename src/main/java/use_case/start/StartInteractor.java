package use_case.start;

import data_access.WordNotFoundException;
import entity.AbstractWord;
import entity.Theme;
import entity.Word;
import interface_adapter.game.GameState;
import interface_adapter.options.OptionsState;
import use_case.game.GameDataAccessInterface;

public class StartInteractor implements StartInputBoundary {
    private final StartDataAccessInterface startDataAccess;
    private final GameDataAccessInterface gameDataAccess;
    private final StartOutputBoundary startPresenter;

    public StartInteractor(StartDataAccessInterface startDataAccess,
                           GameDataAccessInterface gameDataAccess,
                           StartOutputBoundary startOutputBoundary) {
        this.startDataAccess = startDataAccess;
        this.gameDataAccess = gameDataAccess;
        this.startPresenter = startOutputBoundary;
    }

    @Override
    public void prepareStartView() {
        final Theme theme = startDataAccess.getDefaultTheme();
        startPresenter.prepareStartView(theme);
    }

    @Override
    public void prepareGameView(OptionsState optionsState) {
        // TODO put all of this into GameInteractor
        try {
            final String word = gameDataAccess.getRandomWord(optionsState.getLength(), optionsState.getLanguage());
            // System.out.println(word);
            final AbstractWord theword = new Word(word); // TODO replace this with a wordfactory
            final GameState gameState = new GameState(theword, optionsState.getMaxGuesses());
            startPresenter.prepareGameView(gameState);
        }
        catch (WordNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void prepareOptionsView() {
        startPresenter.prepareOptionsView();
    }

    @Override
    public void prepareStatsView() {
        startPresenter.prepareStatsView();
    }
}
