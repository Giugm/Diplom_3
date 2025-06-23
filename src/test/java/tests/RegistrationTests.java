package tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import pages.MainPage;
import pages.RegistrationPage;
import utils.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(AllureJunit5.class)
public class RegistrationTests extends BaseTest {

    @Test
    @DisplayName("Успешная регистрация пользователя")
    @Description("Пользователь успешно регистрируется и логинится с валидными данными")
    void successfulRegistrationTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        // Переход на форму регистрации
        RegistrationPage registrationPage = new RegistrationPage(driver, wait);
        registrationPage.goToRegistrationForm();

        // Генерация уникальных данных
        String name = "TestUser";
        String email = "test" + UUID.randomUUID() + "@mail.ru";
        String password = "123456";

        registrationPage.enterName(name);
        registrationPage.enterEmail(email);
        registrationPage.enterPassword(password);
        registrationPage.clickRegister();

        // **ИЗМЕНЕНИЕ ЗДЕСЬ:** Ждем, пока появится заголовок "Вход"
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Вход')]")));
    }

    @Test
    @DisplayName("Ошибка при регистрации с коротким паролем")
    @Description("Проверка ошибки при вводе пароля короче 6 символов")
    void shortPasswordRegistrationTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        RegistrationPage registrationPage = new RegistrationPage(driver, wait);
        registrationPage.goToRegistrationForm();

        registrationPage.enterName("ShortPass");
        registrationPage.enterEmail("shortpass@mail.ru");
        registrationPage.enterPassword("123"); // слишком короткий пароль
        registrationPage.clickRegister();

        assertTrue(registrationPage.isErrorVisible(), "Ошибка не отображается при коротком пароле");
        assertEquals("Некорректный пароль", registrationPage.getPasswordErrorText());
    }
}


