package use_case.end;

import entity.AbstractWord;

public class EndInputData {
    private final String word;
    private final boolean won;
    private final int guessesUsed;
    private final int maxGuesses;
    private final AbstractWord[] guessHistory;

    public EndInputData(String word, boolean won, int guessesUsed, int maxGuesses,
                        AbstractWord[] guessHistory) {
        this.word = word;
        this.won = won;
        this.guessesUsed = guessesUsed;
        this.maxGuesses = maxGuesses;
        this.guessHistory = guessHistory;
    }

    public String getWord() {
        return word;
    }

    public boolean isWon() {
        return won;
    }

    public int getGuessesUsed() {
        return guessesUsed;
    }

    public int getMaxGuesses() {
        return maxGuesses;
    }

    public AbstractWord[] getGuessHistory() {
        return guessHistory;
    }
}
