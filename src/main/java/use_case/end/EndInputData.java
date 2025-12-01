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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EndInputData)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        final EndInputData other = (EndInputData) obj;
        for (int i = 0; i < this.guessHistory.length; i++) {
            if (!this.guessHistory[i].equals(other.guessHistory[i])) {
                return false;
            }
        }
        final boolean w = this.word.equals(other.word);
        final boolean wn = this.won == other.won;
        final boolean gu = this.guessesUsed == other.guessesUsed;
        final boolean mg = this.maxGuesses == other.maxGuesses;
        final boolean ghl = this.guessHistory.length == other.guessHistory.length;

        return w && wn && gu && mg && ghl;
    }
}
