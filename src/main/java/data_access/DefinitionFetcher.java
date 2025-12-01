package data_access;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Fetches word definitions from the Free Dictionary API.
 */
public class DefinitionFetcher {
    private static final String DICT_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/";
    private final OkHttpClient client;

    public DefinitionFetcher() {
        this.client = new OkHttpClient();
    }

    /**
     * Gets the definition of a word.
     * @param word the target word
     * @return the definition, or an error message if not found
     */
    public String getDefinition(String word) {
        try {
            Request request = new Request.Builder().url(DICT_URL + word.toLowerCase()).build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful() || response.body() == null) {
                    return "Definition not found";
                }

                String responseBody = response.body().string();
                return parseDefinition(responseBody);
            }
        }

        catch (IOException e) {
            return "Error getting definition";
        }
    }

    private String parseDefinition(String jsonString) {
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            if  (jsonArray.isEmpty()) {
                return "No definitions found";
            }

            JSONObject firstDefinition = jsonArray.getJSONObject(0);
            JSONArray meanings = firstDefinition.getJSONArray("meanings");

            if  (meanings.isEmpty()) {
                return "No definitions found";
            }

            JSONObject firstMeaning = meanings.getJSONObject(0);
            String partOfSpeech = firstMeaning.optString("partOfSpeech", "");
            JSONArray definitions = firstMeaning.getJSONArray("definitions");

            if  (definitions.isEmpty()) {
                return "No definitions found";
            }

            String definition = definitions.getJSONObject(0).getString("definition");

            if (!partOfSpeech.isEmpty()) {
                return "(" + partOfSpeech + ") " + definition;
            }
            return definition;
        }
        catch (Exception e) {
            return "Error parsing definition";
        }
    }
}
