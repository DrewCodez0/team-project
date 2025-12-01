package data_access;

import use_case.game.GameDataAccessInterface;

import java.util.regex.Pattern;

/**
 * DAO for game data to generate and check words.
 */
public class WordDataAccessObject implements GameDataAccessInterface {
    private static final int MAX_TRIES = 10;
    private final WordGenerator wordGenerator;
    private final WordChecker wordChecker;

    /**
     * Construct this DAO for generating and checking words.
     * @param wordGenerator the word generator to use in this DAO
     * @param wordChecker the word checker to use in this DAO
     */
    public WordDataAccessObject(WordGenerator wordGenerator, WordChecker wordChecker) {
        this.wordGenerator = wordGenerator;
        this.wordChecker = wordChecker;
    }

    /**
     * Returns a random uppercase word with the given length.
     * @param length the length of the word
     * @param language the language of the word to generate
     * @return a random uppercase word with the given length
     */
    @Override
    public String getRandomWord(int length, Language language) throws WordNotFoundException {
        for (int i = 0; i < MAX_TRIES; i++) {
            final String word = this.wordGenerator.getRandomWord(length, language);
            if (isValidWord(word, language)) {
                return word;
            }
        }
        throw new WordNotFoundException("Word could not be generated after " + MAX_TRIES + " tries");
    }

    /**
     * Checks if the given word is a valid word. For a word to be valid it must be in the dictionary
     * used and must consist of only English characters to be typeable on a standard US keyboard.
     * @param word the word to check
     * @return true if the word is valid, false otherwise
     */
    @Override
    public boolean isValidWord(String word, Language language) {
        return Pattern.matches("^[a-zA-Z]+$", word) && this.wordChecker.isValidWord(word, language);
    }
}
