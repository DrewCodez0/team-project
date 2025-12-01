package data_access;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

abstract class AbstractAPI {
    protected static final String CONTENT_TYPE_LABEL = "Content-Type";
    protected static final String CONTENT_TYPE_JSON = "application/json";
    private final String baseURL;

    protected AbstractAPI(String baseURL) {
        this.baseURL = baseURL;
    }

    protected JSONArray fetch(String params) {
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(baseURL + params)
                .addHeader(AbstractAPI.CONTENT_TYPE_LABEL, AbstractAPI.CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();
            final String bodyString = response.body().string();
            response.close();
            if (bodyString.isEmpty()) {
                throw new WordNotFoundException("Could not fetch word");
            }
            if (bodyString.charAt(0) == '{') {
                return new JSONArray("[" + bodyString + "]");
            }
            final JSONArray responseBody = new JSONArray(bodyString);
            if (!responseBody.isEmpty()) {
                return responseBody;
            }
            else {
                throw new WordNotFoundException("Empty response");
            }
        }
        catch (IOException | JSONException exp) {
            throw new WordNotFoundException("Could not fetch word");
        }
    }
}
