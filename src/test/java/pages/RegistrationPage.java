package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage {
    private final WebDriver driver;
    private final WebDriverWait wait; // <-- добавлено

    public RegistrationPage(WebDriver driver, WebDriverWait wait) { // <-- обновлённый конструктор
        this.driver = driver;
        this.wait = wait;
    }

    // Кнопка перехода на регистрацию
    private final By goToRegisterButton = By.xpath("//*[@href='/register']");

    private final By nameInput = By.xpath("//label[text()='Имя']/following-sibling::input");
    private final By emailInput = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordInput = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By registerButton = By.xpath("//button[contains(text(),'Зарегистрироваться')]");
    private final By errorText = By.xpath("//p[contains(text(), 'Некорректный пароль')]");
    private final By loginEmailInput = By.xpath("//label[contains(text(),'Email')]/following-sibling::input");
    private final By loginPasswordInput = By.xpath("//label[contains(text(),'Пароль')]/following-sibling::input");
    private final By loginButton = By.xpath("//button[contains(text(),'Войти')]");


    @Step("Переход на страницу регистрации")
    public void goToRegistrationForm() {
        driver.findElement(goToRegisterButton).click();
    }

    @Step("Ввод имени: {name}")
    public void enterName(String name) {
        driver.findElement(nameInput).sendKeys(name);
    }

    @Step("Ввод почты: {email}")
    public void enterEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    @Step("Ввод пароля")
    public void enterPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    @Step("Нажатие кнопки Зарегистрироваться")
    public void clickRegister() {
        driver.findElement(registerButton).click();
    }

    @Step("Получение текста ошибки")
    public String getPasswordErrorText() {
        return driver.findElement(errorText).getText();
    }

    public boolean isErrorVisible() {
        return driver.findElements(errorText).size() > 0;
    }

    @Step("Ввод email для логина: {email}")
    public void enterLoginEmail(String email) {
        driver.findElement(loginEmailInput).sendKeys(email);
    }

    @Step("Ввод пароля для логина")
    public void enterLoginPassword(String password) {

        driver.findElement(loginPasswordInput).sendKeys(password);
    }

    @Step("Нажатие кнопки 'Войти' на форме логина")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

}

