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
        this.wordToGuess = new Word("error");
        this.length = 5;
        this.maxGuesses = 6;
        this.currentGuess = 0;
        this.currentLetter = 0;
        this.words = new AbstractWord[length];
        for (int i = 0; i < length; i++) {
            this.words[i] = new Word(this.length);
        }
    }

    public GameState(AbstractWord wordToGuess, int maxGuesses) {
        this.wordToGuess = wordToGuess;
        this.maxGuesses = maxGuesses;
        this.length = wordToGuess.length();
        this.currentGuess = 0;
        this.currentLetter = 0;
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
        this.currentLetter = 0;
        while (this.currentGuess < this.maxGuesses && !this.words[this.currentGuess].isEmpty()) {
            this.currentGuess++;
        }
        while (this.currentLetter < this.length) {
            AbstractLetter letter = this.words[this.currentGuess].getLetter(this.currentLetter);
            if (letter.getStatus() == Status.INITIAL) {
                break;
            } else {
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

    public AbstractWord[] getWords() {
        return words;
    }

    public AbstractWord getWordToGuess() {
        return wordToGuess;
    }
}
