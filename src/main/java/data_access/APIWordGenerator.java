package data_access;

import org.json.JSONObject;

import java.util.Map;

public class APIWordGenerator extends API implements WordGenerator {
    public APIWordGenerator() {
        super("https://random-word-api.herokuapp.com/word");
    }

    @Override
    protected JSONObject fetch(Map<String, String> params) {
        return new JSONObject();
    }

    /**
     * Returns a random word with the given length.
     * @param length the length of the word
     * @return a random word with the given length
     */
    @Override
    public String getRandomWord(int length, String language) {
        return "test";
    }
}
