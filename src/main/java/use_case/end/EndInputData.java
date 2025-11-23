package use_case.end;

public class EndInputData {
    private final String word;
    private final boolean won;
    private final int guessesUsed;
    private final int maxGuesses;

    public EndInputData(String word, boolean won, int guessesUsed, int maxGuesses) {
        this.word = word;
        this.won = won;
        this.guessesUsed = guessesUsed;
        this.maxGuesses = maxGuesses;
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
}
