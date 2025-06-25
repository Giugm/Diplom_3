package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final String url = "/register";

    private final By nameInput = By.xpath("//label[text()='Имя']/following-sibling::input");
    private final By emailInput = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordInput = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By passwordErrorText = By.xpath("//p[text()='Некорректный пароль']");
    private final By loginLink = By.xpath("//a[@href='/login']");

    public RegistrationPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    @Step("Открыть страницу регистрации")
    public void open(){
        driver.get(utils.Config.get("base.url") + url);
    }

    @Step("Клик по ссылке 'Войти'")
    public void clickLoginLink(){
        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
    }

    @Step("Заполнить поле 'Имя': {name}")
    public void setName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput)).sendKeys(name);
    }

    @Step("Заполнить поле 'Email': {email}")
    public void setEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    @Step("Заполнить поле 'Пароль'")
    public void setPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    @Step("Нажать кнопку 'Зарегистрироваться'")
    public void clickRegisterButton() {
        driver.findElement(registerButton).click();
    }

    @Step("Проверить видимость ошибки о некорректном пароле")
    public boolean isPasswordErrorVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(passwordErrorText));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}