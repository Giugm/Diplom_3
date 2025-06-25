package tests;

import api.UserClient;
import api.models.User;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import pages.LoginPage;
import pages.RegistrationPage;
import pages.MainPage;
import utils.BaseTest;
import utils.UserGenerator;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Регистрация пользователя")
@Feature("Проверка формы регистрации")
public class RegistrationTest extends BaseTest {

    // Для этого теста не нужен @BeforeEach, т.к. мы тестируем саму регистрацию
    private String accessToken;
    private static final String SAUCES_SPAN_XPATH = "//span[text()='Соусы']";

    @AfterEach
    void cleanup() {
        // Удаляем пользователя только если он был успешно создан
        UserClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Успешная регистрация нового пользователя")
    void successfulRegistration() {
        User user = UserGenerator.randomUser();
        RegistrationPage registrationPage = new RegistrationPage(driver, wait);
        LoginPage loginPage = new LoginPage(driver, wait);

        registrationPage.open();
        registrationPage.setName(user.getName());
        registrationPage.setEmail(user.getEmail());
        registrationPage.setPassword(user.getPassword());
        registrationPage.clickRegisterButton();

        // После успешной регистрации должен быть редирект на страницу логина
        loginPage.waitForLoad();

        // Проверяем, что пользователь реально создан, логинясь с его данными
        loginPage.login(user.getEmail(), user.getPassword());

        MainPage mainPage = new MainPage(driver, wait);
        mainPage.waitForLoad();

        // Получаем токен для очистки
        //accessToken = driver.manage().getCookieNamed("accessToken").getValue();
        wait.until(driver -> driver.findElement(By.xpath(SAUCES_SPAN_XPATH)));
    }

    @Test
    @DisplayName("Ошибка при регистрации с коротким паролем (менее 6 символов)")
    void registrationWithShortPasswordFails() {
        RegistrationPage registrationPage = new RegistrationPage(driver, wait);
        registrationPage.open();

        registrationPage.setName(UserGenerator.randomName());
        registrationPage.setEmail(UserGenerator.randomEmail());
        registrationPage.setPassword("12345"); // Короткий пароль
        registrationPage.clickRegisterButton();

        assertTrue(registrationPage.isPasswordErrorVisible(), "Сообщение об ошибке для короткого пароля не появилось.");
    }
}