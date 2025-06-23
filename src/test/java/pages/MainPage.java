package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage {
    private final WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By loginButton = By.xpath("//button[contains(text(), 'Войти в аккаунт')]");

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }
}
