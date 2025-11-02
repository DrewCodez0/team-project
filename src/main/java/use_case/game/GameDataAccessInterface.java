package use_case.game;

/**
 * DAO interface for the Game Use Case.
 */
public interface GameDataAccessInterface {
    /**
     * Returns a random word with the given length.
     * @param length the length of the word
     * @return a random word with the given length
     */
    String getRandomWord(int length);

    /**
     * Checks if the given word is a valid word.
     * @param word the word to check
     * @return true if the word is valid, false otherwise
     */
    boolean isValidWord(String word);
}
