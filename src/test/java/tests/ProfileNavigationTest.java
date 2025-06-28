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

@Epic("Личный кабинет")
@Feature("Переход в личный кабинет")
public class ProfileNavigationTest extends BaseTest {

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
        UserClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Успешный переход в 'Личный кабинет' по клику в хэдере")
    void navigationToProfileIsSuccessful() {
        HeaderPage header = new HeaderPage(driver, wait);
        ProfilePage profilePage = new ProfilePage(driver, wait);

        header.clickProfileLink();
        profilePage.waitForLoad();

        assertTrue(driver.getCurrentUrl().contains("/account/profile"), "Не произошел переход в личный кабинет.");
    }
}