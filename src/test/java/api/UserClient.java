package api;

import api.models.User;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class UserClient {
    private static final String REGISTER_PATH = "/auth/register";
    private static final String USER_DATA_PATH = "/auth/user";

    @Step("API: Регистрация пользователя {user.email}")
    public static Response registerUser(User user) {
        return ApiClient.post(REGISTER_PATH, user);
    }

    @Step("API: Удаление пользователя с токеном {accessToken}")
    public static Response deleteUser(String accessToken) {
        return ApiClient.delete(USER_DATA_PATH, accessToken);
    }
}

