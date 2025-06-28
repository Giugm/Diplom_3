package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final String url = "/login";
    private final By pageTitle = By.xpath("//h2[text()='Вход']");
    private final By emailInput = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordInput = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By loginButton = By.xpath("//button[text()='Войти']");
    private final By registerLink = By.xpath("//a[@href='/register']");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    @Step("Открыть страницу логина напрямую")
    public void open() {
        driver.get(utils.Config.get("base.url") + url);
    }

    @Step("Ожидание загрузки страницы логина")
    public void waitForLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
    }

    @Step("Ввод email: {email}")
    public void setEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).sendKeys(email);
    }

    @Step("Ввод пароля")
    public void setPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    @Step("Нажатие кнопки 'Войти'")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Выполнить полный логин пользователя: {email}")
    public void login(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickLoginButton();
    }

    @Step("Клик по ссылке 'Зарегистрироваться'")
    public void clickRegisterLink(){
        wait.until(ExpectedConditions.elementToBeClickable(registerLink)).click();
    }
}
