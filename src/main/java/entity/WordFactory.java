package entity;

import java.util.Arrays;

public final class WordFactory {
    private static final String DEFAULT_WORD = "ERROR";

    private WordFactory() {
    }

    /**
     * Initialize a new AbstractWord.
     * @param word the string used to create the word
     * @return an AbstractWord representing the given string, with all letters set to initial status
     */
    public static AbstractWord createWord(String word) {
        return new Word(word);
    }

    /**
     * Initialize an empty AbstractWord of the specified length.
     * @param length the length of the empty word
     * @return an empty AbstractWord with the specified length
     */
    public static AbstractWord createEmptyWord(int length) {
        return new Word(length);
    }

    /**
     * Initialize an AbstractWord with the default value.
     * @return an AbstractWord with the default value
     */
    public static AbstractWord createDefaultWord() {
        return createWord(DEFAULT_WORD);
    }

    /**
     * Create an AbstractWord consisting of correct letters.
     * @param word the string used to create the word
     * @return the AbstractWord representing the given string, with all letters set to correct status
     */
    public static AbstractWord createWordToGuess(String word) {
        final AbstractWord abstractWord = createWord(word);
        final Status[] statuses = generateCorrectArray(abstractWord.length());
        updateWord(abstractWord, statuses);
        return abstractWord;
    }

    /**
     * Update an AbstractWord with an array of statuses.
     * @param word the AbstractWord to updated
     * @param statuses the statuses to update the word with
     * @throws IndexOutOfBoundsException if the length of statuses is less than the length of the word
     */
    public static void updateWord(AbstractWord word, Status[] statuses) throws IndexOutOfBoundsException {
        for (int i = 0; i < word.length(); i++) {
            word.getLetter(i).setStatus(statuses[i]);
        }
    }

    /**
     * Generate an array consisting only of correct statuses.
     * @param length the length of the array to generate
     * @return an array of correct statuses
     */
    private static Status[] generateCorrectArray(int length) {
        final Status[] statuses = new Status[length];
        Arrays.fill(statuses, Status.CORRECT);
        return statuses;
    }

    /**
     * Check a guessed word against an expected word. Both words should have the same length.
     * @param guess the guessed word
     * @param expected the expected word
     * @return an array of Statuses corresponding to the status of each letter of the active word
     */
    public static Status[] checkGuess(AbstractWord guess, AbstractWord expected) {
        final Status[] statuses = new Status[guess.length()];
        for (int i = 0; i < guess.length(); i++) {
            final char guessChar = guess.getLetter(i).getCharacter();
            final char expectedChar = expected.getLetter(i).getCharacter();
            if (guessChar == expectedChar) {
                statuses[i] = Status.CORRECT;
            }
            else if (expected.toString().indexOf(guessChar) != -1) {
                statuses[i] = Status.PARTIAL;
            }
            else {
                statuses[i] = Status.WRONG;
            }
        }
        return statuses;
    }

    /**
     * Check the correctness of a guess against an expected word and update the status of the guess.
     * @param guess the guessed word
     * @param expected the expected word
     */
    public static void submitGuess(AbstractWord guess, AbstractWord expected) {
        updateWord(guess, checkGuess(guess, expected));
    }
}
