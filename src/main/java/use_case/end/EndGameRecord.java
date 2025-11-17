package use_case.end;

public class EndGameRecord {
    private final String wordToGuess;
    private final boolean won;
    private final int guessesUsed;
    private final int maxGuesses;

    public EndGameRecord(String wordToGuess,
                         boolean won,
                         int guessesUsed,
                         int maxGuesses) {

        this.wordToGuess = wordToGuess;
        this.won = won;
        this.guessesUsed = guessesUsed;
        this.maxGuesses = maxGuesses;

    }
    public String getWordToGuess() {
        return wordToGuess;
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
