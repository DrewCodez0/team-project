package data_access;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;

abstract class API {
    protected static final String CONTENT_TYPE_LABEL = "Content-Type";
    protected static final String CONTENT_TYPE_JSON = "application/json";
    protected final String baseURL;

    protected API(String baseURL) {
        this.baseURL = baseURL;
    }

    protected JSONArray fetch(String params) {
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(baseURL + params)
                .addHeader(API.CONTENT_TYPE_LABEL, API.CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();
            final String bodyString = response.body().string();
            response.close();
            if (bodyString.isEmpty()) {
                throw new WordNotFoundException("Could not fetch word");
            }
            if (bodyString.charAt(0) == '{') {
                throw new WordNotFoundException("Could not convert to JSONArray");
            }
            final JSONArray responseBody = new JSONArray(bodyString);
            if (!responseBody.isEmpty()) {
                return responseBody;
            } else {
                throw new WordNotFoundException("Empty response");
            }
        } catch (IOException | JSONException exp) {
            throw new WordNotFoundException("Could not fetch word");
        }
    }
}
