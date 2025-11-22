package data_access;

public interface WordGenerator {
    /**
     * Returns a random uppercase word with the given length.
     * @param length the length of the word
     * @param language the language of the word
     * @return a random uppercase word with the given length
     */
    String getRandomWord(int length, Language language);
}
