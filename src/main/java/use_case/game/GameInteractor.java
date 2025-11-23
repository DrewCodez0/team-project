package use_case.game;

import interface_adapter.game.GameState;
import use_case.end.EndInputBoundary;
import use_case.end.EndInputData;


public class GameInteractor implements GameInputBoundary {
    private final GameDataAccessInterface gameDataAccess;
    private final GameOutputBoundary gamePresenter;
    private final EndInputBoundary endInteractor;

    public GameInteractor(GameDataAccessInterface gameDataAccess, GameOutputBoundary gameOutputBoundary, EndInputBoundary endInputBoundary) {
        this.gameDataAccess = gameDataAccess;
        this.gamePresenter = gameOutputBoundary;
        this.endInteractor = endInputBoundary;
    }

    @Override
    public void executeLetter(GameState gameInputData, char letter) {
        gameInputData.nextLetter(letter);
        gamePresenter.updateGameView(gameInputData);
//        if (gameInputData.getWordToGuess() == null) {
//            try {
//                gameDataAccess.getRandomWord(gameInputData.getLength(), gameInputData.getLanguage());
//            } catch (WordNotFoundException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//        System.out.println(gameDataAccess.getRandomWord(5, "en"));
    }

    @Override
    public void executeSubmit(GameState gameInputData) { // TODO check if at the end or if correct and switch to end
        final int guess = gameInputData.getCurrentGuess();
        if (gameInputData.getWords()[guess].isFull()) {
            final String word = gameInputData.getWords()[guess].toString();
            if (gameDataAccess.isValidWord(word, gameInputData.getLanguage())) {
                gameInputData.submit();
                gamePresenter.updateGameView(gameInputData);
                if (gameInputData.finished()) {
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
    public void prepareStartView() {
        gamePresenter.prepareStartView();
    }

    @Override
    public void prepareEndView(GameState gameState) {
        String word = gameState.getWordToGuess().toString();
        boolean won = gameState.getCurrentGuess() < gameState.getMaxGuesses() &&
                gameState.getWords()[gameState.getCurrentGuess()].isCorrect();
        int guessesUsed = gameState.getCurrentGuess() + 1;
        int maxGuesses = gameState.getMaxGuesses();

        EndInputData endInputData = new EndInputData(word, won, guessesUsed, maxGuesses);
        endInteractor.execute(endInputData);
    }
}
