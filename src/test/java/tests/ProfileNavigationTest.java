package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.qameta.allure.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(AllureJunit5.class)
@Epic("Stellar Burgers")
@Feature("Переход в личный кабинет")
public class ProfileNavigationTest {

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

        // Регистрация
        driver.get(baseUrl + "/register");
        waitAndSendKeys(By.xpath("//label[text()='Имя']/following-sibling::input"), name);
        waitAndSendKeys(By.xpath("//label[text()='Email']/following-sibling::input"), email);
        waitAndSendKeys(By.xpath("//label[text()='Пароль']/following-sibling::input"), password);
        waitAndClick(By.xpath("//button[text()='Зарегистрироваться']"));

        // Авторизация
        wait.until(ExpectedConditions.urlContains("/login"));
        waitAndSendKeys(By.xpath("//label[text()='Email']/following-sibling::input"), email);
        waitAndSendKeys(By.xpath("//label[text()='Пароль']/following-sibling::input"), password);
        waitAndClick(By.xpath("//button[contains(text(),'Войти')]"));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Переход в личный кабинет")
    @Severity(SeverityLevel.NORMAL)
    void goToProfile() {
        waitAndClick(By.xpath("//*[text()='Личный Кабинет']"));

        boolean isLogoutButtonVisible = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Выход']")))
                .isDisplayed();

        assertTrue(isLogoutButtonVisible, "Не открылся личный кабинет.");
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
