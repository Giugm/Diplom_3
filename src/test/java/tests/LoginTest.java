package tests;

import api.UserClient;
import api.models.User;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.*;
import utils.BaseTest;
import utils.UserGenerator;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Авторизация пользователя")
@Feature("Вход в систему через различные точки входа")
public class LoginTest extends BaseTest {

    private User user;
    private String accessToken;

    @BeforeEach
    void setupUser() {
        user = UserGenerator.randomUser();
        accessToken = UserClient.registerUser(user)
                .then().extract().body().path("accessToken");
    }

    @AfterEach
    void cleanupUser() {
        UserClient.deleteUser(accessToken);
    }

    private void assertLoginSuccess() {
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.waitForLoad();
        assertTrue(driver.getCurrentUrl().endsWith("/"), "Не произошел редирект на главную страницу после логина.");
    }

    @Test
    @DisplayName("Вход через кнопку 'Войти в аккаунт' на главной")
    void loginViaMainPageButton() {
        driver.get(utils.Config.get("base.url"));
        MainPage mainPage = new MainPage(driver, wait);
        LoginPage loginPage = new LoginPage(driver, wait);

        mainPage.clickLoginButton();
        loginPage.waitForLoad();
        loginPage.login(user.getEmail(), user.getPassword());

        assertLoginSuccess();
    }

    @Test
    @DisplayName("Вход через 'Личный Кабинет' в хэдере")
    void loginViaHeaderProfileLink() {
        driver.get(utils.Config.get("base.url"));
        HeaderPage header = new HeaderPage(driver, wait);
        LoginPage loginPage = new LoginPage(driver, wait);

        header.clickProfileLink();
        loginPage.waitForLoad();
        loginPage.login(user.getEmail(), user.getPassword());

        assertLoginSuccess();
    }

    @Test
    @DisplayName("Вход со страницы регистрации")
    void loginViaRegistrationPageLink() {
        RegistrationPage registrationPage = new RegistrationPage(driver, wait);
        LoginPage loginPage = new LoginPage(driver, wait);

        registrationPage.open();
        registrationPage.clickLoginLink();

        loginPage.waitForLoad();
        loginPage.login(user.getEmail(), user.getPassword());

        assertLoginSuccess();
    }

    @Test
    @DisplayName("Вход со страницы восстановления пароля")
    void loginViaForgotPasswordPageLink() {
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver, wait);
        LoginPage loginPage = new LoginPage(driver, wait);

        forgotPasswordPage.open();
        forgotPasswordPage.clickLoginLink();

        loginPage.waitForLoad();
        loginPage.login(user.getEmail(), user.getPassword());

        assertLoginSuccess();
    }
}


