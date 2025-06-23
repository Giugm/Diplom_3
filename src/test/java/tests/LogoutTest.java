package tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(AllureJunit5.class)
@Epic("Stellar Burgers")
@Feature("Выход из аккаунта")
public class LogoutTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private final String baseUrl = "https://stellarburgers.nomoreparties.site";

    private final String email = generateEmail();
    private final String password = "test123";
    private final String name = "TestUser";

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Регистрируем и логинимся
        driver.get(baseUrl + "/register");
        waitAndSendKeys(By.xpath("//label[text()='Имя']/following-sibling::input"), name);
        waitAndSendKeys(By.xpath("//label[text()='Email']/following-sibling::input"), email);
        waitAndSendKeys(By.xpath("//label[text()='Пароль']/following-sibling::input"), password);
        waitAndClick(By.xpath("//button[text()='Зарегистрироваться']"));

        // Логинимся
        wait.until(ExpectedConditions.urlContains("/login"));
        waitAndSendKeys(By.xpath("//label[text()='Email']/following-sibling::input"), email);
        waitAndSendKeys(By.xpath("//label[text()='Пароль']/following-sibling::input"), password);
        waitAndClick(By.xpath("//button[contains(text(),'Войти')]"));

        // Убедимся, что попали на главную
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[text()='Оформить заказ']")));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Выход из аккаунта через личный кабинет")
    @Severity(SeverityLevel.CRITICAL)
    void logoutFromProfile() {
        // Переходим в ЛК
        waitAndClick(By.xpath("//*[text()='Личный Кабинет']"));

        // Ждём кнопку "Выход"
        waitAndClick(By.xpath("//button[text()='Выход']"));

        // Проверка — редирект на /login
        boolean onLoginPage = wait.until(ExpectedConditions.urlContains("/login"));
        assertTrue(onLoginPage, "Не произошёл выход — пользователь остался в системе.");
    }

    // Утилиты
    private void waitAndClick(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    private void waitAndSendKeys(By locator, String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        el.clear();
        el.sendKeys(text);
    }

    private static String generateEmail() {
        return "user_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
    }
}

