package interface_adapter.game;

import entity.AbstractLetter;
import entity.AbstractWord;
import entity.Status;
import entity.Word;

import java.util.Map;

public class GameState {
    private static final Map<Status, String> COLORS = Map.ofEntries(
            Map.entry(Status.INITIAL, "⬛"),
            Map.entry(Status.IN_PROGRESS, "⬛"),
            Map.entry(Status.WRONG, "⬛"),
            Map.entry(Status.PARTIAL, "\uD83D\uDFE8"),
            Map.entry(Status.CORRECT, "\uD83D\uDFE9")
    );
    private final int length;
    private final int maxGuesses;
    private int currentGuess;
    private int currentLetter;
    private final AbstractWord[] words;
    private final AbstractWord wordToGuess;

    public GameState() {
        this.wordToGuess = new Word("ERROR");
        this.length = 5;
        this.maxGuesses = 6;
        this.currentGuess = 0;
        this.currentLetter = -1;
        this.words = new AbstractWord[this.maxGuesses];
        for (int i = 0; i < this.maxGuesses; i++) {
            this.words[i] = new Word(this.length);
        }
    }

    public GameState(AbstractWord wordToGuess, int maxGuesses) {
        this.wordToGuess = wordToGuess;
        this.maxGuesses = maxGuesses;
        this.length = wordToGuess.length();
        this.currentGuess = 0;
        this.currentLetter = -1;
        this.words = new AbstractWord[this.maxGuesses];
        for (int i = 0; i < this.maxGuesses; i++) {
            this.words[i] = new Word(this.length);
        }
    }
    public GameState(AbstractWord wordToGuess, AbstractWord[] words) {
        this.wordToGuess = wordToGuess;
        this.length = wordToGuess.length();
        this.words = words;
        this.maxGuesses = words.length;
        this.currentGuess = 0;
        this.currentLetter = -1;
        while (this.currentGuess < this.maxGuesses && !this.words[this.currentGuess].isEmpty()) {
            this.currentGuess++;
        }
        while (this.currentLetter < this.length) {
            AbstractLetter letter = this.words[this.currentGuess].getLetter(this.currentLetter);
            if (letter.getStatus() == Status.INITIAL) {
                break;
            } else { // This might cause an error later
                this.currentLetter++;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (AbstractWord word : words) {
            for (int i = 0; i < word.length(); i++) {
                AbstractLetter letter = word.getLetter(i);
                sb.append(COLORS.get(letter.getStatus()));
            }
            sb.append("\n");
        }
        return sb.toString().trim();
    }

    public int getLength() {
        return length;
    }

    public int getMaxGuesses() {
        return maxGuesses;
    }

    public int getCurrentGuess() {
        return currentGuess;
    }

    public AbstractLetter getCurrentLetter() { // TODO make this return the letter and have separate for index
        return this.words[this.currentGuess].getLetter(this.currentLetter);
    }

    public int getCurrentLetterIndex() {
        return currentLetter;
    }

    public AbstractWord[] getWords() {
        return words;
    }

    public AbstractWord getWordToGuess() {
        return wordToGuess;
    }

    public void nextLetter(char c) {
        if (currentLetter < this.length - 1) {
            this.currentLetter++;
            getCurrentLetter().setCharacter(c);
        }
    }

    public void previousLetter() {
        if (currentLetter >= 0) {
            getCurrentLetter().resetCharacter();
            if (currentLetter == length) {
                this.currentLetter -= 2; // this shouldnt actually be reached i should probably remove it
            } else {
                this.currentLetter--;
            }
        }
    }

    // This assumes that the word is full
    private void nextWord() {
        this.currentLetter = -1;
        this.currentGuess++;
    }

    private Status[] checkGuess() { // maybe this shouldnt be in gamestate
        Status[] statuses = new Status[this.length];
        for (int i = 0; i < this.length; i++) {
            char guessChar = words[currentGuess].getLetter(i).getCharacter();
            char expectedChar = wordToGuess.getLetter(i).getCharacter();
            if (guessChar == expectedChar) {
                statuses[i] = Status.CORRECT;
            } else if (wordToGuess.toString().indexOf(guessChar) != -1) {
                statuses[i] = Status.PARTIAL;
            } else {
                statuses[i] = Status.WRONG;
            }
        }
        return statuses;
    }

    public void submit() {
        Status[] statuses = checkGuess();
        for (int i = 0; i < this.length; i++) {
            words[currentGuess].getLetter(i).setStatus(statuses[i]);
        }
        nextWord();
    }
}
