package data_access;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class APIWordGenerator extends API implements WordGenerator {
    private static final Map<Language, String> languages = new EnumMap<>(Language.class);
    static {
        languages.put(Language.ENGLISH, "en");
    }
    public APIWordGenerator() {
        super("https://random-word-api.herokuapp.com/word");
    }

    /**
     * Returns a random uppercase word with the given length.
     * @param length the length of the word
     * @param language the language of the word to generate
     * @return a random uppercase word with the given length
     */
    @Override
    public String getRandomWord(int length, Language language) {
        JSONArray responseBody = fetch(String.format("?length=%s&lang=%s", length, language));
        try {
            return responseBody.getJSONObject(0).getString("word").toUpperCase();
        } catch (JSONException ex) {
            throw new WordNotFoundException("Could not fetch word");
        }
    }
}
