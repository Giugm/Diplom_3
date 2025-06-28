package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HeaderPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By profileLink = By.xpath("//a[@href='/account']");
    private final By constructorLink = By.xpath("//a[@href='/']");
    private final By logoLink = By.className("AppHeader_header__logo__2D0X2");

    public HeaderPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    @Step("Клик по ссылке 'Личный Кабинет' в хэдере")
    public void clickProfileLink() {
        wait.until(ExpectedConditions.elementToBeClickable(profileLink)).click();
    }

    @Step("Клик по ссылке 'Конструктор' в хэдере")
    public void clickConstructorLink() {
        wait.until(ExpectedConditions.elementToBeClickable(constructorLink)).click();
    }

    @Step("Клик по логотипу Stellar Burgers")
    public void clickLogo() {
        wait.until(ExpectedConditions.elementToBeClickable(logoLink)).click();
    }
}