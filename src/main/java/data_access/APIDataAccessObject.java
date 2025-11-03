package data_access;

import use_case.game.GameDataAccessInterface;

/**
 * DAO for game data implemented using APIs.
 */
public class APIDataAccessObject implements GameDataAccessInterface {
    private final WordGenerator wordGenerator;
    private final WordChecker wordChecker;

    /**
     * Construct this DAO for generating and checking words with an API.
     */
    public APIDataAccessObject() {
        this.wordGenerator = new APIWordGenerator();
        this.wordChecker = new APIWordChecker();
    }

    /**
     * Returns a random word with the given length.
     * @param length the length of the word
     * @return a random word with the given length
     */
    @Override
    public String getRandomWord(int length, String language)
    {
        return this.wordGenerator.getRandomWord(length, language);
    }

    /**
     * Checks if the given word is a valid word.
     * @param word the word to check
     * @return true if the word is valid, false otherwise
     */
    @Override
    public boolean isValidWord(String word, String language)
    {
        return this.wordChecker.isValidWord(word, language);
    }
}
