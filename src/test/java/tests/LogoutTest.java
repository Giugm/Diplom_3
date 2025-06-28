package tests;

import api.UserClient;
import api.models.User;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.HeaderPage;
import pages.LoginPage;
import pages.ProfilePage;
import utils.BaseTest;
import utils.UserGenerator;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Авторизация пользователя")
@Feature("Выход из системы")
public class LogoutTest extends BaseTest {

    private User user;
    private String accessToken;

    @BeforeEach
    void setupUserAndLogin() {
        user = UserGenerator.randomUser();
        accessToken = UserClient.registerUser(user)
                .then().extract().body().path("accessToken");

        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.open();
        loginPage.login(user.getEmail(), user.getPassword());
    }

    @AfterEach
    void cleanupUser(){
        // Пользователь уже вышел из системы, токен для удаления у нас есть
        UserClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Выход из аккаунта через кнопку 'Выход' в личном кабинете")
    void logoutViaProfileButtonIsSuccessful() {
        HeaderPage header = new HeaderPage(driver, wait);
        ProfilePage profilePage = new ProfilePage(driver, wait);
        LoginPage loginPage = new LoginPage(driver, wait);

        // Переходим в личный кабинет
        header.clickProfileLink();
        profilePage.waitForLoad();

        // Нажимаем 'Выход'
        profilePage.clickLogoutButton();

        // Проверяем, что оказались на странице логина
        loginPage.waitForLoad();
        assertTrue(driver.getCurrentUrl().endsWith("/login"), "Не произошел редирект на страницу входа после выхода.");
    }
}