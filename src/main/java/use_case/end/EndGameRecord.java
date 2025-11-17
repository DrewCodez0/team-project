package use_case.end;

public class EndGameRecord {
    private final String word;
    private final boolean won;
    private final int guessesUsed;

    public EndGameRecord(String word, boolean won, int guessesUsed) {
        this.word = word;
        this.won = won;
        this.guessesUsed = guessesUsed;
    }
    public String getWord() { return word; }
    public boolean isWon() { return won; }
    public int getGuessesUsed() { return guessesUsed; }

}
