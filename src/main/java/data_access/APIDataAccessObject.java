package data_access;

import use_case.game.GameDataAccessInterface;

/**
 * DAO for game data implemented using APIs.
 */
public class APIDataAccessObject implements GameDataAccessInterface {
    private final WordGenerator wordGenerator;
    private final WordChecker wordChecker;
    private static final int maxTries = 10;

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
    public String getRandomWord(int length, String language) throws WordNotFoundException
    {
        for (int i = 0; i < maxTries; i++) {
                String word = this.wordGenerator.getRandomWord(length, language);
                if (isValidWord(word, language)) {
                    return word;
                }
        }
        throw new WordNotFoundException("Word could not be generated after " + maxTries + " tries");
    }

    /**
     * Checks if the given word is a valid word.
     * @param word the word to check
     * @return true if the word is valid, false otherwise
     */
    @Override
    public boolean isValidWord(String word, String language) {
        return this.wordChecker.isValidWord(word, language);
    }
}
