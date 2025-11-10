package data_access;

import java.util.Map;
import org.json.JSONObject;

abstract class API {
    protected static final String CONTENT_TYPE_LABEL = "Content-Type";
    protected static final String CONTENT_TYPE_JSON = "application/json";
    protected final String baseURL;
    // TODO add cache variable to store latest thing

    protected API(String baseURL) {
        this.baseURL = baseURL;
    }

    protected abstract JSONObject fetch(Map<String, String> params);
}
