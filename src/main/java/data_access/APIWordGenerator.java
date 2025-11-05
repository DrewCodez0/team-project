package data_access;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class APIWordGenerator extends API implements WordGenerator {
    public APIWordGenerator() {
        super("https://random-word-api.herokuapp.com/word");
    }

    @Override
    protected JSONObject fetch(Map<String, String> params) {
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format(this.baseURL + "?length=%s&lang=%s", params.get("length"),  params.get("lang")))
                .addHeader(API.CONTENT_TYPE_LABEL, API.CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute(); // TODO put all of this into getRandomWord
            final JSONArray responseBody = new JSONArray(response.body().string());
            if (!responseBody.isEmpty()) {
                return new JSONObject().put("word", responseBody.get(0).toString());
            } else {
                throw new IOException("Empty response");
            }

        } catch (IOException | JSONException ex) {
            System.out.println("oopsies");
        }
        return new JSONObject();
    }

    /**
     * Returns a random word with the given length.
     * @param length the length of the word
     * @return a random word with the given length
     */
    @Override
    public String getRandomWord(int length, String language) {
        Map<String, String> params = new HashMap<>();
        params.put("length", String.valueOf(length));
        params.put("lang", language);
        return (String)fetch(params).get("word");
    }
}
