package interface_adapter.end;

import entity.AbstractWord;

public class EndState {
    private String word;
    private boolean won;
    private int guessesUsed;
    private int maxGuesses;
    private String message;
    private AbstractWord[] guessHistory;

    public EndState() {
        this.word = "";
        this.won = false;
        this.guessesUsed = 0;
        this.maxGuesses = 6;
        this.message = "";
        this.guessHistory = null;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public int getGuessesUsed() {
        return guessesUsed;
    }

    public void setGuessesUsed(int guessesUsed) {
        this.guessesUsed = guessesUsed;
    }

    public int getMaxGuesses() {
        return maxGuesses;
    }

    public void setMaxGuesses(int maxGuesses) {
        this.maxGuesses = maxGuesses;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AbstractWord[]  getGuessHistory() {
        return guessHistory;
    }

    public void setGuessHistory(AbstractWord[] guessHistory) {
        this.guessHistory = guessHistory;
    }
}
