package data_access;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class APIWordChecker2 extends AbstractAPI implements WordChecker {
    private static final Map<Language, String> LANGUAGES = new EnumMap<>(Language.class);
    private static final String JSON_WORD = "word";
    private JSONObject wordData;
    private final Map<String, Boolean> validCache;

    static {
        LANGUAGES.put(Language.ENGLISH, "en");
        LANGUAGES.put(Language.FRENCH, "fr");
        LANGUAGES.put(Language.SPANISH, "es");
        LANGUAGES.put(Language.ITALIAN, "it");
        LANGUAGES.put(Language.GERMAN, "de");
    }

    public APIWordChecker2() {
        super("https://freedictionaryapi.com/api/v1/entries/");
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
        // Not including Language in the validCache will definitely cause bugs later
        if (validCache.containsKey(word)) {
            return validCache.get(word);
        }
        final JSONArray data = fetch(String.format("%s/%s", LANGUAGES.get(language), word.toLowerCase()));
        final JSONObject tempData = data.getJSONObject(0);
        if (tempData.getJSONArray("entries").isEmpty()) {
            validCache.put(word, false);
            return false;
        }
        wordData = tempData;
        validCache.put(word, true);
        return true;
    }

    @Override
    public String getDefinition(String word, Language language) {
        if (wordData != null && wordData.getString(JSON_WORD).equals(word)) {
            return getDefinitionFromJSON(wordData);
        }
        final JSONArray data = fetch(String.format("%s/%s", LANGUAGES.get(language), word.toLowerCase()));
        final JSONObject tempData = data.getJSONObject(0);
        final String definition = getDefinitionFromJSON(tempData);
        wordData = tempData;
        return definition;
    }

    private static String getDefinitionFromJSON(JSONObject data) {
        final JSONArray entries = data.getJSONArray("entries");
        if (entries.isEmpty()) {
            throw new WordNotFoundException("Could not fetch word");
        }
        final JSONArray senses = entries.getJSONObject(0).getJSONArray("senses");
        if (senses.isEmpty()) {
            throw new WordNotFoundException("Could not get definition of word");
        }
        return senses.getJSONObject(0).getString("definition");
    }
}
