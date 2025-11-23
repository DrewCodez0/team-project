package data_access;

import java.util.EnumMap;
import java.util.Map;

import org.json.JSONArray;

public class APIWordGenerator2 extends AbstractAPI implements WordGenerator {
    private static final Map<Language, String> LANGUAGES = new EnumMap<>(Language.class);

    static {
        LANGUAGES.put(Language.ENGLISH, "english");
    }

    public APIWordGenerator2() {
        super("https://random-word-api.vercel.app/api");
    }

    /**
     * Returns a random uppercase word with the given length.
     * @param length the length of the word
     * @param language the language of the word to generate
     * @return a random uppercase word with the given length
     */
    @Override
    public String getRandomWord(int length, Language language) {
        if (!language.equals(Language.ENGLISH)) {
            throw new IllegalArgumentException("Language not supported");
        }
        final JSONArray responseBody = fetch(String.format("?length=%s&type=uppercase", length));
        return responseBody.getString(0).toUpperCase();
    }
}
