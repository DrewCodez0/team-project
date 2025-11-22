package interface_adapter.end;

public class EndState {
    private String word;
    private Boolean won;
    private int guessesUsed;
    private int maxGuesses;
    private String message;

    public EndState() {
        this.word = "";
        this.won = false;
        this.guessesUsed = 0;
        this.maxGuesses = 6;
        this.message = "";
    }
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public Boolean isWon() {
        return won;
    }
    public void setWon(Boolean won) {
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
}
