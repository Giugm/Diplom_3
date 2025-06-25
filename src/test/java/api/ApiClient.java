package api;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.Config;

import static io.restassured.RestAssured.given;

public class ApiClient {
    private static final Gson gson = new Gson();

    static {
        RestAssured.baseURI = Config.get("api.base.url");
    }

    private static RequestSpecification prepareRequest() {
        return given().header("Content-Type", "application/json");
    }

    public static Response post(String path, Object body) {
        return prepareRequest().body(gson.toJson(body)).post(path);
    }

    public static Response delete(String path, String token) {
        if (token == null || token.isEmpty()) {
            return null;
        }
        return prepareRequest().header("Authorization", token).delete(path);
    }
}
