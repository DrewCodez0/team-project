package data_access;

import use_case.game.GameDataAccessInterface;

/**
 * DAO for game data implemented using APIs.
 */
public class APIDataAccessObject implements GameDataAccessInterface {
    private final WordGenerator wordGenerator;
    private final WordChecker wordChecker;
    private static final int MAX_TRIES = 10;

    /**
     * Construct this DAO for generating and checking words with an API.
     */
    public APIDataAccessObject(WordGenerator wordGenerator, WordChecker wordChecker) {
        this.wordGenerator = wordGenerator;
        this.wordChecker = wordChecker; // TODO this doesnt actually specify APIs i could just make it wordDAO
    }

    /**
     * Returns a random uppercase word with the given length.
     * @param length the length of the word
     * @param language the language of the word to generate
     * @return a random uppercase word with the given length
     */
    @Override
    public String getRandomWord(int length, Language language) throws WordNotFoundException
    {
        for (int i = 0; i < MAX_TRIES; i++) {
                String word = this.wordGenerator.getRandomWord(length, language);
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
        return this.wordChecker.isValidWord(word, language);
    }
}
