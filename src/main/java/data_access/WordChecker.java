package data_access;

import org.json.JSONArray;

public interface WordChecker {
    /**
     * Checks if the given word is a valid word. For a word to be valid it must be in the dictionary
     * used and must consist of only English characters to be typeable on a standard US keyboard.
     * @param word the word to check
     * @param language the language of the word
     * @return true if the word is valid, false otherwise
     */
    boolean isValidWord(String word, Language language);

    /**
     * Returns the definitions of the given word.
     * @param word the word to get the definition of
     * @param language the language of the word
     * @return a JSONArray containing the definitions of the word
     */
    JSONArray getDefinitions(String word, Language language);
}
