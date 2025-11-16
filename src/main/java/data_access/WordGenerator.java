package data_access;

public interface WordGenerator {
    /**
     * Returns a random uppercase word with the given length.
     * @param length the length of the word
     * @return a random uppercase word with the given length
     */
    abstract String getRandomWord(int length, Language language);
}
