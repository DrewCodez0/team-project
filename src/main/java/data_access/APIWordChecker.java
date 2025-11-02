package data_access;

import org.json.JSONObject;

import java.util.Map;

public class APIWordChecker extends API implements WordChecker {
    public APIWordChecker() {
        super(" https://api.dictionaryapi.dev/api/v2/entries/en/")
    }

    @Override
    private JSONObject fetch(Map<String, Object> params) {

    }

    /**
     * Checks if the given word is a valid word.
     * @param word the word to check
     * @return true if the word is valid, false otherwise
     */
    @Override
    public boolean isValidWord(String word) {

    }
}
