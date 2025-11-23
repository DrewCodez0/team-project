package data_access;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class APIWordChecker extends API implements WordChecker {
    private JSONObject wordData;
    private final Map<String, Boolean> validCache;
    private static final Map<Language, String> languages = new EnumMap<>(Language.class);
    static {
        languages.put(Language.ENGLISH, "en");
    }

    public APIWordChecker() {
        super("https://api.dictionaryapi.dev/api/v2/entries/");
        wordData = null;
        validCache = new HashMap<>();
    }

    /**
     * Checks if the given word is a valid word. For a word to be valid it must be in the dictionary
     * used and must consist of only English characters to be typeable on a standard US keyboard.
     * @param word the word to check
     * @param language the language of the word
     * @return true if the word is valid, false otherwise
     */
    @Override
    public boolean isValidWord(String word, Language language) { // consider adding cache
        if (validCache.containsKey(word)) {
//            System.out.println("Word found: " + word);
            return validCache.get(word);
        }
        try {
            JSONArray data = fetch(String.format("%s/%s", languages.get(language), word));
//            System.out.println(data);
            JSONObject tempData = data.getJSONObject(0);
            tempData.getString("word"); // This will throw an exception if it does not exist
            wordData = tempData;
        }  catch (JSONException | WordNotFoundException e) {
            if (e.getMessage().equals("Could not convert to JSONArray")) {
                validCache.put(word, false);
                return false;
            } else {
                throw new WordNotFoundException("Something went wrong: " + e.getMessage());
            }
        }
        validCache.put(word, true);
        return true;
    }

    @Override
    public JSONArray getDefinitions(String word, Language language) {
        if (wordData != null && wordData.getString("word").equals(word)) {
            return wordData.getJSONArray("meanings");
        }
        JSONArray data = fetch(String.format("%s/%s", languages.get(language), word));
        try {
            JSONObject tempData = data.getJSONObject(0);
            tempData.getString("word"); // This will throw an exception if it does not exist
            wordData = tempData;
            return tempData.getJSONArray("meanings");
        }  catch (JSONException e) {
            throw new WordNotFoundException("Could not get definition of word");
        }
    }
}
