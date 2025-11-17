package use_case.end;

import data_access.Language;
import entity.AbstractWord;
import interface_adapter.game.GameState;

public class EndInteractor implements EndInputBoundary {

    private final EndDataAccessInterface endDataAccess;
    private final EndOutputBoundary presenter;

    public EndInteractor(EndDataAccessInterface endDataAccess, EndOutputBoundary presenter) {
        this.endDataAccess = endDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(GameState gameState) {
        String wordToGuess = gameState.getWordToGuess().toString();
        int maxGuesses = gameState.getMaxGuesses();
        int guessesUsed = gameState.getCurrentGuess();
        Language language = gameState.getLanguage();
        boolean won = isWon(gameState);

        EndGameRecord record = new EndGameRecord(
                wordToGuess,
                won,
                guessesUsed,
                maxGuesses
        );
        endDataAccess.saveEndGame(record);

        EndOutputData outputData = new EndOutputData(wordToGuess, won, guessesUsed, maxGuesses);
    }
    private boolean playerWon(GameState state) {
        for (AbstractWord word : state.getWords()) {
            if (word != null && word.isCorrect()) {
                return true;
            }
        }
        return false;
    }
}



