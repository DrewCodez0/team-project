package interface_adapter.game;

import data_access.Language;
import entity.*;

import java.util.Map;
import data_access.SettingsStore;
import interface_adapter.options.OptionsState;
import entity.Theme;

public class GameState {
    private static final Map<Status, String> COLORS = Map.ofEntries(
            Map.entry(Status.INITIAL, "⬛"),
            Map.entry(Status.IN_PROGRESS, "⬛"),
            Map.entry(Status.WRONG, "⬛"),
            Map.entry(Status.PARTIAL, "\uD83D\uDFE8"),
            Map.entry(Status.CORRECT, "\uD83D\uDFE9")
    );
    private int length;
    private int maxGuesses;
    private Language language;
    private int currentGuess;
    private int currentLetter;
    private final AbstractWord[] words;
    private AbstractWord wordToGuess;
    private Theme theme;

    public GameState() {
        OptionsState s = SettingsStore.get();
        this.length = s.getLength();
        this.maxGuesses = s.getMaxGuesses();
        this.language = s.getLanguage();
        this.theme = s.getTheme();
        this.currentGuess = 0;
        this.currentLetter = -1;
        this.words = new AbstractWord[this.maxGuesses];
        this.wordToGuess = new Word("ERROR");
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
            this.words[i] = new Word(this.length);
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

    public Language getLanguage() {
        return language;
    }

    public int getCurrentGuess() {
        return currentGuess;
    }
    public Theme getTheme() {
        return theme;
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

    public void setLength(int length) {
        this.length = length;
        for (int i = 0; i < maxGuesses; i++) {
            words[i] = new Word(this.length);
        }
    }
    public void setMaxGuesses(int max) {
        this.maxGuesses = max;
        for (int i = 0; i < words.length; i++) {
            words[i] = new Word(this.length);
        }
    }
    public void setLanguage(Language lang) { this.language = lang; }
    public void setTheme(Theme theme) { this.theme = theme; }

    public void nextLetter(char character) {
        if (currentLetter < this.length - 1) {
            this.currentLetter++;
            getCurrentLetter().setCharacter(character);
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
    public void nextWord() {
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
        if (words[currentGuess].isCorrect()) {
            System.out.println("correct");
        }
//        nextWord();
    }

    public boolean finished() {
        return currentGuess == maxGuesses || words[currentGuess].isCorrect();
    }

    public void setWordToGuess(String newWord) {
        wordToGuess = new Word(newWord);
    }

    public void resetGame() {
        resetWords();
    }

    public void resetWords() {
        for (int i = 0; i < maxGuesses; i++) {
            words[i] = new Word(length);
        }
        currentGuess = 0;
        currentLetter = -1;
    }
}
