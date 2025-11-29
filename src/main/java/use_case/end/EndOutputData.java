package use_case.end;

import entity.AbstractWord;

public class EndOutputData {
    private final String word;
    private final boolean won;
    private final int guessesUsed;
    private final int maxGuesses;
    private final String message;
    private final AbstractWord[] guessHistory;

    public EndOutputData(String word, boolean won, int guessesUsed, int maxGuesses,
                         AbstractWord[] guessHistory) {
        this.word = word;
        this.won = won;
        this.guessesUsed = guessesUsed;
        this.maxGuesses = maxGuesses;
        this.guessHistory = guessHistory;

        if (won) {
            this.message = generateWinMessage(guessesUsed);
        }
        else {
            this.message = "The word was: " + word;
        }
    }

    private String generateWinMessage(int guesses) {
        final String prefix;
        if (guesses == 1) {
            prefix = "First try!";
        }
        else if (guesses == maxGuesses) {
            prefix = "That was close!";
        }
        else {
            prefix = "";
        }
        return "<html><center>" + prefix + "<br>" + "The word was: " + word + "</center></html>";
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

    public String getMessage() {
        return message;
    }

    public AbstractWord[] getGuessHistory() {
        return guessHistory;
    }
}
