package data_access;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class APIWordChecker extends API implements WordChecker {
    private JSONObject wordData;

    public APIWordChecker() {
        super("https://api.dictionaryapi.dev/api/v2/entries/en/");
        wordData = null;
    }

    @Override
    protected JSONObject fetch(Map<String, String> params) { // if type is jsonarray turn it into object
        return new JSONObject();
    }

    /**
     * Checks if the given word is a valid word.
     * @param word the word to check
     * @return true if the word is valid, false otherwise
     */
    @Override
    public boolean isValidWord(String word, String language) { // consider adding cache
        Map<String, String> params =  new HashMap<>();
        params.put("language", language); // Convert language to api string first
        params.put("word", word);
        JSONObject data = fetch(params);
        try {
            data.getString("word"); // This will throw an exception if it does not exist
            wordData = data;
        }  catch (JSONException e) {
            return false;
        }
        return true;
    }

    @Override
    public JSONArray getDefinitions(String word) {
        return wordData.getJSONArray("meanings");
    }
}
