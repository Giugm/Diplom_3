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
@Feature("Переход из профиля в конструктор")
public class ConstructorNavigationTest {

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

        // Регистрация и логин
        driver.get(baseUrl + "/register");
        waitAndSendKeys(By.xpath("//label[text()='Имя']/following-sibling::input"), name);
        waitAndSendKeys(By.xpath("//label[text()='Email']/following-sibling::input"), email);
        waitAndSendKeys(By.xpath("//label[text()='Пароль']/following-sibling::input"), password);
        waitAndClick(By.xpath("//button[text()='Зарегистрироваться']"));

        wait.until(ExpectedConditions.urlContains("/login"));
        waitAndSendKeys(By.xpath("//label[text()='Email']/following-sibling::input"), email);
        waitAndSendKeys(By.xpath("//label[text()='Пароль']/following-sibling::input"), password);
        waitAndClick(By.xpath("//button[contains(text(),'Войти')]"));

        // Заходим в личный кабинет
        waitAndClick(By.xpath("//*[text()='Личный Кабинет']"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Выход']")));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Переход в конструктор по кнопке «Конструктор»")
    @Severity(SeverityLevel.NORMAL)
    void goToConstructorViaButton() {
        waitAndClick(By.xpath("//p[text()='Конструктор']"));

        boolean isOrderButtonVisible = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Оформить заказ']")))
                .isDisplayed();

        assertTrue(isOrderButtonVisible, "Не перешли в конструктор по кнопке.");
    }

    @Test
    @DisplayName("Переход в конструктор по логотипу Stellar Burgers")
    @Severity(SeverityLevel.NORMAL)
    void goToConstructorViaLogo() {
        waitAndClick(By.xpath("//div[@class='AppHeader_header__logo__2D0X2']/a"));

        boolean isOrderButtonVisible = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Оформить заказ']")))
                .isDisplayed();

        assertTrue(isOrderButtonVisible, "Не перешли в конструктор по логотипу.");
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

