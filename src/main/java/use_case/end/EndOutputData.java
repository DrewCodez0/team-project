package use_case.end;

public class EndOutputData {
    private final String word;
    private final boolean won;
    private final int guessesUsed;
    private final int maxGuesses;
    private final String message;

    public EndOutputData(String word, boolean won, int guessesUsed, int maxGuesses) {
        this.word = word;
        this.won = won;
        this.guessesUsed = guessesUsed;
        this.maxGuesses = maxGuesses;

        if (won) {
            this.message = generateWinMessage(guessesUsed);
        }
        else {
            this.message = "You lost! The word was: " + word;
        }
    }

    private String generateWinMessage(int guesses) {
        if (guesses == 1) {
            return "First try! The word was: " + word;
        }
        else if (guesses ==  maxGuesses) {
            return "That was close! The word was: " + word;
        }
        else {
            return "You won! The word was: " + word;
        }
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
}
