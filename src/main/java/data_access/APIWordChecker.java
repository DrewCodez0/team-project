package data_access;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class APIWordChecker extends AbstractAPI implements WordChecker {
    private static final Map<Language, String> LANGUAGES = new EnumMap<>(Language.class);
    private static final String JSON_WORD = "word";
    private JSONObject wordData;
    private final Map<String, Boolean> validCache;

    static {
        LANGUAGES.put(Language.ENGLISH, "en");
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
    public boolean isValidWord(String word, Language language) {
        if (validCache.containsKey(word)) {
//            System.out.println("Word found: " + word);
            return validCache.get(word);
        }
        try {
            final JSONArray data = fetch(String.format("%s/%s", LANGUAGES.get(language), word));
//            System.out.println(data);
            final JSONObject tempData = data.getJSONObject(0);
            tempData.getString(JSON_WORD); // This will throw an exception if it does not exist
            wordData = tempData;
        }
        catch (JSONException | WordNotFoundException ex) {
            if (ex.getMessage().equals("Could not convert to JSONArray")) {
                validCache.put(word, false);
                return false;
            }
            else {
                throw new WordNotFoundException("Something went wrong: " + ex.getMessage());
            }
        }
        validCache.put(word, true);
        return true;
    }

    @Override
    public JSONArray getDefinitions(String word, Language language) {
        if (wordData != null && wordData.getString(JSON_WORD).equals(word)) {
            return wordData.getJSONArray("meanings");
        }
        final JSONArray data = fetch(String.format("%s/%s", LANGUAGES.get(language), word));
        try {
            final JSONObject tempData = data.getJSONObject(0);
            // This will throw an exception if it does not exist
            tempData.getString(JSON_WORD);
            wordData = tempData;
            return tempData.getJSONArray("meanings");
        }
        catch (JSONException ex) {
            throw new WordNotFoundException("Could not get definition of word");
        }
    }
}
