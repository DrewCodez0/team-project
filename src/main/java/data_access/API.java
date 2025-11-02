package data_access;

import java.util.Map;
import org.json.JSONObject;

abstract class API {
    private final String baseURL;

    protected API(String baseURL) {
        this.baseURL = baseURL;
    }

    abstract JSONObject fetch(Map<String, Object> params);
}
