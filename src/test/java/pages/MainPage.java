package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By orderButton = By.xpath("//button[text()='Оформить заказ']");
    private final By saucesTab = By.xpath("//span[text()='Соусы']/parent::div");
    private final By fillingsTab = By.xpath("//span[text()='Начинки']/parent::div");
    private final By bunsTab = By.xpath("//span[text()='Булки']/parent::div");
    private final String activeTabPattern = "//span[text()='%s']/parent::div[contains(@class, 'current')]";

    public MainPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    @Step("Ожидание загрузки главной страницы (по кнопке 'Оформить заказ')")
    public void waitForLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderButton));
    }

    @Step("Клик по кнопке 'Войти в аккаунт'")
    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    @Step("Переключиться на вкладку 'Соусы'")
    public void selectSaucesTab() {
        wait.until(ExpectedConditions.elementToBeClickable(saucesTab)).click();
    }

    @Step("Переключиться на вкладку 'Начинки'")
    public void selectFillingsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(fillingsTab)).click();
    }

    @Step("Переключиться на вкладку 'Булки'")
    public void selectBunsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(bunsTab)).click();
    }

    @Step("Проверить, что вкладка {tabName} активна")
    public boolean isTabActive(String tabName) {
        By activeTab = By.xpath(String.format(activeTabPattern, tabName));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(activeTab)).isDisplayed();
    }
}