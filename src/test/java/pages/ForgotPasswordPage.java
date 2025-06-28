package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ForgotPasswordPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final String url = "/forgot-password";

    private final By loginLink = By.xpath("//a[@href='/login']");

    public ForgotPasswordPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    @Step("Открыть страницу восстановления пароля")
    public void open(){
        driver.get(utils.Config.get("base.url") + url);
    }

    @Step("Кликнуть по ссылке 'Войти'")
    public void clickLoginLink(){
        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
    }
}