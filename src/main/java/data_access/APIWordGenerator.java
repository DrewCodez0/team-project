package data_access;

import java.util.EnumMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

public class APIWordGenerator extends AbstractAPI implements WordGenerator {
    private static final Map<Language, String> LANGUAGES = new EnumMap<>(Language.class);

    static {
        LANGUAGES.put(Language.ENGLISH, "en");
        LANGUAGES.put(Language.SPANISH, "es");
        LANGUAGES.put(Language.ITALIAN, "it");
        LANGUAGES.put(Language.GERMAN, "ge");
        LANGUAGES.put(Language.FRENCH, "fr");
        LANGUAGES.put(Language.PORTUGUESE, "pt-br");
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
        final JSONArray responseBody = fetch(String.format("?length=%s&lang=%s", length, LANGUAGES.get(language)));
        try {
            // return responseBody.getJSONObject(0).getString("word").toUpperCase();
            return responseBody.getString(0).toUpperCase();
        }
        catch (JSONException ex) {
            throw new WordNotFoundException("Could not fetch word");
        }
    }
}
