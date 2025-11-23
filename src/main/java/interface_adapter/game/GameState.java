package interface_adapter.game;

import java.util.Map;

import data_access.Language;
import entity.AbstractLetter;
import entity.AbstractWord;
import entity.Status;
import entity.WordFactory;

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
    private final Language language;
    private int currentGuess;
    private int currentLetter;
    private final AbstractWord[] words;
    private final AbstractWord wordToGuess;

    public GameState() {
        this.wordToGuess = WordFactory.createDefaultWord();
        this.length = 5; // TODO get these from optionsstate once its made
        this.maxGuesses = 6;
        this.language = Language.ENGLISH;
        this.currentGuess = 0;
        this.currentLetter = -1;
        this.words = new AbstractWord[this.maxGuesses];
        initializeEmptyWords();
    }

    public GameState(AbstractWord wordToGuess, int maxGuesses, Language language) {
        this.wordToGuess = wordToGuess;
        this.maxGuesses = maxGuesses;
        this.length = wordToGuess.length();
        this.language = language;
        this.currentGuess = 0;
        this.currentLetter = -1;
        this.words = new AbstractWord[this.maxGuesses];
        initializeEmptyWords();
    }

    public GameState(AbstractWord wordToGuess, int maxGuesses) {
        this(wordToGuess, maxGuesses, Language.ENGLISH);
    }

    public GameState(AbstractWord wordToGuess, AbstractWord[] words, Language language) {
        this.wordToGuess = wordToGuess;
        this.words = words;
        this.length = wordToGuess.length();
        this.maxGuesses = words.length;
        this.language = language;
        initializeCurrentGuess();
        initializeCurrentLetter();
    }

    public GameState(AbstractWord wordToGuess, AbstractWord[] words) {
        this(wordToGuess, words.length, Language.ENGLISH);
    }

    private void initializeEmptyWords() {
        for (int i = 0; i < this.maxGuesses; i++) {
            this.words[i] = WordFactory.createEmptyWord(this.length);
        }
    }

    private void initializeCurrentGuess() {
        this.currentGuess = 0;
        while (this.currentGuess < this.maxGuesses && !this.words[this.currentGuess].isEmpty()) {
            this.currentGuess++;
        }
    }

    private void initializeCurrentLetter() {
        this.currentLetter = -1;
        while (this.currentLetter < this.length) {
            final AbstractLetter letter = this.words[this.currentGuess].getLetter(this.currentLetter);
            if (letter.getStatus() == Status.INITIAL) {
                break;
            }
            else {
                // This might cause an error later
                this.currentLetter++;
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (AbstractWord word : words) {
            for (int i = 0; i < word.length(); i++) {
                sb.append(COLORS.get(word.getLetter(i).getStatus()));
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

    public Language getLanguage() {
        return language;
    }

    public int getCurrentGuess() {
        return currentGuess;
    }

    public AbstractLetter getCurrentLetter() {
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

    /**
     * Enters the specified character into this GameState.
     * @param character the character to enter
     */
    public void nextLetter(char character) {
        if (currentLetter < this.length - 1) {
            this.currentLetter++;
            getCurrentLetter().setCharacter(character);
        }
    }

    /**
     * Removes the current letter of this GameState.
     */
    public void previousLetter() {
        if (currentLetter >= 0) {
            getCurrentLetter().resetCharacter();
            if (currentLetter == length) {
                // this shouldn't actually be reached i should probably remove it
                this.currentLetter -= 2;
            }
            else {
                this.currentLetter--;
            }
        }
    }

    /**
     * Submits the active word in this GameState. Assumes the word is full.
     */
    public void nextWord() {
        this.currentLetter = -1;
        this.currentGuess++;
    }

    /**
     * Submits the active word of this GameState.
     */
    public void submit() {
        WordFactory.submitGuess(words[currentGuess], wordToGuess);
    }

    /**
     * Check if this GameState is finished.
     * @return true if either there are no more guesses or the current word is correct, false otherwise
     */
    public boolean finished() {
        return currentGuess + 1 == maxGuesses || words[currentGuess].isCorrect();
    }
}
