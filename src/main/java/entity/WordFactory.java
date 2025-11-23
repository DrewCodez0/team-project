package entity;

import java.util.Arrays;

public final class WordFactory {
    private static String DEFAULT_WORD = "ERROR";

    private WordFactory() {
    }

    public static AbstractWord createWord(String word) {
        return new Word(word);
    }

    public static AbstractWord createEmptyWord(int length) {
        return new Word(length);
    }

    public static AbstractWord createDefaultWord() {
        return createWord(DEFAULT_WORD);
    }

    public static AbstractWord createWordToGuess(String word) {
        final AbstractWord abstractWord = createWord(word);
        final Status[] statuses = generateCorrectArray(abstractWord.length());
        updateWord(abstractWord, statuses);
        return abstractWord;
    }

    public static void updateWord(AbstractWord word, Status[] statuses) throws IndexOutOfBoundsException {
        for (int i = 0; i < word.length(); i++) {
            word.getLetter(i).setStatus(statuses[i]);
        }
    }

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

    public static void submitGuess(AbstractWord guess, AbstractWord expected) {
        updateWord(guess, checkGuess(guess, expected));
    }
}
