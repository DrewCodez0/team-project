package data_access;

import java.util.regex.Pattern;

/**
 * Debug version of a WordChecker that returns basic results without using an API.
 */
public class DebugWordChecker implements WordChecker {
    /**
     * Debug variant of isValidWord.
     * @param word the word to check
     * @param language the language of the word (unused)
     * @return true if the word only contains the letter A, false otherwise
     */
    @Override
    public boolean isValidWord(String word, Language language) {
        return Pattern.matches("^[aA]+$", word);
    }

    /**
     * Debug variant of getDefinition.
     * @param word the word to get the definition of (unused)
     * @param language the language of the word (unused)
     * @return the first sentence of Lorem Ipsum
     */
    @Override
    public String getDefinition(String word, Language language) {
        return "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod "
                + "tempor incididunt ut labore et dolore magna aliqua.";
    }
}
