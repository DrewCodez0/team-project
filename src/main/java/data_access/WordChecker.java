package data_access;

import org.json.JSONArray;

public interface WordChecker {
    abstract boolean isValidWord(String word, String language);
    abstract JSONArray getDefinitions(String word);
}
