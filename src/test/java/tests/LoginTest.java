package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.qameta.allure.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(AllureJunit5.class)
@Epic("Stellar Burgers")
@Feature("Авторизация")
public class LoginTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private final String baseUrl = "https://stellarburgers.nomoreparties.site";

    // Каждый тест будет использовать одного и того же пользователя
    private final String email = generateEmail();
    private final String password = "test123";
    private final String name = "TestUser";

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1) Регистрируем нового пользователя
        driver.get(baseUrl + "/register");
        waitAndSendKeys(By.xpath("//label[text()='Имя']/following-sibling::input"), name);
        waitAndSendKeys(By.xpath("//label[text()='Email']/following-sibling::input"), email);
        waitAndSendKeys(By.xpath("//label[text()='Пароль']/following-sibling::input"), password);
        waitAndClick(By.xpath("//button[text()='Зарегистрироваться']"));

        // 2) Переходим на login и выходим, чтобы очистить сессию
        wait.until(ExpectedConditions.urlContains("/login"));
        waitAndSendKeys(By.xpath("//label[text()='Email']/following-sibling::input"), email);
        waitAndSendKeys(By.xpath("//label[text()='Пароль']/following-sibling::input"), password);
        waitAndClick(By.xpath("//button[contains(text(),'Войти')]"));

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[text()='Оформить заказ']")));
        waitAndClick(By.xpath("//*[text()='Личный Кабинет']"));
        wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[text()='Выход']")))
                .click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Вход через кнопку «Войти в аккаунт» на главной странице")
    @Severity(SeverityLevel.CRITICAL)
    void loginViaMainLoginButton() {
        driver.get(baseUrl);
        waitAndClick(By.xpath("//button[text()='Войти в аккаунт']"));
        performLoginAndAssertSuccess();
    }

    @Test
    @DisplayName("Вход через кнопку «Личный Кабинет» на главной странице")
    @Severity(SeverityLevel.CRITICAL)
    void loginViaProfileButton() {
        driver.get(baseUrl);
        waitAndClick(By.xpath("//*[text()='Личный Кабинет']"));
        performLoginAndAssertSuccess();
    }

    @Test
    @DisplayName("Вход через ссылку «Войти» на странице регистрации")
    @Severity(SeverityLevel.CRITICAL)
    void loginViaRegisterPageLink() {
        driver.get(baseUrl + "/register");
        waitAndClick(By.xpath("//*[text()='Войти']"));
        performLoginAndAssertSuccess();
    }

    @Test
    @DisplayName("Вход через ссылку «Войти» на странице восстановления пароля")
    @Severity(SeverityLevel.CRITICAL)
    void loginViaForgotPasswordLink() {
        driver.get(baseUrl + "/forgot-password");
        waitAndClick(By.xpath("//*[text()='Войти']"));
        performLoginAndAssertSuccess();
    }

    // Общая логика для заполнения формы и проверки успешного входа
    private void performLoginAndAssertSuccess() {
        wait.until(ExpectedConditions.urlContains("/login"));
        waitAndSendKeys(By.xpath("//label[text()='Email']/following-sibling::input"), email);
        waitAndSendKeys(By.xpath("//label[text()='Пароль']/following-sibling::input"), password);
        waitAndClick(By.xpath("//button[contains(text(),'Войти')]"));

        boolean isAuthorized = wait
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[text()='Оформить заказ']")))
                .isDisplayed();
        assertTrue(isAuthorized, "Авторизация не удалась.");
    }

    // Утилиты для надёжных кликов и ввода
    private void waitAndClick(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator))
                .click();
    }

    private void waitAndSendKeys(By locator, String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        el.clear();
        el.sendKeys(text);
    }

    private static String generateEmail() {
        return "user_" +
                UUID.randomUUID().toString().substring(0, 8) +
                "@example.com";
    }
}

