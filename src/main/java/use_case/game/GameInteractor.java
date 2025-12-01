package use_case.game;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import entity.AbstractWord;
import entity.WordFactory;
import interface_adapter.game.GameState;
import interface_adapter.options.OptionsState;
import interface_adapter.options.OptionsViewModel;
import use_case.end.EndInputBoundary;
import use_case.end.EndInputData;

public class GameInteractor implements GameInputBoundary {
    private final GameDataAccessInterface gameDataAccess;
    private final GameOutputBoundary gamePresenter;
    private final EndInputBoundary endInteractor;
    private final OptionsViewModel optionsViewModel;

    public GameInteractor(GameDataAccessInterface gameDataAccess, GameOutputBoundary gameOutputBoundary,
                          EndInputBoundary endInputBoundary, OptionsViewModel optionsViewModel) {
        this.gameDataAccess = gameDataAccess;
        this.gamePresenter = gameOutputBoundary;
        this.endInteractor = endInputBoundary;
        this.optionsViewModel = optionsViewModel;
    }

    @Override
    public void executeLetter(GameState gameInputData, char letter) {
        gameInputData.nextLetter(letter);
        gamePresenter.updateGameView(gameInputData);
    }

    @Override
    public void executeSubmit(GameState gameInputData) {
        final int guess = gameInputData.getCurrentGuess();
        if (gameInputData.getWords()[guess].isFull()) {
            final String word = gameInputData.getWords()[guess].toString();
            if (gameDataAccess.isValidWord(word, gameInputData.getLanguage())) {
                gameInputData.submit();
                gamePresenter.updateGameView(gameInputData);
                if (gameInputData.finished()) {
                    copyScoreToClipboard(gameInputData);
                    prepareEndView(gameInputData);
                }
                else {
                    gameInputData.nextWord();
                }
            }
            else {
                gamePresenter.shakeWord(gameInputData);
            }
        }
    }

    @Override
    public void executeBackspace(GameState gameInputData) {
        gameInputData.previousLetter();
        gamePresenter.updateGameView(gameInputData);
    }

    @Override
    public void prepareNewGame() {
        final OptionsState optionsState = optionsViewModel.getState();
        final String word = gameDataAccess.getRandomWord(optionsState.getLength(), optionsState.getLanguage());
        final GameState gameState = new GameState(WordFactory.createWordToGuess(word), optionsState.getMaxGuesses());
        gamePresenter.updateGameView(gameState);
    }

    @Override
    public void prepareStartView() {
        gamePresenter.prepareStartView();
    }

    @Override
    public void prepareEndView(GameState gameState) {
        final String word = gameState.getWordToGuess().toString();
        final boolean won = gameState.getWords()[gameState.getCurrentGuess()].isCorrect();
        final int guessesUsed = gameState.getCurrentGuess() + 1;
        final int maxGuesses = gameState.getMaxGuesses();
        final AbstractWord[] guessHistory = gameState.getWords();

        final EndInputData endInputData = new EndInputData(word, won, guessesUsed, maxGuesses, guessHistory);
        endInteractor.execute(endInputData);
    }

    private void copyScoreToClipboard(GameState state) {
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(state.toString()), null);
    }
}
