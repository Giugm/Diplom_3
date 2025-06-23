package tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(AllureJunit5.class)
@Epic("Stellar Burgers")
@Feature("Навигация по разделам ингредиентов")
public class IngredientSectionNavigationTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private final String baseUrl = "https://stellarburgers.nomoreparties.site";

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Переход к разделу Соусы и проверка наличия Соуса Spicy-X")
    @Severity(SeverityLevel.NORMAL)
    void navigateToSaucesSection() {
        driver.get(baseUrl);
        waitAndClick(By.xpath("//*[contains (@class, 'tab_tab')]/span[contains(text(),'Соусы')]"));
        boolean sauceVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(@class,'BurgerIngredient')]/p[contains(text(),'Соус Spicy-X')]")
        )).isDisplayed();
        assertTrue(sauceVisible, "Соус Spicy-X не отображается после перехода.");
    }

    @Test
    @DisplayName("Переход к разделу Начинки и проверка наличия мяса моллюсков")
    @Severity(SeverityLevel.NORMAL)
    void navigateToFillingsSection() {
        driver.get(baseUrl);
        waitAndClick(By.xpath("//*[contains (@class, 'tab_tab')]/span[contains(text(),'Начинки')]"));
        boolean fillingVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(@class,'BurgerIngredient')]/p[contains(text(),'Мясо бессмертных моллюсков Protostomia')]")
        )).isDisplayed();
        assertTrue(fillingVisible, "Начинка 'Мясо моллюсков' не отображается после перехода.");
    }

    @Test
    @DisplayName("Переход к Соусам и обратно к Булкам")
    @Severity(SeverityLevel.NORMAL)
    void navigateToSaucesThenBackToBuns() {
        driver.get(baseUrl);

        // Переход к Соусам
        waitAndClick(By.xpath("//*[contains (@class, 'tab_tab')]/span[contains(text(),'Соусы')]"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(@class,'BurgerIngredient')]/p[contains(text(),'Соус Spicy-X')]")
        ));

        // Переход обратно к Булкам
        waitAndClick(By.xpath("//*[contains (@class, 'tab_tab')]/span[contains(text(),'Булки')]"));
        boolean bunVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(@class,'BurgerIngredient')]/p[contains(text(),'Флюоресцентная булка R2-D3')]")
        )).isDisplayed();
        assertTrue(bunVisible, "Булка R2-D3 не отображается после возврата к разделу Булки.");
    }

    // Утилита для кликов
    private void waitAndClick(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
}

