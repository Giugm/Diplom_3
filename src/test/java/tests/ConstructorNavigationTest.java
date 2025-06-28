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
import pages.MainPage;
import pages.ProfilePage;
import utils.BaseTest;
import utils.UserGenerator;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Конструктор")
@Feature("Переход из личного кабинета в конструктор")
public class ConstructorNavigationTest extends BaseTest {

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

        // Переходим в личный кабинет для старта теста
        HeaderPage header = new HeaderPage(driver, wait);
        header.clickProfileLink();

        ProfilePage profilePage = new ProfilePage(driver, wait);
        profilePage.waitForLoad();
    }

    @AfterEach
    void cleanupUser() {
        UserClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по кнопке 'Конструктор'")
    void navigationToConstructorViaButtonIsSuccessful() {
        HeaderPage header = new HeaderPage(driver, wait);
        header.clickConstructorLink();

        MainPage mainPage = new MainPage(driver, wait);
        mainPage.waitForLoad();

        assertTrue(driver.getCurrentUrl().endsWith("/"), "Не произошел переход на главную страницу конструктора.");
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по логотипу")
    void navigationToConstructorViaLogoIsSuccessful() {
        HeaderPage header = new HeaderPage(driver, wait);
        header.clickLogo();

        MainPage mainPage = new MainPage(driver, wait);
        mainPage.waitForLoad();

        assertTrue(driver.getCurrentUrl().endsWith("/"), "Не произошел переход на главную страницу конструктора.");
    }
}


