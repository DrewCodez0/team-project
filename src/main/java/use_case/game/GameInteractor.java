package use_case.game;

import interface_adapter.game.GameState;

public class GameInteractor implements GameInputBoundary {
    private final GameDataAccessInterface gameDataAccess;
    private final GameOutputBoundary gamePresenter;

    public GameInteractor(GameDataAccessInterface gameDataAccess, GameOutputBoundary gameOutputBoundary) {
        this.gameDataAccess = gameDataAccess;
        this.gamePresenter = gameOutputBoundary;
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
        int guess = gameInputData.getCurrentGuess();
        if (gameInputData.getWords()[guess].isFull()) {
            String word = gameInputData.getWords()[guess].toString();
            if (gameDataAccess.isValidWord(word, gameInputData.getLanguage())) {
                gameInputData.submit();
                gamePresenter.updateGameView(gameInputData);
                if (gameInputData.finished()) {
                    prepareEndView(gameInputData);
                }
            } else {
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
        gamePresenter.prepareEndView(gameState);
    }
}
