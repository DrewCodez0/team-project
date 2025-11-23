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

    public String getWord() {
        return word;
    }

    public boolean isWon() {
        return won;
    }

    public int getGuessesUsed() {
        return guessesUsed;
    }

    @Override
    public String toString() {
        return String.format("EndGameRecord{word=%s, won=%s, guessesUsed=%s}", word, won, guessesUsed);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        final EndGameRecord that = (EndGameRecord) obj;
        return won == that.won && guessesUsed == that.guessesUsed && word.equals(that.word);
    }

    @Override
    public int hashCode() {
        int result = word.hashCode();
        result = 31 * result + (won ? 1 : 0);
        result = 31 * result + guessesUsed;
        return result;
    }
}
